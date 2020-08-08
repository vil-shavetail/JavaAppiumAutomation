package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject {

    public static final String
        FOLDER_BY_NAME_TPL = "//*[@text='{FOLDER_NAME}']",
        ARTICLE_BY_TITLE_TPL = "//*[@text='{TITLE}']",
        DELETE_LIST_BUTTON = "//*[@text='Delete list']",
        MORE_OPTIONS_BUTTON = "org.wikipedia:id/item_overflow_menu";

    private static String getFolderXpathByName(String name_of_folder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByTitle(String article_title) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }

    public MyListsPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void findFolderByName(String name_of_folder) {
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementPresent(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find created folder '" + name_of_folder + "'",
                10
        );
    }

    public void openFolderByName(String name_of_folder) {
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementAndClick(
                By.xpath(folder_name_xpath),
                "Cannot find folder by name " + name_of_folder,
                5
        );
    }

    public void waitForArticleToAppearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementPresent(
                By.xpath(article_xpath),
                "Cannot find saved article by title" + article_title,
                15
        );
    }

    public void waitForArticleToDisappearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementNotPresent(
                By.xpath(article_xpath),
                "Saved article still present with title" + article_title,
                15
        );
    }

    public void swipeByArticleToDelete(String article_title) {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.swipeElementToLeft(
                By.xpath(article_xpath),
                "Cannot find saved article"
        );
        this.waitForArticleToDisappearByTitle(article_title);
    }

    public void deleteMyList() {
        this.waitForElementPresent(
                By.id(MORE_OPTIONS_BUTTON),
                "Cannot find 'More options' button",
                5
        );

        this.waitForElementAndClick(
                By.id("org.wikipedia:id/item_overflow_menu"),
                "Cannot find 'More options' button",
                5
        );

        this.waitForElementPresent(
                By.xpath(DELETE_LIST_BUTTON),
                "Cannot find option to delete list",
                5
        );

        this.waitForElementAndClick(
                By.xpath(DELETE_LIST_BUTTON),
                "Cannot find option to delete list",
                5
        );

        this.waitForElementPresent(
                By.xpath("//*[@text='No reading lists yet']"),
                "Cannot find message 'No reading lists yet'",
                10
        );
    }

    public void openArticle(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementAndClick(
                By.xpath(article_xpath),
                "Cannot find saved article with title" + article_title,
                5
        );
    }

}
