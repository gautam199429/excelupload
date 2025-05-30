package com.excel.upload.controller;

import com.excel.upload.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/excel")
public class ExcelUploadController {

    @Autowired
    private ExcelService excelService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file) {
        long taskId = excelService.createTaskAndQueueExcelUpload(file);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Task created successfully. Excel is being processed in background.");
        response.put("taskId", taskId);
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/process/{taskId}")
//    public ResponseEntity<String> processTask(@PathVariable long taskId) {
//        excelService.processTask(taskId);
//        return ResponseEntity.ok("Processing started for task ID: " + taskId);
//    }
}

