package Ptesting;

//import com.sun.istack.internal.localization.NullLocalizable;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.fail;

/**
 * Created by 11spantong on 19.10.2017.
 */
public class enterB2bShop {
    private static WebDriver driver;
//    private static String baseUrl;
    private boolean acceptNextAlert = true;
    private static StringBuffer verificationErrors = new StringBuffer();
    private static String fr = "a[href*='europe/fr_fr']"; // link of France country selection
    private static String usa = "a[href*='/north-america/us_en']"; // link of usa country selection
    private static String uk = "a[href*='/europe/uk_en']"; // link of uk country selection
    private static String ca = "a[href*='/north-america/ca_en']"; // link of ca en country selection



    @BeforeClass
    public static void setUp() throws Exception {

/*      Properties p = System.getProperties();
        Enumeration keys = p.keys();
        while (keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            String value = (String)p.get(key);
            System.out.println(key + ": " + value);
        }

        Properties props = new Properties();
        props.setProperty("1", "One");
        props.setProperty("2", "Two");
        props.setProperty("3", "Three");
        props.setProperty("4", "Four");
        props.setProperty("5", "Five");

        // Iterating properties using Enumeration

        @SuppressWarnings("unchecked")
        Enumeration<String> enums = (Enumeration<String>) props.propertyNames();
        while (enums.hasMoreElements()) {
          String key = enums.nextElement();
          String value = props.getProperty(key);
          System.out.println(key + " : " + value);
        }
//*/
        String br_param = System.getProperty("browser.cli");

        if(br_param != null){ // check if there is a value entered with cmd line
            System.out.println("Browser = " +br_param);
            if(br_param.contentEquals("firefox"))
                driver = new FirefoxDriver();
            if(br_param.contentEquals("chrome"))
                driver = new ChromeDriver(); // default
            if(br_param.contentEquals("ie"))
                driver = new InternetExplorerDriver();
            if(br_param.contentEquals("edge"))
                driver = new EdgeDriver();
            if(br_param.contentEquals("opera"))
                driver = new OperaDriver();
        }
        else{
            driver = new ChromeDriver(); // default
        }
    }

