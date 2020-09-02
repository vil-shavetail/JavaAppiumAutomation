package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {
    static{
        TITLE = "css:#content h1";
        FOOTER_ELEMENT = "css:#mw-mf-page-center footer";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "css:#page-actions li#ca-watch button";
    }

    public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
