package com.user.po;

/**
 * 省实体类
 */
public class Province {
	/**
	 * 省份标识
	 */
	private String provinceId;

	/**
	 * 省份名称
	 */
	private String province;

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Override
	public String toString() {
		return "Province [ provinceId=" + provinceId
				+ ", province=" + province + "]";
	}

}
