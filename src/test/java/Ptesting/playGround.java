package Ptesting;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.net.ServerSocket;
import java.util.List;

import static org.junit.Assert.fail;

public class playGround {
    private static WebDriver driver;
    @BeforeClass
    public static void setUp() throws Exception {

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
    public void inputText() throws Exception {
        driver.get("http://compendiumdev.co.uk/selenium/testpages/html5_html_form.html" );

        //List<WebElement>
        WebElement textField = driver.findElements(By.name("number_field")).get(1);
        // WebElement textField = textField1.get(1);

        writeText(textField, "");
        Assert.assertEquals("", textField.getAttribute("value"));
        writeText(textField, "35");
        Assert.assertEquals("35", textField.getAttribute("value"));

        driver.findElement(By.id("submitButton")).click();
        Assert.assertEquals("35", driver.findElement(By.id("_valuenumber_field")).getText());
    }

    public void writeText(WebElement textField, String text) {
        /*((JavascriptExecutor) driver).executeScript("arguments[0].value='" +
                text + "';", textField);
        */
        String js;
        JavascriptExecutor exec = (JavascriptExecutor) driver;

        //js = String.format("arguments[0].setAttribute('value','"+text+"')");
        js = String.format("arguments[0].value='%s';",text);
        exec.executeScript(js, textField);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        driver.quit();

    }

}
