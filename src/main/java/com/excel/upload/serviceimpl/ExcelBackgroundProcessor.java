package com.excel.upload.serviceimpl;

import com.excel.upload.model.ExcelLead;
import com.excel.upload.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ExcelBackgroundProcessor {

    @Autowired
    private ExcelService excelService;

    @Async
    public void startBackgroundUpload(MultipartFile file, long taskId) {
        excelService.processExcelInBackground(file, taskId);
    }
}
