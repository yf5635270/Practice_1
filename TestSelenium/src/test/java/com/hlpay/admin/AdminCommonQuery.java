package com.hlpay.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hlpay.admin.base.controller.AdminLoginTest;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

/**
 * 后台查询类
 *
 * @author cxw
 * @date 2022-05-26
 */
public class AdminCommonQuery extends AdminLoginTest {

    private String url;
    private String submit_path;

    // 默认休眠时间100
    public static final int DEFAULT_TIME = 200;

    public static final int LONG_TIME = 10000;

    public AdminCommonQuery() {
    }

    public AdminCommonQuery(String url, String submit_path) {
        this.url = url;
        this.submit_path = submit_path;
    }

    /**
     * 类型查询
     *
     * @param select 下拉框的位置
     * @param index  选择下拉元素的下标
     * @return
     */
    public void typeQuery(String select, int index) throws IOException {
        getUrl(url);
        driver.findElement(By.xpath(getOption(select, index))).click();
        query(submit_path);
    }

    /**
     * 把所有类型逐一查询
     *
     * @param select  下拉框的位置
     * @param options option的个数
     * @return
     */
    public void typesQuery(String select, int options) throws IOException {
        for (int i = 2; i < options + 1; i++) {
            typeQuery(select, i);
        }
    }

    /**
     * 获取选择下拉元素的位置
     *
     * @param select 下拉框的位置
     * @param index  选择下拉元素的下标
     * @return string 选择下拉元素的位置
     */
    public String getOption(String select, int index) {
        return select + "/option[" + index + "]";
    }

    /**
     * 下拉和值查询
     *
     * @param select 下拉框的位置
     * @param index  选择下拉元素的下标
     * @param input  文本框的位置
     * @param value  文本框的值
     * @return
     */
    public void conditionAndValueQuery(String select, int index, String input, String value) throws IOException {
        getUrl(url);
        driver.findElement(By.xpath(getOption(select, index))).click();
        driver.findElement(By.xpath(input)).sendKeys(value);
        query(submit_path);
    }

    /**
     * 日期查询
     *
     * @param startDateJsPath 开始日期文本框jsPath
     * @param startDateXPath  开始日期文本框XPath
     * @param startDate       开始日期
     * @param endDateJsPath   结束日期文本框jsPath
     * @param endDateXPath    结束日期文本框XPath
     * @param endDate         结束日期
     * @return
     */
    public void dateQuery(String startDateJsPath, String startDateXPath, String startDate, String endDateJsPath,
                          String endDateXPath, String endDate) throws IOException {
        getUrl(url);
        if (StringUtils.isNotEmpty(startDateJsPath) && StringUtils.isNotEmpty(startDate)) {
            setDateQuery(startDateJsPath, startDateXPath, startDate);
        }
        if (StringUtils.isNotEmpty(endDateJsPath) && StringUtils.isNotEmpty(endDate)) {
            setDateQuery(endDateJsPath, endDateXPath, endDate);
        }
        query(submit_path);
    }

    /**
     * 带时间类型的日期查询
     *
     * @param select          下拉框的位置
     * @param index           选择下拉元素的下标
     * @param startDateJsPath 开始日期文本框jsPath
     * @param startDateXPath  开始日期文本框XPath
     * @param startDate       开始日期
     * @param endDateJsPath   结束日期文本框jsPath
     * @param endDateXPath    结束日期文本框XPath
     * @param endDate         结束日期
     * @return
     */
    public void dateQuery(String select, int index, String startDateJsPath, String startDateXPath, String startDate,
                          String endDateJsPath, String endDateXPath, String endDate) throws IOException {
        for (int i = 1; i < index + 1; i++) {
            getUrl(url);
            driver.findElement(By.xpath(getOption(select, i))).click();
            if (StringUtils.isNotEmpty(startDateJsPath) && StringUtils.isNotEmpty(startDate)) {
                setDateQuery(startDateJsPath, startDateXPath, startDate);
            }
            if (StringUtils.isNotEmpty(endDateJsPath) && StringUtils.isNotEmpty(endDate)) {
                setDateQuery(endDateJsPath, endDateXPath, endDate);
            }
            query(submit_path);
        }
    }

