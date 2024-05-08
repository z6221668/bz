package com.browse.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.browse.mapper.AppDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

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

    public long calculate(String bz, String price, Long num, String smell) throws Exception {
        long bzNum = appDao.selectBzNum(bz);
        long priceNum = appDao.selectPriceNum(price);
        long smellNum = appDao.selectSmellNum(smell);
        RandomForest rf = new RandomForest();
        // 设置参数
        rf.setNumFeatures(100); // 设置决策树数量
        rf.setSeed(1); // 设置随机种子

        // 创建一个新的实例，输入酒的类型（酒香型、酒箱型、酒价格区间）
        double[] values = new double[4];
        values[0] = bzNum;
        values[1] = priceNum;
        values[2] = smellNum;
        values[3] = (bzNum+priceNum+smellNum) /3;

        Instance inst = new DenseInstance(1.0,values);
        // 创建属性集合（不包括类别属性）
        ArrayList<Attribute> attributes = createAttributes();

        // 创建Instances对象，并设置类别属性
        Instances dataset = new Instances("TestInstances", attributes, 0);

        // 添加新实例到数据集
        dataset.add(inst);
        inst.setDataset(dataset);
        // 设置类别索引
        dataset.setClassIndex(dataset.numAttributes() - 1);

        // 训练模型
        rf.buildClassifier(dataset);

        // 预测酒销售数量
        double prediction = rf.classifyInstance(inst);
        return (long) prediction;
    }

    private static ArrayList createAttributes() {
        // 创建属性
        Attribute wineAroma = new Attribute("WineAromaSales");
        Attribute wineBox = new Attribute("WineBoxSales");
        Attribute winePriceRange = new Attribute("WinePriceRangeSales");
        // 创建类别属性
        Attribute wineSales = new Attribute("WineSales");
        ArrayList list = new ArrayList();
        list.add(wineAroma);
        list.add(wineBox);
        list.add(winePriceRange);
        list.add(wineSales);
        // 创建属性集合
        return list;
    }

    public List<Map<String, Object>> getShopSalesTop10() {
        return appDao.getShopSalesTop10();
    }

    public List<Map<String, Object>> getPriceSales() {
        return appDao.getPriceSales();
    }

    public static void main(String[] args) throws Exception {
        RandomForest rf = new RandomForest();
        // 设置参数
        rf.setNumFeatures(100); // 设置决策树数量
        rf.setSeed(1); // 设置随机种子

        // 创建一个新的实例，输入酒的类型（酒香型、酒箱型、酒价格区间）
        double[] values = new double[4];
        values[0] = 5.0;
        values[1] = 3.5;
        values[2] = 1.3;
//        values[3] = 0.3;

        Instance inst = new DenseInstance(1.0,values);
        // 创建属性集合（不包括类别属性）
        ArrayList<Attribute> attributes = createAttributes();

        // 设置类别属性（酒销售数量）
//        Attribute wineSales = attributes.get(3); // 类别属性索引应为2

        // 创建Instances对象，并设置类别属性
        Instances dataset = new Instances("TestInstances", attributes, 0);
//        dataset.setClass(wineSales);

        // 添加新实例到数据集
        dataset.add(inst);
        inst.setDataset(dataset);
        // 设置类别索引
        dataset.setClassIndex(dataset.numAttributes() - 1);

        // 训练模型
        rf.buildClassifier(dataset);

        // 预测酒销售数量
        double prediction = rf.classifyInstance(inst);
        System.out.println("Predicted wine sales: " + prediction);
    }
}
