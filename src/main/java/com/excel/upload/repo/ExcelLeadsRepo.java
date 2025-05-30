package com.excel.upload.repo;


import com.excel.upload.enums.RecordStatus;
import com.excel.upload.model.ExcelLead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExcelLeadsRepo extends JpaRepository<ExcelLead, Long> {

    List<ExcelLead> findByTaskIdAndRecordStatus(long taskId,RecordStatus good);
}
