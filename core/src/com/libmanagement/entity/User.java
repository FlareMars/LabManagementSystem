package com.libmanagement.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.libmanagement.common.entity.Describertable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="lms_user")
public class User extends Describertable {


    private static final long serialVersionUID = -5291980784960100952L;
    @Column(unique=true)
	private String userName;//帐号
	
	private String password;

	private Integer sex = 1;//性别
	private Integer age = null;//年龄
	private String mobile;//手机号
	private String email;// 电子邮件
	private String company;// 公司
	private String industry;// 行业
	private Integer status = 1; //用户状态
	private String nickName;//用户名
	private String realName;//姓名

	private String userGroup; // 用户组
	

	//用户账户信息
	private Double account=0d;

	//用户积分
	private Long score=0l;
	// 推荐人
	private String recommend;

	private Long storageQuota=1024*1024*100l;// 可用存储
	private Long storageUsed=0l;//已用存储

	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonIgnore
	public String getPassword() {
			return password;

	}

	public void setPassword(String password) {
		this.password = password;
	}


    public String getMobile() {
	
		return mobile;
	}

	
	public void setMobile(String mobile) {
	
		this.mobile = mobile;
	}

	


	
	
	
	public String getEmail() {
	
		return email;
	}

	
	public void setEmail(String email) {
	
		this.email = email;
	}

	public String getNickName() {
	
		return nickName;
	}

	
	public void setNickName(String nickName) {
	
		this.nickName = nickName;
	}

	
	public String getRealName() {
	
		return realName;
	}

	
	public void setRealName(String realName) {
	
		this.realName = realName;
	}

	public String getCompany() {
	
		return company;
	}

	
	public void setCompany(String company) {
	
		this.company = company;
	}

	
	public String getIndustry() {
	
		return industry;
	}

	
	public void setIndustry(String industry) {
	
		this.industry = industry;
	}


	public UserSex getUserSex() {
		return UserSex.getByValue(sex);
	}

    public Integer getSex() {
        return sex;
    }

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Double getAccount() {
		return account;
	}

	public void setAccount(Double account) {
		this.account = account;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public Long getStorageQuota() {
		return storageQuota;

	}


	public void setStorageQuota(Long storageQuota) {
		this.storageQuota = storageQuota;
	}

	public Long getStorageUsed() {

		return storageUsed;
	}

	public void setStorageUsed(Long storageUsed) {
		this.storageUsed = storageUsed;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}


    public static enum UserStatus {
        VALID(1,"有效"), INVALID(0,"无效"),CHECKING(2,"等待验证");

        private Integer value;
        private String desc;

        private UserStatus(Integer value,String desc) {
            this.value = value;
            this.desc = desc;
        }

        public static UserStatus getByValue(Integer value) {
            for (UserStatus e : UserStatus.values()) {
                if (e.value.equals(value)) {
                    return e;
                }
            }
            return null;
        }

        public static String getNameByValue(Integer value) {
            UserStatus e = getByValue(value);
            return e == null ? null : e.name();
        }

        public static String getDescByValue(Integer value) {
            UserStatus e = getByValue(value);
            return e == null ? null : e.getDesc();
        }

        public Integer getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }
    }

	public static enum UserSex {
		MALE(1,"男"), FEMALE(0,"女");

		private Integer value;
		private String desc;

		private UserSex(Integer value,String desc) {
			this.value = value;
			this.desc = desc;
		}

		public static UserSex getByValue(Integer value) {
			for (UserSex e : UserSex.values()) {
				if (e.value.equals(value)) {
					return e;
				}
			}
			return null;
		}

		public static String getNameByValue(Integer value) {
            UserSex e = getByValue(value);
			return e == null ? null : e.name();
		}

		public static String getDescByValue(Integer value) {
            UserSex e = getByValue(value);
			return e == null ? null : e.getDesc();
		}

		public Integer getValue() {
			return value;
		}

		public String getDesc() {
			return desc;
		}
	}
}
