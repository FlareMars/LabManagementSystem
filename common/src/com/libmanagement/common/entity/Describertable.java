package com.libmanagement.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.libmanagement.common.utils.DateUtils;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Describertable extends EntityUUID {


    private Long createTime = System.currentTimeMillis();
    private Long updateTime = System.currentTimeMillis();

    @JsonIgnore
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    @JsonIgnore
    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public void setCreateTimeStr(String createTimeStr){
        try {
            createTime = DateUtils.getTimeStamp(createTimeStr);
        }catch (Exception e){
            createTime = null;
        }
    }

    public String getCreateTimeStr(){
        try {
            return DateUtils.getDateStr(createTime.longValue());
        }catch (Exception e){
            return "";
        }
    }

    public void setUpdateTimeStr(String updateTimeStr){
        try {
            updateTime = DateUtils.getTimeStamp(updateTimeStr);
        }catch (Exception e){
            this.updateTime = null;
        }
    }

    public String getUpdateTimeStr(){
        try {
            return DateUtils.getDateStr(updateTime.longValue());
        }catch (Exception e){
            return "";
        }
    }


}
