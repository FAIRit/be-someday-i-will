package pl.fairit.somedayiwill.newsletter;


import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class SendGridEmailService {
    private final static String EMAIL_FROM = "someday-i-will@someday-i-will.com";
    private final static String SENDER_NAME = "Ewelina from Someday I Will";
    private final SendGrid sendGrid;

    public SendGridEmailService(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    public void sendHtmlMail(final String htmlContent, final String emailTo, final String subject) {
        var from = new Email(EMAIL_FROM, SENDER_NAME);
        var content = new Content("text/html", htmlContent);
        var to = new Email(emailTo);
        var mail = new Mail(from, subject, to, content);
        var request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            log.info("Status code: " + response.getStatusCode());
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }
}
