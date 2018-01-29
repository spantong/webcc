package Ptesting;
//import com.sun.istack.internal.localization.NullLocalizable;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.Assert.fail;

/**
 * Created by 11spantong on 19.10.2017.
 */
public class pEnterShop {
    private static WebDriver driver;
    //    private static String baseUrl;
    private boolean acceptNextAlert = true;
    private static StringBuffer verificationErrors = new StringBuffer();
    private static String fr = "a[href*='europe/fr_fr']"; // link of France country selection
    private static String usa = "a[href*='/north-america/us_en']"; // link of usa country selection
    private static String uk = "a[href*='/europe/uk_en']"; // link of uk country selection


    @BeforeClass
    public static void setUp() throws Exception {

/*
        Properties p = System.getProperties();
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
//
*/
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
//            if(br_param.contentEquals("opera"))
//                driver = new OperaDriver();
        }
        else{
            driver = new ChromeDriver(); // default
        }
    }

    @Test
//  public void main(String[] args)throws Exception {
    public void testEnterShop() throws Exception {

        WebDriverWait wait = new WebDriverWait(driver, 10);
        pShopLoginPage loginPage = new pShopLoginPage(driver);
        try {
            String b_param = System.getProperty("baseUrl.cli"); // get start url
            String baseUrl = b_param; // save baseurl ex cmd parameter
            // System.out.println("baseurl: " +baseUrl);
            driver.get(baseUrl); // go directly to shop login page
            //Assert.assertEquals(driver.getCurrentUrl(), baseUrl + "com/en/home.html");

            wait.until((ExpectedConditions.elementToBeClickable(By.name("pf.username"))));
            loginPage.enterUsername(driver);
            loginPage.enterPassword(driver);
            loginPage.clickShopLoginButton();
            WebElement profile = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='logout']"))));  // check logout menu
            String logout_anchor = profile.getAttribute("href"); // get the link url
            System.out.println(logout_anchor); // output/print the link
            //////xxxx
            // start of <div class="right"> check/processing
            WebElement right_menubar = driver.findElement(By.cssSelector("div[class='right']"));

            List<WebElement> myaccount_links = right_menubar.findElements(By.tagName("a")); // get all avail links
            System.out.println(myaccount_links);
            WebElement item;
            String href;
            String urlitem;
            WebElement child;
            String anchor;
            for (int i=0;i < myaccount_links.size()-1; i++){ // loop through each drop down link and click on it to change to page
                item = myaccount_links.get(i);
//                child = item.findElement(By.tagName("a")); //get anchor
                anchor = item.getAttribute("href"); //get the url of the page
                urlitem = driver.getCurrentUrl(); //get actual page url to compare
                if (!anchor.equals(urlitem)){ // only change if it's not the the same (landingpage)
                    if(!anchor.contains("home") && !anchor.contains("user-management") && !anchor.contains("order-history") && !anchor.contains("documenthistory")){ // skip these pages
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
                right_menubar = driver.findElement(By.cssSelector("div[class='right']"));
                myaccount_links = right_menubar.findElements(By.tagName("a"));
            }
            //////xxxx
            // end of my-account menu check/processing

            driver.get(baseUrl); // go to shop main page
            WebElement logout = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='logout']"))));  // check logout menu

            // start verify shop items
            //urlitem = driver.getCurrentUrl(); //get actual page url to compare
//            pEstoreMainPage estorePage = new pEstoreMainPage(driver); // instanciate shop page object
            WebElement searchy = driver.findElement(By.cssSelector("div[class='search-bar left']"));  // get element shop search box
            List<WebElement> searchInput = searchy.findElements(By.tagName("input"));
            WebElement inputElement = searchInput.get(0); // get the input element
            JavascriptExecutor js = (JavascriptExecutor) driver;  // using javascript
            js.executeScript("arguments[0].setAttribute('value', 'blabla')", inputElement); // input something in the search field

            // end shop checks
            driver.get(baseUrl); // go to shop main page
            profile = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='logout']"))));  // check logout menu
            logout_anchor = profile.getAttribute("href"); // get the link url
            String xxx = driver.getTitle();
            driver.get(logout_anchor);  // Logout user shop
            String yyy = driver.getTitle();
            wait.until((ExpectedConditions.urlContains("/login")));
//            wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href=*'/login/pw/request']"))));

            System.out.println("Logged out from the shop");
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
