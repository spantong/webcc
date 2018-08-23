package Ptesting;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class pUnitronB2bPortal {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static String[] catlinks;
    private static int ii;

    @BeforeClass
    public static void setUp() throws Exception {
        driver = new ChromeDriver(); // default
        System.out.println("Operation System: " + System.getProperty("os.name"));
    }

    @Test(groups = {"big"})
    public static void testUnB2bPortal(){
//WebDriverWait
        wait = new WebDriverWait(driver, 10);
        System.out.println("Starting with testing... " );
        driver.get("http://unitron.com/content/unitron/us/en/professional.html");
//        WebElement xxx = driver.findElement(By.xpath("//*[contains(@class,'ctaButton callToActionButton')]//a")); // test
        WebElement gotoo = wait.until((ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class,'ctaButton callToActionButton')]//a"))));
        gotoo.click(); // enter login page
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        System.out.println("Driver 1. has "+tabs.size()+" tab(s)");  // output on console line
        driver.switchTo().window(tabs.get(1));
        wait.until((ExpectedConditions.visibilityOfElementLocated(By.name("pf.username"))));
        WebElement username = driver.findElement(By.name("pf.username"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('value', 'sun5500admin')", username);
        WebElement upass = driver.findElement(By.name("pf.pass"));
        js.executeScript("arguments[0].setAttribute('value', 'phonak123')", upass);
        WebElement signin = driver.findElement(By.cssSelector("button.button.button-submit"));
        signin.click();  // login button clicking

        WebElement shopCategories = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.menu-container"))));
        //WebElement shopCategories = driver.findElement(By.cssSelector("div.menu-container"));
        List<WebElement> anchor_cats = shopCategories.findElements(By.tagName("a")); // get all avail category links
        WebElement item;
        String href, urlitem;


        ii = anchor_cats.size(); // for array
        String[][] catlinks = new String[ii][2]; // define so much lines as the size and each line has two spaces to store the values
        //catlinks = new String[ii]; // define needed array
        if (anchor_cats.size()>0){ // normally 6 categories to be process
            //int ii =anchor_cats.size(); // for array
            //String[] catlinks = new String[ii]; // define needed array
            for (int i = 0; i < anchor_cats.size(); i++) { // loop through each category
                item = anchor_cats.get(i);
                catlinks[i][0] = item.getAttribute("href");//get the url
                WebElement cattitle = item.findElement(By.tagName("p")); // get all avail category title
                catlinks[i][1] = cattitle.getText();//get the title and store in 2nd space
                int iii = i+1; // bcoz i starting with 0
                System.out.println("Category number "+iii+" Link: "+catlinks[i][0]+" Header: "+catlinks[i][1]); // console out
            }
        }
        // looping into all categories and do appropriated checks
        for (int cati = 0 ; cati < catlinks.length ; cati++){
            href = catlinks[cati][0]; // load category link
            driver.get(href); // go to page
            if (href.contains("hearing-aids")){ // hearing instruments
                wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.sub-categories"))));
                WebElement mode = driver.findElement(By.cssSelector("a[title='toggle fast track mode']")); // get the advanced mode switch
                if (mode != null) {
                    // mode switch found
                    WebElement modeImg = mode.findElement(By.tagName("img")); // get the mode status image
                    String imgModeOnOff = modeImg.getAttribute("src"); // get the text of mode
                    if (imgModeOnOff.contains("mode-off")) { // advanced mode is OFF

                        //count sub categories
                        List<WebElement> list = driver.findElements(By.cssSelector("div[class*='product product-normal']"));
                        System.out.println("Advanced mode OFF, total PRODUCTS displayed on screen: " + list.size());
                        WebElement subcat = driver.findElement(By.cssSelector("div[class='sub-categories']")); // get container
                        list = subcat.findElements(By.cssSelector("li")); // get all sub categories within container
                        System.out.println("Advanced mode OFF, total SUB-CATEGORIES displayed on screen: " + list.size());

                        //change to advanced mode display layout
                        js.executeScript("arguments[0].click();", mode); // change advanced mode to ON
                        //count HI items on screen
                        WebElement products = driver.findElement(By.cssSelector("ul.product-list")); // container of products
                        list = products.findElements(By.tagName("li")); // products tag
                        System.out.println("Advanced mode ON, total PRODUCTS displayed on screen: " + list.size()); // console output founded products
                        list = driver.findElements(By.cssSelector("div[class='sub-categories'] input[type='checkbox']")); // get all filters
                        System.out.println("Advanced mode ON, total FILTERS displayed on screen: " + list.size());

                        WebElement bteFilter = driver.findElement(By.xpath("//label[text()[contains(., ' BTE')]]")); // get BTE filter item
                        WebElement bteCheck = bteFilter.findElement(By.cssSelector("input"));
                        js.executeScript("arguments[0].click();", bteCheck); // click on element with javascript code
                        wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[title='toggle fast track mode']")))); // Wait for element
                        //}
                        //count HI items on screen
                        products = driver.findElement(By.cssSelector("ul.product-list")); // container of products
                        list = products.findElements(By.tagName("li")); // products tag
                        System.out.println("Filtered by BTE, total BTE displayed on screen: " + list.size()); // console output founded products
                        products = list.get(0); // get 1st elelement
                        WebElement thename = products.findElement(By.cssSelector("h3[class='name']")); // element containing name of the product
                        String pname = thename.getText(); // get the name of the product
                        WebElement pronr = products.findElement(By.cssSelector("h4")); // SAP product number
                        String pnbr = pronr.getText(); // get the product number of the product
                        WebElement p2cart = products.findElement(By.cssSelector("a.js-add-to-cart")); // get add to cart element for js click
                        js.executeScript("arguments[0].click();", p2cart); // add item to cart
                        System.out.println("This HI product has been added to cart: "+pname+" "+pnbr); // console output
                        // verify item added to cart
                        List<WebElement> addOKdialog = driver.findElements(By.cssSelector("div.notification.notification-cart"));  // add to card confirmation text element
                        if (addOKdialog.size()>0){  // confirmation text found
                            WebElement addOKdialogElement = addOKdialog.get(0); // item element containing text
                            WebElement addOKdialogP = addOKdialogElement.findElement(By.tagName("p")); // paragraph element
                            String addOKdialogText = addOKdialogP.getText(); // pull the text to var to output on console
                            System.out.println("Add to Cart Confirm Text : "+addOKdialogText); // console output
                        }
                    }
                }
                //System.out.println("Category content displayed: "+catlinks[cati][1]); // console out
            }
            else if(href.contains("/c/UN002")){ // accessories
                wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.sub-categories"))));
                //System.out.println("Category content displayed: "+catlinks[cati][1]); // console out
            }
            else if(href.contains("/earpieces")) {  // earpieces
                String aurl = driver.getCurrentUrl();
                if (!aurl.contains(href)){ // page not fully loaded
                    //---------
                    List<WebElement> dialog = driver.findElements(By.cssSelector("div[class='modal-container'] div[class*='modal-dialog'] div[class*='modal-footer'] a[href*='setOrderType']"));  // any dialog there?
                    if (dialog.size()>0){
                        System.out.println("Dialog box Client order is shown on screen");
                        WebElement change2ClientOrder = dialog.get(0); // get client button element
                        change2ClientOrder.click(); // change 2 client order
                        wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='modal-container']")))); // Wait element containing dialog
                        change2ClientOrder = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='modal-container'] div[class*='modal-dialog'] div[class*='modal-footer'] a"))));  // any dialog there?
                        change2ClientOrder.click(); // confirm OK
                        System.out.println("Changed to Client order and confirmed with OK");
                        //WebElement allSteps = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='input-add-to-cart ng-binding']")))); // Wait for element input-add-to-cart ng-binding
                        //js.executeScript("arguments[0].click();", allSteps);  // confirm add to cart
                        driver.get(href);
                        //driver.navigate().refresh(); // refresh to the page
                    }
                    //---------
                }
                wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.cta-footer"))));
                //System.out.println("Category content displayed: "+catlinks[cati][1]); // console out
            }
            else if(href.contains("/parts")) {  // parts
                wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.parts-categories"))));
                //System.out.println("Category content displayed: "+catlinks[cati][1]); // console out
            }
            else if(href.contains("/in-office")) {  // batteries/maintenance
                wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("main.container"))));
                //System.out.println("Category content displayed: "+catlinks[cati][1]); // console out
            }
            System.out.println("Category content displayed: "+catlinks[cati][1]); // console out
        }
        WebElement cartlink = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a.shopping-cart"))));
        cartlink.click(); //div.cart-container
        WebElement emptyCart = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a#empty-cart"))));
        emptyCart.click();
        //JavascriptExecutor js = (JavascriptExecutor) driver; // use javascript on client/browser
        //js.executeScript("arguments[0].click();", emptyCart); // click on invisible item with javascript code
        wait.until((ExpectedConditions.presenceOfElementLocated(By.id("emptyCartPopup")))); // Wait confirm box
        emptyCart = driver.findElement(By.cssSelector("a[class='js-modal-action']"));
        js.executeScript("arguments[0].click();", emptyCart); // click on confirmation button
        //wait.until((ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("p[class='button button-large checkout']")))); // Wait for element to disappear
        // check cart is empty
        wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("section.cart-header")))); // Wait for cart to reappear

        wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("p.button.text-only")))); // Wait for link text appearance
        System.out.println("Enter cart and empty/clear cart!");

        // go to myServices landing page
        WebElement myServices = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.services-list a"))));
        myServices.click();

        // exit with logout link
        WebElement logout = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/logout']"))));  // check logout menu
        String anchor = logout.getAttribute("href");
        driver.get(anchor);  // Logout user using href ex hidden menu
        wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/login/pw/request']"))));  // check forgot password link is shown after logout successfully
        System.out.println("Current step in testing... " );

        
    }

    @Test(groups = "medium")
    public static void testB2bPortal(){
        // 
    }

    @Test(groups = "misc")
    public class testestng {
        @DataProvider
        public Object[][] data() {
            return new String[][] {new String[] {"data1"}, new String[] {"data2"}};
        }

    }

    @AfterClass
    public static void tearDown()throws Exception {
        driver.quit();
    }
}
