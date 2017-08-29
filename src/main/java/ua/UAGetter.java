package ua;

import entity.UserAgent;
import mapper.UserAgentMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by 12191 on 2017/8/16.
 */
public abstract class UAGetter {

    /**
     * parse all web page in urlList
     * and collect user agents
     *
     * @return
     */
    public abstract List<UserAgent> fetchUserAgent();

    /**
     * parse the web page specified by url
     *
     * @param url
     * @return
     */
    public abstract List<UserAgent> fetchUserAgentByURL(String url);

    /**
     * write all user agents to the database
     *
     * @param userAgentList
     */
    public void writeToDatabase(List<UserAgent> userAgentList) {
        try {
            // create a sql session
            // configuration file of Mybatis
            String resourcePath = "MybatisConfig.xml";
            InputStream inputSteamOfResource = Resources.getResourceAsStream(resourcePath);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputSteamOfResource);
            SqlSession sqlSession = sqlSessionFactory.openSession(true); // auto commit
            UserAgentMapper userAgentMapper = sqlSession.getMapper(UserAgentMapper.class);

            // write to database
            for (UserAgent userAgent : userAgentList) {
                userAgentMapper.insertUniquely(userAgent);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
