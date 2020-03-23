package pl.fairit.somedayiwill.newsletter;

import com.github.javafaker.Faker;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SendGridEmailServiceMockitoTest {

    @Mock
    SendGrid sendGrid;

    @InjectMocks
    SendGridEmailService emailService;

    @Test
    public void shouldSendEmailAndReturnOkResponse() throws IOException {
        var faker = new Faker();
        var htmlContent = faker.lorem().sentence();
        var subject = faker.cat().name();
        var emailTo = faker.internet().emailAddress();
        var response = new Response();
        response.setStatusCode(202);

        when(sendGrid.api(ArgumentMatchers.any(Request.class))).thenReturn(response);
        var actualResponse = emailService.sendHtmlMail(htmlContent, emailTo, subject);

        assertEquals(response, actualResponse);
    }
}
