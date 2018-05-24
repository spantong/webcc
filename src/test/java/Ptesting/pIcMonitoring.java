package Ptesting;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

public class pIcMonitoring {
    ///suntest
    public static void main(String args[]) {
        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));
        Result result = junit.run(pIcMonitoring.class); // Replace "SampleTest" with the name of your class
        if (result.getFailureCount() > 0) {
            System.out.println("Test failed.");
            System.exit(1);
        } else {
            System.out.println("Test finished successfully.");
            System.exit(0);
        }
    }


    //Automatic testing QS of phonak eStore/eServices
    private static WebDriver driver;
    private static String baseUrl, anchor, linktext;
    private boolean acceptNextAlert = true;
    private static StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass
    public static void setUp() throws Exception {
        //Change browser if required here
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        baseUrl = "https://www.phonakpro.com/us/en/home.html";
    }
    @Test
    public void testICPhonak() throws Exception {

        WebDriverWait wait = new WebDriverWait(driver, 10); // set up explicit wait
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        pShopLoginPage loginPage = new pShopLoginPage(driver); // init shop login page object
        try {
            driver.get(baseUrl); // get myphonak portal
            System.out.println("Test Innercircle : "+baseUrl);  // output on console line
            wait.until((ExpectedConditions.urlContains("/home")));
            loginPage.clickCountryLogin(driver);
            WebElement uname = wait.until((ExpectedConditions.elementToBeClickable(By.name("pf.username"))));  // chk input field that should appear
            Assert.assertTrue(uname.isDisplayed());
            String a11 = driver.getCurrentUrl();
            uname = driver.findElement(By.name("pf.username"));
            String u1 = "icusamonitor"; // IC USA phonak
            //String u1 = "ictester"; // IC USA phonak
            //u1 = "icsun"; // temp user IC USA phonak
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('value', '"+u1+"')", uname);
            String yyy = uname.getAttribute("value");
            if (yyy.equals("")) {
                System.out.println("No value in Username input field!");  // output on console line
            } else {
                //System.out.println("Value in Username: "+yyy);  // output on console line
            }
            wait.until((ExpectedConditions.elementToBeClickable(By.name("pf.pass"))));  // chk input field that should appear
            WebElement upw = driver.findElement(By.name("pf.pass"));
            u1 = "spinning4ic";
            //u1 = "test1234"; // temp
            //u1 = "phonak123"; // temp
            js.executeScript("arguments[0].setAttribute('value', '"+u1+"')", upw);

            loginPage.clickShopLoginButton();
            WebElement profile = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='logout']"))));  // check logout menu
            String aaa = driver.getCurrentUrl();

            if (!aaa.contains("home")) System.out.println("ALARM! Not on home.html page" );  // output on console line

            List<WebElement> icnav = driver.findElements(By.cssSelector("li[id='ic-nav'][class*='store']")); // Inner Circle nav
            if (icnav.size()==0){ // no contents  ==0
                System.out.println("No Inner Circle available!");
                throw new Exception("!!! A L A R M !!!");  // test failed
            }
            System.out.println("Logged in successfully in Inner Circle." );
            WebElement menu = icnav.get(0);  // get the menu to click on
            WebElement icancher = menu.findElement(By.tagName("a")); // get the anchor element
            js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();",icancher); //click to goto to ic page
            System.out.println("Current URL: "+driver.getCurrentUrl()); // output where you are
            // check for the modal popup
            Thread.sleep(3000);
            List<WebElement> popModal5Star = driver.findElements(By.cssSelector("div.IC_Five_Star_Center-slide.is-active")); // if popup appear it will have two elements on screen
            if (popModal5Star.size()>1){ // stupid lightbox popup blocking screen is there - just click it away on No thank you button
                driver.findElement(By.cssSelector("a.button.transparent.js-five-star-remind-never")).click(); // go away
                System.out.println("Lightbox popup : [Please join our Phonak 5-Star Center] suppressed because it blocked the screen!");
            }
            wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("li[id='ic-nav'][class*='store']")))); // wait for same ic menu appearance on the new page
            wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.IC_Progress_Bar-sections")))); // wait for IC progress bar
            wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.IC_User_Bar-user")))); // wait for user to appear
            WebElement userbox = driver.findElement(By.cssSelector("div.IC_User_Bar-boxes")); // get the user box element

            for (int second = 0;; second++) {
                if (second >= 60) fail("timeout");
                try { if (!"".equals(driver.findElement(By.cssSelector("div.IC_User_Bar-userName")).getText())) break; } catch (Exception e) {}
                Thread.sleep(1000);
            }
            WebElement username = userbox.findElement(By.cssSelector("div.IC_User_Bar-userName")); // get the user name element
            String usernamevalue = username.getText(); // get the username value
            //System.out.println("Username on screen is : "+usernamevalue); // output
            List<WebElement> userlevel = userbox.findElements(By.cssSelector("div.IC_User_Bar-userLevel"));
            if (userlevel.size()>0){
                for (int l=0; l < userlevel.size(); l++){
                    WebElement userlevelvisible = userlevel.get(l); // get user level element
                    String ulvisibility = userlevelvisible.getAttribute("class");
                    if (ulvisibility.contains("is-visible")){ // flagged as visible on screen
                        String ulvtext = userlevelvisible.getText(); // get the value
                        System.out.println("User : "+usernamevalue+ ", and IC Level is : "+ulvtext); // output
                    }
                }
            }
            // check 5 star status
            List<WebElement> fiveStar = userbox.findElements(By.cssSelector("div.IC_User_Bar-fiveStarText.is-populated"));
            if (fiveStar.size()>0){
                WebElement fiveStarElement = fiveStar.get(0); // get 5* element
                String fiveStarText = fiveStarElement.getText(); // get status text
                System.out.println("5 Star Status : "+fiveStarText); // output

/*                if (fiveStarText.contains("Click here to")) { // 5 star center status contains a "Click here to" link
                    // a call to salesforce that returns a status None will trigger "Click here to" link generation and a popup modal to join the 5 Star Center

                }

                if (!fiveStarText.contains("approved")){ // 5 star center status is approved
                    throw new Exception("!!! 5* status is not equal APPROVED !!!");  // test failed
                }
                //Assert.assertTrue(fiveStarText.contains("sapproved")); // 5 star center status is approved
*/
            }
            //check that Total success points > 0
            WebElement userPoints = userbox.findElement(By.cssSelector("div.IC_User_Bar-pointsBalance")); // get the user points element
            String actualPoints = userPoints.getText(); // get the user points value in text format
            int upValue = Integer.parseInt(actualPoints); // string convertion to integer
            if (upValue<1){ // no points
                System.out.println("ATTN: Zero point found! No Inner Circle Success Points available!");
                //throw new Exception("!!! A L A R M !!! Zero point found! No Inner Circle Success Points available!");  // test failed
                System.out.println("Sleeping 10 seconds , Zzzzz....");
                Thread.sleep(10000);
            }
            System.out.println("Actual Inner Circle Points: " +upValue);  // output points the user has now

            // go to All Services page, and check that service appear in the grid
            WebElement allServices = driver.findElement(By.cssSelector("ul > li > a[href*='all-services']")); // get the navigation link of all services
            js.executeScript("arguments[0].click();",allServices); //click to goto to all services page
            System.out.println("Current URL: "+driver.getCurrentUrl()); // output where you are

            for (int second = 0;; second++) {
                if (second >= 60) fail("timeout");
                try { if (!"".equals(driver.findElement(By.cssSelector("div.IC_User_Bar-userName")).getText())) break; } catch (Exception e) {}
                Thread.sleep(1000);
            }
            wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.IC_User_Bar-user")))); // wait for user to appear
            wait.until((ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='dk0-combobox']")))); // wait for it to appear
            List<WebElement> gridTiles = driver.findElements(By.cssSelector("div.IC_Grid-content.js-grid-tiles div.IC_Grid-tile"));
            int hiddenCounter = 0;
            for (int g=0; g < gridTiles.size(); g++){ // loop count hidden elements
                WebElement gridItem = gridTiles.get(g);
                String classtext = gridItem.getAttribute("class"); // get text of the class
                if (classtext.contains("is-hidden")){
                    hiddenCounter++; //encrease counter
                }
            }
            if (gridTiles.size()<=hiddenCounter){ // no tile visible due all are hidden
                System.out.println("No Inner Circle Services available!");
                throw new Exception("!!! A L A R M !!! No Inner Circle Services available!!");  // test failed
            }
            int xtiles = gridTiles.size()-hiddenCounter; // calc visible tiles
            System.out.println("Grid Tiles found: "+xtiles); // output amount of tiles

            //Go to Points and Balance page, and check that the text Starting Balance appears
            WebElement pointAndBalance = driver.findElement(By.cssSelector("ul > li > a[href*='points-and-balance']")); // get the navigation link of p&b
            js.executeScript("arguments[0].click();",pointAndBalance); //click to goto to p&b page
            System.out.println("Current URL: "+driver.getCurrentUrl()); // output where you are

            for (int second = 0;; second++) {
                if (second >= 60) fail("timeout");
                try { if (!"".equals(driver.findElement(By.xpath("//span[contains(text(),'Starting Balance')]")).getText())) break; } catch (Exception e) {}
                Thread.sleep(1000);
            }

            WebElement balanceTable = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("table.IC_Balance_Sheet-table")))); // wait for tbl
            List<WebElement> sapConfirmLine = balanceTable.findElements(By.xpath("//span[contains(text(),'Starting Balance')]")); // this text must be there due it's comming from SAP, for display on screen
            if (sapConfirmLine.size() == 0){
                System.out.println("No Starting Balance text from SAP!");
                throw new Exception("!!! A L A R M !!! No Starting Balance text from SAP!!");  // test failed
            }
            System.out.println("Points and Balance has line 'Starting Balance'. Finishing test now..."); // output where you are

            // check logout link and do logout
            WebElement logout = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/logout.html']")))); // wait for link appearance
            anchor = logout.getAttribute("href");
            driver.get(anchor);  // goto login page
            // login should appear on screen again after logout
            wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href='/bin/phonakpro/login']")))); // wait for logout completion
            //List<WebElement> loginBtn = driver.findElements(By.cssSelector("a[href='/bin/phonakpro/login']"));

            aaa = driver.getCurrentUrl();
            System.out.println("Logged out from Inner Circle, Landingpage: "+aaa);  // output on console line
        }
        catch (Exception e) {
            System.out.println("TEST Failed due to an Exception! Pls check");
            System.out.println(e.getMessage());

            System.out.println("Current URL: "+driver.getCurrentUrl());  // output on console line
            throw new Exception("!!! A L A R M !!!");  // flag test failed

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
