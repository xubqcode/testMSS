package com.wuyuzegang.proj;

import java.math.BigDecimal;
import java.util.Date;

public class ThirtyAverageValueProj {
    private Integer id;

    private String securityCode;

    private BigDecimal thirtyAverageValue;

    private Date submitTime;

    private String free1;

    private String free3;

    private String free2;

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

    public BigDecimal getThirtyAverageValue() {
        return thirtyAverageValue;
    }

    public void setThirtyAverageValue(BigDecimal thirtyAverageValue) {
        this.thirtyAverageValue = thirtyAverageValue;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getFree1() {
        return free1;
    }

    public void setFree1(String free1) {
        this.free1 = free1 == null ? null : free1.trim();
    }

    public String getFree3() {
        return free3;
    }

    public void setFree3(String free3) {
        this.free3 = free3 == null ? null : free3.trim();
    }

    public String getFree2() {
        return free2;
    }

    public void setFree2(String free2) {
        this.free2 = free2 == null ? null : free2.trim();
    }
}