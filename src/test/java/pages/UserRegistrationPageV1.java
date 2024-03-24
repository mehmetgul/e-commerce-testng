package pages;


import elements.UserRegistrationElements;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserRegistrationPageV1 extends PageBase{
    private static final Logger logger = LogManager.getLogger(UserRegistrationPageV1.class);
    UserRegistrationElements userRegistrationElements;

    public void createNewRegistration() {
        logger.info("Creating random account user started");
        String email= getFaker().internet().emailAddress();
        String firstName= getFaker().name().firstName();
        String lastNAme= getFaker().name().lastName();
        userRegistrationElements =new UserRegistrationElements();
        clickElement(userRegistrationElements.signInButton);
        clickElement(userRegistrationElements.emailAddress);
        logger.info("Filling registration form");
        sendKeyToElement(userRegistrationElements.emailAddress,email);
        clickElement(userRegistrationElements.createAccountButton);
        clickElement(userRegistrationElements.mrsCheckBox);
        clickElement(userRegistrationElements.firstName);
        sendKeyToElement(userRegistrationElements.firstName,firstName);
        clickElement(userRegistrationElements.lastName);
        sendKeyToElement(userRegistrationElements.lastName, lastNAme);
        sendKeyToElement(userRegistrationElements.password, "Test4321");
        selectElementByValue(userRegistrationElements.day, "25");
        selectElementByValue(userRegistrationElements.month, "4");
        selectElementByValue(userRegistrationElements.year, "2000");
        logger.info("Submitting registration");
        clickElement(userRegistrationElements.register);
        logger.info("Registration successful");
    }
}
