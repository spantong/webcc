package Ptesting;

//import com.sun.istack.internal.localization.NullLocalizable;
import junit.framework.TestFailure;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * Created by 11spantong on 19.10.2017.
 */
public class pEnterB2bShop {
    private static WebDriver driver;
//    private static String baseUrl;
    private boolean acceptNextAlert = true;
    private static StringBuffer verificationErrors = new StringBuffer();
    private static String fr = "a[href*='europe/fr_fr']"; // link of France country selection
    private static String usa = "a[href*='/north-america/us_en']"; // link of usa country selection
    private static String uk = "a[href*='/europe/uk_en']"; // link of uk country selection
    private static String ca = "a[href*='/north-america/ca_en']"; // link of ca en country selection
    private static WebDriverWait wait;
    private static int n;
    private static String nStep;
    private static String shop_anchor, c_param;




    @BeforeClass
    public static void setUp() throws Exception {

/*      Properties p = System.getProperties();
        Enumeration keys = p.keys();
        while (keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            String value = (String)p.get(key);
            System.out.println(key + ": " + value);
        }

        Properties props = new Properties();
        props.setProperty("1", "One");
        props.setProperty("2", "Two");
        props.setProperty("3", "Three");
        props.setProperty("4", "Four");
        props.setProperty("5", "Five");

        // Iterating properties using Enumeration

        @SuppressWarnings("unchecked")
        Enumeration<String> enums = (Enumeration<String>) props.propertyNames();
        while (enums.hasMoreElements()) {
          String key = enums.nextElement();
          String value = props.getProperty(key);
          System.out.println(key + " : " + value);
        }
//*/
        System.out.println("Operation System: "+System.getProperty("os.name"));
/*        String OSy = System.getProperty("os.name");
        if (OSy.contains("Win")){
            System.out.println("Operation System contains Win* ");
        }
*/
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
//  public void main(String[] args)throws Exception {
    public void testTestLogin51abrownCom() throws Exception {

        //WebDriverWait
        wait = new WebDriverWait(driver, 30);
        pShopLoginPage loginPage = new pShopLoginPage(driver);
        try {
            String b_param = System.getProperty("baseUrl.cli"); // get start url
            String baseUrl = b_param; // save baseurl ex cmd parameter
            loginPage.loadB2bPage(driver);
            loginPage.clickChange(driver);

            // Get command line input if started that way or via contineous integration (CI)
            String country=usa; // set up default country
            //System.out.println("Default country selection = " +country);
            c_param = System.getProperty("country.cli");

            if(c_param != null){ // check if there is a value entered with cmd line
                System.out.println("country = " +c_param);
                if(c_param.contentEquals("fr"))
                    country = fr;
                if(c_param.contentEquals("us"))
                    country = usa;
                if(c_param.contentEquals("uk"))
                    country = uk;
                if(c_param.contentEquals("ca"))
                    country = ca;
                //System.out.println("country.cli = " +country);
            }

            WebElement gotoo = wait.until((ExpectedConditions.elementToBeClickable(By.cssSelector(country)))); // wait for specific element country selection
            String gotoo_anchor = gotoo.getAttribute("href"); //get the url of the link
            System.out.println("Click on link "+gotoo_anchor);  // output on console line
            gotoo.click();  // result will be according AEM setting

            // Check in case we are testing on other system then P, that we are on that environment after click on country selection which is static to go to P most of the time.
            // Get command line input if started that way or via contineous integration (CI)
            // String b_param = System.getProperty("baseUrl.cli");

            if(b_param != null){ // check if there is a value entered with cmd line
                //System.out.println("baseUrl.cli = " +baseUrl);  // output actual value
                String c_url = driver.getCurrentUrl(); // get actual url
                if(!c_url.contains(baseUrl)){ // not on intented test system environment
                    System.out.println("You are on " +c_url+ " and NOT on " +baseUrl );  // output where you are and where it should be
                    // manipulate url by using baseUrl
                    // replace starting part with the baseUrl value
                    URL aURL = new URL(c_url); // using url class splitting
                    driver.get(baseUrl + aURL.getPath());  // change test environment

                }
            } // end test system switch

            wait.until((ExpectedConditions.urlContains("/home")));
            loginPage.clickCountryLogin(driver);

            wait.until((ExpectedConditions.elementToBeClickable(By.name("pf.username"))));
            loginPage.getMyAccount(driver); // check the top line is present b4 login
            loginPage.ForgotPwUn(driver); // check help link
            loginPage.getCreateId(driver); // check create your id
            loginPage.enterUsername(driver);
            loginPage.enterPassword(driver);
            loginPage.clickShopLoginButton();
            WebElement profile = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='logout']"))));  // check logout menu
            loginPage.getMyAccount(driver); // check the top line after logged in to compare with b4 login
            //get the exact link value of my-profile
            profile = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/account']"))));
            String anchor = profile.getAttribute("href");
            //Set var again, bcoz click() IS only possible with exact link value
            profile = driver.findElement(By.cssSelector("a[href='"+anchor+"']"));
            //check/validate bcoz default it's invisible
            if (profile.isDisplayed()) { // It must has status displayed otherwise cannot click on it
                profile.click();
            } else { // click via javascript code on none displayed item
                JavascriptExecutor js = (JavascriptExecutor) driver; // use javascript on client/browser
                js.executeScript("arguments[0].click();", profile); // click on invisible item with javascript code
            }

            // Switch to shop/eservices page
            WebElement logout = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/saml/logout']")))); // check page loaded and logout menu is there
            // start of my-account drop down menu check/processing
            WebElement myaccount_menubar = driver.findElement(By.cssSelector("div[class='account-name']"));
            List<WebElement> myaccount_links = myaccount_menubar.findElements(By.tagName("li")); // get all avail drop down links
            System.out.println(myaccount_links);
            WebElement item, child;
            String href, urlitem;
            for (int i=0;i < myaccount_links.size()-1; i++){ // loop through each drop down link and click on it to change to page
                item = myaccount_links.get(i);
                child = item.findElement(By.tagName("a")); //get anchor
                anchor = child.getAttribute("href"); //get the url of the page
                urlitem = driver.getCurrentUrl(); //get actual page url to compare
                if (!anchor.equals(urlitem)){ // only change if it's not the the same (landingpage)
                    if(!anchor.contains("user-management") && !anchor.contains("salesforce")){ // skip user-management order- and documenthistory due to long processing
                        // if(!anchor.contains("user-management") && !anchor.contains("order-history") && !anchor.contains("documenthistory") && !anchor.contains("salesforce")){ // skip user-management order- and documenthistory due to long processing
                        driver.get(anchor);  // change to that page (clicking somehow not working here)
                    }
                    else{
                        System.out.println("This page is skipped: " + anchor);
                    }
                }
                wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/saml/logout']")))); // check page loaded and logout menu is there
                urlitem = driver.getCurrentUrl(); //get actual page url to compare
                if (anchor.equals(urlitem)){
                    System.out.println("This page is displayed: " + anchor);
                }
                else{
                    System.out.println("ATTN: Wrong page " + urlitem + ", should show this page " + anchor ); // output warning that not the same page is showing as in the link!
                }
                // reset for the loop these two vars again due reference is somehow lost!
                myaccount_menubar = driver.findElement(By.cssSelector("div[class='account-name']"));
                myaccount_links = myaccount_menubar.findElements(By.tagName("li"));
            }

            List<WebElement> logoutBtn = driver.findElements(By.id("id_accountmenu_logout_link"));
            if (!logoutBtn.isEmpty()) {
                WebElement logoutBtnElement = logoutBtn.get(0);
                anchor = logoutBtnElement.getAttribute("href");
                System.out.println(anchor); // console output logout link
            }
            else{
                System.out.println("Logout not possible, missing that element!");
            }
            // end of my-account drop down check/processing

            // start verify shop items
            boolean shoplink = isElementPresent(By.cssSelector("li[class='homenav-li'] a[class='homenav-aa']"));
            if(shoplink) {  // shop link is available
                String actualText = driver.findElement(By.cssSelector("li[class='homenav-li'] a[class='homenav-aa']")).getText(); // get the label text
                System.out.println("Shop link name: " +actualText); // output to show language specific text
                WebElement store = driver.findElement(By.cssSelector("li[class='homenav-li'] a[class='homenav-aa']")); // get the Store link
                shop_anchor = store.getAttribute("href");  // get and save the exact url
                store.click(); // enter the shop, switch from eservices to estore

                // temporary uk overwrite bcoz not went live yet, but soon
                tempUkProcess(driver); // special UK treatment


                wait.until((ExpectedConditions.urlContains("shop.phonakpro.com")));
                urlitem = driver.getCurrentUrl(); //get actual page url to compare
                if (shop_anchor.equals(urlitem)){  //current main shop page is same as in the link
                    pEstoreMainPage estorePage = new pEstoreMainPage(driver); // instanciate shop page object
                    Assert.assertTrue(isElementPresent(driver, By.cssSelector("div[class='search-bar left']"))); // check search bar is present
                    estorePage.inputSearchBar(driver); // enter a text value into the search box
                    estorePage.eSupportRight(driver); // check support text on the same line on the right
                    estorePage.eSticky_element(driver); // check the  line containing account nbr and shartcuts and cart.
                    estorePage.eShopCategories(driver); // check the categories
                    // to do: scan all categories for completeness
                    estorePage.eMaincontainer(driver); // check the main area have links
                    estorePage.eShopFooter(driver); // check shop footer

                    ///TEST
                    List<WebElement> anchor_cats;
                    anchor_cats = estorePage.shopCategories.findElements(By.tagName("a")); // get all avail category links
                    int ii =anchor_cats.size(); // for array
                    String[] catlinks = new String[ii]; // define needed array
                    for (int i = 0; i < anchor_cats.size(); i++) { // loop through each category
                        item = anchor_cats.get(i);
                        catlinks[i] = item.getAttribute("href");//get the url
                        int iii = i+1; // bcoz i starting with 0
                        //System.out.println("Category number "+iii+" = "+catlinks[i]); // console out
                    }
                    //System.out.println("Length of stored Category array = "+catlinks.length); // console out
                    /// END TEST

                    // Send order what's actually in the cart
                    sendOrder(driver);
                    // clear cart
                    eCartClear(driver);
                    //estorePage.eCartClear(driver);
                    System.out.println("Actual Url after cart clear =  "+driver.getCurrentUrl());
                    store = driver.findElement(By.cssSelector("li[class='homenav-li'] a[class='homenav-aa']")); // get the Store link
                    JavascriptExecutor js = (JavascriptExecutor) driver; // use javascript on client/browser
                    js.executeScript("arguments[0].click();", store); // click on invisible item with javascript code
                    //store.click();  // go back to store main page

                    tempUkProcess(driver); // special UK treatment

                    wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.categories"))));  // categories menu bar is there
                    int catNbr = estorePage.getCategoryNbr(driver);

                    if(catNbr!=0) { //
                        //List<WebElement> anchor_cats;
                        WebElement imageElement;
                        String tImageElement;

                        anchor_cats = estorePage.shopCategories.findElements(By.tagName("a")); // get all avail category links
                        for (int i = 0; i < catlinks.length; i++) { // loop through each category
                            href = catlinks[i]; // load category link
// temp set i to start at nbr 3 (for Parts & Fitting category)
                        // for (int i = 0; i < anchor_cats.size(); i++) { // loop through each category
                            //item = anchor_cats.get(i);
                            //get the url of the page
                            // href = item.getAttribute("href");
                            //imageElement = item.findElement(By.tagName("img")); // check picture element
                            //tImageElement = imageElement.getAttribute("src"); // get the text name of the image
                            //System.out.println("Anchor = " + href+" "+tImageElement); // console out
                            System.out.println("Anchor = " + href); // console out
                            driver.get(href); // enter the first category, usually it's HI category
                            wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.main-nav.sticky_element")))); // Wait for new page loading finish
                            driver.navigate().refresh(); // refresh to get rid of the account popup that appears if cart is empty
                            WebElement mode, modeImg, products, subcat, nextbtn, cfitting, selectModel, bteFilter, bteCheck, p2cart;
                            String imgModeOnOff, configure;
                            List<WebElement> list, dk_option;
                            if (href.contains("hearing-aids")) { // check to process HI category related actions
                            //if (tImageElement.contains("hearing-aids")) { // check to process HI category related actions
//                                driver.navigate().refresh(); // refreh to get rid of the account popup that appears if cart is empty
                                wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[title='toggle fast track mode']")))); // Wait for element
                                mode = driver.findElement(By.cssSelector("a[title='toggle fast track mode']")); // get the advanced mode switch
                                if (mode != null) { // mode switch found
                                    int onOff = 2; // init counter for ON/OFF mode check
                                    for (int i1 = 0; i1 != onOff; ) { // do it for Mode ON and OFF
                                        modeImg = mode.findElement(By.tagName("img")); // get the mode status image
                                        imgModeOnOff = modeImg.getAttribute("src"); // get the text of mode
                                        if (imgModeOnOff.contains("mode-on")) { // advanced moe is ON
                                            //count HI items on screen
                                            products = driver.findElement(By.cssSelector("ul.product-list")); // container of products
                                            list = products.findElements(By.tagName("li")); // products tag
                                            System.out.println("Advanced mode ON, total PRODUCTS displayed on screen: " + list.size()); // console output founded products
                                            list = driver.findElements(By.cssSelector("div[class='sub-categories'] input[type='checkbox']")); // get all filters
                                            System.out.println("Advanced mode ON, total FILTERS displayed on screen: " + list.size());
                                            // add a bte device to cart
                                            bteFilter = driver.findElement(By.xpath("//label[text()[contains(., ' BTE')]]")); // get BTE filter item
                                            bteCheck = bteFilter.findElement(By.cssSelector("input"));
                                            js.executeScript("arguments[0].click();", bteCheck); // click on element with javascript code
                                            wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[title='toggle fast track mode']")))); // Wait for element

                                            //count HI items on screen
                                            products = driver.findElement(By.cssSelector("ul.product-list")); // container of products
                                            list = products.findElements(By.tagName("li")); // products tag
                                            System.out.println("Filtered by BTE, total BTE displayed on screen: " + list.size()); // console output founded products
                                            products = list.get(0); // get 1st elelement
                                            WebElement thename = products.findElement(By.cssSelector("h3[class='name']")); // element containing name of the product
                                            String pname = thename.getText(); // get the name of the product
                                            WebElement pronr = products.findElement(By.cssSelector("h4")); // SAP product number
                                            String pnbr = pronr.getText(); // get the product number of the product
                                            p2cart = products.findElement(By.cssSelector("a.js-add-to-cart")); // get add to cart element for js click
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

                                            onOff--; // decrease loop counter
                                        }
                                        if (imgModeOnOff.contains("mode-off")) { // advanced mode is OFF
                                            //count sub categories
                                            list = driver.findElements(By.cssSelector("div[class*='product product-normal']"));
                                            System.out.println("Advanced mode OFF, total PRODUCTS displayed on screen: " + list.size());
                                            subcat = driver.findElement(By.cssSelector("div[class='sub-categories']")); // get container
                                            list = subcat.findElements(By.cssSelector("li")); // get all sub categories within container
                                            System.out.println("Advanced mode OFF, total SUB-CATEGORIES displayed on screen: " + list.size());
                                            onOff--; // decrease loop counter
                                        }
                                        if (i1 == onOff) {
                                            break;
                                        }

                                        mode.click(); // change advanced mode ON/OFF
                                        wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[title='toggle fast track mode']")))); // Wait for element
                                        mode = driver.findElement(By.cssSelector("a[title='toggle fast track mode']")); // reset the advanced mode switch
                                    }
                                }
                            }else if (href.contains("wireless-accessories")){ // check to process wireless accessories category related actions
//                                driver.navigate().refresh(); // refreh to get rid of the account popup that appears if cart is empty
                                wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[title='toggle fast track mode']")))); // Wait for element
                                mode = driver.findElement(By.cssSelector("a[title='toggle fast track mode']")); // get the advanced mode switch
                                if(mode!=null){ // mode switch found
                                    int onOff = 2; // init counter for ON/OFF mode check
                                    while (onOff>0){ // do it for Mode ON and OFF
                                        modeImg = mode.findElement(By.tagName("img")); // get the mode status image
                                        imgModeOnOff = modeImg.getAttribute("src"); // get the text of mode
                                        if(imgModeOnOff.contains("mode-on")){ // advanced moe is ON
                                            //count HI items on screen
                                            products = driver.findElement(By.cssSelector("ul.product-list")); // container of products
                                            list = products.findElements(By.tagName("li")); // products tag
                                            System.out.println("Advanced mode ON, total PRODUCTS displayed on screen: "+list.size()); // console output founded products
                                            products = list.get(0); // get 1st elelement
                                            WebElement thename = products.findElement(By.cssSelector("h3[class='name']")); // element containing name of the product
                                            String pname = thename.getText(); // get the name of the product
                                            WebElement pronr = products.findElement(By.cssSelector("h4")); // SAP product number
                                            String pnbr = pronr.getText(); // get the product number of the product
                                            p2cart = products.findElement(By.cssSelector("a.js-add-to-cart")); // get add to cart element for js click
                                            js.executeScript("arguments[0].click();", p2cart); // add item to cart
                                            System.out.println("This WA product has been added to cart: "+pname+" "+pnbr); // console output
                                            // verify item added to cart
                                            List<WebElement> addOKdialog = driver.findElements(By.cssSelector("div.notification.notification-cart"));  // add to card confirmation text element
                                            if (addOKdialog.size()>0){  // confirmation text found
                                                WebElement addOKdialogElement = addOKdialog.get(0); // item element containing text
                                                WebElement addOKdialogP = addOKdialogElement.findElement(By.tagName("p")); // paragraph element
                                                String addOKdialogText = addOKdialogP.getText(); // pull the text to var to output on console
                                                System.out.println("Add to Cart Confirm Text : "+addOKdialogText); // console output
                                            }
                                            list = driver.findElements(By.cssSelector("div[class='sub-categories'] li")); // get all sub-menus
                                            System.out.println("Advanced mode ON, total SUB-MENU displayed on screen: "+list.size());
                                            onOff--; // decrease loop counter
                                        }
                                        if(imgModeOnOff.contains("mode-off")){ // advanced mode is OFF
                                            //count sub categories
                                            list = driver.findElements(By.cssSelector("div[class*='product product-normal']"));
                                            System.out.println("Advanced mode OFF, total PRODUCTS displayed on screen: "+list.size());
                                            subcat = driver.findElement(By.cssSelector("div[class='sub-categories']")); // get container
                                            list = subcat.findElements(By.cssSelector("li")); // get all sub categories within container
                                            System.out.println("Advanced mode OFF, total SUB-CATEGORIES displayed on screen: "+list.size());
                                            onOff--; // decrease loop counter
                                        }
                                        if (onOff==0){
                                            break;
                                        }
                                        mode.click(); // change advanced mode ON/OFF
                                        wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[title='toggle fast track mode']")))); // Wait for element
                                        mode = driver.findElement(By.cssSelector("a[title='toggle fast track mode']")); // reset the advanced mode switch
                                    }
                                }
                            } else if (href.contains("earpieces")){ // check to process WA category related actions)
//                                driver.navigate().refresh(); // refreh to get rid of the account popup that appears if cart is empty
                                wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[id='custom-fittings']")))); // Wait for element
                                cfitting = driver.findElement(By.cssSelector("div[id='custom-fittings']")); // get the custom fitting container

                                nextbtn = cfitting.findElement(By.cssSelector("div.input-cta-configure-product.disabled.ng-binding")); // get next button element
                                configure = nextbtn.getText(); // button text label
                                System.out.println("Text of the next configure button: "+configure);
                                //Select step 1 options:
                                // tricky to select the drop down because it's not a classic html element, it's contructed by div element
                                // select family
                                WebElement earPieceFamily = driver.findElement(By.id("dk1-ear-piece-family")); // get drop down web element
                                earPieceFamily.click(); // expand drop down and make it possible to select an option, otherwise options are hidden and error if click on them directly
                                List<WebElement> ddOptions = earPieceFamily.findElements(By.tagName("li"));  // get all options
                                // WebElement ddItem = ddOptions.get(1); // get 2nd options bcoz first is just text label
// ************
                                WebElement ddItem = ddOptions.get(1); // temp test, change which item number in bracket
                                String idOfOption = ddItem.getAttribute("id"); // get the id content of the option
                                String xxx2 = ddItem.getText(); // just to see what's there
                                WebElement selectFamily = driver.findElement(By.id(idOfOption));  // get element to click on
                                js.executeScript("arguments[0].click();", selectFamily); // click on item with javascript code
                                System.out.println("Earpiece families: "+ddOptions.size());
                                System.out.println("Earpiece family selected : "+xxx2); // get the text value
                                // Select model
                                WebElement earPieceModel = driver.findElement(By.cssSelector("div[id*='ear-piece-family-model']")); // get drop down web element
                                earPieceModel.click(); // expand drop down and make it possible to select an option, otherwise options are hidden and error if click on them directly
                                ddOptions = earPieceModel.findElements(By.tagName("li"));  // get all options
                                ddItem = ddOptions.get(1); // get 2nd options bcoz first is just text label
                                //ddItem = ddOptions.get(2); // get 3rd option to test it explicitely
                                idOfOption = ddItem.getAttribute("id"); // get the id content of the option; /////
                                xxx2 = ddItem.getText(); // just to see what's there
                                WebElement selectModelOption = driver.findElement(By.id(idOfOption));  // click on our 2nd option
                                js.executeScript("arguments[0].click();", selectModelOption); // click on item with javascript code
                                System.out.println("Earpiece models: "+ddOptions.size());
                                System.out.println("Earpiece model selected : "+xxx2); // get the text value
                                // step 2. select ear sides R/L
                                WebElement earSide = driver.findElement(By.cssSelector("div.step.position-select")); // get step 2 container
                                List<WebElement> earCheckBox = earSide.findElements(By.cssSelector("div.checkbox")); // collect check box elements
                                WebElement clickEar = earCheckBox.get(0); // load first checkbox
                                clickEar.click(); // click on checkbox
                                System.out.println("Right Ear selected");
                                clickEar = earCheckBox.get(1); // load 2nd checkbox
                                clickEar.click(); // click on checkbox
                                System.out.println("Left Ear selected");
                                // step 3, fill out the audiogram
                                WebElement audiograms = driver.findElement(By.cssSelector("div.step.select-audiograms")); // get step 3 container
                                List<WebElement> inputFields = driver.findElements(By.cssSelector("div[id='rightAudiogram'] input"));  // get all input flds in this div container
                                int inputValue = 5; // init start value
                                String inpString = Integer.toString(inputValue); // int to string
                                for (int inp=0; inp < inputFields.size(); inp++ ){
                                    WebElement inputField = inputFields.get(inp); // get 1st fld
                                    String q1 = inputField.getAttribute("id"); // get the id
                                    inputField = driver.findElement(By.id(q1)); // set by id
                                    inputField.click();
                                    JavascriptExecutor jsun = (JavascriptExecutor) driver;
                                    String jscode;
                                    jscode = String.format("arguments[0].value='%s';",inpString); // set up javascript format to chang value
                                    jsun.executeScript(jscode, inputField); // run javascript command with fld as param
                                    if (inputValue==5){ // Title line
                                        System.out.println("Following values are set in the Audiogram:");
                                    }
                                    System.out.println(inpString);
                                    inputValue = inputValue + 5; // add 5 to value for the next fld
                                    inpString = Integer.toString(inputValue); // int to string
                                }
                                // due to the chacteristic of the browser interaction filling in the value above doesn't draw any audigram lines, we need copy to left and than back to get the wanted visibility of the lines
                                driver.findElement(By.cssSelector("div[id='cta-copy-left']")).click(); // copy same audiogram to the left, which draws the lines
                                driver.findElement(By.cssSelector("div[id='cta-copy-right']")).click(); // vice versa to see the lines also on the right side

                                // check impression processing
                                WebElement earImpression = driver.findElement(By.cssSelector("div.step.select-ear-impression")); // get step 4 container
                                // do the right side
                                List<WebElement> form_options = earImpression.findElements(By.cssSelector("div[class='type'] input[name='ear-impression-Right']")); //find all available options
                                if (form_options.size()==0){
                                    System.out.println("error : there is no impression option available!");
                                    throw new myException("error : there is no impression option available!");
                                }
                                int mi_iei=0;
                                WebElement itemChecked;
                                String actualOption;
                                for (int iei = 0; iei < form_options.size(); iei++){
                                    itemChecked = form_options.get(iei); // click on each options starting from top to down
                                    js.executeScript("arguments[0].click();", itemChecked);
                                    actualOption = itemChecked.getAttribute("id");  // get the id name
                                    if (actualOption.contains("upload-file")){
                                        System.out.println("File Upload option selected for right ear");
                                    }else if (actualOption.contains("file-impression")){
                                        System.out.println("Enter S/N option selected for right ear");
                                    }else if (actualOption.contains("mail-in")){
                                        System.out.println("Mail-in option selected for right ear");
                                        mi_iei = iei; // save this to set as default
                                    }
                                }
                                itemChecked = form_options.get(mi_iei);
                                js.executeScript("arguments[0].click();", itemChecked);
                                System.out.println("Final Mail-in option selected for right ear");

                                // the same for left side
                                form_options = earImpression.findElements(By.cssSelector("div[class='type'] input[name='ear-impression-Left']")); //find all available options
                                for (int iei = form_options.size(); iei > 0; iei--){
                                    itemChecked = form_options.get(iei-1); // click on each options starting from down to top
                                    js.executeScript("arguments[0].click();", itemChecked);
                                    actualOption = itemChecked.getAttribute("id");
                                    if (actualOption.contains("upload-file")){
                                        System.out.println("File Upload option selected for left ear");
                                    }else if (actualOption.contains("file-impression")){
                                        System.out.println("Enter S/N option selected for left ear");
                                    }else if (actualOption.contains("mail-in")){
                                        System.out.println("Mail-in option selected for left ear");
                                        // mi_iei = iei-1; // default is equal above
                                    }
                                }
                                itemChecked = form_options.get(mi_iei);
                                js.executeScript("arguments[0].click();", itemChecked);
                                System.out.println("Final Mail-in option selected for left ear");

                                // next page continuation
                                nextbtn = driver.findElement(By.cssSelector("div.input-cta-configure-product.ng-binding")); // get next button element
                                js.executeScript("arguments[0].click();", nextbtn);
                                System.out.println("Total options available: "+form_options.size());
// 2nd page of the configuration
                                // 2nd page of earpiece configuration
                                System.out.println("*** Start of the Second Page of the Configurator: ***");
                                wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[id='custom-product']")))); // Wait for element
                                WebElement allSteps = driver.findElement(By.cssSelector("div[id='custom-product']")); // get element

                                WebElement step1 = allSteps.findElement(By.cssSelector("div[step='1']")); // Get step 1 container

                                String s1label= step1.getAttribute("label");
                                System.out.println("Title of step 1: "+s1label);

                                WebElement modelOnPage2 = driver.findElement(By.cssSelector("div.custom.ng-isolate-scope"));  // get the element containing the models
                                List<WebElement> modelList = modelOnPage2.findElements(By.cssSelector("li"));  // count how many are there (some are hidden)
                                WebElement modelSelect=null, forJs=null, step2;
                                for (int li=0; li<modelList.size();li++) {
                                    modelSelect = modelList.get(li); // select the item
                                    String classHidden = modelSelect.getAttribute("class");
                                    if (classHidden.contains("ng-hide"))
                                        System.out.println("This model number "+li+" is hidden: " +modelSelect.getText());
                                    else {
                                        System.out.println("This model number "+li+" is visible: " +modelSelect.getText());
                                        if (forJs==null){
                                            forJs = modelSelect.findElement(By.tagName("a")); // get the 1st anchor to click on with js command below
                                        }
                                    }
                                }

                                js.executeScript("arguments[0].click();", forJs);  // mark the model
                                System.out.println("Final options selected: "+forJs.getText());
                                System.out.println("Total options available: "+modelList.size());
                                wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[id='custom-product']")))); // Wait for element
                                allSteps = driver.findElement(By.cssSelector("div[id='custom-product']")); // get element
                                wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[id='custom-product'] div[step='2']")))); // Wait for element is available
                                step2 = allSteps.findElement(By.cssSelector("div[step='2']")); // Get step 2 container
                                String s2label= step2.getAttribute("label"); // get the text label
                                System.out.println("Title of step 2: "+s2label);

                                WebElement parent = step2.findElement(By.xpath("..")); // get parent element
                                s2label= parent.getAttribute("id"); //
                                n = 2; // init step 2
                                if (s2label.contains("shell-styles")){  // Shell style step (not always available)
                                    chooseShellStyle(driver); // step 2
                                    n++; // encrease nxt step = 3
                                    chooseLength(driver);   // step 3
                                    n++; // encrease nxt step = 4
                                } else if (s2label.contains("power-levels")){ // select power level step
                                    chooseReceiver(driver); // step 2
                                    n++; // encrease nxt step = 3
                                    chooseLength(driver);   // step 3
                                    n++; // encrease nxt step = 4
                                } else if (s2label.contains("lengths")){  // select length
                                    chooseLength(driver);   // step 2
                                    n++; // encrease nxt step = 3
                                }

                                nStep = Integer.toString(n); // text value of nxt step
                                // Next step 3 or 4:
                                wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[id='custom-product']")))); // Wait for element
                                allSteps = driver.findElement(By.cssSelector("div[id='custom-product']")); // load element
                                wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[id='custom-product'] div[step='"+nStep+"']")))); // Wait for element is available
                                WebElement step4 = allSteps.findElement(By.cssSelector("div[step='"+nStep+"']")); // Get nxt step container
                                String s4label= step4.getAttribute("label");
                                System.out.println("Title of step "+nStep+": "+s4label);
                                wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[id='RightShellColors']")))); // Wait for element
                                allSteps = driver.findElement(By.cssSelector("div[id='RightShellColors']")); // get the element
                                List<WebElement> colorList = allSteps.findElements(By.cssSelector("a"));
                                System.out.println("Total colors "+colorList.size()+ " available");
                                WebElement colorSelect = colorList.get(0);
                                js.executeScript("arguments[0].click();", colorSelect);  // mark the 1st color
                                //colorSelect.click();
                                String coItemText = colorSelect.getAttribute("data-color-id");
                                System.out.println("Final Color selected: "+coItemText);

                                // next step 4 or 5:
                                n++; //set up nxt step
                                nStep = Integer.toString(n); // int of nxt step to string
                                wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[id='earpiece-options']")))); // Wait for element
                                allSteps = driver.findElement(By.cssSelector("div[id='earpiece-options']")); // Wait for element

                                wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[id='custom-product'] div[step='"+nStep+"']")))); // Wait for element is available

                                WebElement step5 = allSteps.findElement(By.cssSelector("div[step='"+nStep+"']")); // Get nxt step container
                                String s5label= step5.getAttribute("label");
                                System.out.println("Title of step "+nStep+": "+s5label);
                                String avMode = step5.findElement(By.cssSelector("div.left.cta-label.ng-binding")).getText();
                                System.out.println("Advanced Mode text value: "+avMode);
                                //List<WebElement> oCounter = allSteps.findElements(By.cssSelector("div[class='column-1']")); // options
                                WebElement optionContainer = allSteps.findElement(By.cssSelector("div[class='option-select'] div[class='column-1'] div[class='ng-isolate-scope']"));
                                List<WebElement> optionCounter = optionContainer.findElements(By.cssSelector("div[class='item ng-scope']")); // options
                                System.out.println("Available options: "+optionCounter.size()+" in default mode");
                                WebElement amSwitch = step5.findElement(By.cssSelector("div.switch.left > label"));
                                js.executeScript("arguments[0].click();", amSwitch);
                                //amSwitch.click();
                                System.out.println("Clicked on Advanced Mode");
                                wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[id='earpiece-options']")))); // Wait for element
                                optionContainer = allSteps.findElement(By.cssSelector("div[class='option-select'] div[class='column-1'] div[class='ng-isolate-scope']"));
                                optionCounter = optionContainer.findElements(By.cssSelector("div[class='item ng-scope']")); // options
                                System.out.println("Available options: "+optionCounter.size()+" in advanced mode");
                                System.out.println("Final Options selected: No action, leave default as shown on screen");
                                WebElement confirm = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='input-cta-configure-product ng-binding']")))); // Wait for element
                                js.executeScript("arguments[0].click();", confirm);
                                //confirm.click(); // click on confirm button
// 3rd page of the configuration
                                allSteps = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='instructions-border']")))); // Wait for element
                                JavascriptExecutor jssp = (JavascriptExecutor) driver;
                                WebElement siTextArea = allSteps.findElement(By.cssSelector("textarea"));
                                String textValue = "This is a test please ignore.";
                                String jscode = String.format("arguments[0].value='%s';",textValue); // set up javascript format to chang value
                                jssp.executeScript(jscode, siTextArea); // run javascript command with fld as param
                                System.out.println("Special instructions text added: " +textValue);
                                allSteps = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='input-add-to-cart ng-binding']")))); // Wait for element input-add-to-cart ng-binding
                                // add to card using javascript
                                js.executeScript("arguments[0].click();", allSteps);
                                //allSteps.click();  // put product into cart
                                List<WebElement> addOKdialog = driver.findElements(By.cssSelector("div.notification.notification-cart"));  // add to card confirmation text element
                                if (addOKdialog.size()>0){
                                    WebElement addOKdialogElement = addOKdialog.get(0); //
                                    WebElement addOKdialogP = addOKdialogElement.findElement(By.tagName("p"));
                                    String addOKdialogText = addOKdialogP.getText();
                                    System.out.println("Add to Cart Confirm Text : "+addOKdialogText);
                                }
                                wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='modal-container']")))); // Wait element containing dialog
                                List<WebElement> dialog = driver.findElements(By.cssSelector("div[class='modal-container'] div[class*='modal-dialog'] div[class*='modal-footer'] a[href*='setOrderType']"));  // any dialog there?
                                if (dialog.size()>0){
                                    System.out.println("Dialog box Client order is shown on screen");
                                    WebElement change2ClientOrder = dialog.get(0); // get client button element
                                    change2ClientOrder.click(); // change 2 client order
                                    wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='modal-container']")))); // Wait element containing dialog
                                    change2ClientOrder = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='modal-container'] div[class*='modal-dialog'] div[class*='modal-footer'] a"))));  // any dialog there?
                                    change2ClientOrder.click(); // confirm OK
                                    System.out.println("Changed to Client order and confirmed with OK");
                                    allSteps = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='input-add-to-cart ng-binding']")))); // Wait for element input-add-to-cart ng-binding
                                    js.executeScript("arguments[0].click();", allSteps);  // confirm add to cart
                                }

