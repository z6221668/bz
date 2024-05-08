package com.browse.hadoop;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


import java.io.IOException;


public class HadoopMain {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(HadoopMain.class);

        job.setMapperClass(HadoopMapper.class);
        job.setReducerClass(HadoopReducer.class);

        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Text.class);


        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);


        FileInputFormat.setInputPaths(job, new Path("input/taobao.csv"));
        FileOutputFormat.setOutputPath(job, new Path("output/"));

        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
