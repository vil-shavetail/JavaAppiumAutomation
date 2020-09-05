package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.By;

public class MyListsTests extends CoreTestCase {
    private static final String
            login = "HaloAmtest",
            password = "V34s0T51zxvbT";

    @Test
    public void testSaveFirstArticleToMyList() throws InterruptedException {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        if(Platform.getInstance().isMW()) {
            searchPageObject.clickByArticleWithSubString("Java (programming language)");
        } else {
            searchPageObject.clickByArticleWithSubString("Object-oriented programming language");
        }

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        String article_title = articlePageObject.getArticleTitle();
        String name_of_folder = "Learning programming";
        if(Platform.getInstance().isAndroid()){
            articlePageObject.addArticleToMyList(name_of_folder);
        } else {
            articlePageObject.addArticlesToMySaved();
            if(Platform.getInstance().isIOS()) {
                articlePageObject.closeSyncYourSavedArticlesOverlay();
            }
        }
        if(Platform.getInstance().isMW()) {
            AuthorizationPageObject authorizationPageObject = new AuthorizationPageObject(driver);
            authorizationPageObject.clickAuthButton();
            Thread.sleep(10000);
            authorizationPageObject.enterLoginData(login, password);
            authorizationPageObject.submitForm();

            articlePageObject.waitForTitleElement();
            assertEquals(
                    "We are not on the same page after login",
                    article_title,
                    articlePageObject.getArticleTitle()
            );

            articlePageObject.addArticlesToMySaved();
        }
        articlePageObject.closeArticle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if(Platform.getInstance().isAndroid()){
            myListsPageObject.openFolderByName(name_of_folder);
        }
        myListsPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    public void testSaveTwoArticlesInMyList() throws InterruptedException {
        String name_of_folder = "Yamaha bikes";
        String first_article_expected_title = "Yamaha YZF-R1";
        String second_article_expected_title = "Yamaha YZF-R6";
        String first_article_expected_description = "Sport bike";
        String second_article_expected_description = "Yzf600r";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Yamaha YZ");
        if(Platform.getInstance().isMW()) {
            searchPageObject.clickByArticleWithSubString(first_article_expected_title);
        } else {
            searchPageObject.clickByArticleWithSubString(first_article_expected_description);
        }

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        if(Platform.getInstance().isAndroid() || Platform.getInstance().isMW()) {
            articlePageObject.waitForTitleElement();
        } else {
            articlePageObject.waitForTitleElement(first_article_expected_title);
        }
        String article_title = articlePageObject.getArticleTitle();
        if(Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(name_of_folder);
        } else {
            articlePageObject.addArticlesToMySaved();
            if(Platform.getInstance().isIOS()) {
                articlePageObject.closeSyncYourSavedArticlesOverlay();
            }
        }
        if(Platform.getInstance().isMW()) {
            AuthorizationPageObject authorizationPageObject = new AuthorizationPageObject(driver);
            authorizationPageObject.clickAuthButton();
            Thread.sleep(100);
            authorizationPageObject.enterLoginData(login, password);
            authorizationPageObject.submitForm();

            articlePageObject.waitForTitleElement();
            assertEquals(
                    "We are not on the same page after login",
                    article_title,
                    articlePageObject.getArticleTitle()
            );

            articlePageObject.addArticlesToMySaved();
        }
        articlePageObject.closeArticle();

        Thread.sleep(100);
        searchPageObject.initSearchInput();
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isMW()) {
            searchPageObject.typeSearchLine("Yamaha YZ");
        }
        if(Platform.getInstance().isMW()) {
            searchPageObject.clickByArticleWithSubString(second_article_expected_title);
        } else {
            searchPageObject.clickByArticleWithSubString(second_article_expected_description);
        }
        if(Platform.getInstance().isAndroid()) {
            articlePageObject.waitForTitleElement();
            articlePageObject.addArticleToSavedList(name_of_folder);
        } else if(Platform.getInstance().isIOS()) {
            articlePageObject.waitForTitleElement(second_article_expected_title);
            articlePageObject.addArticlesToMySaved();
        } else {
            articlePageObject.waitForTitleElement();
            articlePageObject.addArticlesToMySaved();
        }
        articlePageObject.closeArticle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.openNavigation();
        if(!Platform.getInstance().isMW()) {
            navigationUI.findMyLists();
        }
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if(Platform.getInstance().isAndroid()) {
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
        } else if(Platform.getInstance().isIOS()) {
            article_title = second_article_expected_title;
            myListsPageObject.swipeByArticleToDelete(article_title);
            article_title = first_article_expected_title;
            searchPageObject.clickByArticleWithSubString(first_article_expected_description);
            articlePageObject.waitForTitleElement(article_title);
            articlePageObject.closeArticle();
            myListsPageObject.swipeByArticleToDelete(article_title);
            myListsPageObject.getNoSavedPagesYetScreenForIOS();
        } else {
            article_title = second_article_expected_title;
            myListsPageObject.swipeByArticleToDelete(article_title);
            Thread.sleep(100);
            article_title = first_article_expected_title;
            Thread.sleep(100);
            searchPageObject.clickByMWResultArticleWithSubString(article_title);
            Thread.sleep(100);
            articlePageObject.waitForTitleElement();
            assertEquals(
                    "The articles titles is different. Expected title " + article_title + " but Actual title is " + articlePageObject.getArticleTitle(),
                    article_title,
                    articlePageObject.getArticleTitle()
            );
        }
    }

}
