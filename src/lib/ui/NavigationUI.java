package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject {

    protected static String
        MY_LISTS_LINK;

    public NavigationUI(RemoteWebDriver driver) {
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
