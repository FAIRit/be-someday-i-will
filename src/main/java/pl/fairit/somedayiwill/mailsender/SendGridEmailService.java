package pl.fairit.somedayiwill.mailsender;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.fairit.somedayiwill.user.AppUser;

import java.io.IOException;

@Slf4j
@Component
public class SendGridEmailService {
    private final static String EMAIL_FROM = "someday-i-will@someday-i-will.com";
    private final SendGrid sendGrid;


    public SendGridEmailService(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    public void sendMail(final String emailTo, final String subject, final String contentValue) {
        Email from = new Email(EMAIL_FROM, "Ewelina");
        Content content = new Content("text/plain", contentValue);
        Email to = new Email(emailTo);
        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            log.info("Status code: " + response.getStatusCode());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendRegisterConfirmationEmail(final AppUser appUser) {
        final String subject = "Welcome to Someday I Will!";
        final String content = "Hi " + appUser.getName() + ", welcome to Someday I Will! Thanks for registering an account on the Someday I Will!";
        sendMail(appUser.getEmail(), subject, content);
    }
}
