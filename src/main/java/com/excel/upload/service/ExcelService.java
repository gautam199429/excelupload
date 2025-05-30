package com.excel.upload.service;

import com.excel.upload.model.ExcelLead;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExcelService {

    void processExcelInBackground(MultipartFile file, long taskId);

    long createTaskAndQueueExcelUpload(MultipartFile file);

    void insertIntoMainTable(long taskId);
}
