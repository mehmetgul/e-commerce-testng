package utility.library;

import pages.LoginFunctionalityPage;
import pages.UserRegistrationPage;

public class PageLibrary {
   LoginFunctionalityPage loginFunctionalityPage;
   UserRegistrationPage userRegistrationPage;

   public PageLibrary(){
       loginFunctionalityPage = new LoginFunctionalityPage();
       userRegistrationPage =new UserRegistrationPage();
   }

    public LoginFunctionalityPage getLoginFunctionalityPage() {
        return loginFunctionalityPage;
    }

    public UserRegistrationPage getUserRegistrationPage() {
        return userRegistrationPage;
    }
}
