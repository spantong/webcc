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
import static org.junit.Assert.fail;

public class qMyPhonak {

    //Automatic testing QS of phonak eStore/eServices
    private static WebDriver driver;
    private static String baseUrl;
    private boolean acceptNextAlert = true;
    private static StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass
    public static void setUp() throws Exception {
        //Change browser if required here
        driver = new FirefoxDriver();
        //driver = new ChromeDriver();
        //driver = new HtmlUnitDriver();
        baseUrl = "https://my.phonak.com/us/en";
        //baseUrl = "https://www.phonakpro.com/";
    }
    @Test
    public void testQMyPhonak() throws Exception {

        WebDriverWait wait = new WebDriverWait(driver, 10); // set up explicit wait
        pShopLoginPage loginPage = new pShopLoginPage(driver); // init shop login page object
        try {
            driver.get(baseUrl); // get myphonak portal
            WebElement gotoo = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/samllogin.html']")))); // wait for link appearance
            String anchor = gotoo.getAttribute("href");
            String linktext = driver.findElement(By.cssSelector("li[class='nav-item login-link'] h5 a")).getText(); // get the label text //class="nav-item login-link"
            System.out.println("Text of Login: "+linktext); //console output the text of the login link
            System.out.println(anchor);  // output on console line
            driver.get(anchor);  // goto login page
            WebElement uname = wait.until((ExpectedConditions.elementToBeClickable(By.name("pf.username"))));  // chk input field that should appear
            Assert.assertTrue(uname.isDisplayed());
            String a11 = driver.getCurrentUrl();
            uname = driver.findElement(By.name("pf.username"));
            String u1 = "sarotsun.pantong@sonova.com";
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('value', '"+u1+"')", uname);
            //uname.sendKeys("sarotsun.pantong@sonova.com"); // not working in FF
            String yyy = uname.getAttribute("value");
            if (yyy.equals("")) {
                System.out.println("No value in Username input field!");  // output on console line
            } else {
                System.out.println("Value in Username: "+yyy);  // output on console line
            }
            wait.until((ExpectedConditions.elementToBeClickable(By.name("pf.pass"))));  // chk input field that should appear
            WebElement upw = driver.findElement(By.name("pf.pass"));
            u1 = "phonak123";
            js.executeScript("arguments[0].setAttribute('value', '"+u1+"')", upw);
            // upw.sendKeys("phonak123"); // this throws exception in FF
            WebElement loginbutton = driver.findElement(By.cssSelector(".acct_info_submit.button.round.primary.complete"));
            loginbutton.click();
            wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/logout.html']")))); // wait for link appearance
            String aaa = driver.getCurrentUrl();
            System.out.println("Url b4 login "+a11 +" and after "+aaa);  // output on console line
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
                System.out.println("No Popup Window appeared.");
            }

            // check logout link and do logout
            WebElement logout = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/logout.html']")))); // wait for link appearance
            anchor = logout.getAttribute("href");
            linktext = driver.findElement(By.cssSelector("li[class='nav-item logout-link'] h5 a")).getText(); // get the label text //class="nav-item login-link"
            System.out.println("Text of Logout: "+linktext); //console output the text of the login link
            System.out.println(anchor);  // output on console line
            driver.get(anchor);  // goto login page
            // login should appear on screen again after logout
            logout = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/samllogin.html']")))); // wait for link appearance
            aaa = driver.getCurrentUrl();
            System.out.println("Logout confirmation page: "+aaa);  // output on console line
        }
        catch (Exception e) {
//            e.printStackTrace();
            System.out.println("TEST Failed due to an Exception! Pls check");
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
