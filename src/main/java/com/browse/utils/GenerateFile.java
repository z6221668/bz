package com.browse.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class GenerateFile {
    public static void arff(String sourceFile, String targetFile, int column) {
        try {
            BufferedReader in;
            BufferedWriter out;
            String temp;
            out = new BufferedWriter(new FileWriter(targetFile, false));
            //关系声明
            out.write("@relation" + "  spam");
            out.newLine();
            //属性声明
            int i1;
            for (i1 = 0; i1 < column; i1++) {
                out.write("@attribute attr" + i1 + " numeric");
                out.newLine();
            }
            //数据声明
            out.write("@data");
            out.newLine();
            //读CSV文件
            in = new BufferedReader(new FileReader(sourceFile));
            temp = in.readLine();
            while (temp != null) {
                temp = temp.replace("%","").replace("{","").replace("}","").replace(" ","");
                String[] a = temp.split("\t");
                String line = "";
                if (a.length != 5) {
                    int index = 2;
//                    for (int i = index; i < a.length; i++) {
//
//                    }
                    for (String s : a) {
                        line = line + "," + s;
                    }
                    System.out.println(line);
                    out.write(line.replaceFirst(",", ""));
                    out.newLine();
                }
                temp = in.readLine();
            }
            in.close();
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Integer changeSmell(String smell) {
        return 0;
    }

    public static Integer changeBox(String box){
        return 0;
    }

}