    /**
     * 金额查询
     *
     * @param startAmountXPath 开始金额文本框XPath
     * @param startAmount      开始金额
     * @param endAmountXPath   结束金额文本框XPath
     * @param endAmount        结束金额
     * @return
     */
    public void amountQuery(String startAmountXPath, String startAmount, String endAmountXPath, String endAmount) throws IOException {
        getUrl(url);
        if (StringUtils.isNotEmpty(startAmountXPath) && StringUtils.isNotEmpty(startAmount)) {
            driver.findElement(By.xpath(startAmountXPath)).sendKeys(startAmount);
        }
        if (StringUtils.isNotEmpty(endAmountXPath) && StringUtils.isNotEmpty(endAmount)) {
            driver.findElement(By.xpath(endAmountXPath)).sendKeys(endAmount);
        }
        query(submit_path);
    }

    /**
     * 带金额类型的金额查询
     *
     * @param select           下拉框的位置
     * @param index            选择下拉元素的下标
     * @param begin            第几个开始
     * @param startAmountXPath 开始金额文本框XPath
     * @param startAmount      开始金额
     * @param endAmountXPath   结束金额文本框XPath
     * @param endAmount        结束金额
     * @return
     */
    public void amountQuery(String select, int index, int begin, String startAmountXPath, String startAmount,
                            String endAmountXPath, String endAmount) throws IOException {
        for (int i = begin; i < index + 1; i++) {
            getUrl(url);
            driver.findElement(By.xpath(getOption(select, i))).click();
            if (StringUtils.isNotEmpty(startAmountXPath) && StringUtils.isNotEmpty(startAmount)) {
                driver.findElement(By.xpath(startAmountXPath)).sendKeys(startAmount);
            }
            if (StringUtils.isNotEmpty(endAmountXPath) && StringUtils.isNotEmpty(endAmount)) {
                driver.findElement(By.xpath(endAmountXPath)).sendKeys(endAmount);
            }
            query(submit_path);
        }
    }

    /**
     * 单个文本框的值查询
     *
     * @param inputXPath 文本框XPath
     * @param value      值
     * @return
     */
    public void singleInputQuery(String inputXPath, String value) throws IOException {
        getUrl(url);
        if (StringUtils.isNotEmpty(inputXPath) && StringUtils.isNotEmpty(value)) {
            driver.findElement(By.xpath(inputXPath)).sendKeys(value);
            query(submit_path);
        }
    }

    /**
     * 设置日期查询
     *
     * @param dateJsPath 日期文本框jsPath
     * @param dateXPath  日期文本框XPath
     * @param dateStr    日期
     * @return
     */
    private void setDateQuery(String dateJsPath, String dateXPath, String dateStr) {
        if (StringUtils.isNotEmpty(dateJsPath) && StringUtils.isNotEmpty(dateJsPath) && StringUtils.isNotEmpty(dateStr)) {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript(dateJsPath + ".removeAttribute('readonly');");
            executor.executeScript(dateJsPath + ".value=\"\";");
            driver.findElement(By.xpath(dateXPath)).sendKeys(dateStr);
        }
    }

    /**
     * 打开页面
     */
    public void getUrl(String url) throws IOException {
        driver.get(url);
        sleep(DEFAULT_TIME);
    }

    /**
     * 点击查询按钮查询
     */
    public void query(String submit_path) throws IOException {
        driver.findElement(By.xpath(submit_path)).click();
        sleep(DEFAULT_TIME);
    }

    /**
     * 关闭
     */
    public void close() throws IOException {
        sleep(LONG_TIME);
        driver.close();
    }

    /**
     * 获取当前日期
     */
    public String getDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}
