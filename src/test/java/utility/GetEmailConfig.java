package utility;

import javax.mail.*;
import javax.mail.search.SubjectTerm;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GetEmailConfig {

    //get them from cofig properties or pass them as env parameter.
    private static final String EMAIL_HOST = System.getenv("EMAIL_HOST");//host: mail.store.protocol
    private static final String EMAIL_PROTOCOL = System.getenv("EMAIL_PROTOCOL");//protocol: imaps

    public String email(String email, String password, String searchSubjectText) throws Exception {
        String linkUrl = "";
        Properties props = new Properties();
        props.setProperty(EMAIL_HOST, EMAIL_PROTOCOL);

        // Set your email settings
        String host = "mail.richardrosellc.com";
        String username = email;
        String userPassword = password; // Replace with the actual password

        // Establish a mail session
        Session session = Session.getDefaultInstance(props, null);
        Store store = session.getStore("imaps");
        store.connect(host, username, userPassword);

        // Access the inbox
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);

        System.out.println("Total Messages: " + folder.getMessageCount());
        System.out.println("Unread Messages: " + folder.getUnreadMessageCount());

        Message[] messages;
        boolean isMailFound = false;
        Message mailFromGod = null;

        // Search for the specific mail
        for (int i = 0; i < 5 && !isMailFound; i++) {
            messages = folder.search(new SubjectTerm(searchSubjectText));
            // Check if any new mail arrived
            if (messages.length == 0) {
                Thread.sleep(10000);
            }

            // Check for unread mail
            for (Message mail : messages) {
                if (!mail.isSet(Flags.Flag.SEEN)) {
                    mailFromGod = mail;
                    System.out.println("Message Count: " + mailFromGod.getMessageNumber());
                    isMailFound = true;
                    break;
                }
            }
        }

        // Process the found email
        if (!isMailFound) {
            throw new Exception("Could not find new mail with the specified subject.");
        } else {
            // Extract subject
            String subject = mailFromGod.getSubject();
            System.out.println("Subject: " + subject);

            // Process the message content
            Object content = mailFromGod.getContent();
            if (content instanceof Multipart) {
                Multipart multipart = (Multipart) content;
                for (int i = 0; i < multipart.getCount(); i++) {
                    BodyPart bodyPart = multipart.getBodyPart(i);
                    String contentType = bodyPart.getContentType();

                    // Handle plain text and HTML content differently
                    if (contentType.toLowerCase().contains("text/plain")) {
                        System.out.println("Text: " + bodyPart.getContent());
                    } else if (contentType.toLowerCase().contains("text/html")) {
                        String html = (String) bodyPart.getContent();
//                        System.out.println("HTML: " + html);

                        // Extract links from HTML content
                        linkUrl = extractLinks(html);
                    }
                }
            } else if (content instanceof String) {
                // Handle simple text emails
                System.out.println("Text: " + content);
            }
        }

        // Clean up
        folder.close(false);
        store.close();
        return linkUrl;
    }

    private static String extractLinks(String html) {
        // Use a regex to extract URLs
        Pattern pattern = Pattern.compile("href=\"(.*?)\"", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            System.out.println("Link: " + matcher.group(1));
            return matcher.group(1);
        }
        return html;
    }

}
