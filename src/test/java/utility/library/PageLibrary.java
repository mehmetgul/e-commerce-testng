package utility.library;

import pages.LoginFunctionalityPage;
import pages.SearchFunctionalityPage;
import pages.UserRegistrationPage;

public class PageLibrary {
    LoginFunctionalityPage loginFunctionalityPage;
    SearchFunctionalityPage searchFunctionalityPage;
    UserRegistrationPage userRegistrationPage;

    public PageLibrary() {
        loginFunctionalityPage = new LoginFunctionalityPage();
        searchFunctionalityPage = new SearchFunctionalityPage();
        userRegistrationPage = new UserRegistrationPage();

    }

    public LoginFunctionalityPage getLoginFunctionalityPage() {
        return loginFunctionalityPage;
    }

    public SearchFunctionalityPage getSearchFunctionalityPage() {
        return searchFunctionalityPage;
    }

    public UserRegistrationPage getUserRegistrationPage() {
        return userRegistrationPage;
    }


}
