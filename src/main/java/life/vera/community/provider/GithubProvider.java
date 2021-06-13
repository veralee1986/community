package life.vera.community.provider;

import com.alibaba.fastjson.JSON;
import life.vera.community.dto.AccessTokenDTO;
import life.vera.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author : Veralee
 * @date: 2021/6/12 - 06 - 12 - 下午12:20
 * @Description: life.vera.community.provider
 * 获取github认证类
 * 基于OAuth2 实现App端登录验证
 * @version: 1.0
 *///Component 仅仅的把当前的类初始化到spring容器的上下文 new对象不需要
// （自动化实例化放在池子里面） 迅速拿到变量
@Component
public class GithubProvider {
    //参数超过两个就要封装成对象去做
    //传入数据传输对象实例（装有github登录认证信息）
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        //OkHttp实例对象
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            //获得返回数据token和state串，进行分割提取token
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public GithubUser getUser(String accessToken){
        //OkHttp实例对象
        OkHttpClient client = new OkHttpClient();
        //请求头携带token
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token "+accessToken)
                .build();
        try { Response response = client.newCall(request).execute();
            String string = response.body().string();
            //通过返回信息中拿到返回Git用户数据
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            //json.paresobject把string自动转换成Java类对象，再把json的对象自动转换解析成Java类对象
            return githubUser;
        } catch (IOException e) {
        }
        return null;
    }
}
