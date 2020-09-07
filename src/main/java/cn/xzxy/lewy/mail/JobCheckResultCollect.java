package cn.xzxy.lewy.mail;

import java.util.Date;

public class JobCheckResultCollect {
    private String collectId;

    private String checkId;

    private String orgCode;

    private String businessDate;

    private Integer totalIndex;

    private Integer warnIndex;

    private Date createTime;

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId == null ? null : collectId.trim();
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId == null ? null : checkId.trim();
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public String getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate == null ? null : businessDate.trim();
    }

    public Integer getTotalIndex() {
        return totalIndex;
    }

    public void setTotalIndex(Integer totalIndex) {
        this.totalIndex = totalIndex;
    }

    public Integer getWarnIndex() {
        return warnIndex;
    }

    public void setWarnIndex(Integer warnIndex) {
        this.warnIndex = warnIndex;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public JobCheckResultCollect(String collectId, String checkId, String orgCode, String businessDate, Integer totalIndex, Integer warnIndex, Date createTime) {
        this.collectId = collectId;
        this.checkId = checkId;
        this.orgCode = orgCode;
        this.businessDate = businessDate;
        this.totalIndex = totalIndex;
        this.warnIndex = warnIndex;
        this.createTime = createTime;
    }
}