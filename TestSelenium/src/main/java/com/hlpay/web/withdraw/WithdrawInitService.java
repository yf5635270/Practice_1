package com.hlpay.web.withdraw;

import com.hlpay.base.entity.HlpayUser;
import com.hlpay.base.utils.CommonHelper;
import com.hlpay.web.withdraw.mapper.WithdrawAccountInfoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WithdrawInitService {

    @Autowired
    private WithdrawAccountInfoMapper withdrawAccountInfoMapper;

    // 新增一张银行卡
    public String addBankAccount(HlpayUser user) {
        String account = CommonHelper.getRandomAccount(16);
        int length = account.length();
        String ellipsis = "尾号".concat(account.substring(length - 4, length));

        String id = CommonHelper.getUid();

        //银行卡
        user.setPlatform("3");
        user.setBankCode("ICBC");
        user.setBankName("中国工商银行");

        withdrawAccountInfoMapper.insertWithdrawAccount(id, ellipsis, account, user);

        return id;
    }
}