    @Test
//  public void main(String[] args)throws Exception {
    public void testTestLogin51abrownCom() throws Exception {

        WebDriverWait wait = new WebDriverWait(driver, 10);
        shopLoginPage loginPage = new shopLoginPage(driver);
        try {
            String b_param = System.getProperty("baseUrl.cli"); // get start url
            String baseUrl = b_param; // save baseurl ex cmd parameter
            loginPage.loadB2bPage(driver);
            loginPage.clickChange(driver);

            // Get command line input if started that way or via contineous integration (CI)
            String country=usa; // set up default country
            //System.out.println("Default country selection = " +country);
            String c_param = System.getProperty("country.cli");

            if(c_param != null){ // check if there is a value entered with cmd line
                System.out.println("country = " +c_param);
                if(c_param.contentEquals("fr"))
                    country = fr;
                if(c_param.contentEquals("us"))
                    country = usa;
                if(c_param.contentEquals("uk"))
                    country = uk;
                if(c_param.contentEquals("ca"))
                    country = ca;
                //System.out.println("country.cli = " +country);
            }

            WebElement gotoo = wait.until((ExpectedConditions.elementToBeClickable(By.cssSelector(country)))); // wait for specific element country selection
            String gotoo_anchor = gotoo.getAttribute("href"); //get the url of the link
            System.out.println(gotoo_anchor);  // output on console line
            gotoo.click();  // result will be according AEM setting

            // Check in case we are testing on other system then P, that we are on that environment after click on country selection which is static to go to P most of the time.
            // Get command line input if started that way or via contineous integration (CI)
            // String b_param = System.getProperty("baseUrl.cli");

            if(b_param != null){ // check if there is a value entered with cmd line
                //System.out.println("baseUrl.cli = " +baseUrl);  // output actual value
                String c_url = driver.getCurrentUrl(); // get actual url
                if(!c_url.contains(baseUrl)){ // not on intented test system environment
                    System.out.println("You are on " +c_url+ " and NOT on " +baseUrl );  // output where you are and where it should be
                    // manipulate url by using baseUrl
                    // replace starting part with the baseUrl value
                    URL aURL = new URL(c_url); // using url class splitting
                    /*
                    System.out.println("protocol = " + aURL.getProtocol());
                    System.out.println("authority = " + aURL.getAuthority());
                    System.out.println("host = " + aURL.getHost());
                    System.out.println("port = " + aURL.getPort());
                    System.out.println("path = " + aURL.getPath());
                    System.out.println("query = " + aURL.getQuery());
                    System.out.println("filename = " + aURL.getFile());
                    System.out.println("ref = " + aURL.getRef());
                    baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
                    String xxx=baseUrl+aURL.getPath();
                    System.out.println("xxx = " + xxx);
                    */
                    driver.get(baseUrl + aURL.getPath());  // change test environment

                }
            } // end test system switch

            wait.until((ExpectedConditions.urlContains("/home")));
            loginPage.clickCountryLogin(driver);

            wait.until((ExpectedConditions.elementToBeClickable(By.name("pf.username"))));
            loginPage.enterUsername(driver);
            loginPage.enterPassword(driver);
            loginPage.clickShopLoginButton();
            WebElement profile = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='logout']"))));  // check logout menu
            String logout_anchor = profile.getAttribute("href"); // get the link url
            //System.out.println(logout_anchor); // output/print the link
            //get the exact link value of my-profile
            profile = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/account']"))));
            String anchor = profile.getAttribute("href");
            //System.out.println(anchor);  // output on console line
            //Set var again, bcoz click() only possible with exact link value
            profile = driver.findElement(By.cssSelector("a[href='"+anchor+"']"));
            //check/validate bcoz default it's invisible
            if (profile.isDisplayed()) {
                //System.out.println("my-profile is displayed");  // console output
                profile.click();
            } else {
                //System.out.println("my-profile is NOT displayed"); // console output
                JavascriptExecutor js = (JavascriptExecutor) driver; // use javascript on client/browser
                js.executeScript("arguments[0].click();", profile); // click on invisible item with javascript code
            }

            // Switch to shop/eservices page
            WebElement logout = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/saml/logout']")))); // check page loaded and logout menu is there

            // start of my-account drop down menu check/processing
            WebElement myaccount_menubar = driver.findElement(By.cssSelector("div[class='account-name']"));

            List<WebElement> myaccount_links = myaccount_menubar.findElements(By.tagName("li")); // get all avail drop down links
            System.out.println(myaccount_links);
            WebElement item;
            String href;
            String urlitem;
            WebElement child;
            for (int i=0;i < myaccount_links.size()-1; i++){ // loop through each drop down link and click on it to change to page
                item = myaccount_links.get(i);
                child = item.findElement(By.tagName("a")); //get anchor
                anchor = child.getAttribute("href"); //get the url of the page
                urlitem = driver.getCurrentUrl(); //get actual page url to compare
                if (!anchor.equals(urlitem)){ // only change if it's not the the same (landingpage)
                    if(!anchor.contains("user-management") && !anchor.contains("order-history") && !anchor.contains("documenthistory") && !anchor.contains("salesforce")){ // skip user-management order- and documenthistory due to long processing
                        driver.get(anchor);  // change to that page (clicking somehow not working here)
                    }
                    else{
                        System.out.println("This page is skipped: " + anchor);
                    }
                }
                wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/saml/logout']")))); // check page loaded and logout menu is there
                urlitem = driver.getCurrentUrl(); //get actual page url to compare
                if (anchor.equals(urlitem)){
                    System.out.println("This page is displayed: " + anchor);
                }
                else{
                    System.out.println("ATTN: Wrong page " + urlitem + ", should show this page " + anchor ); // output warning that not the same page is showing as in the link!
                }
                // reset for the loop these two vars again due reference is somehow lost!
                myaccount_menubar = driver.findElement(By.cssSelector("div[class='account-name']"));
                myaccount_links = myaccount_menubar.findElements(By.tagName("li"));
            }

            List<WebElement> logoutBtn = driver.findElements(By.id("id_accountmenu_logout_link"));
            if (!logoutBtn.isEmpty()) {
                WebElement logoutBtnElement = logoutBtn.get(0);
                anchor = logoutBtnElement.getAttribute("href");
                System.out.println(anchor); // console output logout link
            }
            else{
                System.out.println("Logout not possible, missing that element!");
            }
            // end of my-account drop down check/processing

            // start verify shop items
            boolean shoplink = isElementPresent(By.cssSelector("li[class='homenav-li'] a[class='homenav-aa']"));
            if(shoplink) {  // shop link is available
                String actualText = driver.findElement(By.cssSelector("li[class='homenav-li'] a[class='homenav-aa']")).getText(); // get the label text
                System.out.println("Shop link name: " +actualText); // output to show language specific text
                WebElement store = driver.findElement(By.cssSelector("li[class='homenav-li'] a[class='homenav-aa']")); // get the Store link
                String shop_anchor = store.getAttribute("href");  // get and save the exact url
                store.click(); // enter the shop, switch from eservices to estore
                wait.until((ExpectedConditions.urlContains("shop.phonakpro.com")));
                urlitem = driver.getCurrentUrl(); //get actual page url to compare
                if (shop_anchor.equals(urlitem)){  //current main shop page is same as in the link
                    estoreMainPage estorePage = new estoreMainPage(driver); // instanciate shop page object
                    WebElement searchy = driver.findElement(By.cssSelector("div[class='search-bar left']"));  // get element shop search box
                    List<WebElement> searchInput = searchy.findElements(By.tagName("input"));
                    WebElement inputElement = searchInput.get(0); // get the input element
                    JavascriptExecutor js = (JavascriptExecutor) driver;  // using javascript
                    js.executeScript("arguments[0].setAttribute('value', 'blabla')", inputElement); // input something in the search field

                    Assert.assertTrue(isElementPresent(driver, By.cssSelector("div[class='search-bar left']"))); // check search bar is present
                    estorePage.inputSearchBar(driver);
                }

            }
            // end shop checks

            // switch to b2b portal and logout there
            WebElement pagetitle = driver.findElement(By.cssSelector("div[class='pagetitle'] a")); // b2b home link
            String pt_anchor = pagetitle.getAttribute("href"); // get the link value to click on
            if (pt_anchor.contains("/home.html")){ // link points to /home.html
                driver.get(pt_anchor);  // change to that page
                profile = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='logout']"))));  // check logout menu
                anchor = profile.getAttribute("href");
                driver.get(anchor);  // Logout user from b2b
//            wait.until((ExpectedConditions.urlContains("/home")));
                wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href='/bin/phonakpro/login']"))));
                System.out.println("Logged out from b2b portal");
            }
            else{ // cannot switch to b2b portal due link is not valid
                driver.get(anchor);  // Logout user from shop menu
                System.out.println("Logged out from the shop");
            }
        }
/*        catch (Exception e) {
            e.printStackTrace();
        }
*/

        finally {
            // close the Browser
            // driver.quit(); //do this in @AfterClass
        }
    }

    @AfterClass
    public static void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

/*    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
*/
    private static boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private static boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }


    public boolean isElementPresent(WebDriver driver, String className) {
        return driver.findElements(By.className(className)).size() != 0;
    }

    public boolean isElementPresent(WebDriver driver, By by) {

        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