                                allSteps = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='input-add-to-cart ng-binding disabled']")))); // Wait element is disabled
                                System.out.println("Earpieces added into cart");

                            }else if (href.contains("parts")) { // check to process category Parts and Fitting
                                wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.parts-categories")))); // Wait for the title element to reappear
                                WebElement pfgrid = driver.findElement(By.cssSelector("div.parts-categories")); // get the grid

                                List<WebElement> pftiles = driver.findElements(By.cssSelector("div.parts-categories > ul > li")); // count sub-categories tiles within grid
                                System.out.println("Found "+pftiles.size()+" Parts & Fitting sub-categories");
                                for (int p = 0; p < pftiles.size(); p++) { // test all sub-categories
                                    WebElement pfcat = pftiles.get(p); // actual item
                                    WebElement tilediv = pfcat.findElement(By.cssSelector("div")); // get the 1st element from the list
                                    String tileclass = tilediv.getAttribute("class"); // get class name
                                    if (tileclass.contains("card")){
                                        WebElement pfitem = pfcat.findElement(By.cssSelector("div.face-front.js-show-parts")); //tile image
                                        js.executeScript("arguments[0].click();", pfitem);  // click with js cmd
                                        WebElement dd_selectfamily = pfcat.findElement(By.cssSelector("div[id='dk1-combobox']")); // drop down element
                                        js.executeScript("arguments[0].click();", dd_selectfamily);  // click on it with js cmd to expand the drop down
                                        WebElement parent = dd_selectfamily.findElement(By.xpath("..")); // get parent element
                                        List<WebElement> listoffamilies = parent.findElements(By.cssSelector("li")); // count families
                                        WebElement selectfamily = listoffamilies.get(1); // get top item from the list
                                        js.executeScript("arguments[0].click();", selectfamily);  // click to select item
                                        //
                                        WebElement dd_selectmodel = pfcat.findElement(By.cssSelector("div[id*='spare-parts-model'] div[id*='combobox']")); // model drop down element
                                        js.executeScript("arguments[0].click();", dd_selectmodel);  // click on it with js cmd to expand the drop down
                                        WebElement parentmodel = dd_selectmodel.findElement(By.xpath("..")); // get parent element
                                        List<WebElement> listofmodels = parentmodel.findElements(By.cssSelector("li")); // count families
                                        WebElement selectmodel = listofmodels.get(1); // get top item from the list
                                        js.executeScript("arguments[0].click();", selectmodel);  // click to select item
                                        WebElement submitbutton = parentmodel.findElement(By.xpath("../p/a")); // submit button element
                                        js.executeScript("arguments[0].click();", submitbutton);  // submit - this action will change to a different page
                                        wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("main.container")))); // Wait for the container element

                                        // we are now on the spare parts page
                                        products = driver.findElement(By.cssSelector("ul.product-list")); // container of products
                                        list = products.findElements(By.tagName("li")); // products tag
                                        System.out.println("Total Products displayed on screen: " + list.size()); // console output founded products
                                        products = list.get(0); // get 1st elelement
                                        WebElement thename = products.findElement(By.cssSelector("h3[class='name']")); // element containing name of the product
                                        String pname = thename.getText(); // get the name of the product
                                        WebElement pronr = products.findElement(By.cssSelector("h4")); // SAP product number
                                        String pnbr = pronr.getText(); // get the product number of the product
                                        p2cart = products.findElement(By.cssSelector("a.js-add-to-cart")); // get add to cart element for js click
                                        js.executeScript("arguments[0].click();", p2cart); // add item to cart
                                        System.out.println("This spare part product has been added to cart: "+pname+" "+pnbr); // console output
                                        // verify item added to cart
                                        List<WebElement> addOKdialog = driver.findElements(By.cssSelector("div.notification.notification-cart"));  // add to card confirmation text element
                                        if (addOKdialog.size()>0){  // confirmation text found
                                            WebElement addOKdialogElement = addOKdialog.get(0); // item element containing text
                                            WebElement addOKdialogP = addOKdialogElement.findElement(By.tagName("p")); // paragraph element
                                            String addOKdialogText = addOKdialogP.getText(); // pull the text to var to output on console
                                            System.out.println("Add to Cart Confirm Text : "+addOKdialogText); // console output
                                        }
                                        driver.navigate().back(); // return back one page to parts & fitting main page to process the next tile
                                        wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.parts-categories")))); // Wait for the title element to reappear
                                    } else if (tileclass.contains("product")){ // it's a product
                                        WebElement shopitem = tilediv.findElement(By.cssSelector("p > a")); // get the shop link
                                        js.executeScript("arguments[0].click();", shopitem);  // click with js cmd on shop link
                                        WebElement maincontainer = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("main.container")))); // Wait for the container element
                                        WebElement mc_div = maincontainer.findElement(By.cssSelector("div")); //get the first div
                                        String firstdivclass = mc_div.getAttribute("class"); // get the value of class=
                                        if (firstdivclass.contains("simple-product-page")){
                                            WebElement add2cart = driver.findElement(By.cssSelector("button[class='button js-add-to-cart']")); // get the add to cart element
                                            js.executeScript("arguments[0].click();", add2cart);  // click with js cmd on shop link
                                        } else if (firstdivclass.contains("sub-categories")){  // list of product are shown on the actual sub-page
                                            products = driver.findElement(By.cssSelector("ul.product-list")); // container of products
                                            list = products.findElements(By.tagName("li")); // products tag
                                            System.out.println("Total Products displayed on screen: " + list.size()); // console output founded products
                                            products = list.get(0); // get 1st elelement
                                            WebElement thename = products.findElement(By.cssSelector("h3[class='name']")); // element containing name of the product
                                            String pname = thename.getText(); // get the name of the product
                                            WebElement pronr = products.findElement(By.cssSelector("h4")); // SAP product number
                                            String pnbr = pronr.getText(); // get the product number of the product
                                            p2cart = products.findElement(By.cssSelector("a.js-add-to-cart")); // get add to cart element for js click
                                            js.executeScript("arguments[0].click();", p2cart); // add item to cart
                                            System.out.println("This spare part product has been added to cart: "+pname+" "+pnbr); // console output
                                        } else {
                                            System.out.println("UNKNOWN sub-page! Parts & Fitting sub-page is different."); // console output
                                        }
                                        List<WebElement> addOKdialog = driver.findElements(By.cssSelector("div.notification.notification-cart"));  // add to card confirmation text element
                                        if (addOKdialog.size()>0){  // confirmation text found
                                            WebElement addOKdialogElement = addOKdialog.get(0); // item element containing text
                                            WebElement addOKdialogP = addOKdialogElement.findElement(By.tagName("p")); // paragraph element
                                            String addOKdialogText = addOKdialogP.getText(); // pull the text to var to output on console
                                            System.out.println("Add to Cart Confirm Text : "+addOKdialogText); // console output
                                        }
                                        driver.navigate().back(); // return back one page to parts & fitting main page to process the next tile
                                        wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.parts-categories")))); // Wait for the title element to reappear

                                    } else { // no card nor product type, check it out what it is
                                        System.out.println("UNKNOWN! Type of tile in Parts & Fitting not known by coding."); // console output
                                    }
                                    pftiles = driver.findElements(By.cssSelector("div.parts-categories > ul > li")); // reset var
                                }

                            }else if (href.contains("batteries")) { // check to process category Cleaning and Maintenance
                                WebElement maincontainer = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("main.container")))); // Wait for the container element
                                List<WebElement> cleaningtiles = driver.findElements(By.cssSelector("div.cleaning-categories > ul > li")); // count sub-categories tiles within grid
                                System.out.println("Found "+cleaningtiles.size()+" Batteries & Maintenance sub-categories");
                                for (int p = 0; p < cleaningtiles.size(); p++) { // test all sub-categories
                                    WebElement bmcat = cleaningtiles.get(p); // actual item
                                    WebElement tilediv = bmcat.findElement(By.cssSelector("div")); // get the 1st element from the list
                                    String tileclass = tilediv.getAttribute("class"); // get class name
                                    if (tileclass.contains("card")){
                                        WebElement shopitem = tilediv.findElement(By.cssSelector("p > a")); // get the shop link
                                        js.executeScript("arguments[0].click();", shopitem);  // click with js cmd on shop link
                                        maincontainer = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("main.container")))); // Wait for the container element
                                        WebElement mc_div = maincontainer.findElement(By.cssSelector("div")); //get the first div
                                        String firstdivclass = mc_div.getAttribute("class"); // get the value of class=
                                        if (firstdivclass.contains("sub-categories")) {  // list of product are shown on the actual sub-page
                                            products = driver.findElement(By.cssSelector("ul.product-list")); // container of products
                                            list = products.findElements(By.tagName("li")); // products tag
                                            System.out.println("Total Products displayed on screen: " + list.size()); // console output founded products
                                            products = list.get(0); // get 1st elelement
                                            WebElement thename = products.findElement(By.cssSelector("h3[class='name']")); // element containing name of the product
                                            String pname = thename.getText(); // get the name of the product
                                            WebElement pronr = products.findElement(By.cssSelector("h4")); // SAP product number
                                            String pnbr = pronr.getText(); // get the product number of the product
                                            p2cart = products.findElement(By.cssSelector("a.js-add-to-cart")); // get add to cart element for js click
                                            js.executeScript("arguments[0].click();", p2cart); // add item to cart
                                            System.out.println("This spare part product has been added to cart: " + pname + " " + pnbr); // console output
                                            List<WebElement> addOKdialog = driver.findElements(By.cssSelector("div.notification.notification-cart"));  // add to card confirmation text element
                                            if (addOKdialog.size()>0){  // confirmation text found
                                                WebElement addOKdialogElement = addOKdialog.get(0); // item element containing text
                                                WebElement addOKdialogP = addOKdialogElement.findElement(By.tagName("p")); // paragraph element
                                                String addOKdialogText = addOKdialogP.getText(); // pull the text to var to output on console
                                                System.out.println("Add to Cart Confirm Text : "+addOKdialogText); // console output
                                            }
                                        }
                                    }
                                    driver.navigate().back(); // return back one page to parts & fitting main page to process the next tile
                                    wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.cleaning-categories")))); // Wait for the title element to reloaded
                                    cleaningtiles = driver.findElements(By.cssSelector("div.cleaning-categories > ul > li")); // count sub-categories tiles within grid
                                }

                            }else if (href.contains("in-office")) { // check to process category In-Office Materials
                                WebElement maincontainer = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("main.container")))); // Wait for the container element
                                List<WebElement> iotiles = maincontainer.findElements(By.cssSelector("div.image-array-tile")); // count sub-categories tiles within grid
                                // TEST
                                int io =iotiles.size(); // for array
                                String[][] iocatlinks = new String[io][2]; // define so much lines as the size and each line has two spaces to store the values
                                for (i = 0; i < iotiles.size(); i++) { // loop through each category
                                    item = iotiles.get(i);
                                    WebElement io_subcats = item.findElement(By.tagName("a")); // get all avail category links
                                    iocatlinks[i][0] = io_subcats.getAttribute("href");//get the url and store in first space
                                    WebElement io_subtitle = item.findElement(By.tagName("h3")); // get all avail category title
                                    iocatlinks[i][1] = io_subtitle.getText();//get the title and store in 2nd space

                                    System.out.println(i+1 +" "+iocatlinks[i][1]+" = "+iocatlinks[i][0]); // console out
                                }
                                //System.out.println("Found "+iotiles.size()+" In-Office Materials sub-categories" +" match array length = " +iocatlinks.length);
                                /// END TEST
                                for (i = 0; i < iocatlinks.length; i++) { // loop through each category and add its first product to cart
                                    driver.get(iocatlinks[i][0]);  // go to the stored url
                                    maincontainer = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("main.container")))); // Wait for the container element
                                    products = maincontainer.findElement(By.cssSelector("ul.product-list")); //get the first product
                                    //products = driver.findElement(By.cssSelector("ul.product-list")); // container of products
                                    list = products.findElements(By.tagName("li")); // products tag
                                    System.out.println("Total "+iocatlinks[i][1]+ " displayed on screen: " + list.size()); // console output founded products
                                    products = list.get(0); // get 1st elelement
                                    WebElement thename = products.findElement(By.cssSelector("h3[class='name']")); // element containing name of the product
                                    String pname = thename.getText(); // get the name of the product
                                    WebElement pronr = products.findElement(By.cssSelector("h4")); // SAP product number
                                    String pnbr = pronr.getText(); // get the product number of the product
                                    p2cart = products.findElement(By.cssSelector("a.js-add-to-cart")); // get add to cart element for js click
                                    js.executeScript("arguments[0].click();", p2cart); // add item to cart
                                    System.out.println("This product has been added to cart: " + pname + " " + pnbr); // console output
                                    List<WebElement> addOKdialog = driver.findElements(By.cssSelector("div.notification.notification-cart"));  // add to card confirmation text element
                                    if (addOKdialog.size()>0){  // confirmation text found
                                        WebElement addOKdialogElement = addOKdialog.get(0); // item element containing text
                                        WebElement addOKdialogP = addOKdialogElement.findElement(By.tagName("p")); // paragraph element
                                        String addOKdialogText = addOKdialogP.getText(); // pull the text to var to output on console
                                        System.out.println("Add to Cart Confirm Text : "+addOKdialogText); // console output
                                    }
                                }
                            }
                            // reset this var for the loop due reference is somehow lost!
                            //store = driver.findElement(By.cssSelector("li[class='homenav-li'] a[class='homenav-aa']")); // get the Store link
                            //js.executeScript("arguments[0].click();", store);
                            //store.click(); // main estore page

                            //tempUkProcess(driver); // special UK treatment

                            //wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.categories"))));  // categories menu bar is there
                            //anchor_cats = estorePage.shopCategories.findElements(By.tagName("a")); // reset list pointer
                        }  // end loop through each shop category
                    }
                }

            }
            // end shop checks

            // switch to b2b portal and logout there
            WebElement pagetitle = driver.findElement(By.cssSelector("div[class='pagetitle'] a")); // b2b home link
            String pt_anchor = pagetitle.getAttribute("href"); // get the link value to click on
            if (pt_anchor.contains("/home.html")){ // link points to /home.html
                driver.get(pt_anchor);  // change to that page
                profile = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='logout']"))));  // check logout menu
                anchor = profile.getAttribute("href");
                driver.get(anchor);  // Logout user from b2b
