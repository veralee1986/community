package life.vera.community.controller;

import life.vera.community.dto.AccessTokenDTO;
import life.vera.community.dto.GithubUser;
import life.vera.community.mapper.UserMapper;
import life.vera.community.model.User;
import life.vera.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author : Veralee
 * @date: 2021/6/12 - 06 - 12 - 下午12:06
 * @Description: life.vera.community.controller
 * 登录用户信息验证
 * 基于OAuth2 实现App端登录验证
 * @version: 1.0
 */
@Controller
public class AuthorizeController {
    //通过这个标签，可以将Component实例化好的东西放在这里，直接使用
    @Autowired
    private GithubProvider githubProvider;

    //读取配置信息，得到Git的OAuth2验证信息
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    //获取数据库接口
    @Autowired(required=false)
    private UserMapper userMapper;

    @GetMapping("/callback")
    //返回参数接收
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletResponse response){
        //传输数据对象实例化，并将获取信息放入该实例
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();//alt+command+v 自动创建实例
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        //将实例传入githubProvider获取github认证类
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        //获取返回值为Github用户信息 存储在github用户类实例
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null) {// github用户类不为空，登陆成功 获取用户信息
            User user = new User();
            //生成一个token 存储入数据库，并通过github用户类给该用户实例重新赋值
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            //重新赋值后的用户数据插入数据库
            userMapper.insert(user);
            //只将不会暴露用户隐私的token写入cookie中以保存用户登录信息
            //传过去一个token可以获得一个用户对象
            //(目前在本地存储了自己的github用户信息，直接获取登录权限)
            //通过github用户信息登陆成功自动生成token,存入cookie
            response.addCookie(new Cookie("token", token));
            return "redirect:/";//不写redirect的情况，只是页面被渲染为index，地址不变，用了之后地址发生改变。
        }else {
            return "redirect:/";
        }
    }
}
