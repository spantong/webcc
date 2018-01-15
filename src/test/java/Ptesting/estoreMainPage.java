package Ptesting;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class estoreMainPage {

    @FindBy(css="div.search-bar.left")
    private WebElement searchbarleft;

    @FindBy(className="search-bar left")
    private WebElement searchbarleft1;

    @FindBy(className="support right")
    private WebElement supportright;

    @FindBy(id="account-nav")
    private WebElement accountnav;

    @FindBy(className = "information")
    private WebElement accountinformation;

    @FindBy(className = "order-details")
    private WebElement orderdetails;

    @FindBy(className = "categories")
    private WebElement categories;

    @FindBy(className = "main[class='container']")
    private WebElement maincontainer;

    /*
    @FindBy(css = "button.button.button-submit")
    private WebElement shopLoginButton;
    */

    public estoreMainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void inputSearchBar(WebDriver driver) {
        searchbarleft.click();
        WebElement searchbox = driver.findElement(By.cssSelector("div[class='search-bar left']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('value', 'blabla')", searchbox); // to be changed : not the field for input
    }

}
