package com.excel.upload.model;

import com.excel.upload.enums.Feedback1;
import com.excel.upload.enums.IsActive;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;

@Entity
public class RawLeadData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long leadId;

    @Column(nullable = false)
    private String leadName;

    @Column(name = "lead_mobile", nullable = false)
    private String leadMobile;

    @Column(columnDefinition = "text")
    private String remark;

    private String project = "N/A";

    private String projectCategory = "N/A";

    private Feedback1 status;

    private String status2;

    private int transferToAssociate;

    private int transferBy;

    @Column(name = "createddate", nullable = false, updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedDate", nullable = true)
    private Date updatedDate;

    private String updatedBy;

    private IsActive isExists = IsActive.NO;

    private IsActive isTransferred = IsActive.YES;

    public long getLeadId() {
        return leadId;
    }

    public void setLeadId(long leadId) {
        this.leadId = leadId;
    }

    public String getLeadName() {
        return leadName;
    }

    public void setLeadName(String leadName) {
        this.leadName = leadName;
    }

    public String getLeadMobile() {
        return leadMobile;
    }

    public void setLeadMobile(String leadMobile) {
        this.leadMobile = leadMobile;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
    }

    public Feedback1 getStatus() {
        return status;
    }

    public void setStatus(Feedback1 status) {
        this.status = status;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    public int getTransferToAssociate() {
        return transferToAssociate;
    }

    public void setTransferToAssociate(int transferToAssociate) {
        this.transferToAssociate = transferToAssociate;
    }

    public int getTransferBy() {
        return transferBy;
    }

    public void setTransferBy(int transferBy) {
        this.transferBy = transferBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public IsActive getIsExists() {
        return isExists;
    }

    public void setIsExists(IsActive isExists) {
        this.isExists = isExists;
    }

    public IsActive getIsTransferred() {
        return isTransferred;
    }

    public void setIsTransferred(IsActive isTransferred) {
        this.isTransferred = isTransferred;
    }

    public RawLeadData(String leadName, String leadMobile, int transferToAssociate) {
        this.leadName = leadName;
        this.leadMobile = leadMobile;
        this.transferToAssociate = transferToAssociate;
        this.project = "N/A";
        this.projectCategory = "NA";
    }

    public RawLeadData(){

    }
}
