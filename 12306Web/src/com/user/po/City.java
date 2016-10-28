package com.user.po;

/**
 * 城市实体类
 */
public class City {
	/**
	 * 市标识
	 */
	private String cityId;
	
	/**
	 * 市名称
	 */
	private String city;
	
	/**
	 * 省份
	 */
	private Province province;

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	@Override
	public String toString() {
		return "City [cityId=" + cityId + ", city=" + city
				+ ", province=" + province + "]";
	}

}
