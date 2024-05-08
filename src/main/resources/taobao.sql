create database taobao;
use taobao;

DROP table IF  EXISTS taobao;
CREATE TABLE IF NOT EXISTS taobao
(
    title string comment '标题',
    shop string comment '店铺',
    province string comment '省份',
    sales int comment '销量',
    price int comment '价格',
    pck String comment '包装类型'
) ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' STORED AS TEXTFILE;
--  将hdfs 中的数据加载到hive ：
load data local inpath '/root/data/data.csv' into table taobao;
-- 二、在hive中创建对应的表,并将分析的数据存入对应的表中

-- （1）包装类型销量排行榜（总评数）
drop table if exists demo1;
drop table if exists demo2;
drop table if exists demo3;
drop table if exists demo4;
drop table if exists demo5;
drop table if exists demo6;
drop table if exists demo7;
drop table if exists demo8;
drop table if exists demo9;


CREATE TABLE IF NOT EXISTS demo1
(
    name string,
    num  bigint
) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';
insert overwrite table demo1
select pck, sum(sales) value
from taobao where  pck in ('礼盒装','裸瓶装','桶装','坛装','箱装','盒装')
group by pck;
-- （2）包装类型产品记录数
CREATE TABLE IF NOT EXISTS demo2
(
    name string,
    num  bigint
) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';
insert overwrite table demo2
select pck, count(*) value
from taobao where  pck in ('礼盒装','裸瓶装','桶装','坛装','箱装','盒装')
group by pck;
-- （3）不同类型的销量
CREATE TABLE IF NOT EXISTS demo3
(
    name string,
    num  bigint
) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';
insert overwrite table demo3
select '清香型' as name,sum(sales)
from taobao where title  like '%清香%'
union all
select '酱香型' as name,sum(sales)
from taobao where title  like '%酱香%'
union all
select '浓香型' as name,sum(sales)
from taobao where title  like '%浓香%'
union all
select '米香型' as name,sum(sales)
from taobao where title  like '%米香%'
union all
select '兼香型' as name,sum(sales)
from taobao where title  like '%兼香%';
union all
select '凤香型' as name,sum(sales)
from taobao where title  like '%凤香%';
union all
select '复合香型' as name,sum(sales)
from taobao where title  like '%复合%';
union all
select '特香型' as name,sum(sales)
from taobao where title  like '%特香%';
union all
select '绵柔型' as name,sum(sales)
from taobao where title  like '%绵柔%';
union all
select '老白干香型' as name,sum(sales)
from taobao where title  like '%老白干%';
union all
select '芝麻香型' as name,sum(sales)
from taobao where title  like '%芝麻%';
union all
select '董香型' as name,sum(sales)
from taobao where title  like '%董香%';
union all
select '馥郁香型' as name,sum(sales)
from taobao where title  like '%馥郁%';
-- （4）不同店铺产品平均价格
CREATE TABLE IF NOT EXISTS demo4
(
    name string,
    num  float
) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';
insert overwrite table demo4
select shop, avg(price) value
from taobao
group by shop;
-- （5）标题词云
CREATE TABLE IF NOT EXISTS demo5
(
    name string,
    num  bigint
) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';
insert overwrite table demo5
    select name,num from
(select fenci name, count(1) num
from taobao LATERAL VIEW explode(split(fenciTest(title), ' ')) tmpTable AS fenci
group by fenci) t1 where length(name)>1 and name is not null order by num desc limit 100;
-- （6）店铺销量排行榜Top10（总销量）
CREATE TABLE IF NOT EXISTS demo6
(
    name string,
    num  bigint
) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';
insert overwrite table demo6
select shop, sum(sales) value
from taobao
group by shop
order by value desc
limit 10;
-- （7）价格区级对应的销售额
CREATE TABLE IF NOT EXISTS demo7
(
    name string,
    num  bigint
) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';

insert overwrite table demo7
select name,sum(total) from
(select case when price <= 100 then '销售额：0~100'
        when price>100 and price<=200 then '销售额：100~200'
        when price>200 and price<=500 then '销售额：200~500'
        when price>500 and price<=1000 then '销售额：500~1000'
        else '价格：1000以上' end name,
       sales*price as total
from taobao) t1 group by name;
-- （8）价格区级对应的销售量
CREATE TABLE IF NOT EXISTS demo8
(
    name string,
    num  bigint
) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';

insert overwrite table demo8
select name,sum(total) from
    (select case when price <= 100 then '价格：0~100'
                 when price>100 and price<=200 then '价格：100~200'
                 when price>200 and price<=400 then '价格：200~400'
                 when price>400 and price<=600 then '价格：400~600'
                 when price>600 and price<=800 then '价格：600~800'
                 when price>800 and price<=1000 then '价格：800~1000'
                 else '价格：1000以上' end name,
            sales as total
     from taobao) t1 group by name;

-- （9）城市对应的销售量
CREATE TABLE IF NOT EXISTS demo9
(
    name string,
    num  bigint
) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',';

insert overwrite table demo9
select province,sum(sales)
     from taobao group by province;



-- 三、sqoop数据迁移

