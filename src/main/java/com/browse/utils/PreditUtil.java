package com.browse.utils;

import weka.classifiers.trees.RandomForest;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

public class PreditUtil {
    public static RandomForest randomForest = new RandomForest();
    public static Instances data;
    public static final String FILE_URL = "/Users/sxier/Desktop/data.arff";
    public static RandomForest getRandomForest() {
        randomForest.setNumFeatures(100);
        randomForest.setSeed(1);
        return randomForest;
    }
    public  static double predit(Integer smell, Integer price, Integer box) throws Exception {
        double[] values = new double[6];
        values[0] = smell;
        values[1] = box;
        values[2] = price;
        values[3]= 0;
        Instance inst = new DenseInstance(4.0, values);
        //将预测数据与 模型关联
        inst.setDataset(getData());
        getRandomForest().buildClassifier(getData());

        //此处注释可筛选出对应列系数
//        Evaluation eval = new Evaluation(data);
//        eval.crossValidateModel(rf, data, 10, new Random(1));
        //输出对应的预测数据
        System.out.println("当前预测售价 " + getRandomForest().classifyInstance(inst));
        return getRandomForest().classifyInstance(inst);
    }

    public static Instances getData() throws Exception {
        //获取数据
        ConverterUtils.DataSource dataSource = new ConverterUtils.DataSource(FILE_URL);
        Instances data = dataSource.getDataSet();
        //设置预测列
        data.setClassIndex(3);
        return data;
    }
}
