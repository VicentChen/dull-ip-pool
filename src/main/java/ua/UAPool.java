package ua;

import java.io.File;
import java.util.*;

/**
 * Using:
 *        1. new UAPool();
 *        2. OPTIONAL: setUaGetterPackagePath(), setUaGetterPackagePrefix(), setUaGetterList();
 *        3. autoRun();
 *           OR: run the UAPool step by step
 *           loadUAGetters() -> fetchUA()
 *        4. get the UA:
 *
 * @author Vicent
 */
public class UAPool{

    /**
     * the package path of UAGetters
     * files under such path are java class files after compiled
     * which have suffix .class
     * because this is a package path
     * which means it's a directory path
     * it should be end with '/'
     * otherwise error will occur when parsing uaGetterPackagePath in method LoadClass()
     */
    private String uaGetterPackagePath;

    /**
     * the entire package name of UAGetters
     * uaGetterPackagePrefix should end with '.'
     */
    private String uaGetterPackagePrefix;

    // list of UAGetters
    private List<UAGetter> uaGetterList;

    /**
     * Default constructor
     * this constructor will set the default value of uaGetterPackagePath, uaGetterPackagePrefix
     */
    public UAPool() {
        // set the default path UAGetters package
        // the default path is the path of package ua
        uaGetterPackagePath = ClassLoader.getSystemResource("").getPath() + "ua/";
        // set the default package of UAGetters
        // the default package is package ua
        uaGetterPackagePrefix = "ua.";
        // create a new uaGetterlist
        uaGetterList = new ArrayList<UAGetter>();
    }

    /**
     * automatically run the UAPool for one time
     */
    public void autoRunForOneTime() {
        loadUAGetters();
        fetchUA();
    }

    /**
     * load UAGetters under the directory 'uaGetterList'
     * <p>
     * the UAGetters to be loaded have:
     * 1. suffix "UAGetter.class"
     * 2. prefix to identify the UAGetter, such as prefix "Webapp" in "WebappGetter"
     */
    public void loadUAGetters() {
        // the length of string "UAGetter.class"
        final int LENGTH_OF_SUFFIX = 14;

        // open the directory - ua
        File file = new File(uaGetterPackagePath);

        //
        try {
            if (!file.exists()) throw new NullPointerException();
            if (!file.isDirectory()) throw new NullPointerException();
        } catch (NullPointerException npe) {
            System.err.println("directory \"" + uaGetterPackagePath + "\" not exist");
            npe.printStackTrace();
            return;
        }

        // get all files in the directory
        for (String name : file.list()) {
            // choose the proper class
            if (name.endsWith("UAGetter.class") && name.length() > LENGTH_OF_SUFFIX) {
                // entire class name
                String className = uaGetterPackagePrefix + name.substring(0, name.lastIndexOf('.')); // Get class name
                // load class
                try {
                    // load class file and create a new UAGetter object
                    UAGetter uaGetter = (UAGetter) Class.forName(className).newInstance();
                    // add uaGetter to list
                    uaGetterList.add(uaGetter);
                } catch (ClassNotFoundException cnfe) {
                    System.err.println("Class \"" + className + "\" not exist");
                    cnfe.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * invoke method fetchUserAgent() of all UAGetter in the list
     */
    public void fetchUA() {
        for (UAGetter uaGetter : uaGetterList)
            uaGetter.fetchUserAgent();
    }

    public String getUaGetterPackagePath() {
        return uaGetterPackagePath;
    }

    public void setUaGetterPackagePath(String uaGetterPackagePath) {
        this.uaGetterPackagePath = uaGetterPackagePath;
    }

    public String getUaGetterPackagePrefix() {
        return uaGetterPackagePrefix;
    }

    public void setUaGetterPackagePrefix(String uaGetterPackagePrefix) {
        this.uaGetterPackagePrefix = uaGetterPackagePrefix;
    }

    public List<UAGetter> getUaGetterList() {
        return uaGetterList;
    }

    public void setUaGetterList(List<UAGetter> uaGetterList) {
        this.uaGetterList = uaGetterList;
    }
}