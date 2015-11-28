package com.libmanagement.entity;


import com.libmanagement.common.entity.Describertable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 账户流水
 * @author caochun
 *
 */

@Entity
@Table(name="lms_account_log")
public class AccountLog extends Describertable {

	public static final Integer TYPE_CONSUME = 1; // 消费
	public static final Integer TYPE_RECHARGE = 2; // 充值
	public static final String FROM_PORTAL = "PORTAL";
	public static final String FROM_ADMIN = "ADMIN";
	private static final long serialVersionUID = -6422193118042915384L;

	@ManyToOne
	@JoinColumn
	private User user;
	private Integer logType = 1; //消费，充值
	private Double amount = 0.0;// 变动金额
	private Double balance = 0.0;// 变动后的余额
 
	private Long score;
	
	private String adminUser;// 管理员
	private String fromType;// 来源
	private String remark;
	
	public String getRemark() {
	
		return remark;
	}

	
	public void setRemark(String remark) {
	
		this.remark = remark;
	}

	public String getAdminUser() {
	
		return adminUser;
	}
	
	public void setAdminUser(String adminUser) {
	
		this.adminUser = adminUser;
	}
	
	public String getFromType() {
	
		return fromType;
	}
	
	public void setFromType(String fromType) {
	
		this.fromType = fromType;
	}


	public Integer getLogType() {
		return logType;
	}

	public void setLogType(Integer logType) {
		this.logType = logType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
