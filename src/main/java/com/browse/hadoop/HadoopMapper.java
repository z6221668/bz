package com.browse.hadoop;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HadoopMapper extends Mapper<LongWritable, Text, NullWritable, Text> {
    private Text outValue = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String[] arr = value.toString().split(",", -1);
        arr[0] = arr[0].replace("【", "")
                .replace("】", "")
                .replace(" ", "")
                .replace("\t", "");
        arr[2] = arr[2].split(" ")[0];
        String sales = arr[3];
        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sales);
        int num = 0;
        if (matcher.find()) {
            num = Integer.parseInt(matcher.group());
        }
        if (sales.contains("万")) {
            num = num * 10000;
        }
        arr[3] = String.valueOf(num);
        if (StringUtils.isEmpty(arr[5])) {
            arr[5] = "未知";
        }
        outValue.set(StringUtils.join(arr, "\t"));
        context.write(NullWritable.get(), outValue);
    }
}
