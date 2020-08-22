package lib.ui;

import io.appium.java_client.AppiumDriver;

abstract public class NavigationUI extends MainPageObject {

    protected static String
        MY_LISTS_LINK;

    public NavigationUI(AppiumDriver driver) {
        super(driver);
    }

    public void findMyLists() {
        this.waitForElementPresent(
                MY_LISTS_LINK,
                "Cannot find navigate button to My lists",
                10
        );
    }

    public void clickMyLists() {
        this.waitForElementAndClick(
                MY_LISTS_LINK,
                "Cannot find navigate button to My lists",
                5
        );
    }

}
