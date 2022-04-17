package room.wdcmds;

import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import room.config.StartBrowser;
import room.utils.ConfiguratorSupport;

import java.io.IOException;
import java.util.Set;

public class ActionDriver {

    public WebDriver driver;
    ConfiguratorSupport cs = new ConfiguratorSupport("config.properties");

    public ActionDriver() {
        driver = StartBrowser.driver;

    }

    // This method is used to navigate to the application URL
    public void navigateToApplication(){
        String url = cs.getProperty("url");
        try {
            driver.get(url);
            StartBrowser.childTest.pass("Successfully opened URL: " +url);
        } catch (Exception e) {
            StartBrowser.childTest.fail("Unable to navigate to URL: "+url);
        }

    }

    // This method is used to enter the keyboard enter
    public void enterkey(By locator, String eleName) throws Exception{
        try {
            driver.findElement(locator).sendKeys(Keys.ENTER);
            StartBrowser.childTest.pass("Performed click operation on: "+ eleName);
        } catch(Exception e){
            StartBrowser.childTest.fail("Unable to perform enter operation on: "+ eleName);
            MediaEntityBuilder.createScreenCaptureFromBase64String(screenShot()).build();
            StartBrowser.childTest.info(e);
            throw e;
        }
    }

    // This method is used to enter the keyboard enter
    public void downkey(By locator, String eleName) throws Exception{
        try {
            driver.findElement(locator).sendKeys(Keys.ARROW_DOWN);
            StartBrowser.childTest.pass("Performed arrow down operation on: "+ eleName);
        } catch(Exception e){
            StartBrowser.childTest.fail("Unable to press the arrow down key: "+ eleName);
            MediaEntityBuilder.createScreenCaptureFromBase64String(screenShot()).build();
            StartBrowser.childTest.info(e);
            throw e;
        }
    }

    // This method performs click operation
    public void click(By locator, String eleName) throws Exception {
        try {
            driver.findElement(locator).click();
            StartBrowser.childTest.pass("Performed click operation on: "+ eleName);
        } catch (Exception e) {
            StartBrowser.childTest.fail("Unable to perform click operation on: "+ eleName);
            MediaEntityBuilder.createScreenCaptureFromBase64String(screenShot()).build();
            StartBrowser.childTest.info(e);
            throw e;
        }
    }


    // This method is used to get string data
    public void type(By locator, String testdata, String eleName) throws Exception {
        try {
            driver.findElement(locator).sendKeys(testdata);
            StartBrowser.childTest.pass("Successfully performed action on: "+ eleName + " with data :"+testdata);
        } catch (Exception e) {
            StartBrowser.childTest.fail("Unable to performed type action on: "+ eleName + " with data "+testdata);
            MediaEntityBuilder.createScreenCaptureFromBase64String(screenShot()).build();
            StartBrowser.childTest.info(e);
            throw e;
        }
    }

    // This is used to get a certain text
    public String getText(By locator) {
        try {
            String x = driver.findElement(locator).getText();
            StartBrowser.childTest.pass("Text is :" +x);
            return x;
        } catch (Exception e) {
            StartBrowser.childTest.fail("unable to return text");
            return null;
        }
    }

    // This is a mouse hover function
    public void mouseHover(By locator, String eleName) throws Exception {
        try {
            Actions a = new Actions(driver);
            WebElement mo = driver.findElement(locator);
            a.moveToElement(mo).build().perform();
            StartBrowser.childTest.pass("Successfully mouse hover action on: "+ eleName);
        } catch (Exception e) {
            StartBrowser.childTest.fail("Unable to performe mouse hover on: "+ eleName,
                    MediaEntityBuilder.createScreenCaptureFromBase64String(screenShot()).build());
            StartBrowser.childTest.info(e);
            throw e;
        }
    }

    // This function is used to verify a certain text
    public void verifyText(By locator, String expText) throws Exception{
        String actualText = getText(locator);
        if(actualText.equals(expText)) {
            StartBrowser.childTest.pass("Actual text is same as expected text");
        }
        else {
            StartBrowser.childTest.fail("Actual Text :" + actualText + "is not equal to expected text: " +expText,
                    MediaEntityBuilder.createScreenCaptureFromBase64String(screenShot()).build());
            throw new Exception();
        }
    }
    public void waitForelementVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    // Using an index value to select a drop down list
    public void selectByIndex(By locator, int index) throws Exception {
        try {
            WebElement dd = driver.findElement(locator);
            Select s = new Select(dd);
            s.selectByIndex(index);
            StartBrowser.childTest.pass("Selected: "+ index + " value from dropdown");
        } catch (Exception e) {
            StartBrowser.childTest.fail("Unable to select: "+ index + " value from dropdown",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(screenShot()).build());
            StartBrowser.childTest.info(e);
            throw e;
        }
    }

    // selecting a visible text in a drop down list
    public void selectByVisibleText(By locator, String visibleText) throws Exception {
        try {
            WebElement dd = driver.findElement(locator);
            Select s = new Select(dd);
            s.selectByVisibleText(visibleText);
            StartBrowser.childTest.pass("Selected: "+ visibleText + " value from dropdown");
        } catch (Exception e) {
            StartBrowser.childTest.fail("Unable to select: "+ visibleText + " value from dropdown",
                    MediaEntityBuilder.createScreenCaptureFromBase64String(screenShot()).build());
            StartBrowser.childTest.info(e);
            throw e;
        }
    }

    // method for getting the parent window
    public String getParentWindow() {
        return driver.getWindowHandle();
    }

    // method for switching to child window browser
    public void switchtoChildWindow(By locator, String expText) {
        Set<String> windows = driver.getWindowHandles();
        try {
            for (String string : windows) {
                driver.switchTo().window(string);{
                    if (getText(locator).equals(expText)) {
                        System.out.println("You are in the correct window");
                        break;
                    }
                    else {
                        System.out.println("This is the wrong window");
                    }
                }

            }
        }catch (Exception e) {

        }
    }

    // Method for mouse hover and click
    public void mouseHoverAndClickSubMenu(By menulocator, By subMenuLocator, String menu, String submenu) throws Exception {
        try {
            Actions act = new Actions(driver);
            WebElement ele = driver.findElement(menulocator);
            act.moveToElement(ele).build().perform();
            Thread.sleep(3000);
            driver.findElement(subMenuLocator).click();
            StartBrowser.childTest.pass("Successfully mouse hover action on menu: "+ menu +" and clicked on submenu: "+submenu);
        } catch (Exception e) {
            StartBrowser.childTest.fail("Unable to performe mouse hover on: "+ menu + " and unable to click submenu: "+ submenu,
                    MediaEntityBuilder.createScreenCaptureFromBase64String(screenShot()).build());
            StartBrowser.childTest.info(e);
            throw e;
        }
    }

    // Checking if an element is present
    public boolean isElePresent(By locator, String elementName) throws IOException {
        try {
            driver.findElement(locator);
            StartBrowser.childTest.pass("Element: "+ elementName +" is present");
            return true;
        } catch (Exception e) {
            StartBrowser.childTest.fail("Unable to performe mouse hover on: "+ elementName + " is present" +
                    MediaEntityBuilder.createScreenCaptureFromBase64String(screenShot()).build());
            StartBrowser.childTest.info(e);
            return false;
        }
    }
    // This function captures the screenshot
    public String screenShot() {
        return((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }
}
