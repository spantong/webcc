package Ptesting;

//import com.sun.istack.internal.localization.NullLocalizable;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.Assert.fail;

//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;
//import org.openqa.selenium.ie.InternetExplorerDriver;

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

//    WebElement gotoo = wait.until((ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='europe/fr_fr']")))); // France country selection
//          WebElement gotoo = wait.until((ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='/north-america/us_en']")))); // USA
//          WebElement gotoo = wait.until((ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='/europe/uk_en']")))); // UK

    @BeforeClass
    public static void setUp() throws Exception {
        //driver = new FirefoxDriver();
        driver = new ChromeDriver();
    }

    @Test
//    public void main(String[] args)throws Exception {
    public void testTestLogin51abrownCom() throws Exception {

        WebDriverWait wait = new WebDriverWait(driver, 10);
        shopLoginPage loginPage = new shopLoginPage(driver);
        try {
            // Start on b2b landing/home page
            loginPage.loadB2bPage(driver);
            loginPage.clickChange(driver);

            // Get command line input if started that way or via contineous integration (CI)
            String country=usa; // set up default country
            System.out.println("Default country selection = " +country);
            String c_param = System.getProperty("country.cli");

            if(c_param != null){ // check if there is a value entered with cmd line
                if(c_param.contentEquals("fr"))
                    country = fr;
                if(c_param.contentEquals("us"))
                    country = usa;
                if(c_param.contentEquals("uk"))
                    country = uk;

                System.out.println("country.cli = " +country);
            }

            WebElement gotoo = wait.until((ExpectedConditions.elementToBeClickable(By.cssSelector(country)))); // wait for specific element country selection
            gotoo.click();

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

            /* Exclude these lines because after refresh issue was fixed
            String url = driver.getCurrentUrl();
            if(!url.contains("/phonakus")) {
                driver.get("https://q-shop.phonakpro.com/phonakus/en/USD/");
            }
            else {
                List<WebElement> logoutBtn = driver.findElements(By.cssSelector("a[href='/system/sling/logout.html']"));
                if (!logoutBtn.isEmpty()) {
                    WebElement logoutnBtnElement = logoutBtn.get(0);
                    //                boolean viewable = logoutnBtnElement.isDisplayed(); //hidden, it's only visible with mouseover
                    if (logoutnBtnElement.isDisplayed()) {
                        //System.out.println("Login is displayed");
                        logoutnBtnElement.click();
                    } else {
                        System.out.println("Login is NOT displayed");
                        //JavascriptExecutor js = (JavascriptExecutor) driver;
                        JavascriptExecutor js = (JavascriptExecutor) driver;
                        js.executeScript("arguments[0].click();", logoutnBtnElement);
                    }

                }
                //	End no redirect to USA store
            }
            */ // End of issue

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
                    if(!anchor.contains("user-management")){ // skip user-management due to long listing
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
                    System.out.println("ATTN: Wrong page " + urlitem + ", should show this page " + anchor ); // not the wanted page appears!
                }
                // set these two vars again due ref is somehow lost!
                myaccount_menubar = driver.findElement(By.cssSelector("div[class='account-name']"));
                myaccount_links = myaccount_menubar.findElements(By.tagName("li"));
            }
            // end of my-account drop down check/processing

            /* // start hero menu-bar
            WebElement menubar = driver.findElement(By.cssSelector("div[class='menu-bar']"));

            List<WebElement> mblink = menubar.findElements(By.tagName("li")); //get all avail links
            System.out.println(mblink);
            WebElement item;
            String href;
            String urlitem;
            WebElement child;
            for (int i=0;i < mblink.size(); i++){
                item = mblink.get(i); //get each link and click on it to change to page
                child = item.findElement(By.tagName("a")); //get anchor
                href = child.getAttribute("href"); //get the url of the page
                child.click(); // go to that page
                urlitem = driver.getCurrentUrl(); //get actual page url to compare
                if (href.equals(urlitem)){
                   System.out.println("This page is displayed: " + href);
                }
                // set these two vars again due ref is somehow lost!
                menubar = driver.findElement(By.cssSelector("div[class='menu-bar']"));
                mblink = menubar.findElements(By.tagName("li"));
            }
            */ // end hero menu-bar

            List<WebElement> logoutBtn = driver.findElements(By.id("id_accountmenu_logout_link"));
            if (!logoutBtn.isEmpty()) {
                WebElement logoutBtnElement = logoutBtn.get(0);
                anchor = logoutBtnElement.getAttribute("href");
                System.out.println(anchor); // console output logout link
                /* no logout ex shop switch to b2b portal and do logout there
                // use javascript to click on invisible element (visible only if mouse moved over it)
                if (logoutBtnElement.isDisplayed()) {
                    System.out.println("Logout is displayed");
                    logoutBtnElement.click();
                } else {
                    System.out.println("Logout is NOT displayed");
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].click();", logoutBtnElement);
                }
                */ // no logout ex shop
            }
            else{
                System.out.println("Logout not possible, missing that element!");
            }

            // start verify shop items
            boolean shoplink = isElementPresent(By.cssSelector("li[class='homenav-li'] a[class='homenav-aa']"));
            if(shoplink) {  // shop link is available
                String actualText = driver.findElement(By.cssSelector("li[class='homenav-li'] a[class='homenav-aa']")).getText();
                System.out.println("Shop link name: " +actualText);
                WebElement store = driver.findElement(By.cssSelector("li[class='homenav-li'] a[class='homenav-aa']")); // Store link
                store.click(); // enter the shop

                wait.until((ExpectedConditions.urlContains("shop.phonakpro.com")));
                urlitem = driver.getCurrentUrl(); //get actual page url to compare
                if (store.equals(urlitem)){
                    estoreMainPage estorePage = new estoreMainPage(driver);
                    WebElement searchy = driver.findElement(By.cssSelector("div[class='search-bar left']"));
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].setAttribute('value', 'blabla')", searchy);

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