//            wait.until((ExpectedConditions.urlContains("/home")));
                wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href='/bin/phonakpro/login']"))));
                System.out.println("Logged out from b2b portal");
            }
            else{ // cannot switch to b2b portal due link is not valid
                driver.get(anchor);  // Logout user from shop menu
                System.out.println("Logged out from the shop");
            }
        }
        catch (Exception e) {
            System.out.println("TEST Failed due to an Exception!");
            System.out.println(e.getMessage());
            String OS = System.getProperty("os.name");
            if (OS.contains("Win")){  // windows machine?
                TakesScreenshot snapper = (TakesScreenshot)driver;

                File tempScreenshot = snapper.getScreenshotAs(OutputType.FILE);

                System.out.println(tempScreenshot.getAbsolutePath());

                File myScreenshotDirectory = new File("C:\\temp\\screenshots\\");
                myScreenshotDirectory.mkdirs();

                File myScreenshot = new File(myScreenshotDirectory, "gotoPageScreen.png");
                if(myScreenshot.exists()){
                    FileUtils.deleteQuietly(myScreenshot);
                }

                FileUtils.moveFile(tempScreenshot, myScreenshot);

                //assertThat(myScreenshot.length(), is(greaterThan(0L)));

                driver.get("file://" + myScreenshot.getAbsolutePath());
            }
            throw new Exception("!!! A L A R M !!!");  // test failed
        }
        finally {
            System.out.println("The test is over !!!");
            // close the Browser
            // driver.quit(); //do this in @AfterClass
        }
    }

    private static void sendOrder(WebDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, 60);
        WebElement sCart = driver.findElement(By.cssSelector("a[class='shopping-cart']")); // get the cart
        WebElement iCart = sCart.findElement(By.cssSelector("span[id='items-in-cart']")); // get the items in the cart
        String cItems = iCart.getText();
        if (!cItems.contains("0")){
            System.out.println("Have items in cart! SEND ORDER now...");
            sCart.click();
            WebElement checkout = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("p.button.button-large.checkout")))); // Wait for button appearance
            // check stock of client option selection
            List<WebElement> orderType = driver.findElements(By.cssSelector("input[name='order-type']"));  // order type radio options
            JavascriptExecutor js = (JavascriptExecutor) driver;  // using javascript
            for (int x =0; x < orderType.size(); x++) {
                WebElement stockOrClient = orderType.get(x);

                JavascriptExecutor executor = (JavascriptExecutor) driver;
                Object aa=executor.executeScript("var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;", stockOrClient);
                System.out.println(aa.toString());
                String checkedOption = aa.toString();

                //String checkedOption = stockOrClient.getAttribute("innerText");
                if (checkedOption.contains("checked")){
                    String optionValue = stockOrClient.getAttribute("value");
                    System.out.println("Option selected : "+optionValue);
                    if (optionValue.contains("Stock")){
                        optionValue = "Stockski";
                    }else if (optionValue.contains("Client")){
                        WebElement clientOptions = driver.findElement(By.cssSelector("div[id='client-options']")); // option container
                        List <WebElement> clops = clientOptions.findElements(By.cssSelector("div.label-container"));  // count client options
                        for (int c = 0; c < clops.size(); c++){
                            WebElement clopi = clops.get(c);
                            String oinnerhtml = clopi.getAttribute("innerHTML");
                            if (oinnerhtml.contains("clientfirstname")){
                                WebElement input_tag = clopi.findElement(By.tagName("input"));
                                js.executeScript("arguments[0].setAttribute('value', 'Sun')", input_tag); // enter first name in the fld
                            } else if (oinnerhtml.contains("clientlastname")){
                                WebElement input_tag = clopi.findElement(By.tagName("input"));
                                js.executeScript("arguments[0].setAttribute('value', 'Fun')", input_tag); // enter last name in the fld
                            } else if (oinnerhtml.contains("age")&& !oinnerhtml.contains("hidden")){ // age drop down element not hidden
                                WebElement ageCombobox = clientOptions.findElement(By.id("dk1-combobox")); // drop down element of age values
                                js.executeScript("arguments[0].click()", ageCombobox); // click to expand the drop down menu
                                WebElement ageoption = clientOptions.findElement(By.id("dk1-YOUNGADULT")); // young adult age element
                                String ageValue = ageoption.getText(); // get the value
                                js.executeScript("arguments[0].click()", ageoption); // select young adult
                            } else if (oinnerhtml.contains("gender")&& !oinnerhtml.contains("hidden")) { // gender drop down element not hidden
                                WebElement genderCombobox = clientOptions.findElement(By.id("dk2-combobox")); // drop down element of gender values
                                js.executeScript("arguments[0].click()", genderCombobox); // click to expand the drop down menu
                                WebElement genderoption = clientOptions.findElement(By.id("dk2-MALE")); // male element
                                String genderValue = genderoption.getText(); // get the value
                                js.executeScript("arguments[0].click()", genderoption); // select option male
                            }
                        }
                        System.out.println("Client data entered : Sun, Fun, ...etc... ");
                    }else {
                        System.out.println("Order Type Option UNKNOWN!");
                    }
                }
            }
            js.executeScript("arguments[0].click()", checkout); // click on proceed to checkout
            WebElement submitorder = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("p[id='submitButton']")))); // Wait for button appearance
            //Final page b4 send order

            String enviroment = driver.getCurrentUrl(); // to check on which system we are on
            if (enviroment.contains("q-shop")) { // not on production system !
                js.executeScript("arguments[0].click()", submitorder); // send the order to sap
                wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("section.checkout.checkout-thank-you")))); // Wait for order sent confirmation
                System.out.println("Order SENT!");
            }else {
                System.out.println("You are on this platform "+enviroment+" NO Order has been sent!");
            }
        }
        else{
            System.out.println("No item in the Cart, no order send possible");
        }
    }

    private void chooseShellStyle(WebDriver driver) {
        WebElement allSteps = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[id='custom-product']")))); // Wait for element
        List<WebElement> listPl = allSteps.findElements(By.cssSelector("div[id='earpiece-shell-styles'] div[class='column-1'] div[class*='ng-isolate-scope']")); // Get list of options
        // System.out.println("Power level(s) found : "+listPl.size());
        if (listPl.size()!=0){
            // Leave default style selection
        }
        System.out.println("Style Options found : "+(listPl.size()-1)); // total size deducted by 1 (title item)
    }

    private void tempUkProcess(WebDriver driver) {
        if(c_param.contentEquals("uk")){
            String shop_anchor_copy = shop_anchor; // copy anchor for uk testing
            if (shop_anchor_copy.endsWith("/GBP/")) {  // check the ending of the shop anchor
                shop_anchor_copy = shop_anchor_copy+"store"; // set up the none public shop link
                System.out.println("UK temp store link: "+shop_anchor_copy);
                shop_anchor=shop_anchor_copy; // shop link
                driver.get(shop_anchor);  // go to shop
            } else if (shop_anchor_copy.endsWith("/GBP/store")){  // not the first time enter here
                driver.get(shop_anchor);  // go to shop
            }
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


    public boolean isElementPresent(WebDriver driver, String className) {
        return driver.findElements(By.className(className)).size() != 0;
    }

    public boolean isElementPresent(WebDriver driver, By by) {

        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static void eCartClear(WebDriver driver){
        //sticky_element.click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement sCart = driver.findElement(By.cssSelector("a[class='shopping-cart']")); // get the cart
        WebElement iCart = sCart.findElement(By.cssSelector("span[id='items-in-cart']")); // get the items in the cart
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
            //wait.until((ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("p[class='button button-large checkout']")))); // Wait for element to disappear
            // check cart is empty
            wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("section.cart-header")))); // Wait for cart to reappear

            wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("p.button.text-only")))); // Wait for link text appearance
            System.out.println("Cart cleared!");
        }
        else{
            System.out.println("There is no item in the Cart");
        }

    }

    public void chooseReceiver(WebDriver driver){
        WebElement allSteps = wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[id='custom-product']")))); // Wait for element
        List<WebElement> listPl = allSteps.findElements(By.cssSelector("div[id='power-levels'] div[class='column-1'] li")); // Get step 2 container
        //System.out.println("Power level(s) found : "+listPl.size());
        if (listPl.size()!=0){
            for (int iPl=0; iPl < listPl.size(); iPl++) {
                String textOfPl = listPl.get(iPl).getText();
                System.out.println("Selectable PowerLevel item label : " + textOfPl);
            }
            WebElement selectPl = listPl.get(0); // get the first item
            selectPl.click(); // click on it to marked as selected power level
            System.out.println("Final Receiver selected: "+selectPl.getText());
        }
        System.out.println("Power level(s) found : "+listPl.size());
    }

    public void chooseLength(WebDriver driver){
        wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[id='custom-product']")))); // Wait for element
        WebElement allSteps = driver.findElement(By.cssSelector("div[id='custom-product']")); // Wait for element
        nStep = Integer.toString(n); // int of nxt step to string
        wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[id='custom-product'] div[step='"+nStep+"']")))); // Wait for element is available
        WebElement step3 = allSteps.findElement(By.cssSelector("div[step='"+nStep+"']")); // Get nxt step container
        String s3label= step3.getAttribute("label");
        System.out.println("Title of step "+nStep+": "+s3label);

        WebElement chooseLength = driver.findElement(By.cssSelector("div.lengths.ng-isolate-scope"));
        List<WebElement> lengthList = chooseLength.findElements(By.cssSelector("li"));
        WebElement lengthSelect=null, lengthJs=null, lengthSelect1 = null;
        for (int li=0; li<lengthList.size();li++) {
            lengthSelect = lengthList.get(li); // select the item
            System.out.println("This Length item "+li+" is visible: " +lengthSelect.getText());
            //if (li==0) {lengthSelect1 = lengthSelect;} // item to click on, see below
        }
        lengthSelect = lengthList.get(0); // get the first item
        lengthSelect1 = lengthSelect.findElement(By.cssSelector("div.details.ng-binding"));  // element to click on

        //lengthSelect1.click();

        JavascriptExecutor js = (JavascriptExecutor) driver; // use javascript on client/browser
        js.executeScript("arguments[0].click();", lengthSelect1); // click on item with javascript code

        wait.until((ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[id='custom-product']")))); // Wait for element
        System.out.println("Final Length selected :"+lengthSelect.getText());
    }

}
