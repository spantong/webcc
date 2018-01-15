import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

import javax.swing.JOptionPane;

//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;
//import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * Created by 11spantong on 19.10.2017.
 */
public class Login51abrownCom {
    private static WebDriver driver;
    private static String baseUrl;
    private boolean acceptNextAlert = true;
    private static StringBuffer verificationErrors = new StringBuffer();
    private static String p="AkwaB4so@1Phonakte#Wal0Unitron#456AdvancedBionics#007".substring(10,22);
    private static String u="iSchB4vv@1k51aBrown++Unigtro#789Advancybikonics#101".substring(11,19);

    @BeforeClass
    public static void setUp() throws Exception {

        String OS = System.getProperty("os.name");

        if (OS=="Linux"){
                System.setProperty("webdriver.chrome.driver", "/usr/local/share/chromedriver");
            /*start: for Travis ci
            final ChromeOptions chromeOptions = new ChromeOptions();
            //chromeOptions.setBinary("/usr/local/share/chromedriver");
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--disable-gpu");
            final DesiredCapabilities dc = new DesiredCapabilities();
            dc.setJavascriptEnabled(true);
            dc.setCapability(
                    ChromeOptions.CAPABILITY, chromeOptions
            );
            // end: for Travis ci */

            //driver = new ChromeDriver(dc);
            driver = new ChromeDriver();
        } else{
            //System.setProperty("webdriver.ie.driver", "C:\\Software\\Drivers\\IE\\EDriverServer_Win32_3.6.0\\IEDriverServer.exe");
            driver = new FirefoxDriver();
        }

        baseUrl = "https://www.phonakpro.com/";

        //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
//    public void main(String[] args)throws Exception {
    public void testLogin51abrownCom() throws Exception {
        // input prompt
        //String prompt4pw = JOptionPane.showInputDialog(null, "Please enter password", "Password required", JOptionPane.INFORMATION_MESSAGE);
        //System.out.println(prompt4pw);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            driver.get(baseUrl + "/com/en/home.html");
            //Cannot click if element is not in visible area of browser (Chrome) need scrolling
            WebElement location = driver.findElement(By.linkText("Change"));
            // Use javascript to click which works w/o scrolling to that element
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", location);

            WebElement gotoo = wait.until((ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='/north-america/us_en']"))));
            gotoo.click();
            //driver.findElement(By.linkText("United States")).click();

            List<WebElement> loginBtn = driver.findElements(By.cssSelector("a[href='/bin/phonakpro/login']"));

            if (!loginBtn.isEmpty()) {
                WebElement loginBtnElement = loginBtn.get(0);
                // use javascript to click on invisible element (visible only if mouse moved over it)
                if (loginBtnElement.isDisplayed()) {
                    //System.out.println("Login is displayed");
                    loginBtnElement.click();
                } else {
                    //System.out.println("Login is NOT displayed");
                    js.executeScript("arguments[0].click();", loginBtnElement);
                }

            }

//            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            wait.until((ExpectedConditions.elementToBeClickable(By.name("pf.username"))));

            WebElement username = driver.findElement(By.name("pf.username"));
//            username.click();
//            driver.findElement(By.name("pf.username")).clear();
            //sendKeys not working in FF, use javascript instead of to enter username value
            js.executeScript("arguments[0].setAttribute('value', '"+u+"')", username);
//            String namevalue = "51abrown";
//            username.sendKeys(namevalue);
            WebElement userpw = driver.findElement(By.name("pf.pass"));
            js.executeScript("arguments[0].setAttribute('value', '"+p+"')", userpw);
            //driver.findElement(By.name("pf.pass")).sendKeys("xxxx");
            //WebElement button1 = wait.until(optionWithValueDisplayed("23"));
            WebElement button1 = wait.until((ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.button.button-submit"))));
            button1.click();

            //       driver.findElement(By.cssSelector("button.button.button-submit")).click();

            //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            WebElement profile;
            //driver.findElement(By.xpath("//a[contains(@href, 'my-profile')]"));
            //profile = driver.findElement(By.cssSelector("a[href*='my-profile']"));
            profile = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='my-profile']"))));
            //get the exact link value
            String anchor = profile.getAttribute("href");
            //System.out.println(anchor);
            //Set again, bcoz click() only possible with exact link value
            profile = driver.findElement(By.cssSelector("a[href='"+anchor+"']"));
            //check/validate bcoz default it's invisible
            if (profile.isDisplayed()) {
                //System.out.println("my-profile is displayed");
                profile.click();
            } else {
                //System.out.println("my-profile is NOT displayed");
                js.executeScript("arguments[0].click();", profile);
            }

            WebElement logout = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/saml/logout']"))));
            String url = driver.getCurrentUrl();
            if(!url.contains("/phonakus")) {
                driver.get("https://q-shop.phonakpro.com/phonakus/en/USD/");
            }
            else {

                //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                // There was no auto redirect to USA store in QS -> Just do Logout test only if on b2b home.html

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
                        js.executeScript("arguments[0].click();", logoutnBtnElement);
                    }

                }
                //	End no redirect to USA store
            }
//            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

//            driver.findElement(By.linkText("Store")).click();

//            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//            driver.findElement(By.linkText("Logout")).click();
//            List<WebElement> logoutBtn = driver.findElements(By.linkText("Logout"));
            //url = driver.getCurrentUrl();
            //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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

/*              driver.findElement(By.linkText("User Management")).click();
                wait.until((ExpectedConditions.visibilityOfElementLocated(By.xpath("//body"))));
                for (int second = 0;; second++) {
                   if (second >= 60) fail("timeout");
                   try { if (isElementPresent(By.xpath("//body"))) break; } catch (Exception e) {}
                   Thread.sleep(1000);
                }

*/
            List<WebElement> logoutBtn = driver.findElements(By.id("id_accountmenu_logout_link"));
            if (!logoutBtn.isEmpty()) {
                WebElement logoutBtnElement = logoutBtn.get(0);
                anchor = logoutBtnElement.getAttribute("href");
                System.out.println(anchor);
                // use javascript to click on invisible element (visible only if mouse moved over it)
                if (logoutBtnElement.isDisplayed()) {
                    System.out.println("Logout is displayed");
                    logoutBtnElement.click();
                } else {
                    System.out.println("Logout is NOT displayed");
                    js.executeScript("arguments[0].click();", logoutBtnElement);
                }

            }
            else{
                System.out.println("Logout not possible, missing that element!");
            }
        }
/*        catch (Exception e) {
            e.printStackTrace();
        }
*/

        finally {
            // close the Browser
            // driver.quit(); //do this in @After
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
}
