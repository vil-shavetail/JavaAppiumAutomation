package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String search_line = "Linkin Park disc";
        searchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = searchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "We found to few results",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String search_line = "affafafafafaf";
        searchPageObject.typeSearchLine(search_line);
        searchPageObject.waitForEmptyResultsLabel();
        if(Platform.getInstance().isAndroid()){
            searchPageObject.assertThereIsNoResultOfSearch();
        }
    }

    @Test
    public void testFindCoupleOfArticlesAndCancelSearch() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String search_line = "React";
        searchPageObject.typeSearchLine(search_line);
        String first_search_result_article_title = "React";
        String second_search_result_article_title = "React (web framework)";
        String third_search_result_article_title = "ReactOS";
        searchPageObject.waitForSearchResult(first_search_result_article_title);
        searchPageObject.waitForSearchResult(second_search_result_article_title);
        searchPageObject.waitForSearchResult(third_search_result_article_title);
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testFindArticlesByTitleAndDescription() {
        String search_line = "The Witcher",
                first_article_title = "The Witcher",
                first_article_description = "Series of fantasy novels and short stories by Polish writer Andrzej Sapkowski",
                second_article_title = "The Witcher (TV series)",
                second_article_description = "2019 fantasy drama television series",
                third_article_title = "The Witcher 3: Wild Hunt",
                third_article_description = "2015 action role-playing video game";
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search_line);
        searchPageObject.waitForElementByTitleAndDescription(first_article_title, first_article_description);
        searchPageObject.waitForElementByTitleAndDescription(second_article_title, second_article_description);
        searchPageObject.waitForElementByTitleAndDescription(third_article_title, third_article_description);
    }

}
