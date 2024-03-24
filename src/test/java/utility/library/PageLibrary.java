package utility.library;

import pages.LoginFunctionalityPage;

public class PageLibrary {
   LoginFunctionalityPage loginFunctionalityPage;

   public PageLibrary(){
       loginFunctionalityPage = new LoginFunctionalityPage();
   }

    public LoginFunctionalityPage getLoginFunctionalityPage() {
        return loginFunctionalityPage;
    }
}
