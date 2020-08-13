package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class WelcomePageObject extends MainPageObject {
    private static final String
    STEP_LEARN_MORE_LINK ="//*[@name='Learn more about Wikipedia']",
    STEP_NEW_WAYS_TO_EXPLORE_TEXT = "New ways to explore",
    STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK = "//*[@name='Add or edit preferred languages']",
    STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK = "//*[@name='Learn more about data collected']",
    NEXT_LINK = "//*[@name='Next']",
    GET_STARTED_BUTTON = "//*[@name='Get started']";

    public WelcomePageObject(AppiumDriver driver) {
        super(driver);
    }

    public void waitForLearnMoreLink() {
        this.waitForElementPresent(
                By.xpath(STEP_LEARN_MORE_LINK),
                "Cannot find 'Learn more about Wikipedia' link",
                10
                );
    }

    public void waitForNewWayToExploreText() {
        this.waitForElementPresent(
                By.id(STEP_NEW_WAYS_TO_EXPLORE_TEXT),
                "Cannot find 'New ways to explore' link",
                10
        );
    }

    public void waitForAddOrEditPreferredLangText() {
        this.waitForElementPresent(
                By.xpath(STEP_ADD_OR_EDIT_PREFERRED_LANG_LINK),
                "Cannot find 'Add or edit preferred languages' link",
                10
        );
    }

    public void waitForLearnMoreAboutDataCollectedText() {
        this.waitForElementPresent(
                By.xpath(STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK),
                "Cannot find 'Learn more about data collected' link",
                10
        );
    }

    public void clickNextButton() {
        this.waitForElementAndClick(
                By.xpath(NEXT_LINK),
                "Cannot find and click 'Next' link",
                10
        );
    }

    public void clickGetStartedButton() {
        this.waitForElementAndClick(
                By.xpath(GET_STARTED_BUTTON),
                "Cannot find and click 'Get started' link",
                10
        );
    }
}
