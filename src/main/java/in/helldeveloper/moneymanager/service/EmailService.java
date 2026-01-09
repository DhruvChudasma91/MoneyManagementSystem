package in.helldeveloper.moneymanager.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.properties.mail.smtp.from}")
    private String fromEmail;

    // Generic HTML email sender
    public void sendMail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // HTML content

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    // Activation email sender
    public void sendActivationMail(String to, String fullName, String activationUrl) {
        String body = """
                Hi %s,<br><br>
                Welcome to Money Manager!<br>
                Please activate your account by clicking the button below:<br><br>
                <a href="%s"
                   style="display:inline-block;padding:10px 20px;background-color:#007bff;color:#ffffff;
                          text-decoration:none;border-radius:5px;font-weight:bold;">
                    Activate Account
                </a>
                <br><br>
                If you didn't request this, please ignore this email.<br><br>
                Best regards,<br>
                Money Manager Team
                """.formatted(fullName, activationUrl);

        sendMail(to, "Activate your Money Manager account", body);
    }

    public void sendEmailWithAttachment(String to, String subject, String body, byte[] attachment,String filename) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);

            // Attach Excel file
            ByteArrayResource resource = new ByteArrayResource(attachment);
            helper.addAttachment(filename, resource);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email with attachment", e);
        }
    }


}
