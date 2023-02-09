package com.hlpay.admin.withdraw.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 旧库转出记录
 *
 * @author 马飞海
 * @version v0.01
 */
public class PEnableRecord implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String pno;

    private String title;

    private Long uid;

    private BigDecimal money;

    private Date posttime;

    private String message;

    private Integer type;

    private Integer state;

    private BigDecimal servicemoney;

    private BigDecimal umoney;

    private Integer utype;

    private Integer isbalance;

    private Integer fromid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno == null ? null : pno.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getPosttime() {
        return posttime;
    }

    public void setPosttime(Date posttime) {
        this.posttime = posttime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public BigDecimal getServicemoney() {
        return servicemoney;
    }

    public void setServicemoney(BigDecimal servicemoney) {
        this.servicemoney = servicemoney;
    }

    public BigDecimal getUmoney() {
        return umoney;
    }

    public void setUmoney(BigDecimal umoney) {
        this.umoney = umoney;
    }

    public Integer getUtype() {
        return utype;
    }

    public void setUtype(Integer utype) {
        this.utype = utype;
    }

    public Integer getIsbalance() {
        return isbalance;
    }

    public void setIsbalance(Integer isbalance) {
        this.isbalance = isbalance;
    }

    public Integer getFromid() {
        return fromid;
    }

    public void setFromid(Integer fromid) {
        this.fromid = fromid;
    }
}
