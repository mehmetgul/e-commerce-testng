package utility;

import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.testng.annotations.Test;

public class SmsFetcher {

    // Initialize with your Twilio account SID and Auth Token
    public static final String ACCOUNT_SID = "YOUR_ACCOUNT_SID";
    public static final String AUTH_TOKEN = "YOUR_AUTH_TOKEN";
    public static final String TWILIO_NUMBER = "YOUR_TWILIO_PHONE_NUMBER";

    public static void initializeTwilio() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public String fetchLatestMessageCode() {
        initializeTwilio();

        ResourceSet<Message> messages = Message.reader()
                .setTo(new PhoneNumber(TWILIO_NUMBER)) // Filter messages sent to your Twilio number
                .limit(1) // Fetch only the latest message
                .read();

        for (Message message : messages) {
            // Assuming your verification code is a 4-digit number within the message body
            String body = message.getBody();
            String code = body.replaceAll("[^0-9]", ""); // Extract numbers only
            System.out.println("Latest code: " + code);
            return code; // Return the first (latest) code found
        }

        return null; // No message found
    }

    @Test
    public void testMethod() {
        String verificationCode = fetchLatestMessageCode();
        if (verificationCode != null) {
            System.out.println("Verification code: " + verificationCode);
            // Use the verification code as needed
        } else {
            System.out.println("No verification code found.");
        }
    }
}
