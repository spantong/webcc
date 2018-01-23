package Qtesting;

import Ptesting.shopLoginPage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.Assert.fail;

public class phonakB2bHomeTest {
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
        // set up explicit wait
        WebDriverWait wait = new WebDriverWait(driver, 10);
        shopLoginPage loginPage = new shopLoginPage(driver);
        try {
            driver.get(baseUrl + "/com/en/home.html");
            //Workaround: cannot click if element is not in visible area of browser (Chrome) need scrolling
            WebElement location = driver.findElement(By.linkText("Change"));
            // Use javascript to click which works w/o scrolling to that element
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", location);

            WebElement gotoo = wait.until((ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='/north-america/us_en']"))));
            gotoo.click();
            //driver.findElement(By.linkText("United States")).click();
            loginPage.clickCountryLogin(driver);
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
/*
                    //js.executeScript("arguments[0].st‌​yle.display='block';‌​", targetDiv);
                    js.executeScript("document.getElementsByClassName('account-info-dropdown')[0].style.display='block'");
                    wait.until((ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/bin/phonakpro/login']"))));
                    WebElement login = driver.findElement(By.cssSelector("a[href='/bin/phonakpro/login']"));
                    login.click();
*/
                }
                wait.until((ExpectedConditions.elementToBeClickable(By.name("pf.username"))));
            }
            else{
                System.out.println("Logout not possible, missing that element!");
            }
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
