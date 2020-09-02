package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {

    @Test
    public void testCompareArticleTitle() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubString("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        String article_title = articlePageObject.getArticleTitle();

        assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    public void testSwipeArticle() {
        String expected_article_title = "Appium";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Appium");
        searchPageObject.clickByArticleWithSubString(expected_article_title);

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        if(Platform.getInstance().isAndroid()){
            articlePageObject.waitForTitleElement();
        } else if(Platform.getInstance().isIOS()) {
            articlePageObject.waitForTitleElement(expected_article_title);
        }
        articlePageObject.swipeToFooter();
    }

    @Test
    public void testAssertArticleTitlePresent() {
        String search_line = "Yamaha Niken";
        String search_article_title_locator;
        if(Platform.getInstance().isAndroid()) {
            search_article_title_locator = "xpath://*[@resource-id='org.wikipedia:id/view_page_title_text'][@text='" + search_line + "']";
        } else if(Platform.getInstance().isIOS()) {
            search_article_title_locator = "id:Yamaha Niken";
        } else {
            search_article_title_locator = "xpath://a[contains(@class, 'title')][contains(@data-title, '{TITLE}')]/..";
        }

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_line);
        searchPageObject.clickByArticleWithSubString(search_line);

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.assertElementPresent(
                search_article_title_locator,
                search_line
        );
    }

}
