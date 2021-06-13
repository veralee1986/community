package life.vera.community.dto;

import life.vera.community.controller.dto.AccessTokenDTO;
import life.vera.community.controller.dto.GithubUser;
import life.vera.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : Veralee
 * @date: 2021/6/12 - 06 - 12 - 下午12:06
 * @Description: life.vera.community.controller
 * @version: 1.0
 */
@Controller
public class AuthorizeController {
    //通过这个标签，可以将Component实例化好的东西放在这里，直接使用
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    //返回参数接收
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();//alt+command+v 自动创建实例
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null) {
            //登陆成功，写cookie和session
            request.getSession().setAttribute("githubUser", githubUser);
            return "redirect:/";//不写redirect的情况，只是页面被渲染为index，地址不变，用了之后地址发生改变。
        }else {
            return "redirect:/";
        }
    }
}
