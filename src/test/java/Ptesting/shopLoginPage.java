package Ptesting;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by 11spantong on 16.11.2017.
 */
public class shopLoginPage {
    private static WebDriver driver;
    private static String baseUrl = "https://www.phonakpro.com/";
    private static String p="AkwaB4so@1Phonakte#Wal0Unitron#456AdvancedBionics#007".substring(10,22);
//  private static String p="AkwaB4so@1Phonakte#Wal0Unitron#456AdvancedBionics#007".substring(10,22);
//  private static String p="AkwaB4so@1Phonakte#1230Unitron#456AdvancedBionics#007".substring(10,22);
    private static String u="iSchB4vv@1k51aBrown++Unigtro#789Advancybikonics#101".substring(11,19);
//  private static String u="iSchB4vv@1k21nsarkozy++Unigtro#789Advancybikonics#101".substring(11,21);
//  private static String u="iSchB4vv@1k25ewindsor++Unigtro#789Advancybikonics#101".substring(11,21);
//  JavascriptExecutor js = (JavascriptExecutor) driver;

    @FindBy(name="pf.username")
    private WebElement userName;

    @FindBy(name="pf.pass")
    private WebElement password;

    @FindBy(className="button button-submit ")
    private WebElement loginButton;

    @FindBy(className = "helplink")
    private WebElement forgotPwUn;

    @FindBy(css = "button.button.button-submit")
    private WebElement shopLoginButton;




    public shopLoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void loadB2bPage(WebDriver driver) {

        driver.get(baseUrl + "/com/en/home.html");
        Assert.assertEquals(driver.getCurrentUrl(), baseUrl + "com/en/home.html");
    }

    public void enterUsername(WebDriver driver) {
        userName.clear();
        String u1=u; // copy u
        System.out.println("username = " +u1);
        String u_param = System.getProperty("userName.cli");

        if(u_param != null){ // check if there is a value entered with cmd line
            u1 = u_param; // set username ex cmd parameter
            System.out.println("userName.cli = " +u1);
        }
        WebElement username = driver.findElement(By.name("pf.username"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('value', '"+u1+"')", username);
        //userName.sendKeys();
    }

    public void enterPassword(WebDriver driver) {
        //password.sendKeys("testpassword");
        password.clear();
        String p1=p; // copy u
        System.out.println("userPw = " +p1);
        String p_param = System.getProperty("userPw.cli");

        if(p_param != null){ // check if there is a value entered with cmd line
            p1 = p_param; // set username ex cmd parameter
            System.out.println("userPw.cli = " +p1);
        }
        WebElement userpw = driver.findElement(By.name("pf.pass"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('value', '"+p1+"')", userpw);
    }

    public void clickLoginButton() {
        loginButton.click();
    }
    public void clickShopLoginButton() {
        shopLoginButton.click();
    }

    public void clickForgotPw() {
        //to do: there are two such links to identified
        forgotPwUn.click();
    }

    public void clickChange(WebDriver driver){
        //Cannot click if element is not in visible area of browser (Chrome) need scrolling
        WebElement location = driver.findElement(By.linkText("Change"));
        // Use javascript to click which works w/o scrolling to that element
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", location);
    }

    public void clickCountryLogin(WebDriver driver){
        List<WebElement> loginBtn = driver.findElements(By.cssSelector("a[href='/bin/phonakpro/login']"));

        if (!loginBtn.isEmpty()) {
            WebElement loginBtnElement = loginBtn.get(0);
            // use javascript to click on invisible element (visible only if mouse moved over it)
            if (loginBtnElement.isDisplayed()) {
                //System.out.println("Login is displayed");
                loginBtnElement.click();
            } else {
                //System.out.println("Login is NOT displayed");
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", loginBtnElement);
            }

        }
    }

}
