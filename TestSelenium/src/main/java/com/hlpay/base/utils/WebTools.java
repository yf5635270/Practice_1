package com.hlpay.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.util.Map;
import java.util.SortedMap;

/**
 * WEB工具
 *
 * @author yzln.
 * @version Copyright(C)2020 一站网版权所有  V1.0
 * @date 2020-07-01 15:50
 */
public class WebTools {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebTools.class);

    /**
     * 将数据集按a-z的顺序转成uri参数形式，如：a=xxx&b=xxx&c=xxx，跳过空值
     *
     * @param paramsMap 数据集
     * @return uri形式的参数串
     */
    public static String buildUriParamsStr(SortedMap<String, Object> paramsMap, boolean encode) {
        StringBuilder sb = new StringBuilder();
        String mark = "";
        for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
            if (!StringUtils.isEmpty(entry.getValue())) {
                sb.append(mark).append(entry.getKey()).append("=");
                if (encode) {
                    sb.append(urlEncode(String.valueOf(entry.getValue())));
                } else {
                    sb.append(String.valueOf(entry.getValue()));
                }
                mark = "&";
            }
        }
        LOGGER.info("urlParamsStr：" + sb);
        return sb.toString();
    }

    /**
     * 请求参数转码
     *
     * @param str 原参数
     * @return 转码后的参数
     */
    public static String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (Throwable e) {
            LOGGER.error("[" + str + "] URL转码异常：" + e.getMessage());
            return str;
        }
    }

    /**
     * 构造post请求表单
     *
     * @param url       请求地址
     * @param paramsMap 表单参数
     * @return
     */
    public static String buildPostFormHtml(String url, SortedMap<String, Object> paramsMap) {

        //构造POST请求表单
        StringBuffer sb = new StringBuffer();

        sb.append("data:text/html;charset=utf-8,");
        sb.append("<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"></head><body>");
        sb.append("<form id=\"submitForm\" action=\"").append(url).append("\"  method=\"POST\">");
        for (Map.Entry entry : paramsMap.entrySet()) {
            String value = String.valueOf(entry.getValue()).replaceAll("\"", "&quot;");
            sb.append("<input type=\"hidden\" name=\"").append(entry.getKey()).append("\" value=\"").append(value).append("\">");
        }
        sb.append("</form>");
        sb.append("<script>document.forms['submitForm'].submit();</script>");
        sb.append("</body></html>");

        return sb.toString();
    }
}
