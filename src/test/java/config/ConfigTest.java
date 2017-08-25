package config;

import mapper.UserAgentMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 12191 on 2017/8/2.
 */
public class ConfigTest {
    public static void main(String[] args) throws IOException {
        String resources = "MybatisConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resources);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserAgentMapper userAgentMapper = sqlSession.getMapper(UserAgentMapper.class);
        System.out.println(userAgentMapper.selectByExample(null).size());
    }
}
