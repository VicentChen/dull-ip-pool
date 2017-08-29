package ua;

import mapper.UserAgentMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Created by 12191 on 2017/8/21.
 */
public class UAPoolTest {
    @Test
    public void loadUAGetters() throws Exception {
        UAPool uaPool = new UAPool();
        uaPool.setUaGetterPackagePath(System.getProperty("user.dir")+"/target/classes/ua/");
        uaPool.setUaGetterPackagePrefix("ua.");
        uaPool.loadUAGetters();
        assertEquals(uaPool.getUaGetterList().size(), 1);
    }

    @Test
    public void fetchUA() throws Exception {
        UAPool uaPool = new UAPool();
        uaPool.setUaGetterPackagePath(System.getProperty("user.dir")+"/target/classes/ua/");
        uaPool.setUaGetterPackagePrefix("ua.");
        uaPool.loadUAGetters();
        uaPool.fetchUA();
        String resourcePath = "MybatisConfig.xml";
        InputStream inputSteamOfResource = Resources.getResourceAsStream(resourcePath);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputSteamOfResource);
        SqlSession sqlSession = sqlSessionFactory.openSession(true); // auto commit
        UserAgentMapper userAgentMapper = sqlSession.getMapper(UserAgentMapper.class);
        // the number may change
        // last update time : 2017/8/29
        assertEquals(userAgentMapper.selectByExample(null).size(), 448);
    }

}