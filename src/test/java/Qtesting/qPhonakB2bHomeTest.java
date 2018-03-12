package Qtesting;

import Ptesting.pShopLoginPage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.Assert.fail;

public class qPhonakB2bHomeTest {
    //Automatic testing QS of phonak eStore/eServices
    private static WebDriver driver;
    private static String baseUrl;
    private boolean acceptNextAlert = true;
    private static StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass
    public static void setUp() throws Exception {
        //Change browser if required here
        //driver = new FirefoxDriver();
        driver = new ChromeDriver();
        baseUrl = "https://qs-www.phonakpro.com/";
        //baseUrl = "https://www.phonakpro.com/";
    }
    @Test
    public void testphonakB2bHomeTest() throws Exception {

        WebDriverWait wait = new WebDriverWait(driver, 10); // set up explicit wait
        pShopLoginPage loginPage = new pShopLoginPage(driver); // init shop login page object
        try {
            driver.get(baseUrl + "/com/en/home.html"); // get b2b portal
            //Workaround: cannot click if element is not in visible area of browser (Chrome) need scrolling
            WebElement location = driver.findElement(By.linkText("Change")); // detect cntry selection elelment at the buttom of the page
            // Use javascript to click, which works w/o scrolling to that element
            JavascriptExecutor js = (JavascriptExecutor) driver; // imply js
            js.executeScript("arguments[0].click();", location); // get cntry selection page

            WebElement gotoo = wait.until((ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='/north-america/us_en']")))); // wait for usa link appearance
            gotoo.click(); // goto usa b2b portal

            loginPage.clickCountryLogin(driver); // login button
            List<WebElement> loginBtn = driver.findElements(By.cssSelector("a[href='/bin/phonakpro/login']")); // check for submit button on the login page
            wait.until((ExpectedConditions.elementToBeClickable(By.name("pf.username"))));  // chk input field that should appear
            /*
            if (!loginBtn.isEmpty()) { // check page contains a login button, could be not found
                WebElement loginBtnElement = loginBtn.get(0); // get that element to check its attribute
                // use javascript to click on invisible element (visible only if mouse moved over it)
                if (loginBtnElement.isDisplayed()) { // element status is visible
                    //System.out.println("Login is displayed");
                    loginBtnElement.click();
                } else { // element status is not visible
                    //System.out.println("Login is NOT displayed");
                    js.executeScript("arguments[0].click();", loginBtnElement); // js can click on an invisible element
                }
                wait.until((ExpectedConditions.elementToBeClickable(By.name("pf.username"))));  // chk input field that should appear
            }
            else{ // no login submit button on the actual page
                System.out.println("Logout not possible, missing that element!");
            }
            */
        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
        finally {
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
}
