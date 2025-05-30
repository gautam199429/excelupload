package com.excel.upload.repo;

import com.excel.upload.model.RawLeadData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RawLeadDataRepo extends JpaRepository<RawLeadData, Long> {
}
