package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.By;

public class MyListsTests extends CoreTestCase {

    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubString("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        String article_title = articlePageObject.getArticleTitle();
        String name_of_folder = "Learning programming";
        if(Platform.getInstance().isAndroid()){
            articlePageObject.addArticleToMyList(name_of_folder);
        } else {
            articlePageObject.addArticlesToMySaved();
            articlePageObject.closeSyncYourSavedArticlesOverlay();
        }
        articlePageObject.closeArticle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if(Platform.getInstance().isAndroid()){
            myListsPageObject.openFolderByName(name_of_folder);
        }
        myListsPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    public void testSaveTwoArticlesInMyList() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Yamaha YZ");
        searchPageObject.clickByArticleWithSubString("Sport bike");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
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

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.findMyLists();
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
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
