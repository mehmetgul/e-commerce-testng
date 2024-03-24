package pages;


import elements.UserRegistrationElements;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserRegistrationPage extends PageBase {
    private static final Logger logger = LogManager.getLogger(UserRegistrationPage.class);
    UserRegistrationElements userRegistrationElements;

    /**
     * navigateTo registration page
     * Fill out the registration form
     * submit registration
     * verification
     */
    public boolean createNewRegistration() {
        logger.info("Creating random account user started");
        String email = getFaker().internet().emailAddress();
        String firstName = getFaker().name().firstName();
        String lastNAme = getFaker().name().lastName();
        userRegistrationElements = new UserRegistrationElements();

        navigateToRegistrationPage(email);
        fillOutRegistrationForm(firstName, lastNAme);
        submitRegistration();
        return verifyRegistrationSuccess();
    }

    private void navigateToRegistrationPage(String email) {
        clickElement(userRegistrationElements.signInButton);
        clickElement(userRegistrationElements.emailAddress);
        sendKeyToElement(userRegistrationElements.emailAddress, email);
        clickElement(userRegistrationElements.createAccountButton);
    }

    private void fillOutRegistrationForm(String firstName, String lastNAme) {
        clickElement(userRegistrationElements.mrsCheckBox);
        clickElement(userRegistrationElements.firstName);
        sendKeyToElement(userRegistrationElements.firstName, firstName);
        clickElement(userRegistrationElements.lastName);
        sendKeyToElement(userRegistrationElements.lastName, lastNAme);
        sendKeyToElement(userRegistrationElements.password, "Test4321");
        setBirthDate("5", "12", "2001");
    }

    private void setBirthDate(String day, String month, String year) {
        selectElementByValue(userRegistrationElements.day, day);
        selectElementByValue(userRegistrationElements.month, month);
        selectElementByValue(userRegistrationElements.year, year);
    }

    private void submitRegistration() {
        clickElement(userRegistrationElements.register);
    }

    private boolean verifyRegistrationSuccess() {
        String title = getCurrentPageTitle();
        if(title.contains("account")){
            return true;
        }
        return false;
    }

    public String getRegistrationPageTitle() {
        return getCurrentPageTitle();
    }
}
