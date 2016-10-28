package com.user.po;

/**
 * 旅客类型实体类
 */
public class UserType {

	/**
	 * ID
	 */
	private Integer id;

	/**
	 * 旅客类型
	 */
	private String content;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "UserType [id=" + id + ", content=" + content + "]";
	}

}
