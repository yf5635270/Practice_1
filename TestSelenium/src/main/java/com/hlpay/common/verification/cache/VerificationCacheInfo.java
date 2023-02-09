package com.hlpay.common.verification.cache;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述: 验证结果实体类，只包含了返回结果编号以及返回结果信息;
 *
 * @author 马飞海;
 * @version : Copyright(C)2020 一站网版权所有  V1.0
 * @since : 2020/12/22 13:53;
 */
public class VerificationCacheInfo implements Serializable {

    private static final long serialVersionUID = -1387523785895050875L;

    private String verificationCode;

    private Integer errorNumber;

    private Integer sendNumbers;

    private Date lastSendTime;


    public Integer getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(Integer errorNumber) {
        this.errorNumber = errorNumber;
    }

    public Date getLastSendTime() {
        return lastSendTime;
    }

    public void setLastSendTime(Date lastSendTime) {
        this.lastSendTime = lastSendTime;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Integer getSendNumbers() {
        return sendNumbers;
    }

    public void setSendNumbers(Integer sendNumbers) {
        this.sendNumbers = sendNumbers;
    }
}
