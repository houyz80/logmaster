/**
 * 
 */
package com.faithworth.utils;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * @author Harris
 * @date 2018年8月11日
 */

public class ToKen {

	private String toKen;
	private String userid;
	private boolean expired;
	
	
	/**
	 * 
	 */
	public ToKen() {
	}
	
	/**
	 * @param toKen
	 */
	public ToKen(String toKen) {
		super();
		this.toKen = toKen;
	}

	/**
	 * @param toKen
	 * @param expired
	 */
	public ToKen(String toKen, boolean expired) {
		super();
		this.toKen = toKen;
		this.expired = expired;
	}

	/**
	 * @param toKen
	 * @param userid
	 * @param expired
	 */
	public ToKen(String toKen, String userid, boolean expired) {
		super();
		this.toKen = toKen;
		this.userid = userid;
		this.expired = expired;
	}

	/**
	 * @return the toKen
	 */
	public String getToKen() {
		return toKen;
	}
	/**
	 * @param toKen the toKen to set
	 */
	public void setToKen(String toKen) {
		this.toKen = toKen;
	}
	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}
	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	/**
	 * @return the expired
	 */
	public boolean isExpired() {
		if(userid == null || "".equals(userid)) return true;
		return expired;
	}
	/**
	 * @param expired the expired to set
	 */
	public void setExpired(boolean expired) {
		this.expired = expired;
	}
}
