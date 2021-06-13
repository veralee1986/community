package life.vera.community.controller;

import life.vera.community.mapper.UserMapper;
import life.vera.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author : Veralee
 * @date: 2021/5/30 - 05 - 30 - 下午5:47
 * @Description: life.vera.community.controller
 * 路由api的承载者
 * @version: 1.0
 */ //Controller 把当前的类作为路由api的承载者
@Controller //自动扫描当前类，把他当做spring的bean去管理，同时识别它为一个controller
//允许该类去接受前端的请求
public class IndexController {
    //获取访问数据库实例
    @Autowired(required=false)
    private UserMapper userMapper;

    //根路径，打开本地页面指向。（什么都默认访问下面这个模板）
    @GetMapping("/")
    public String index(HttpServletRequest request){
        //传过去一个token可以获得一个用户对象
        //通过登录用户信息验证类AuthorizeController存入cookie
        //在请求中获得该cookie
        Cookie[] cookies = request.getCookies();
        //遍历cookie，拿到token，并与数据库中用户信息比对，如果token值一致
        //获得该用户数据 网页文件中，会进行判断，如果user值不为空则显示该用户名称，并显示下拉栏，同时不会显示登录按钮
        //登录用户数据库信息里没有该token则会显示登录按钮，无法显示用户名及下拉菜单
        for (Cookie cookie:cookies){
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                if(user != null){//将用户信息放入session属性
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }
        //显示HTML index页面
        return "index"; }

}