sqoop export --connect jdbc:mysql://localhost:3306/taobao --username root --password 123456  --table demo1 --fields-terminated-by ','  --export-dir /user/hive/warehouse/taobao.db/demo1
sqoop export --connect jdbc:mysql://localhost:3306/taobao --username root --password 123456  --table demo2 --fields-terminated-by ',' --export-dir /user/hive/warehouse/taobao.db/demo2
sqoop export --connect jdbc:mysql://localhost:3306/taobao --username root --password 123456  --table demo3 --fields-terminated-by ',' --export-dir /user/hive/warehouse/taobao.db/demo3
sqoop export --connect jdbc:mysql://localhost:3306/taobao --username root --password 123456  --table demo4 --fields-terminated-by ',' --export-dir /user/hive/warehouse/taobao.db/demo4
sqoop export --connect jdbc:mysql://localhost:3306/taobao --username root --password 123456  --table demo5 --fields-terminated-by ',' --export-dir /user/hive/warehouse/taobao.db/demo5
sqoop export --connect jdbc:mysql://localhost:3306/taobao --username root --password 123456  --table demo6 --fields-terminated-by ',' --export-dir /user/hive/warehouse/taobao.db/demo6
sqoop export --connect jdbc:mysql://localhost:3306/taobao --username root --password 123456  --table demo7 --fields-terminated-by ',' --export-dir /user/hive/warehouse/taobao.db/demo7
sqoop export --connect jdbc:mysql://localhost:3306/taobao --username root --password 123456  --table demo8 --fields-terminated-by ',' --export-dir /user/hive/warehouse/taobao.db/demo8
sqoop export --connect jdbc:mysql://localhost:3306/taobao --username root --password 123456  --table demo9 --fields-terminated-by ',' --export-dir /user/hive/warehouse/taobao.db/demo9



use taobao;
TRUNCATE TABLE demo1;
TRUNCATE TABLE demo2;
TRUNCATE TABLE demo3;
TRUNCATE TABLE demo4;
TRUNCATE TABLE demo5;
TRUNCATE TABLE demo6;
TRUNCATE TABLE demo7;
TRUNCATE TABLE demo8;
TRUNCATE TABLE demo9;


run_sql="
use taobao;
add jar /data/HiveUDF.jar;
add jar /data/ikanalyzer-2012_u6.jar;
create temporary function fenciTest as 'com.link.datawarehouse.hive.IkParticiple';

load data local inpath '/root/data/data' overwrite into table taobao;

insert overwrite table demo1
select pck, sum(sales) value
from taobao where  pck in ('礼盒装','裸瓶装','桶装','坛装','箱装','盒装')
group by pck;

insert overwrite table demo2
select pck, count(*) value
from taobao where  pck in ('礼盒装','裸瓶装','桶装','坛装','箱装','盒装')
group by pck;

insert overwrite table demo3
select ''清香型'' as name,sum(sales)
from taobao where title  like ''%清香%''
union all
select ''酱香型'' as name,sum(sales)
from taobao where title  like ''%酱香%''
union all
select ''浓香型'' as name,sum(sales)
from taobao where title  like ''%浓香%''
union all
select ''米香型'' as name,sum(sales)
from taobao where title  like ''%米香%''
union all
select ''兼香型'' as name,sum(sales)
from taobao where title  like ''%兼香%'';
union all
select ''凤香型'' as name,sum(sales)
from taobao where title  like ''%凤香%'';
union all
select ''复合香型'' as name,sum(sales)
from taobao where title  like ''%复合%'';
union all
select ''特香型'' as name,sum(sales)
from taobao where title  like ''%特香%'';
union all
select ''绵柔型'' as name,sum(sales)
from taobao where title  like ''%绵柔%'';
union all
select ''老白干香型'' as name,sum(sales)
from taobao where title  like ''%老白干%'';
union all
select ''芝麻香型'' as name,sum(sales)
from taobao where title  like ''%芝麻%'';
union all
select ''董香型'' as name,sum(sales)
from taobao where title  like ''%董香%'';
union all
select ''馥郁香型'' as name,sum(sales)
from taobao where title  like ''%馥郁%'';

insert overwrite table demo4
select shop, avg(price) value
from taobao
group by shop;

insert overwrite table demo5
    select name,num from
(select fenci name, count(1) num
from taobao LATERAL VIEW explode(split(fenciTest(title), ' ')) tmpTable AS fenci
group by fenci) t1 where length(name)>1 and name is not null order by num desc limit 100;

insert overwrite table demo6
select shop, sum(sales) value
from taobao
group by shop
order by value desc
limit 10;

insert overwrite table demo7
select name,sum(total) from
(select case when price <= 100 then '价格：0~100'
        when price>100 and price<=200 then '价格：100~200'
        when price>200 and price<=500 then '价格：200~500'
        when price>500 and price<=1000 then '价格：500~1000'
        else '价格：1000以上' end name,
       sales*price as total
from taobao) t1 group by name;

insert overwrite table demo8
select name,sum(total) from
    (select case when price <= 100 then ''价格：0~100''
                 when price>100 and price<=200 then ''价格：100~200''
                 when price>200 and price<=400 then ''价格：200~400''
                 when price>400 and price<=600 then ''价格：400~600''
                 when price>600 and price<=800 then ''价格：600~800''
                 when price>800 and price<=1000 then ''价格：800~1000''
                 else ''价格：1000以上'' end name,
            sales as total
     from taobao) t1 group by name;


insert overwrite table demo9
select province,sum(sales)
     from taobao group by province;
"
hive -e "$run_sql"


mysql -uroot -p123456 taobao < mysql.sql


