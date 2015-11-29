package com.libmanagement.admin.common;

import java.util.HashMap;


/**
 * Rest Api返回结果
 * @author caochun
 *
 */

public class Result extends HashMap {
	
	private static final String STATUS_CODE = "statusCode";
	private static final String STATUS_MESSAGE = "message";
//	private String message;
//	private Object data;
	
	public static final int CODE_OK = 1;
	public static final int CODE_ERROR = 0;
	public static final int CODE_NOT_AUTHORIZATION = -98;


	public Result(int code, String message){
		put(STATUS_CODE,code);
		put(STATUS_MESSAGE,message);
	}

	public Result(){
//		this.statusCode = CODE_OK;
//		put(STATUS_CODE,CODE_OK);
	}
	
	public Integer getStatusCode() {

		if(!containsKey(STATUS_CODE)){
			return null;
		}else {
			return Integer.getInteger((String) get(STATUS_CODE));
		}
	}
	
	public void setStatusCode(Integer statusCode) {

		put(STATUS_CODE,statusCode);
	}

	public void setData(Object object){
		put("data",object);
	}



	public String getMessage() {

		return (String)get(STATUS_MESSAGE);
	}

	public void setMessage(String message) {

		put(STATUS_MESSAGE,message);
	}
}
