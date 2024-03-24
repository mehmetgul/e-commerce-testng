package pages;

import elements.LoginFunctionalityElements;
import utility.ConfigReader;

/**
 * Go to Login page
 * Perform login
 * verify login
 */
public class LoginFunctionalityPage extends PageBase {
    LoginFunctionalityElements loginFunctionalityElements = new LoginFunctionalityElements();

    public String[] loginFunctionality() {
        navigateToSingInPage();
        performLogin();
        return getLoginVerificationTexts();
    }

    private void navigateToSingInPage() {
        clickElement(loginFunctionalityElements.signInLink);
    }

    private void performLogin() {
        String userLoginEmail= ConfigReader.getProperty("email");
        String userLoginPassword= ConfigReader.getProperty("password");
        sendKeyToElement(loginFunctionalityElements.email, userLoginEmail);
        sendKeyToElement(loginFunctionalityElements.password, userLoginPassword);
        clickElementWithWait(loginFunctionalityElements.signInButton);
    }

    public String[] getLoginVerificationTexts() {
        String actualResult = getTextElement(loginFunctionalityElements.accountCheck);
        String actualWelcomeMessage= getTextElement(loginFunctionalityElements.welcomeMessage);
        return new String[]{actualResult,actualWelcomeMessage};
    }
}
