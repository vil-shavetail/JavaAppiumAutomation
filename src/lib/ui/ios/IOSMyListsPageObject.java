package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class IOSMyListsPageObject extends MyListsPageObject {
    static {
        FOLDER_BY_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}']";
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeStaticText[@name='{TITLE}']";
        DELETE_LIST_BUTTON = "xpath://*[@text='Delete list']";
        MORE_OPTIONS_BUTTON = "id:org.wikipedia:id/item_overflow_menu";
    }
    public IOSMyListsPageObject(AppiumDriver driver){
        super(driver);
    }
}
