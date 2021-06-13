package life.vera.community.mapper;
import org.apache.ibatis.annotations.Insert;
import life.vera.community.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author : Veralee
 * @date: 2021/6/13 - 06 - 13 - 下午1:54
 * @Description: life.vera.community.mapper
 * 改接口相当jdbc连接h2数据库，执行增删查改功能
 * @version: 1.0
 */
@Mapper
public interface UserMapper {
    //将用户信息插入数据库
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified) " +
            "values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);

    //使用token来取得用户信息查询集
    //#{}中如果是类，会自动放入数据，如果不是类需要在形参前面加@Param
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);
}
