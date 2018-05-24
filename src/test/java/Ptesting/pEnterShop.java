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
import java.util.concurrent.TimeUnit;

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
    private static String shop_anchor, c_param;

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

        WebDriverWait wait = new WebDriverWait(driver, 60);
        pShopLoginPage loginPage = new pShopLoginPage(driver);
        try {
            c_param = System.getProperty("country.cli");

            if(c_param != null){ // check if there is a value entered with cmd line
                System.out.println("country = " +c_param);
                if(c_param.contentEquals("fr"))
                    //country = fr;
                if(c_param.contentEquals("us"))
                    //country = usa;
                if(c_param.contentEquals("uk"))
                    //country = uk;
                if(c_param.contentEquals("ca")){}
                    //country = ca;
                //System.out.println("country.cli = " +country);
            }
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
            System.out.println("Total menus : "+myaccount_links.size());
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
                    //if(!anchor.contains("home") && !anchor.contains("user-management") && !anchor.contains("order-history") && !anchor.contains("documenthistory")){ // skip these pages
                    if(!anchor.contains("home") && !anchor.contains("logout")){
                        if (!anchor.contains("user-management")) {  // UM is not working if user role is professional or viewer !
                            long startTime = System.nanoTime();
                            driver.get(anchor);  // change to that page (clicking somehow not working here)
                            if (anchor.contains("order-history")) { // just for debugging
                                List<WebElement> testa = driver.findElements(By.cssSelector("div[class='history-list waiting']"));
                                if (testa.size()>0){
                                    WebElement testaElement = testa.get(0);;
                                    System.out.println("Spinner content: "+testaElement.getAttribute("onerHTML"));
                                    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[class='history-list waiting']")));
                                    long difference = System.nanoTime() - startTime;
                                    System.out.println("Total OH execution time: " +
                                            String.format("%d min, %d sec",
                                                    TimeUnit.NANOSECONDS.toHours(difference),
                                                    TimeUnit.NANOSECONDS.toSeconds(difference) -
                                                            TimeUnit.MINUTES.toSeconds(TimeUnit.NANOSECONDS.toMinutes(difference))));
                                    //startTime = System.nanoTime(); // reset

                                }
                            }
                            if (anchor.contains("salesforce")) { // Lyric/Alps/Salesforce processing
                                wait.until(ExpectedConditions.urlContains("salesforce"));
                                System.out.println("Jump to Alps URL : "+driver.getCurrentUrl());
                                driver.navigate().back(); // go back to shop
                                driver.navigate().back(); // go back to shop
                                wait.until(ExpectedConditions.urlContains("shop."));
                                System.out.println("Return to Shop URL : "+driver.getCurrentUrl());
                            }

                        } else { // Special processing for user profile Professional and viewer, bcoz they are not allow to access user management
                            List<WebElement> sun_testing_now = driver.findElements(By.cssSelector("script"));  // get all <SCRIPT> elements
                            System.out.println("SCRIPT tags found: " + sun_testing_now.size());  // show how many are there
                            for (int s=0; s < sun_testing_now.size(); s++){ // loop through the list to get the one with user role in it
                                WebElement sun = sun_testing_now.get(s);  // set the actual from the list
                                boolean scriptFound = sun.getAttribute("innerHTML").contains("dataLayer ="); // check the data started with this content
                                if (scriptFound) {  // Yes this one has the user role in it
                                    String scriptText = sun.getAttribute("innerHTML"); // get the string text
                                    if (scriptText.contains("Professional") || scriptText.contains("Viewer")){ // skip this as it shows error (should be fixed by Michael R. acc. Jira)
                                        System.out.println("SCRIPT item nr. "+s+" content: " + scriptText);
                                        System.out.println("This page is skipped: " + anchor+" Professional and Viewer role are not allowed to access UM.");
                                        break; // exit loop without visit UM, due this will get blocked by processing w. error
                                    }
                                    System.out.println("SCRIPT item nr. "+s+" content: " + scriptText);
                                    // cont'd to user management page for all other user roles than the two above
                                    driver.get(anchor);  // change to that page (clicking somehow not working here, use driver.get)
                                    break; // exit loop since we are now not on the same page  anymore
                                }
                            }

                        }
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
// test subroutines
            pEnterB2bShop.eCartClear(driver);
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
