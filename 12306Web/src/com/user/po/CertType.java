package com.user.po;

/**
 * 证件类型实体类
 */
public class CertType {

	/**
	 * ID
	 */
	private Integer id;

	/**
	 * 证件类型
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
		return "CertType [id=" + id + ", content=" + content + "]";
	}

}
