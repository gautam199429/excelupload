package com.excel.upload.repo;

import com.excel.upload.model.TaskMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskMaster , Long> {
}
