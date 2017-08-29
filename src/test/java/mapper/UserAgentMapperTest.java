package mapper;

import entity.UserAgent;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 12191 on 2017/8/16.
 */
public class UserAgentMapperTest {

    @Test
    public void insertUniquely() throws Exception {
        UserAgent oldUserAgent = new UserAgent();
        oldUserAgent.setId(1);
        oldUserAgent.setBrowserName("IE");
        oldUserAgent.setBrowserType("IE");
        oldUserAgent.setBrowserVersion("1.0");
        oldUserAgent.setOperatingSystem("OS");
        oldUserAgent.setString("IE 1.0 OS");

        UserAgent newUserAgent = new UserAgent();
        newUserAgent.setId(1);
        newUserAgent.setBrowserName("FF");
        newUserAgent.setBrowserType("FF");
        newUserAgent.setBrowserVersion("2.0");
        newUserAgent.setOperatingSystem("OS");
        newUserAgent.setString("FF 2.0 OS");

        String resources = "MybatisConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resources);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        UserAgentMapper userAgentMapper = sqlSession.getMapper(UserAgentMapper.class);

        userAgentMapper.insertUniquely(oldUserAgent);
        assertEquals(userAgentMapper.selectByExample(null).size(), 1);

        userAgentMapper.insertUniquely(newUserAgent);
        assertEquals(userAgentMapper.selectByExample(null).size(), 1);

        sqlSession.close();
    }

}