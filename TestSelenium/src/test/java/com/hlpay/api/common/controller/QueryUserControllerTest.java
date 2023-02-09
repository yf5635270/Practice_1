package com.hlpay.api.common.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import com.hlpay.base.config.BaseParams;
import com.hlpay.base.config.RedirectConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;


/**
 * 描述:用户信息查询
 *
 * @author 杨非;
 * 创建时间: 2020/7/21 17:21
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */
public class QueryUserControllerTest extends APIBaseTest {

    @Autowired
    private BaseParams baseParams;
    @Autowired
    private RedirectConfig config;

    @Test
    public void test() {
        String jsonData =
                "{\"site\":" + baseParams.getSite() + ",\"userCode\":" + user.getUid() + ",\"userdata\":" + user.getUid() + ",\"type\":" + 1 +
                        "}";
        queryUserBalance(jsonData);
        userStateQuery(jsonData);
        correctQuery(jsonData);
    }

    public void correctQuery(String jsonData) {
        String key = baseParams.getApiKey();
        String url = config.getApiCommon() + "isExistOfUser";
        ////////////////////////////正确的实例///////////////////////////////
        String sign = DigestUtils.md5DigestAsHex((jsonData + key).getBytes());
        // 参数
        StringBuilder params = new StringBuilder();
        try {
            // 字符数据最好encoding以下;这样一来，某些特殊字符才能传过去(如:某人的名字就是“&”,不encoding的话,传不过去)
            params.append("jsonData=").append(URLEncoder.encode(jsonData, "UTF-8")).append("&sign=").append(sign);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        String str = "";
        try {
            str = readContentFromPost(url, params.toString(), 5000, 5000);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        String str = httpRequestCommon(url, params);
        Assert.assertEquals("{\"success\":" + user.getUid() + "}", str);
        System.out.println(str);
    }

    private void userStateQuery(String jsonData) {
        String key = baseParams.getApiKey();
        String sign = DigestUtils.md5DigestAsHex((jsonData + key).getBytes());

        String url = config.getApiCommon() + "queryUserState";

        // 参数
        StringBuilder paramsHttp = new StringBuilder();
        try {
            // 字符数据最好encoding以下;这样一来，某些特殊字符才能传过去(如:某人的名字就是“&”,不encoding的话,传不过去)
            paramsHttp.append("jsonData=").append(URLEncoder.encode(jsonData, "UTF-8")).append("&sign=").append(sign);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
//        String str = httpRequestCommon(url, paramsHttp);

        String str = "";
        try {
            str = readContentFromPost(url, paramsHttp.toString(), 5000, 5000);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Object> params = jsonToBean(str, Map.class);
        String balanceParam = "balance";
        Assert.assertTrue(params.containsKey(balanceParam));
        BigDecimal balance = new BigDecimal(params.get(balanceParam).toString());
        Assert.assertTrue(balance.compareTo(BigDecimal.ZERO) > 0);

        String emailParam = "email";
        Assert.assertTrue(params.containsKey(emailParam));

        String userCodeParam = "userCode";
        Assert.assertTrue(params.containsKey(userCodeParam));

        String realNameParam = "realName";
        Assert.assertTrue(params.containsKey(realNameParam));

        String isPassParam = "isPass";
        Assert.assertTrue(params.containsKey(isPassParam));

        String mobile = "mobile";
        Assert.assertTrue(params.containsKey(mobile));
        System.out.println(str);
    }


    private void queryUserBalance(String jsonData) {
        String key = baseParams.getApiKey();
        String sign = DigestUtils.md5DigestAsHex((jsonData + key).getBytes());
        String url = config.getApiCommon() + "queryUserBlance";
        // 参数
        StringBuilder paramsHttp = new StringBuilder();
        try {
            // 字符数据最好encoding以下;这样一来，某些特殊字符才能传过去(如:某人的名字就是“&”,不encoding的话,传不过去)
            paramsHttp.append("jsonData=").append(URLEncoder.encode(jsonData, "UTF-8")).append("&sign=").append(sign);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        String str = "";
        try {
            str = readContentFromPost(url, paramsHttp.toString(), 5000, 5000);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        String str = httpRequestCommon(url, paramsHttp);

        Map<String, Object> params = jsonToBean(str, Map.class);
        String param = "success";
        Assert.assertTrue(params.containsKey(param));
        BigDecimal balance = new BigDecimal(params.get(param).toString());
        Assert.assertTrue(balance.compareTo(BigDecimal.ZERO) > 0);
        System.out.println(str);
    }


    private static String readContentFromPost(String POST_URL, String postdata, int connTimeout, int readTimeout) throws IOException {
        StringBuffer response = new StringBuffer();
        URL postUrl = new URL(POST_URL);
        try {
            if ("https".equalsIgnoreCase(postUrl.getProtocol())) {
                SslUtils.ignoreSsl();
            }

            HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
            connection.setConnectTimeout(connTimeout);
            connection.setReadTimeout(readTimeout);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            connection.connect();
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(postdata.getBytes());
            out.flush();
            out.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));//设置编码,否则中文乱码
            String line = "";

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response.toString();
    }


    /**
     * 方法名: jsonToBean
     * 描述: json转bean
     *
     * @param content
     * @param valueType
     * @return 作者: 马飞海
     * 创建时间: 2014-12-22下午12:09:55
     */
    public static <T> T jsonToBean(String content, Class<T> valueType) {
        if (StringUtils.isBlank(content)) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(content, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
