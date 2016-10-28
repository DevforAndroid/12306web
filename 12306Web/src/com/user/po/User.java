package com.user.po;

import java.util.Date;

/**
 * 用户体类
 */
public class User {

	/**
	 * ID
	 */
	private Integer id;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 确认密码
	 */
	private String password2;

	/**
	 * 原密码
	 */
	private String passwordOld;

	/**
	 * 权限(1、管理员 2、普通用户)
	 */
	private String rule;

	/**
	 * 真实姓名
	 */
	private String realname;

	/**
	 * 性别(1、男 2、女)
	 */
	private String sex;

	/**
	 * 市信息
	 */
	private City city;

	/**
	 * 证件类型
	 */
	private CertType certType;

	/**
	 * 证件号码
	 */
	private String cert;

	/**
	 * 生日
	 */
	private Date birthday;

	/**
	 * 旅客类型
	 */
	private UserType userType;

	/**
	 * 备注信息
	 */
	private String content;

	/**
	 * 用户状态（0、无效 1、有效 ）
	 */
	private String status;

	/**
	 * 登陆IP
	 */
	private String loginIp;

	/**
	 * 用户头像路径
	 */
	private String imagePath;

	/**
	 * 自动登陆
	 */
	private boolean autoLogin;

	/**
	 * 验证码
	 */
	private String code;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public CertType getCertType() {
		return certType;
	}

	public void setCertType(CertType certType) {
		this.certType = certType;
	}

	public String getCert() {
		return cert;
	}

	public void setCert(String cert) {
		this.cert = cert;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public boolean isAutoLogin() {
		return autoLogin;
	}

	public void setAutoLogin(boolean autoLogin) {
		this.autoLogin = autoLogin;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPasswordOld() {
		return passwordOld;
	}

	public void setPasswordOld(String passwordOld) {
		this.passwordOld = passwordOld;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password="
				+ password + ", password2=" + password2 + ", passwordOld="
				+ passwordOld + ", rule=" + rule + ", realname=" + realname
				+ ", sex=" + sex + ", city=" + city + ", certType=" + certType
				+ ", cert=" + cert + ", birthday=" + birthday + ", userType="
				+ userType + ", content=" + content + ", status=" + status
				+ ", loginIp=" + loginIp + ", imagePath=" + imagePath
				+ ", autoLogin=" + autoLogin + ", code=" + code + "]";
	}

}
