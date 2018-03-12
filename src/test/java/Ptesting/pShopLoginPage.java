package Ptesting;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.w3c.dom.NodeList;

import java.util.List;

/**
 * Created by 11spantong on 16.11.2017.
 */
public class pShopLoginPage {
    private static WebDriver driver;
    private static String baseUrl = "https://www.phonakpro.com/";
    private static String p="AkwaB4so@1Phonakte#Wal0Unitron#456AdvancedBionics#007".substring(10,22);
    private static String u="iSchB4vv@1k51aBrown++Unigtro#789Advancybikonics#101".substring(11,19);

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

    @FindBy(className = "account-name")
    private WebElement myAccount;

    @FindBy(className = "create-id-button")
    private WebElement createId;

    public pShopLoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void getCreateId(WebDriver driver) {
        String lableText = createId.getText();
        System.out.println("Create ID text: "+lableText); //output what text (language) is there
    }

    public void getMyAccount(WebDriver driver) {
        List<WebElement> ma_child = myAccount.findElements(By.tagName("span")); // get all elements
        String topLine=" ";
        if (ma_child.size()==0){
            topLine = " Missing My Account Menu Bar on top line!";
        }
        String actualPage = driver.getCurrentUrl();
        System.out.println("On this page: " +actualPage);
        System.out.println("My Account content is : "+ma_child.size()+topLine); //output what text (language) is there
    }

    public void loadB2bPage(WebDriver driver) {
        String b1=baseUrl; // copy
        baseUrl = "xxx"; //test
        baseUrl=b1;
        //System.out.println("baseUrl = " +b1);
        String b_param = System.getProperty("baseUrl.cli");

        if(b_param != null){ // check if there is a value entered with cmd line
            baseUrl = b_param; // set baseurl ex cmd parameter
            //System.out.println("baseUrl.cli = " +baseUrl);  // output actual value
        }

        driver.get(baseUrl + "/com/en/home.html");
        Assert.assertEquals(driver.getCurrentUrl(), baseUrl + "com/en/home.html");
        System.out.println("Starting at b2b portal: " +baseUrl);
    }

    public void enterUsername(WebDriver driver) {
        userName.clear();
        String u1=u; // copy u
        String u_param = System.getProperty("userName.cli");

        if(u_param != null){ // check if there is a value entered with cmd line
            u1 = u_param; // set username ex cmd parameter
        }
        String un3m = u1.substring(0,u1.length()-3) + "***";
        System.out.println("username: " +un3m);
        WebElement username = driver.findElement(By.name("pf.username"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('value', '"+u1+"')", username);
    }

    public void enterPassword(WebDriver driver) {
        password.clear();
        String p1=p; // copy u
        String p_param = System.getProperty("userPw.cli");

        if(p_param != null){ // check if there is a value entered with cmd line
            p1 = p_param; // set username ex cmd parameter
        }
        System.out.println("password: ******");
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

    public void ForgotPwUn(WebDriver driver) {
        //to do: there are two such links to identified
        List<WebElement> helplink_anchors = forgotPwUn.findElements(By.tagName("a")); // get all anchors within first helplink class
        if (helplink_anchors.size() <1){
            System.out.println("Forgot Passwort/Username link missing");
        }
        else {
            String anchor;
            WebElement item, child;

            for (int i=0;i < helplink_anchors.size(); i++){ // loop through each in the list
                item = helplink_anchors.get(i);
                //child = item.findElement(By.tagName("a")); //get anchor
                //anchor = item.getAttribute("href"); //get the url of the page
                anchor = item.getText();
                System.out.println("Helplink ...: "+anchor); //output what link has been found
            }
        }
        //forgotPwUn.click();
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
