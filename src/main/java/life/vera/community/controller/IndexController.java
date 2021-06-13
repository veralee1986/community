package life.vera.community.dto;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author : Veralee
 * @date: 2021/5/30 - 05 - 30 - 下午5:47
 * @Description: life.vera.community.controller
 * @version: 1.0
 */ //Controller 把当前的类作为路由api的承载者
@Controller //自动扫描当前类，把他当做spring的bean去管理，同时识别它为一个controller
//允许该类去接受前端的请求
public class IndexController {
    @GetMapping("/") //根路径，什么都默认访问下面这个模板
    public String index(){ return "index"; }

}
