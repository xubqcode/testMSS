package com.wuyuzegang.proj;

import java.util.Date;

public class DayEndPriceProj {
    private Integer id;

    private String securityCode;

    private String securityName;

    private String openPrice;

    private String closePrice;

    private String nowPrice;

    private String maxPrice;

    private String minPrice;

    private Date submitTime;

    private Date daataTime;

    private String free1;

    private String free2;

    private String free3;
    
    private String num;
    
    private String alllimit;
    
    
    

    public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getAlllimit() {
		return alllimit;
	}

	public void setAlllimit(String alllimit) {
		this.alllimit = alllimit;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode == null ? null : securityCode.trim();
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName == null ? null : securityName.trim();
    }

    public String getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }

    public String getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(String closePrice) {
        this.closePrice = closePrice;
    }

    public String getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(String nowPrice) {
        this.nowPrice = nowPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Date getDaataTime() {
        return daataTime;
    }

    public void setDaataTime(Date daataTime) {
        this.daataTime = daataTime;
    }

    public String getFree1() {
        return free1;
    }

    public void setFree1(String free1) {
        this.free1 = free1 == null ? null : free1.trim();
    }

    public String getFree2() {
        return free2;
    }

    public void setFree2(String free2) {
        this.free2 = free2 == null ? null : free2.trim();
    }

    public String getFree3() {
        return free3;
    }

    public void setFree3(String free3) {
        this.free3 = free3 == null ? null : free3.trim();
    }
}