package lib.ui.ios;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class IOSSearchPageObject extends SearchPageObject {
    static {
        SEARCH_INIT_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']";
        SEARCH_INPUT = "xpath://XCUIElementTypeSearchField";
        SEARCH_CANCEL_BUTTON = "id:Close";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeStaticText[@name='{SUBSTRING}']";
        SEARCH_RESULT_BY_TWO_SUBSTRING_TPL = "xpath://XCUIElementTypeStaticText[@name='{TITLE}']/..//XCUIElementTypeStaticText[@name='{DESCRIPTION}']/..";
        SEARCH_RESULT_ELEMENT = "xpath://XCUIElementTypeStaticText";
        SEARCH_EMPTY_RESULT_ELEMENT = "id:No results found";
    }

    public IOSSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

}
