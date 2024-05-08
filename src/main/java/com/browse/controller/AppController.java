package com.browse.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.browse.service.AppService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class AppController {

    @Autowired
    private AppService appService;

    @ResponseBody
    @RequestMapping(value = "/user")
    public String register(String username, String password, String password1) {
        System.out.println(username +":"+ password +":"+ password1);
        if (StringUtils.isEmpty(username)) {
            return "用户名不能为空";
        }
        if (StringUtils.isEmpty(password)) {
            return "密码不能为空";
        }
        if (StringUtils.isEmpty(password1)) {
            return "确认密码不能为空";
        }
        if (!StringUtils.equals(password, password1)) {
            return "两次密码输入不一致";
        }
        if (StringUtils.isNotEmpty(appService.getUser(username))) {
            return "用户已经存在";
        }
        appService.addUser(username, password);
        return "success";
    }

    @RequestMapping(value = "/getPart2")
    public List<Map<String, Object>> getPart2() {
        List<Map<String, Object>> list = appService.getPart2();
        return list;
    }

    @RequestMapping(value = "/getProviceSales")
    public List<Map<String, Object>> getProviceSales() {
        List<Map<String, Object>> list = appService.getProviceSales();
        return list;
    }

    /**
     * 包装类型销量分析
     */
    @RequestMapping(value = "/getPackAgeSales")
    public Map getPackAgeSales() {
        List<Map<String, Object>> list = appService.getPackAgeSales();

        List<String> keyList = new ArrayList<>();
        List<String> valList = new ArrayList<>();
        for (Map<String, Object> m : list) {
            keyList.add(m.get("name").toString());
            valList.add(m.get("value").toString());
        }

        Map<String, List> returnMap = new HashMap<>();
        returnMap.put("val", valList);
        returnMap.put("key", keyList);
        return returnMap;
    }

    @RequestMapping(value = "/getPackageCount")
    public Map getPackageCount() {
        List<Map<String, Object>> list = appService.getPackageCount();
        List<String> keyList = new ArrayList<>();
        List<String> valList = new ArrayList<>();
        for (Map<String, Object> m : list) {
            keyList.add(m.get("name").toString());
            valList.add(m.get("value").toString());
        }

        Map<String, List> returnMap = new HashMap<>();
        returnMap.put("val", valList);
        returnMap.put("key", keyList);
        return returnMap;
    }

    /**
     * 不同类型的销量
     */
    @RequestMapping(value = "/getGenresSales")
    public Map getGenresSales() {
        List<Map<String, Object>> list = appService.getGenresSales();
        List<String> keyList = new ArrayList<>();
        List<String> valList = new ArrayList<>();
        for (Map<String, Object> m : list) {
            keyList.add(m.get("name").toString());
            valList.add(m.get("value").toString());
        }

        Map<String, List> returnMap = new HashMap<>();
        returnMap.put("val", valList);
        returnMap.put("key", keyList);
        return returnMap;
    }

    @RequestMapping(value = "/getShopAvgPrice")
    public Map getShopAvgPrice() {
        List<Map<String, Object>> list = appService.getShopAvgPrice();
        List<String> keyList = new ArrayList<>();
        List<String> valList = new ArrayList<>();
        for (Map<String, Object> m : list) {
            keyList.add(m.get("name").toString());
            valList.add(m.get("value").toString());
        }

        Map<String, List> returnMap = new HashMap<>();
        returnMap.put("val", valList);
        returnMap.put("key", keyList);
        return returnMap;
    }

    @RequestMapping(value = "/getPriceSalesTotal")
    public List<Map<String, Object>> getPriceSalesTotal() {
        List<Map<String, Object>> list = appService.getPriceSalesTotal();
        return list;
    }

    @RequestMapping(value = "/getWordCount")
    public List<Map<String, Object>> getWordCount() {
        List<Map<String, Object>> list = appService.getWordCount();
        return list;
    }

    @RequestMapping(value = "/getPriceSales")
    public List<Map<String, Object>> getPriceSales() {
        List<Map<String, Object>> list = appService.getPriceSales();
        return list;
    }

    @ResponseBody
    @RequestMapping(value = "/login")
    public String login(String username, String password, HttpSession session) {
        if (StringUtils.isEmpty(username)) {
            return "用户名不能为空";
        }
        if (StringUtils.isEmpty(password)) {
            return "密码不能为空";
        }
        if (password.equals(appService.getUser(username))) {
            session.setAttribute("username", username);
            return "success";
        } else {
            return "用户名密码错误";
        }

    }

    @ResponseBody
    @RequestMapping(value = "/getShopSalesTop10")
    public Map getShopSalesTop10() {
        List<Map<String, Object>> list = appService.getShopSalesTop10();
        List key = new ArrayList();
        List val = new ArrayList();
        Map returnMap = new HashMap<String, List>();
        for (Map<String, Object> m : list) {
            key.add(m.get("name"));
            val.add(m.get("value"));
        }
        returnMap.put("key", key);
        returnMap.put("val", val);
        return returnMap;
    }

    @RequestMapping(value = "/updatePassword")
    public Object updatePassword(String password, HttpSession session) {
        String username = session.getAttribute("username").toString();
        appService.updatePassword(username, password);
        return true;
    }

    @RequestMapping(value = "/calculate")
    public Object calculate(String bz, String price, Long num, HttpSession session) {
        return appService.calculate(bz, price, num);
    }
}
