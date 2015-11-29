package com.libmanagement.entity;




import com.libmanagement.common.entity.EntityUUID;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 系统参数数据库持久化Bean
 * @author FlareMars
 *
 */
@Entity
@Table(name = "lms_config_param")
public class ConfigParam extends EntityUUID {


	private String paramName;
	private String paramValue;

	public String getParamName() {

		return paramName;
	}

	public void setParamName(String paramName) {

		this.paramName = paramName;
	}

	public String getParamValue() {
	
		return paramValue;
	}
	
	public void setParamValue(String paramValue) {
	
		this.paramValue = paramValue;
	}

}