package life.vera.community.dto;

/**
 * @author : Veralee
 * @date: 2021/6/12 - 06 - 12 - 下午12:24
 * @Description: life.vera.community.controller.dto
 * OAuth2 实现App端登录验证 获取Git登录信息以获得token在index登录的 数据传输对象类
 * Data Transfer Object数据传输对象,主要用于远程调用等需要大量传输对象的地方。
 * 比如我们一张表有100个字段，那么对应的PO就有100个属性。但是我们界面上只要显示10个字段，
 * 客户端用WEB service来获取数据，没有必要把整个PO对象传递到客户端，就可以用只有这10个属性的DTO来传递结果到客户端，
 * 这样也不会暴露服务端表结构.到达客户端以后，如果用这个对象来对应界面显示，那此时它的身份就转为VO
 * @version: 1.0
 */
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
