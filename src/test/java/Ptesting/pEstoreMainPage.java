package Ptesting;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class pEstoreMainPage {



    @FindBy(css="div.search-bar.left")
    private WebElement searchbarleft;

    @FindBy(className="search-bar left")
    private WebElement searchbarleft1;

    @FindBy(css="div.support.right")
    private WebElement supportright;

    @FindBy (css = "div.main-nav.sticky_element")
    private WebElement sticky_element; // this part contains account/myshortcut/cart

    @FindBy(id="account-nav")
    private WebElement accountnav;

    @FindBy(className = "information")
    private WebElement accountinformation;

    @FindBy(className = "order-details")
    private WebElement orderdetails;

    @FindBy(className = "categories")
    private WebElement categories;

    @FindBy(css = "main.container")
    private WebElement maincontainer;

    @FindBy(css = "div.categories")
    public WebElement shopCategories;

    @FindBy(css = "div.footer")
    private WebElement shopFooter;



    public pEstoreMainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public int getCategoryNbr(WebDriver driver){
        List<WebElement> anchor_cats = shopCategories.findElements(By.tagName("a")); // get all avail category links
        if (anchor_cats.size()>0){
            return anchor_cats.size();
        }
        return 0;
    }

    public void eShopFooter (WebDriver driver){
        List<WebElement> footer_links = shopFooter.findElements(By.tagName("a")); // check footer links
        if (footer_links.size()==0){
            System.out.println("No any link in the footer area!");
        }
        else{
            System.out.println("There are "+footer_links.size()+" links in the footer area found");
            WebElement item, anchor;
            String href, text;
            for (int i=0;i < footer_links.size(); i++){ // loop through each link
                item = footer_links.get(i);
                //get the url of the page
                href = item.getAttribute("href");
                text = item.getText(); // label text
                System.out.println("Anchor = " + href+" "+text);
                // reset for the loop these two vars again due reference is somehow lost!
                footer_links = shopFooter.findElements(By.tagName("a")); // reset list pointer
            }
        }
    }

    public void eMaincontainer (WebDriver driver){
        //maincontainer.click();
        List<WebElement> anchor_container = maincontainer.findElements(By.tagName("a")); // get all main container links
        if (anchor_container.size()==0){
            System.out.println("No any link in the main container area!");
        }
        else{
            System.out.println("There are "+anchor_container.size()+" links in the container area");
            WebElement item, anchor;
            String href, text;
            for (int i=0;i < anchor_container.size(); i++){ // loop through each link
                item = anchor_container.get(i);
                //get the url of the page
                href = item.getAttribute("href");
                text = item.getText(); // label text
                System.out.println("Anchor = " + href+" "+text);
                // reset for the loop these two vars again due reference is somehow lost!
                anchor_container = maincontainer.findElements(By.tagName("a")); // reset list pointer
            }
        }
    }

    public void eShopCategories(WebDriver driver){
        //shopCategories.click();
        List<WebElement> anchor_cats = shopCategories.findElements(By.tagName("a")); // get all avail category links
        if (anchor_cats.size()==0){
            System.out.println("No any link to categories exists!");
        }
        else{
            System.out.println("There are "+anchor_cats.size()+" categories");
            WebElement item, anchor, paragraph;
            String href, text;
            for (int i=0;i < anchor_cats.size(); i++){ // loop through each link
                item = anchor_cats.get(i);
                //get the url of the page
                href = item.getAttribute("href");
                paragraph = item.findElement(By.tagName("p")); // element containing the label text
                text = paragraph.getText(); // label text
                System.out.println("Anchor = " + href+" "+text);
                // reset for the loop these two vars again due reference is somehow lost!
                anchor_cats = shopCategories.findElements(By.tagName("a")); // reset list pointer
            }
        }
    }

    public void inputSearchBar(WebDriver driver) {
        //searchbarleft.click();
        //WebElement searchy = driver.findElement(By.cssSelector("div[class='search-bar left']"));  // get element shop search box
        List<WebElement> searchInput = searchbarleft.findElements(By.tagName("input"));
        WebElement inputElement = searchInput.get(0); // get the input element
        JavascriptExecutor js = (JavascriptExecutor) driver;  // using javascript
        js.executeScript("arguments[0].setAttribute('value', 'blabla')", inputElement); // input something in the search field
    }
    public void eSupportRight(WebDriver driver){
        //supportright.click();
        String supportrighttext = supportright.getText();
        if (supportrighttext.length()==0){
            System.out.println("no support right text");
        }
        else{
            System.out.println("support right text = ["+supportrighttext+"]");
        }
    }
    public void eSticky_element(WebDriver driver){
        //sticky_element.click();
        List<WebElement> anchor_links = sticky_element.findElements(By.tagName("a")); // get all avail anchors/links
        if (anchor_links.size()==0){
            System.out.println("No any links to shortcut or cart!");
        }
        else{
            System.out.println("There are "+anchor_links.size()+" links");
            WebElement item, anchor;
            String href, text;
            for (int i=0;i < anchor_links.size(); i++){ // loop through each link
                item = anchor_links.get(i);
                //get the url of the page
                href = item.getAttribute("href");
                text = item.getText();
                System.out.println("Anchor = " + href+" "+text);
                // reset for the loop these two vars again due reference is somehow lost!
                anchor_links = sticky_element.findElements(By.tagName("a"));
            }
        }

    }

    public void eCartClear(WebDriver driver){
        //sticky_element.click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement sCart = sticky_element.findElement(By.cssSelector("a[class='shopping-cart']")); // get the cart
        WebElement iCart = sticky_element.findElement(By.cssSelector("span[id='items-in-cart']")); // get the items in the cart
        String cItems = iCart.getText();
        if (!cItems.contains("0")){
            System.out.println("Have items in cart! Clear it now...");
            sCart.click();
            wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("p.button.button-large.checkout")))); // Wait for button appearance
            WebElement emptyCart = driver.findElement(By.id("empty-cart"));
            String cText = emptyCart.getText();
            System.out.println("Empty Cart label :"+cText);
            JavascriptExecutor js = (JavascriptExecutor) driver; // use javascript on client/browser
            js.executeScript("arguments[0].click();", emptyCart); // click on invisible item with javascript code
            wait.until((ExpectedConditions.presenceOfElementLocated(By.id("emptyCartPopup")))); // Wait confirm box
            emptyCart = driver.findElement(By.cssSelector("a[class='js-modal-action']"));
            js.executeScript("arguments[0].click();", emptyCart); // click on confirmation button
            wait.until((ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("p[class='button button-large checkout']")))); // Wait for element to disappear
            // check cart is empty
            int loopi = 1;
            while (loopi!=0){
                iCart = sticky_element.findElement(By.cssSelector("span[id='items-in-cart']")); // get the items in the cart
                cItems = iCart.getText();
                if (cItems.contains("0")){
                    loopi = 0; // exit loop indicator
                }
            }

            System.out.println("Cart cleared!");
        }
        else{
            System.out.println("There is no item in the Cart");
        }

    }
}
