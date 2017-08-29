package ua;

import entity.UserAgent;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 12191 on 2017/8/9.
 */
public class WebappUAGetterTest {
    @Test
    public void getUserAgentByDefault() throws Exception {
        List<UserAgent> userAgentList;
        WebappUAGetter defaultWebappUAGetter = new WebappUAGetter();
        userAgentList = defaultWebappUAGetter.fetchUserAgent();
        assertEquals(userAgentList.size(), 2939);
    }

    @Test
    public void getUserAgentByFile() throws Exception {
        List<UserAgent> userAgentList;
        WebappUAGetter fileWebappGetter = new WebappUAGetter("src/test/java/ua/urlList");
        userAgentList = fileWebappGetter.fetchUserAgent();
        assertEquals(userAgentList.size(), 2999);
    }

    @Test
    public void getUserAgentByList() throws Exception {
        List<UserAgent> userAgentList;
        List<String> urlList = new ArrayList<String>();
        urlList.add("http://www.webapps-online.com/online-tools/user-agent-strings/dv/browser51853/chrome");
        urlList.add("http://www.webapps-online.com/online-tools/user-agent-strings/dv/browser51854/firefox");
        urlList.add("http://www.webapps-online.com/online-tools/user-agent-strings/dv/browser51852/internet-explorer");
        WebappUAGetter listWebappUAGetter = new WebappUAGetter(urlList);
        userAgentList = listWebappUAGetter.fetchUserAgent();
        assertEquals(userAgentList.size(), 2999);
    }

    @Test
    public void getUserAgentByURL() throws Exception {
        String url = "http://www.webapps-online.com/online-tools/user-agent-strings/dv/browser51853/chrome";
        WebappUAGetter webappUAGetter = new WebappUAGetter();
        List<UserAgent> userAgentList = webappUAGetter.fetchUserAgentByURL(url);
        assertEquals(userAgentList.size(), 996);
    }

}