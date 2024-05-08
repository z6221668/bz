package com.browse.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;

@Mapper
public interface AppDao {

    @Select("select password from users where username = #{username, jdbcType=VARCHAR}")
    public String getUser( @Param("username") String username);

	@Select("select  name,num value from demo9")
	public List<Map<String,Object>> getProviceSales();

	@Select("select  name, num value from demo1")
	public List<Map<String,Object>> getPackAgeSales();
	
	@Select("select be name,num value from demo2")
	public List<Map<String,Object>> getPackageCount();
	
	@Select("select  name,num value from demo3")
	public List<Map<String,Object>> getGenresSales();
	
	@Select("SELECT  name,num value FROM demo4")
	public List<Map<String,Object>> getShopAvgPrice();

	@Select("select  name,num value from demo7")
	public List<Map<String,Object>> getPriceSalesTotal();
	
	@Select("select name,num value from demo5 order by num desc")
	public List<Map<String,Object>> getWordCount();
	
	@Select("select name,num value from demo6 order by num desc")
	public List<Map<String,Object>> getShopSalesTop10();

	@Select("select name,num value from demo8")
	public List<Map<String,Object>> getPriceSales();

	@Insert("INSERT INTO users (username, password) VALUES (#{username}, #{password})")
	public int addUser(@Param("username") String username, @Param("password") String password);

	@Select("SELECT shopstyle name,value FROM demo4 a , DATA b WHERE a.name=b.id   GROUP BY shopstyle")
	public List<Map<String,Object>> getPart2();

	/**
	 * 修改密码
	 */
	@Update("update users set password = #{password} where username = #{userName}")
	public int updatePassword(@Param("userName") String userName, @Param("password") String password);

	/**
	 * 获取包装数量
	 */
	@Select("select num from demo1 where name = #{bzName}")
	long selectBzNum(@Param("bzName") String bzName);

	/**
	 * 获取包装总量
	 */
	@Select("select sum(num) from demo1")
	long selectBzTotal();

	/**
	 * 获取价格数量
	 */
	@Select("select num from demo8 where name = #{price}")
	long selectPriceNum(@Param("price") String price);

	/**
	 * 获取价格总数
	 */
	@Select("select sum(num) from demo8")
	long selectPriceNumTotal();

	/**
	 * 获取香型数量
	 */
	@Select("select num from demo3 where name = #{smell}")
	long selectSmellNum(@Param("smell")String smell);

	/**
	 * 获取香型数量
	 */
	@Select("select sum(num) from demo3 ")
	long selectSmellNumTotal();
}
