import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


import java.sql.Driver;

/**
 * Created by 11spantong on 26.10.2017.
 */
public class testarossa {
    private static WebDriver driver;

    @BeforeClass
    public static void beforeClass(){
        //driver = new ChromeDriver();
        //driver = Driver.get();
        //driver.get();
        //System.setProperty("webdriver.edge.driver", "C:\\Software\\Drivers\\Edge\\MicrosoftWebDriver.exe");
        driver = new ChromeDriver();
        System.out.println("Before class");
    }


    @Before
    public void beforeTest(){
        System.out.println("Before test");


    }

    @Test
    public void testarossa1(){
        driver.navigate().to("http://www.google.ch");
        Assert.assertTrue(driver.getTitle().startsWith("Google"));
        driver.navigate().to("http://www.amazon.ch");
        Assert.assertTrue(driver.getTitle().startsWith("Amazon"));
        driver.navigate().back();
        driver.navigate().forward();
        driver.navigate().refresh();
        driver.navigate().back();
        Assert.assertTrue(driver.getTitle().startsWith("Google"));
        System.out.println("Testarossa uno");
    }
    @Test
    public void testarossa2(){
        System.out.println("Testarossa due");
    }
    @Test
    public void testarossa3(){
        System.out.println("Testarossa tre");
    }

    @After
    public void afterTest(){
        System.out.println("After test");
    }

    @AfterClass
    public static void afterClass(){
        System.out.println("After class");
        driver.quit();
    }

}
