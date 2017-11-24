import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
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
//    private static String p="AkwaB4so@1Phonakte#Wal0Unitron#456AdvancedBionics#007".substring(10,22);
//    private static String u="iSchB4vv@1k51aBrown++Unigtro#789Advancybikonics#101".substring(11,19);

    @BeforeClass
    public static void setUp() throws Exception {
        driver = new ChromeDriver();
    }

    @Test
//    public void main(String[] args)throws Exception {
    public void testLogin51abrownCom() throws Exception {

        WebDriverWait wait = new WebDriverWait(driver, 10);
        shopLoginPage loginPage = new shopLoginPage(driver);
        try {
            loginPage.loadB2bPage(driver);
            loginPage.clickChange(driver);
            WebElement gotoo = wait.until((ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='/north-america/us_en']"))));
            gotoo.click();
            wait.until((ExpectedConditions.urlContains("/us/en/home")));
            loginPage.clickCountryLogin(driver);
            loginPage.enterUsername(driver);
            loginPage.enterPassword(driver);
            loginPage.clickShopLoginButton();
            WebElement profile = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='my-profile']"))));
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
                JavascriptExecutor js = (JavascriptExecutor) driver;
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
                        JavascriptExecutor js = (JavascriptExecutor) driver;
                        js.executeScript("arguments[0].click();", logoutnBtnElement);
                    }

                }
                //	End no redirect to USA store
            }

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
                    JavascriptExecutor js = (JavascriptExecutor) driver;
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
