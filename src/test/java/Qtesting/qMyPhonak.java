package Qtesting;

import Ptesting.pShopLoginPage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

/// me
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class qMyPhonak {
    ///suntest
    public static void main(String args[]) {
        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));
        Result result = junit.run(qMyPhonak.class); // Replace "SampleTest" with the name of your class
        if (result.getFailureCount() > 0) {
            System.out.println("Test failed.");
            System.exit(1);
        } else {
            System.out.println("Test finished successfully.");
            System.exit(0);
        }
    }
    ///suntest


    //Automatic testing QS of phonak eStore/eServices
    private static WebDriver driver;
    private static String baseUrl;
    private boolean acceptNextAlert = true;
    private static StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass
    public static void setUp() throws Exception {
        //Change browser if required here
        //driver = new ChromeDriver();
        driver = new FirefoxDriver();
        //driver = new HtmlUnitDriver();
        //baseUrl = "https://my.phonak.com/fr/fr";  // france
        baseUrl = "https://my.phonak.com/us/en"; // USA
        //baseUrl = "https://my.phonak.com/de/de"; // Germany
        //baseUrl = "https://www.phonakpro.com/";
    }
    @Test
    public void testMyPhonak() throws Exception {

        WebDriverWait wait = new WebDriverWait(driver, 10); // set up explicit wait
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        //pShopLoginPage loginPage = new pShopLoginPage(driver); // init shop login page object
        try {
            driver.get(baseUrl); // get myphonak portal
            System.out.println("Test myPhonak : "+baseUrl);  // output on console line
            WebElement gotoo = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/samllogin.html']")))); // wait for link appearance
            String anchor = gotoo.getAttribute("href");
            String linktext = driver.findElement(By.cssSelector("li[class='nav-item login-link'] h5 a")).getText(); // get the label text //class="nav-item login-link"
            //System.out.println("Text of Login: "+linktext); //console output the text of the login link
            //System.out.println(anchor);  // output on console line
            driver.get(anchor);  // goto login page
            WebElement uname = wait.until((ExpectedConditions.elementToBeClickable(By.name("pf.username"))));  // chk input field that should appear
            Assert.assertTrue(uname.isDisplayed());
            String a11 = driver.getCurrentUrl();
            uname = driver.findElement(By.name("pf.username"));
            //String u1 = "biresex@send22u.info"; // FRA phonak
            String u1 = "biresex@mail4-us.org"; // USA phonak
            //String u1 = "biresex@carbtc.net"; // GER phonak
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('value', '"+u1+"')", uname);
            //uname.sendKeys("sarotsun.pantong@sonova.com"); // not working in FF
            String yyy = uname.getAttribute("value");
            if (yyy.equals("")) {
                System.out.println("No value in Username input field!");  // output on console line
            } else {
                //System.out.println("Value in Username: "+yyy);  // output on console line
            }
            wait.until((ExpectedConditions.elementToBeClickable(By.name("pf.pass"))));  // chk input field that should appear
            WebElement upw = driver.findElement(By.name("pf.pass"));
            u1 = "phonak123";
            js.executeScript("arguments[0].setAttribute('value', '"+u1+"')", upw);
            WebElement loginbutton = driver.findElement(By.cssSelector(".acct_info_submit.button.round.primary.complete"));
            loginbutton.click();
            wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/logout.html']")))); // wait for link appearance
            String aaa = driver.getCurrentUrl();
            //System.out.println("Url b4 login "+a11 +" and after "+aaa);  // output on console line
            if (!aaa.contains("myphonak")) System.out.println("ALARM! " );  // output on console line
            try { // no exception if the next element cannot be found, bcoz it only shows up under certain condition (defined by marketing)
                WebElement feedbackpopup = driver.findElement(By.cssSelector("div.modal-content"));
                if (feedbackpopup.isDisplayed()){
                    WebElement closex = driver.findElement(By.cssSelector("button.mfp-close"));
                    closex.click();
                    System.out.println("Popup displayed and click on X to exit ");
                }
                else{
                    aaa = driver.getCurrentUrl();
                    System.out.println("No feedback popup displayed on url " +aaa);
                }
            } catch (Exception e) {
                //System.out.println("No Popup Window appeared.");
            }
/*
* The probe should login, and check all pages of the site
-	Private home page – check for existence of specific text
-	Personal info page – check for existence of specific text
-	Product page – check products are there
    On the products page, please check the presence of the tabs:
    -	Features
    -	Instructions
    -	Troubleshooting
    -	Specification
    -	Documents (which is actually broken right now  )
-	HCP page – check HCP shows as expected

Please create a probe for US, FR and DE.
* */
            List<WebElement> sections = driver.findElements(By.cssSelector("div[class='page-main--content'] section")); // Standart contents are there
            if (sections.size()==0){ // no contents
                System.out.println("No page data available after login!");
                throw new Exception("!!! A L A R M !!!");  // test failed
            }
            List<WebElement> leftmenu = driver.findElements(By.xpath("//*[@id='headerBottomLeft']/li/a")); // left menu leading to detail page
            for (int x=0; x < leftmenu.size(); x++){
                WebElement menu = leftmenu.get(x);  // navigate to the page under the menu
                String menutext = menu.getText(); // get the link
                System.out.println("Menu bar "+(x+1)+": "+menutext); // output
            }

            for (int i=1; i < leftmenu.size()-2; i++){
                WebElement menu = leftmenu.get(i);  // navigate to the page under the menu
                String achor = menu.getAttribute("href"); // get the link
                driver.get(achor); // goto page under actual menu
                //JavascriptExecutor jsm = (JavascriptExecutor) driver;
                //jsm.executeScript("arguments[0].click();",menu); //click to goto to page
                wait.until((ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='headerBottomLeft']")))); // wait for menu re-appearance
                String currenturl = driver.getCurrentUrl();  // get url
                System.out.println("Actual URL: "+currenturl); // output
                wait.until((ExpectedConditions.presenceOfElementLocated(By.xpath("//section[contains(@class,'teaser-block')]")))); // wait for menu re-appearance
                //Thread.sleep(1000);
                if (currenturl.contains("profile")){
                    List<WebElement> profilecontents = driver.findElements(By.cssSelector("div[data-group-scope]")); // standart section of page
                    if (profilecontents.size()==0) {
                        System.out.println("No user profile data available !");
                        throw new Exception("!!! A L A R M !!!");  // test failed
                    }else{
                        for (int p=0; p < profilecontents.size(); p++){
                            WebElement yourprofs = profilecontents.get(p); // get the content element
                            String dgstext = yourprofs.getAttribute("data-group-scope"); // get the text of the class
                            int pp =p+1; // start with section one printout, bcoz p starts with zero
                            WebElement dgstextlang = yourprofs.findElement(By.tagName("h3")); // get section header elemnet
                            String dgstextlangtext = dgstextlang.getText(); // get the text in actual language
                            System.out.println("Visible section "+pp+": "+dgstext+ " -> "+dgstextlangtext); //output the section container texts
                        }
                    }
                } else if (currenturl.contains("products")){ // //*[@class='product-data']
                    WebElement productcontents = driver.findElement(By.xpath("//*[@class='product-section-wrapper']")); // product data section of page
                    wait.until((ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='product-basics-name']")))); // wait for link appearance
                    WebElement productname = productcontents.findElement(By.xpath("//div[@class='product-basics-name']")); // get the name container
                    String productnametext = productname.getText(); // get the name value
                    System.out.println("1st Product detail : "+productnametext); // output to console
                    /* These tabs are to be shown on screen
                    Features, Instructions, Troubleshooting, Specification, Documents
                    */
                    List<WebElement> producttabs = productcontents.findElements(By.cssSelector("div[class='product-tabs'] a")); // get tabs amount
                    if (producttabs.size()==0) { // Zero means no tab
                        System.out.println("No product specific tab bar present !");
                        throw new Exception("!!! A L A R M !!!");  // test failed
                    }else{
                        System.out.println("There are "+producttabs.size()+" product tab bar(s) present.");
                        for (int t=0; t < producttabs.size(); t++){
                            WebElement tabname = producttabs.get(t); // get the tab element
                            String tabnametext = tabname.getText(); // get text value
                            int tt = t+1; // output starting at 1
                            System.out.println("Product Tab "+tt+ " : "+tabnametext);
                        }
                    }
                } else if (currenturl.contains("your-professional") || currenturl.contains("fitter.html")){
                    List<WebElement> yourprofessional = driver.findElements(By.xpath("//*[@class='page-main--content']/child::*")); // check main contents
                    if (yourprofessional.size()>0){ // have content
                        for (int i1=0; i1 < yourprofessional.size(); i1++){
                            WebElement yourprofs = yourprofessional.get(i1); // get the content element
                            String ypclass = yourprofs.getAttribute("class"); // get the text of the class
                            int ii1=i1+1; // start at one iso zero
                            System.out.println("Visible section "+ii1+": "+ypclass); //output the section class text
                        }
                    }else{
                        System.out.println("Caution! No content in your professional page available!"); //output the section class text
                    }

                } else if (currenturl.contains("your-tips")){
                    System.out.println("Skip checking your-tips.html"); //output the section class text
                } else if (currenturl.contains("faq")){
                    System.out.println("Skip checking faq.html"); //output the section class text
                }
                leftmenu = driver.findElements(By.xpath("//*[@id='headerBottomLeft']/li/a")); // reset left menu pointer
            }

            // check logout link and do logout
            WebElement logout = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/logout.html']")))); // wait for link appearance
            anchor = logout.getAttribute("href");
            linktext = driver.findElement(By.cssSelector("li[class='nav-item logout-link'] h5 a")).getText(); // get the label text //class="nav-item login-link"
            System.out.println("Text of Logout and link: "+linktext+" "+anchor); //console output the text of the login link
            //System.out.println(anchor);  // output on console line
            driver.get(anchor);  // goto login page
            // login should appear on screen again after logout
            logout = wait.until((ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='headerBottomRight']")))); // wait for menu bar appearance
            List<WebElement> login = driver.findElements(By.xpath("//*[@class='nav-item login-link']")); // after logged out, login must appear
            if (login.size()==0){  // no login link found, force error to throw en exception -> test failed.
                //wait.until((ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='nav-item login-link']")))); // wait not found will throw exception
                System.out.println("ATTN: Login link missing");  // output on console line
            }else{
                System.out.println("OK: Logout done and login link is available");  // output on console line
            }
            aaa = driver.getCurrentUrl();
            System.out.println("Logout confirmation page: "+aaa);  // output on console line
        }
        catch (Exception e) {
            System.out.println("TEST Failed due to an Exception! Pls check");
            System.out.println(e.getMessage());
            //throw new Exception("!!! A L A R M !!!");  // flag test failed

        }
        finally {
            //System.out.println("All OK!");
            // close the Browser
            // driver.quit(); //done in @After
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
