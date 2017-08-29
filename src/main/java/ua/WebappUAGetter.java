package ua;

import entity.UserAgent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * get user agent strings from website: http://www.webapps-online.com/online-tools/user-agent-strings
 *
 * @author Vicent
 */
public class WebappUAGetter extends UAGetter {

    // URL list of different kinds of browsers
    private List<String> urlList;

    // list of all UserAgent
    private List<UserAgent> userAgentList;

    /**
     * Default constructor that get Chrome, Firefox, IE user agent strings
     */
    public WebappUAGetter() {
        // create a new url list
        urlList = new ArrayList<String>();
        // URL contains user agent strings of Chrome
        urlList.add("http://www.webapps-online.com/online-tools/user-agent-strings/dv/browser51853/chrome");
        // URL contains user agent strings of Firefox
        urlList.add("http://www.webapps-online.com/online-tools/user-agent-strings/dv/browser51854/firefox");
        // URL contains user agent strings of IE
        urlList.add("http://www.webapps-online.com/online-tools/user-agent-strings/dv/browser51852/internet-explorer");

        // create a new user agent list
        userAgentList = new ArrayList<UserAgent>();
    }

    /**
     * constructor that get urlList from file
     * format of file: each line contains and only contains one url
     *
     * @param filePath
     */
    public WebappUAGetter(String filePath) {
        // create a new url list
        urlList = new ArrayList<String>();

        // read from file
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                urlList.add(line);
            }
        } catch (FileNotFoundException fnfe) {
            System.err.println("File " + filePath + "not found");
        } catch (IOException ioe) {
            System.err.println("Fail to read file");
            ioe.printStackTrace();
        }

        // create a new user agent list
        userAgentList = new ArrayList<UserAgent>();
    }

    /**
     * constructor that uses existing urlList
     *
     * @param urlList
     */
    public WebappUAGetter(List<String> urlList) {
        // use existing urlList
        this.urlList = urlList;
        // create a new user agent list
        userAgentList = new ArrayList<UserAgent>();
    }

    /**
     * parse all web page in urlList and collect user agents
     * then write to database
     *
     * @return
     */
    public List<UserAgent> fetchUserAgent() {
        for (String url : urlList)
            userAgentList.addAll(fetchUserAgentByURL(url));
        writeToDatabase(userAgentList);
        return userAgentList;
    }

    /**
     * parse the web page specified by url
     *
     * @param url
     * @return
     */
    public List<UserAgent> fetchUserAgentByURL(String url) {
        Document webPage = null; // web page in Jsoup format
        List<UserAgent> userAgentList = new ArrayList<UserAgent>(); // user agent list to be returned

        // get web page
        try {
            webPage = Jsoup.connect(url).get();
        } catch (IOException ioe) {
            // if fail to get the page, return a empty list
            System.err.println("Fail to connect: " + url);
            return userAgentList;
        }

        // parse web page
        Elements uaElements = webPage.getElementsByClass("uas_useragent"); // get elements that contain ua string
        List<String> uaStringList = uaElements.eachText(); // extract ua string from HTML tags
        for (String uaString : uaStringList) {
            // parse ua string
            eu.bitwalker.useragentutils.UserAgent ua = eu.bitwalker.useragentutils.UserAgent.parseUserAgentString(uaString);

            // create a new UserAgent and implement it
            UserAgent userAgent = new UserAgent();
            userAgent.setId(ua.getId());
            userAgent.setString(uaString);
            userAgent.setBrowserName(ua.getBrowser().getGroup() == null ? null : ua.getBrowser().getGroup().getName());
            userAgent.setBrowserVersion(ua.getBrowserVersion() == null ? null : ua.getBrowserVersion().getVersion());
            userAgent.setBrowserType(ua.getBrowser().getBrowserType() == null ? null : ua.getBrowser().getBrowserType().getName());
            userAgent.setOperatingSystem(ua.getOperatingSystem().getGroup() == null ? null : ua.getOperatingSystem().getGroup().getName());

            // add useful user agents to UserAgent list
            if (!userAgent.getOperatingSystem().contains("Unknown"))
                userAgentList.add(userAgent);
        }

        return userAgentList;
    }

    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }

    public List<UserAgent> getUserAgentList() {
        return userAgentList;
    }

    public void setUserAgentList(List<UserAgent> userAgentList) {
        this.userAgentList = userAgentList;
    }
}