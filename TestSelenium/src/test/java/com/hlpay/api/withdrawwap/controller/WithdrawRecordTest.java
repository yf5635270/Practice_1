package com.hlpay.api.withdrawwap.controller;

import com.hlpay.api.withdrawwap.mapper.WithdrawwapMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * 描述: ${todo} ;
 *
 * @author 马飞海;
 * 创建时间: 2020/7/7 13:51;
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public class WithdrawRecordTest extends WapBase {
    @Autowired
    private WithdrawwapMapper withdrawwapMapper;

    @Test
    public void confirmWithdraw() {
        String withdrawMoney = "500";
        driver.findElementById("J_Money").sendKeys(withdrawMoney);
        sleep(1000);

        driver.findElementById("J_Submit").click();
        sleep(1000);

        driver.findElementByName("password").sendKeys(user.getPayPassword());
        sleep(1000);

        driver.findElementByClassName("sure-submit").click();
        sleep(1000);

        Assert.assertTrue(withdrawCorrect(user.getMoney(), withdrawMoney, user.getUid()));

        driver.findElementByClassName(" link-btn").click();
    }

    private boolean withdrawCorrect(String userBalance, String withdrawMoney, String uid) {
        //初始用户余额
        BigDecimal balance = new BigDecimal(userBalance);
        //手续费，银行卡固定1元
        BigDecimal feeMoney = BigDecimal.ONE;
        //提现金额
        BigDecimal money = new BigDecimal(withdrawMoney);
        //提现申请后的用户余额= 初始用户余额 - 提现金额
        BigDecimal result = balance.subtract(money);
        //实际转出金额 = 提现金额 - 手续费
        BigDecimal enableMoney = money.subtract(feeMoney);

        return checkValue(result, uid);

    }

    private boolean checkValue(BigDecimal value, String uid) {
        Integer count = withdrawwapMapper.selectCountWithdrawRecord(uid);
        if (count < 1) {
            return false;
        }
        BigDecimal money = withdrawwapMapper.getMoneyById(uid);

        return money.compareTo(value) == 0;
    }

}
