package Ptesting;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.*;
//import static javafx.event.Event.fireEvent;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.format;
import static org.junit.Assert.fail;

public class pRegistrationPro {
    //Automatic testing of phonak Registration process and more
    private static WebDriver driver, driver1;
    private static String tempEmailUrl, anchor, shopUrl;
    private boolean acceptNextAlert = true;
    private static StringBuffer verificationErrors = new StringBuffer();
    private static String uname= "sunnyfunny";
    private static String upw = "Testarossa@1";
    private static String upw1 = "sonova123";
    private WebDriverWait wait = new WebDriverWait(driver, 30); // set up explicit wait
/*
    @FindBy(name="pf.username")
    private WebElement userName;

    @FindBy(name="pf.pass")
    private WebElement password;

    @FindBy(css = "button.button.button-submit")
    private WebElement loginButton;

    public pRegistrationPro (WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
*/
    @BeforeClass
    public static void setUp() throws Exception {
        //Change browser if required here
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        System.setProperty(
                FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,"false");
        tempEmailUrl = "https://temp-mail.org/de/";
        shopUrl = "https://q-shop.phonakpro.com";
    }

    @Test
    public void testRegistrationPro() throws Exception {


        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        try {
            checkAndRemoveTestuser(driver);
            driver.get(tempEmailUrl); // get temporary email address from free provider
            System.out.println("Driver 1. Access temp email provider at this URL: "+driver.getCurrentUrl());  // output on console line
            String emailAddress = driver.findElement(By.cssSelector("input#mail")).getAttribute("value");
            System.out.println("Driver 1. Current EMAIL ADDRESS: "+emailAddress);  // output on console line
            sleep(2000);
            driver1 = new ChromeDriver();
            //driver1 = new FirefoxDriver();
            driver1.get(shopUrl); // get new browser window for shop main page
            String cUrl = driver1.getCurrentUrl();
            if (cUrl.contains("register")){
                System.out.println("Open new Driver 2. Current URL: "+cUrl);  // output on console line
            }
            //sleep(3000);
            String cTitle = driver1.getTitle();
            System.out.println("Driver 2. Current window Title: "+cTitle);  // output on console line
            for (int second = 0;; second++) { // refresh inbox until email is there

                if (second >= 180) fail("timeout"); // wait and refresh for 6 minutes
                try {
                    sleep(1000);
                    WebElement xxxx = driver1.findElement(By.cssSelector("p.button.button-large.create-id-button a"));
                    String exxxx = xxxx.getText();
                    if (!"".equals(driver1.findElement(By.cssSelector("p.button.button-large.create-id-button a")).getText())) break;
                } catch (Exception e) {}

            }
            WebElement createYourId = driver1.findElement(By.cssSelector("p.button.button-large.create-id-button a")); // get create new id btn
//            WebElement createYourIdBtn = createYourId.findElement(By.cssSelector("a")); // get the ancher
            createYourId.click(); // start registration
            System.out.println("Driver 2. Click on Create Phonak ID");  // output on console line
            String regPage = driver1.getCurrentUrl();
            if (regPage.contains("register")){
                driver1.findElement(By.name("firstName")).clear();
                driver1.findElement(By.name("firstName")).sendKeys("Sunny");
                driver1.findElement(By.name("lastName")).clear();
                driver1.findElement(By.name("lastName")).sendKeys("Funny");
                driver1.findElement(By.id("dk2-combobox")).click();
                driver1.findElement(By.id("dk2-AUDIOLOGIST")).click();
                driver1.findElement(By.id("officePhoneNumber")).clear();
                driver1.findElement(By.id("officePhoneNumber")).sendKeys("123");
                driver1.findElement(By.id("businessName")).clear();
                driver1.findElement(By.id("businessName")).sendKeys("Businessname");
                driver1.findElement(By.id("address")).clear();
                driver1.findElement(By.id("address")).sendKeys("street 123");
                driver1.findElement(By.id("city")).clear();
                driver1.findElement(By.id("city")).sendKeys("City");
                driver1.findElement(By.xpath("//div[5]/div/div/div")).click();
                driver1.findElement(By.xpath("//div[5]/div/div/div[2]/ul/li[2]")).click();
                driver1.findElement(By.id("postalCode")).clear();
                driver1.findElement(By.id("postalCode")).sendKeys("12345");

                driver1.findElement(By.cssSelector("input#username")).sendKeys(uname); // set up login name
                driver1.findElement(By.cssSelector("input#password")).sendKeys(upw); // set up password
                driver1.findElement(By.cssSelector("input#checkPassword")).sendKeys(upw); // confirm pw
                driver1.findElement(By.cssSelector("input#emailInput")).sendKeys(emailAddress); // email address
                driver1.findElement(By.id("dk1-combobox")).click();
                driver1.findElement(By.id("dk1-Mr.")).click();

                driver1.findElement(By.id("submit-button")).click();
                System.out.println("Driver 2. Enter all required data and click on Submit registration button");  // output on console line

                for (int second = 0;; second++) { // refresh inbox until email is there
                    //driver.findElement(By.tagName("a[href*='refresh']")); //
                    WebElement refreshEmail = driver.findElement(By.xpath("//a[@id='click-to-refresh']"));
                    JavascriptExecutor js = (JavascriptExecutor) driver; // imply javascript on client/browser
                    js.executeScript("arguments[0].click();", refreshEmail); // click on item with javascript code
                    System.out.println("Driver 1. Refreshing email inbox for 6' to get token to activate registration, time passed: "+second);  // output on console line
                    // driver.findElements(By.xpath("//label[text()[contains(., ' BTE')]]")); // get BTE filter item
                    //String xxx = driver.findElement(By.xpath("//a[text()[contains(.,'donotreply')]]")).getText();
                    if (second >= 180) fail("timeout"); // wait and refresh for 6 minutes
                    try { if (!"".equals(driver.findElement(By.xpath("//a[text()[contains(.,'donotreply')]]")).getText())) break; } catch (Exception e) {}
                    sleep(1000);
                }
                WebElement toEamilDetails = driver.findElement(By.xpath("//a[text()[contains(.,'donotreply')]]")); // go to page and get token to activate user
                JavascriptExecutor js = (JavascriptExecutor) driver; // imply javascript on client/browser
                js.executeScript("arguments[0].click();", toEamilDetails); // click on item with javascript code
                System.out.println("Driver 1. Email found. Click to enter detail page of email containing the token link");  // output on console line
                System.out.println("Driver 1. Current URL: "+driver.getCurrentUrl());  // output on console line
                WebElement tokenElem = driver.findElement(By.cssSelector("a[href*='shop.phonakpro.com']")); // get token to activate user
                String token = tokenElem.getAttribute("href");  // save the activation token
                js.executeScript("arguments[0].click();", tokenElem); // click on item with javascript code
                System.out.println("Driver 1. click on the token, which will open a 2nd browser tab window to confirm activation");  // output on console line
                /////driver.findElement(By.cssSelector("a[href*='/delete/']")).click();  // delete the email to clean the inbox
                /////driver.get(token); // activate the user by go to this url incl. the token
                //String aaa = driver.getCurrentUrl();
                ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
                System.out.println("Driver 1. has "+tabs.size()+" tab(s)");  // output on console line
                driver.switchTo().window(tabs.get(1));
                System.out.println("Driver 1. Switched to 2nd browser tab. Current URL: "+driver.getCurrentUrl());  // output on console line

                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.container h1")));
                String bbb = driver.findElement(By.xpath("//h1[contains(text(),'All done!')]")).getText();
                if (!bbb.contains("done!")){
                    throw new Exception("!!! User Not ACTIVATED !!!");  // flag test failed
                }
                System.out.println("Driver 1. User "+uname+" has been verified and activated.");  // output on console line

                // Login with the newly created user to prove the registration process is working fine.
                loginTheUser(driver);

                tabs = new ArrayList<String>(driver.getWindowHandles());
                System.out.println("Driver 1. window has "+tabs.size()+" tab(s)");  // output on console line
                driver.switchTo().window(tabs.get(0));
                System.out.println("Driver 1. switch to tab 1 to delete the redundant email");  // output on console line
                //driver.findElement(By.cssSelector("a.linkbord.click-to-delete-mail")).click();  // delete the email to clean the inbox
                WebElement deleteMailLink = driver.findElement(By.cssSelector("a.linkbord.click-to-delete-mail")); //.click();  // delete the email to clean the inbox
                String deleteMailNow = deleteMailLink.getAttribute("href"); // get the url
                driver.get(deleteMailNow); // del email

                // test forgot password process (FPP)
                driver1.get(shopUrl); // get 1st browser window and select shop main page
                System.out.println("Driver 2. reset to URL: "+driver1.getCurrentUrl()+" to test Forgot Password Process (FPP)");  // output on console line
                for (int second = 0;; second++) { // refresh required element is available

                    if (second >= 180) fail("timeout"); // wait and refresh for 6 minutes
                    try {
                        sleep(1000);
                        WebElement xxxx = driver1.findElement(By.cssSelector("a[href*='/pw/request']"));
                        String exxxx = xxxx.getText();
                        if (!"".equals(driver1.findElement(By.cssSelector("a[href*='/pw/request']")).getText())) break;
                    } catch (Exception e) {}

                }
                driver1.findElement(By.cssSelector("a[href*='/pw/request']")).click();  // click on forgot password link
                driver1.findElement(By.cssSelector("input#username")).sendKeys(uname); // enter username to request forgot pw
                driver1.findElement(By.cssSelector("button.button.button-submit")).click(); // click on submit button
                System.out.println("Driver 2. click on renew pw link and enter username and submit");  // output on console line

                checkTheEmailInbox(driver); // poll for email token and and click on it if found

                tabs = new ArrayList<String>(driver.getWindowHandles());
                driver.switchTo().window(tabs.get(2));
                System.out.println("Driver 1. Switch to tab 2, with URL: "+driver.getCurrentUrl()); // We are on this url
                if (driver.getCurrentUrl().contains("/pw/change")){  // we are on the password change page with username prefilled
                    System.out.println("Driver 1. Provide new password twice and submit the request.");  // output on console line
                    driver.findElement(By.cssSelector("input.validate-password")).sendKeys(upw1); // enter new pw
                    driver.findElement(By.cssSelector("input.validate-check-password")).sendKeys(upw1); // confirm new pw
                    driver.findElement(By.cssSelector("button.button.button-submit")).click(); // click on submit button
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.container h1")));
                    bbb = driver.findElement(By.xpath("//h1[contains(text(),'All done!')]")).getText();
                    if (!bbb.contains("done!")){
                        throw new Exception("!!! Password change function ERROR !!!");  // flag test failed
                    }
                    System.out.println("Driver 1. User "+uname+" password has been changed successfully. Continue to login w. newly changed pw.");  // output on console line
                    upw = upw1; // newly changed password to login
                    loginTheUser(driver); // test user can login with new pw
                }
                driver.switchTo().window(tabs.get(0));
                deleteMailLink = driver.findElement(By.cssSelector("a.linkbord.click-to-delete-mail")); //.click();  // delete the email to clean the inbox
                deleteMailNow = deleteMailLink.getAttribute("href"); // get the url
                driver.get(deleteMailNow); // del email
                System.out.println("Driver 1. Switch to tab 1 and delete email containing obsolete token. This concludes FPP");

                // test forgot username process (FUP)
                System.out.println("*** Start testing Forgot Username Process (FUP) ***");  // output on console line
                driver1.get(shopUrl); // get 1st browser window and select shop main page
                System.out.println("Driver 2. Reset to URL: "+driver1.getCurrentUrl());  // output on console line
                for (int second = 0;; second++) { // refresh until required element is available
                    if (second >= 180) fail("timeout"); // wait and refresh for 6 minutes
                    try {
                        sleep(1000);
                        WebElement xxxx = driver1.findElement(By.cssSelector("a[href*='/login/username']"));
                        String exxxx = xxxx.getText();
                        if (!"".equals(driver1.findElement(By.cssSelector("a[href*='/login/username']")).getText())) break;
                    } catch (Exception e) {}
                }
                driver1.findElement(By.cssSelector("a[href*='/login/username']")).click();  // click on forgot password link
                driver1.findElement(By.cssSelector("input#emailAddress")).sendKeys(emailAddress); // enter email address to request forgot username
                driver1.findElement(By.cssSelector("button.button.button-submit")).click(); // click on submit button
                System.out.println("Driver 2. Click on FUP link and enter email address and submit the request.");  // output on console line

                checkTheEmailInbox(driver); // poll for email token and and click on it if found

                //System.out.println("*** This concludes FUP testing. ***");  // output on console line

                /// this if structure is obsolete? if yes, delete pls
                //driver.findElement(By.cssSelector("a.linkbord.click-to-delete-mail")).click();  // delete the email to clean the inbox
                deleteMailLink = driver.findElement(By.cssSelector("a.linkbord.click-to-delete-mail")); //.click();  // delete the email to clean the inbox
                deleteMailNow = deleteMailLink.getAttribute("href"); // get the url
                driver.get(deleteMailNow); // del email
                System.out.println("Driver 1. In tab 1, delete email containing obsolete token. This concludes FUP testing.");

                // delete user to be able to recreate the same one again otherwise user already exists will be shown.
                // checkAndRemoveUser(driver);
            }

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

    private void checkAndRemoveTestuser(WebDriver driver) throws InterruptedException {
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        JavascriptExecutor js = (JavascriptExecutor) driver; // imply javascript on client/browser
        driver.switchTo().window(tabs.get(0));
        System.out.println("Driver 1. Enter HMC BE to remove the user from the system.");  // output on console line
        driver.get("https://q-ecommerce.sonova.com/backoffice"); // goto hybris backoffice
        //driver.findElement(By.xpath("//select")).click(); // open drop down menu
        //driver.findElement(By.xpath("//option[contains(text(),'English')]")).click(); // select language option English

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='j_username']")));
        WebElement loginField = driver.findElement(By.xpath("//input[@name='j_username']")); // login field present input[name='j_username']
        //loginField.clear();
        loginField.sendKeys("h11spantong");
        //js.executeScript("arguments[0].setAttribute('value', 'h11spantong')", loginField); // enter data
        loginField = driver.findElement(By.xpath("//input[@name='j_password']")); // load pw field
        //loginField.clear();
        loginField.sendKeys("staefa123");
        //js.executeScript("arguments[0].setAttribute('value', 'staefa123')", loginField); // enter data
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(@type,'submit')]"))); // login button
        WebElement loginButton = driver.findElement(By.xpath("//button[contains(@type,'submit')]")); // login button
        js.executeScript("arguments[0].click();", loginButton); // click on item with javascript code
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//button[contains(@type,'submit')]"))); // disappearance login button
        System.out.println("Driver 1. Enter credentials and clicked on login button");  // output on console line
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'User')]")));
        driver.findElement(By.xpath("//*[contains(text(),'User')]")).click(); // expand user tree
        driver.findElement(By.xpath("//*[contains(text(),'Customer')]")).click(); // click on Customer
        WebElement searchField = driver.findElement(By.cssSelector("input.z-bandbox-input.z-bandbox-rightedge")); // clear input field
        //driver.findElement(By.cssSelector("input.z-bandbox-input.z-bandbox-rightedge")).sendKeys(uname); // enter search value
        searchField.sendKeys(uname+"\t"); // enter username value in search field with tab charactor to trigger a change in browser

        System.out.println("Driver 1. Navigate to Customer node and enter username to search for");  // output on console line
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button.yw-textsearch-searchbutton.y-btn-primary.z-button")));
        WebElement searchButton = driver.findElement(By.cssSelector("button.yw-textsearch-searchbutton.y-btn-primary.z-button"));  // start search user
        String searchId = searchButton.getAttribute("id"); // get the id value
        searchButton = driver.findElement(By.id(searchId)); // load click on search button by using ID
        js.executeScript("arguments[0].click();", searchButton); // click on item with javascript code
        //searchButton.click();
        System.out.println("Driver 1. Clicked on Search button.");  // output on console line

        for (int i=0 ; i < 30; i++){
            Thread.sleep(1000);
            WebElement paging = driver.findElement(By.cssSelector("div.z-paging")); // paging element
            String pagingVisible = paging.getAttribute("style");
            if (pagingVisible.contains("none")){
                break;
            }
        }
        //Thread.sleep(5000);
        List<WebElement> testuserFound = driver.findElements(By.xpath("//*[contains(text(),'"+uname+"')]")); // check for testuser text apperance
        if (testuserFound.size() == 1){  // test user exists
            System.out.println("Driver 1. Wait for username to appear and clicked on checkbox to mark user for delete.");  // output on console line
            WebElement checkBoxUser = driver.findElement(By.xpath("//*[contains(text(),'"+uname+"')]/../span/i")); // checkbox element infront of the username
            js.executeScript("arguments[0].click();", checkBoxUser); // click on item with javascript code
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("span.z-listheader-checkable.z-listheader-checked"))); // listheader is bold and checked
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("img.cng-action-icon.z-image")));
            WebElement deleteIcon = driver.findElement(By.cssSelector("img.cng-action-icon.z-image")); // click on delete image
            String deleteId = deleteIcon.getAttribute("id"); //get the id value
            deleteIcon = driver.findElement(By.id(deleteId)); // load with ID
            js.executeScript("arguments[0].click();", deleteIcon); // click on item with javascript code
            System.out.println("Driver 1. Clicked on delete user icon.");  // output on console line
            Thread.sleep(1000);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class*='z-messagebox-window']")));
            WebElement msgbox = driver.findElement(By.cssSelector("div[class*='z-messagebox-window']")); // confirmation msg box
            WebElement msgboxYes = msgbox.findElement(By.xpath("//button[contains(text(),'Yes')]"));  // click delete confirmation = Yes
            js.executeScript("arguments[0].click();", msgboxYes); // click on item with javascript code
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Deleted items:')]")));  // confirmation text appears on screen
            List<WebElement> textBar = driver.findElements(By.xpath("//*[contains(text(),'Deleted items:')]"));
            if (textBar.size()>0){
                System.out.println("Delete confirmation message shown on screen.");
                WebElement textBar1 = textBar.get(0);
                String textBarContent = textBar1.getAttribute("innerHTML");
                System.out.println("innerHTML of texBar1: " +textBarContent);
                WebElement textBar2 = driver.findElement(By.xpath("//*[contains(text(),'Deleted items:')]/.."));  // step a level up to see what there
                textBarContent = textBar2.getAttribute("innerHTML");
                System.out.println("innerHTML of texBar2: " +textBarContent);
            }
            System.out.println("Driver 1. On Message box, confirm delete with click on Yes button.");  // output on console line
            System.out.println("Driver 1. User "+uname+" has been removed from the system: "+driver.getCurrentUrl());  // output on console line
        }
        else{
            System.out.println("Driver 1. No User deleted in Hybris, searched user is not found in system");  // output on console line
        }
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("span.z-toolbarbutton-content img[src*='logout']")));
        WebElement logoutHybris = driver.findElement(By.cssSelector("span.z-toolbarbutton-content img[src*='logout']")); // logout img icon
        js.executeScript("arguments[0].click();", logoutHybris); // click on item with javascript code
        System.out.println("Driver 1. Logged out from Hybris.");  // output on console line

    }

    @AfterClass
    public static void tearDown() throws Exception {
        driver.quit();
        driver1.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private void checkReplyUsername(WebDriver driver){

    }

    private void loginTheUser(WebDriver driver){
        System.out.println("Driver 1. switch to login page and provide credentials and submit.");  // output on console line
        driver.get("https://q-shop.phonakpro.com"); // goto shop portal and login w. newly activate user
        loginNewUser(driver);
        System.out.println("Driver 1. Successfully logged in, actual URL is: "+driver.getCurrentUrl());  // output on console line
        for (int second = 0;; second++) { // refresh until required element is available
            if (second >= 180) fail("timeout"); // wait and refresh for 6 minutes
            try {
                sleep(1000);
                List<WebElement> xxxx = driver.findElements(By.cssSelector("a[href*='logout']"));
                if (xxxx.size()>0) {
                    //WebElement selectPl = list.get(0); // get the first item
                    WebElement profile = xxxx.get(0); // get item from list
                    // wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='logout']"))));  // check logout menu
                    anchor = profile.getAttribute("href");  // get the logout link
                    driver.get(anchor); // logout
                    break;
                }
                //if (!"".equals(driver.findElement(By.cssSelector("a[href*='logout']")).getText())) break;
            } catch (Exception e) {}
        }


        System.out.println("Driver 1. Logged out, current URL: "+driver.getCurrentUrl());  // output on console line
    }

    private void checkTheEmailInbox(WebDriver driver) {
        System.out.println("Driver 1. Polling for email in inbox");  // output on console line
        for (int second = 0;; second++) { // refresh inbox until email is there

            WebElement refreshEmail = driver.findElement(By.xpath("//a[@id='click-to-refresh']"));
            JavascriptExecutor js = (JavascriptExecutor) driver; // imply javascript on client/browser
            js.executeScript("arguments[0].click();", refreshEmail); // click on item with javascript code

            if (second >= 180) fail("timeout"); // wait and refresh for 6 minutes
            try { if (!"".equals(driver.findElement(By.xpath("//a[text()[contains(.,'donotreply')]]")).getText())) break; } catch (Exception e) {}

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println("Driver 1. Email arrived in inbox. Click on links twice to go to page where required data must be entered.");  // output on console line
        WebElement shopLinkEmail = driver.findElement(By.xpath("//a[text()[contains(.,'donotreply')]]")); // get token to step further
        JavascriptExecutor js = (JavascriptExecutor) driver; // imply javascript on client/browser
        js.executeScript("arguments[0].click();", shopLinkEmail); // click on item with javascript code
        System.out.println("Driver 1. Click 1 on email item to enter email detail page and check username. Current URL: "+driver.getCurrentUrl());  // output on console line
        List<WebElement> isUsername = driver.findElements(By.xpath("//*[text()[contains(.,'"+uname+"')]]"));
        try { if (!"".equals(driver.findElement(By.xpath("//*[text()[contains(.,'"+uname+"')]]")).getText()));  } catch (Exception e) {}
        System.out.println("Driver 1. The Username is visible in the email body text.");  // output on console line

        shopLinkEmail = driver.findElement(By.cssSelector("a[href*='shop.phonakpro.com']")); // to click on token for action
        js.executeScript("arguments[0].click();", shopLinkEmail); // click on item with javascript code

        System.out.println("Driver 1. Click 2 on the token link to finish the flow. Current URL: "+driver.getCurrentUrl());  // output on console line
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

    public void loginNewUser(WebDriver driver) {
        enterUsername(driver);
        enterPassword(driver);
        clickLoginButton();
    }

    public void enterUsername(WebDriver driver) {
        //userName.clear();
        WebElement username = driver.findElement(By.name("pf.username"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('value', '"+uname+"')", username);
    }

    public void enterPassword(WebDriver driver) {
        //password.clear();
        WebElement userpw = driver.findElement(By.name("pf.pass"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('value', '"+upw+"')", userpw);
    }

    public void clickLoginButton() {
        driver.findElement(By.cssSelector("button.button.button-submit")).click();
        //loginButton.click();
    }

}
