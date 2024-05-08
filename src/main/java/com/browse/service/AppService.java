package com.browse.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import com.browse.mapper.AppDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppService {

    @Autowired
    private AppDao appDao;


    public int addUser(String username, String password) {
        return appDao.addUser(username, password);
    }

    public List<Map<String, Object>> getPart2() {
        return appDao.getPart2();
    }

    public List<Map<String, Object>> getProviceSales() {
        return appDao.getProviceSales();
    }

    public List<Map<String, Object>> getPackAgeSales() {
        return appDao.getPackAgeSales();
    }

    public List<Map<String, Object>> getPackageCount() {
        return appDao.getPackageCount();
    }

    public List<Map<String, Object>> getGenresSales() {
        return appDao.getGenresSales();
    }

    public List<Map<String, Object>> getShopAvgPrice() {
        return appDao.getShopAvgPrice();
    }

    public List<Map<String, Object>> getPriceSalesTotal() {
        return appDao.getPriceSalesTotal();
    }

    public List<Map<String, Object>> getWordCount() {
        return appDao.getWordCount();
    }

    public String getUser(String username) {
        return appDao.getUser(username);
    }

    public void updatePassword(String username, String password) {
        appDao.updatePassword(username, password);
    }

    public long calculate(String bz, String price, Long num, String smell) {
        long bzNum = appDao.selectBzNum(bz);
        long totalNum = appDao.selectBzTotal();
        BigDecimal bzRation = new BigDecimal(bzNum).divide(new BigDecimal(totalNum),2,RoundingMode.HALF_UP);

        long priceNum = appDao.selectPriceNum(price);
        long priceTotal = appDao.selectPriceNumTotal();
        BigDecimal priceRatio = new BigDecimal(priceNum).divide(new BigDecimal(priceTotal),2,RoundingMode.HALF_UP);

        long smellNum = appDao.selectSmellNum(smell);
        long smellNumTotal = appDao.selectSmellNumTotal();
        BigDecimal smellRatio = new BigDecimal(smellNum).divide(new BigDecimal(smellNumTotal),2,RoundingMode.HALF_UP);

        BigDecimal ration = (bzRation.add(priceRatio).add(smellRatio)).divide(new BigDecimal("3"),2,RoundingMode.HALF_UP);

        return new BigDecimal(num).multiply(ration).longValue();
    }

    public List<Map<String, Object>> getShopSalesTop10() {
        return appDao.getShopSalesTop10();
    }

    public List<Map<String, Object>> getPriceSales() {
        return appDao.getPriceSales();
    }

    public static void main(String[] args) {
        long a = 100;
        long b = 3;
        System.out.printf(String.valueOf(a / b));
    }
}
