import lib.CoreTestCase;
import lib.ui.MainPageObject;
import org.junit.Test;
import org.openqa.selenium.WebElement;

public class FirstTest extends CoreTestCase {

    private MainPageObject mainPageObject;

    protected void setUp() throws Exception {
        super.setUp();

        mainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testCompareElementText() {
        mainPageObject.assertElementHasText(
                "xpath://*[contains(@text, 'Search Wikipedia')]",
                "Search Wikipedia",
                "We see unexpected element text"
        );
    }

    @Test
    public void testSearchTextContainsInEachTitleSearchResult() {
        String search_string = "JAVA";
        String[] search_result = new String[5];
        String error_message = "Element is not present";

        mainPageObject.waitForElementAndClick(
                "xpath://*[contains(@text, 'Search Wikipedia')]",
                "Cannot find 'Search Wikipedia' input",
                5
        );

        mainPageObject.waitForElementAndSendKeys(
                "xpath://*[contains(@text, 'Search…')]",
                search_string,
                "Cannot find search input",
                10
        );

        WebElement first = mainPageObject.waitForElementPresent(
                "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Java']",
                error_message

        );
        search_result[0] = first.getAttribute("text");

        WebElement second = mainPageObject.waitForElementPresent(
                "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='JavaScript']",
                error_message
        );
        search_result[1] = second.getAttribute("text");


        WebElement third = mainPageObject.waitForElementPresent(
                "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Java (programming language)']",
                error_message
        );
        search_result[2] = third.getAttribute("text");

        WebElement fourth = mainPageObject.waitForElementPresent(
                "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Java (software platform)']",
                error_message
        );
        search_result[3] = fourth.getAttribute("text");

        WebElement fifth = mainPageObject.waitForElementPresent(
                "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Javanese language']",
                error_message
        );
        search_result[4] = fifth.getAttribute("text");

        for (int i = 0; i < search_result.length; i++) {
            assertTrue(
                    "Element " + (i + 1) + " with text '" + search_result[i] + "' is not contains searching text '"
                            + search_string + "'",
                    search_result[i].toLowerCase().contains(search_string.toLowerCase())
            );
        }

    }

}
