package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;

public class MyListsTests extends CoreTestCase {

    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubString("Object-oriented programming language");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();
        String article_title = articlePageObject.getArticleTitle();
        String name_of_folder = "Learning programming";
        articlePageObject.addArticleToMyList(name_of_folder);
        articlePageObject.closeArticle();

        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
        myListsPageObject.openFolderByName(name_of_folder);
        myListsPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    public void testSaveTwoArticlesInMyList() {
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Yamaha YZ");
        searchPageObject.clickByArticleWithSubString("Sport bike");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();
        String article_title = articlePageObject.getArticleTitle();
        String name_of_folder = "Yamaha bikes";
        articlePageObject.addArticleToMyList(name_of_folder);
        articlePageObject.closeArticle();
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Yamaha YZ");
        searchPageObject.clickByArticleWithSubString("Yzf600r");
        articlePageObject.waitForTitleElement();
        articlePageObject.addArticleToSavedList(name_of_folder);
        articlePageObject.closeArticle();

        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.findMyLists();
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
        myListsPageObject.findFolderByName(name_of_folder);
        myListsPageObject.openFolderByName(name_of_folder);
        myListsPageObject.checkThatFolderIsOpened(name_of_folder);

        assertEquals("Cannot find articles in the '" + name_of_folder + "' list",
                2,
                driver.findElements(By.id("org.wikipedia:id/page_list_item_container")).size()
//                driver.findElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']")).size()
        );

        myListsPageObject.swipeByArticleToDelete(article_title);
        assertEquals("Cannot find article in the '" + name_of_folder + "' list",
                1,
                driver.findElements(By.id("org.wikipedia:id/page_list_item_container")).size()
//                driver.findElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']")).size()
        );

        article_title = "Yamaha YZF-R6";
        myListsPageObject.openArticle(article_title);
        articlePageObject.getArticleTitle();
        articlePageObject.findCloseArticleButton();
        articlePageObject.closeArticle();
        myListsPageObject.deleteMyList();
    }

}
