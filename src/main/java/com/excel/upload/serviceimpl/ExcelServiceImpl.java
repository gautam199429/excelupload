package com.excel.upload.serviceimpl;

import com.excel.upload.enums.RecordStatus;
import com.excel.upload.enums.TaskStages;
import com.excel.upload.model.ExcelLead;
import com.excel.upload.model.RawLeadData;
import com.excel.upload.model.TaskMaster;
import com.excel.upload.repo.ExcelLeadsRepo;
import com.excel.upload.repo.RawLeadDataRepo;
import com.excel.upload.repo.TaskRepository;
import com.excel.upload.service.ExcelService;
import com.google.gson.Gson;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private ExcelLeadsRepo leadRepo;

    @Autowired
    private ExcelBackgroundProcessor processor;

    @Autowired
    private RawLeadDataRepo rawLeadDataRepo;

    @Override
    public long createTaskAndQueueExcelUpload(MultipartFile file) {
        TaskMaster task = new TaskMaster();
        long taskId = System.currentTimeMillis();
        task.setTaskId(taskId);
        task.setTaskStages(TaskStages.IDEL);
        task.setCreatedDate(new Date());
        taskRepo.save(task);
        task.setTaskStages(TaskStages.UPLOADING_EXCEL);
        taskRepo.save(task);
        processor.startBackgroundUpload(file, taskId);  // async now
        return taskId;
    }

    @Override
    @Transactional
    public void insertIntoMainTable(long taskId) {
        try {
            List<ExcelLead> leads = leadRepo.findByTaskIdAndRecordStatus(taskId,RecordStatus.GOOD);
            for(ExcelLead singel : leads){
                rawLeadDataRepo.save(new RawLeadData(singel.getName(), singel.getMobile(), singel.getAssociateId()));
                singel.setUploadRemark("Successfully uploaded to table");
                leadRepo.save(singel);
            }
            TaskMaster task = taskRepo.findById(taskId).orElseThrow();
            task.setTaskStages(TaskStages.COMPLETED);
            taskRepo.save(task);
        }
        catch (Exception e) {
            TaskMaster task = taskRepo.findById(taskId).orElseThrow();
            task.setTaskStages(TaskStages.ERROR);
            task.setErrorMessage(e.getMessage());
            taskRepo.save(task);
            throw new RuntimeException("Failed to process Excel file", e);
        }
    }

    @Override
    @Transactional
    public void processExcelInBackground(MultipartFile file, long taskId) {
        try (InputStream is = file.getInputStream();
             XSSFWorkbook workbook = new XSSFWorkbook(is)) {

            XSSFSheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                ExcelLead lead = new ExcelLead();
                lead.setId(System.nanoTime());
                lead.setTaskId(taskId);

                StringBuilder errorMsg = new StringBuilder();

                // Name
                try {
                    String name = row.getCell(0).getStringCellValue().trim();
                    lead.setName(name);
                } catch (Exception e) {
                    errorMsg.append("Invalid name; ");
                }

                // Mobile
                try {
                    Cell cell = row.getCell(1);
                    String rawMobile;
                    if (cell.getCellType() == CellType.NUMERIC) {
                        rawMobile = BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();
                    } else {
                        rawMobile = cell.getStringCellValue().trim();
                    }
                    String digitsOnly = rawMobile.replaceAll("\\D", "");
                    if (digitsOnly.length() >= 10) {
                        lead.setMobile(digitsOnly.substring(digitsOnly.length() - 10));
                    } else {
                        errorMsg.append("Invalid mobile (must be 10 digits); ");
                    }

                } catch (Exception e) {
                    errorMsg.append("Invalid mobile; ");
                }

                // Associate ID
                try {
                    Cell cell = row.getCell(2);
                    if (cell.getCellType() == CellType.NUMERIC) {
                        lead.setAssociateId((int) cell.getNumericCellValue());
                    } else {
                        lead.setAssociateId(Integer.parseInt(cell.getStringCellValue().trim()));
                    }
                } catch (Exception e) {
                    errorMsg.append("Invalid associateId; ");
                }

                if (!errorMsg.isEmpty()) {
                    lead.setUploadRemark("Error: " + errorMsg);
                    lead.setRecordStatus(RecordStatus.EXCEL_ERROR);
                } else {
                    lead.setUploadRemark("Uploaded");
                    lead.setRecordStatus(RecordStatus.GOOD);
                }
                leadRepo.save(lead);
            }
            TaskMaster task = taskRepo.findById(taskId).orElseThrow();
            task.setTaskStages(TaskStages.INPROCESS);
            taskRepo.save(task);
            insertIntoMainTable(taskId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process Excel file", e);
        }
    }
}
