package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

    protected RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver) {
        this.driver = driver;
    }


    public WebElement waitForElementPresent(String locator, String error_message, long timeoutInSeconds){
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForElementPresent(String locator, String error_message){
        return waitForElementPresent(locator, error_message, 5);
    }

    public WebElement waitForElementAndClick(String locator, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(String locator, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(String locator, String error_message, long timeoutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }
    public WebElement waitForElementAndClear(String locator, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    public void assertElementHasText(String locator, String expected_text, String error_message){
        String expected_element_text = expected_text;
        WebElement element = waitForElementPresent(locator, error_message);
        String actual_element_text = element.getAttribute("text");
        Assert.assertEquals(
                error_message,
                expected_element_text,
                actual_element_text
        );
    }

    public void swipeUp(int timeOfSwipe) {
        if(driver instanceof AppiumDriver) {
            TouchAction action = new TouchAction((AppiumDriver) driver);
            Dimension size = driver.manage().window().getSize();
            int x = size.width/2;
            int start_y = (int) (size.height * 0.8);
            int end_y = (int) (size.height * 0.2);

            action
                    .press(PointOption.point(x, start_y))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)))
                    .moveTo(PointOption.point(x, end_y))
                    .release()
                    .perform();
        } else {
            System.out.println("Method swipeUp() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void swipeUpQuick() {
        swipeUp(200);
    }

    public void scrollWebPageUp() {
        if(Platform.getInstance().isMW()) {
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
            javascriptExecutor.executeScript("window.scrollBy(0, 250)");
        } else {
            System.out.println("Method scrollWebPageUp() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void scrollWebPageTillElementNotVisible(String locator, String error_message, int max_swipes) {
        int already_swiped = 0;

        WebElement element = this.waitForElementPresent(locator, error_message);

        while(!this.isElementLocatedOnTheScreen(locator)) {
            scrollWebPageUp();
            ++already_swiped;
            if(already_swiped > max_swipes) {
                Assert.assertTrue(error_message, element.isDisplayed());
            }
        }
    }

    public void swipeUpToFindElement(String locator, String error_message, int max_swipes) {
        By by = getLocatorByString(locator);
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {
            if(already_swiped > max_swipes) {
                waitForElementPresent(locator, "Cannot find element by swiping up. \n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    public void swipeUpTillElementAppear(String locator, String error_message, int max_swipes) {
        int already_swiped = 0;
        while(!this.isElementLocatedOnTheScreen(locator)) {
            if(already_swiped > max_swipes) {
                Assert.assertTrue(error_message, this.isElementLocatedOnTheScreen(locator));
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    public boolean isElementLocatedOnTheScreen(String locator) {
        int element_location_by_y = this.waitForElementPresent(
                locator,
                "Cannot find element by locator",
                1
        ).getLocation().getY();
        if(Platform.getInstance().isMW()) {
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
            Object js_result = javascriptExecutor.executeScript("return window.pageYOffset");
            element_location_by_y -= Integer.parseInt(js_result.toString());
        }
        int screen_size_by_y = driver.manage().window().getSize().getHeight();
        return element_location_by_y < screen_size_by_y;
    }

    public void swipeElementToTheLeft(String locator, String error_message) {
        if(driver instanceof AppiumDriver) {
            WebElement element = waitForElementPresent(locator, error_message, 10);

            int left_x = element.getLocation().getX();
            int right_x = left_x + element.getSize().getWidth();
            int upper_y = element.getLocation().getY();
            int lower_y = upper_y + element.getSize().getHeight();
            int middle_y = (upper_y + lower_y) / 2;

            TouchAction action = new TouchAction((AppiumDriver) driver);

            action
                    .press(PointOption.point(right_x, middle_y))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(400)))
                    .moveTo(PointOption.point(left_x, middle_y))
                    .release()
                    .perform();
        } else {
            System.out.println("Method swipeElementToTheLeft() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public int getAmountOfElements(String locator) {
        By by = getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent(String locator, String error_message) {
        int amount_of_elements = getAmountOfElements(locator);
        if(amount_of_elements > 0) {
            String default_message = "An element '" + locator + "' supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    public String waitForElementAndGetAttribute(String locator, String attribute, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    public void assertElementPresent(String locator, String search_line) {
        String error_message = "Cannot find article title with text '" + search_line +"'";
        WebElement element = waitForElementPresent(
                locator,
                error_message,
                0
        );

        String text_element;
        if(Platform.getInstance().isAndroid()) {
            text_element= element.getAttribute("text").toString();
        } else {
            text_element= element.getAttribute("name").toString();
        }

        String default_message = "An element '" + locator + "' supposed to be not present.";

        if(!search_line.equalsIgnoreCase(text_element)){
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    private By getLocatorByString(String locator_with_type) {
        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"), 2);
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];

        if(by_type.equals("xpath")) {
            return By.xpath(locator);
        } else if(by_type.equals("id")) {
            return By.id(locator);
        } else if(by_type.equals("css")) {
            return By.cssSelector(locator);
        } else {
            throw new IllegalArgumentException("Cannot get type of locator, Locator: " + locator_with_type);
        }
    }

    public void clickElementToTheRightUpperCorner(String locator, String error_message) {
        if(driver instanceof AppiumDriver) {
            WebElement element = this.waitForElementPresent(locator + "/..", error_message);
            int right_x = element.getLocation().getX();
            int upper_y = element.getLocation().getY();
            int lower_y = upper_y + element.getSize().getHeight();
            int middle_y = (upper_y + lower_y) / 2;
            int width = element.getSize().getWidth();
            int point_to_click_x = (right_x + width) - 3;
            int point_to_click_y = middle_y;

            TouchAction action = new TouchAction((AppiumDriver) driver);
            action.tap(PointOption.point(point_to_click_x, point_to_click_y)).perform();
        } else {
            System.out.println("Method clickElementToTheRightUpperCorner() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public boolean isElementPresent(String locator) {
        return getAmountOfElements(locator) > 0;
    }

    public void tryClickElementWithFewAttempts(String locator, String error_message, int amount_of_attempts) {
        int current_attempts = 0;
        boolean need_more_attempts = true;

        while(need_more_attempts) {
            try {
                this.waitForElementAndClick(locator, error_message, 5);
                need_more_attempts = false;
            } catch (Exception e) {
                if (current_attempts > amount_of_attempts) {
                    this.waitForElementAndClick(locator, error_message, 5);
                }
            }
            ++current_attempts;
        }
    }
}
