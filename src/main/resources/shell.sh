## 启动hadoop
start-all.sh
## 启动hive
cd /data/hive
nohup hive --service metastore &
nohup hive --service hiveserver2 &


# 首先 运行  taobao.py
# 清空 data.csv
# 然后 运行  data.py  mapreduce

# 1.数据文件 data.csv
rm -rf /root/data/*
cd /root/data/
ls
#  2. 将 data.csv 拖到 xsehll
ls /root/data/
# 3.要确定文件有 。没有则重复 1 2 步骤

# 执行处理脚本
run-sql.sh

# 将数据导入到mysql
hdfs_to_mysql

