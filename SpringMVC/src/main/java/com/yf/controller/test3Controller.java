package com.yf.controller;

public class test3Controller {

    @RequestMapping("/index-text")
    public String index(HttpServletRequest request, HttpServletResponse response){
        System.out.println("进入控制台");
        request.setAttribute("title","欢迎");
        int i = userService.test();
        System.out.println("总数:"+i);
        return "index";
    }

}
