package by.darkvelton69.inputproject.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;


    @Async("mailExecutor")
    public void sendHtmlMessageWithAttachment(String to, String subject, String clientName, String ticketContent){
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);


            Context context = new Context();
            context.setVariable("firstName", clientName);
            String htmlContent = templateEngine.process("appointment", context);

            helper.setText(htmlContent, true);

            byte[] fileBytes = ticketContent.getBytes(StandardCharsets.UTF_8);
            ByteArrayResource resource = new ByteArrayResource(fileBytes);

            helper.addAttachment("ticket.txt", resource);

            emailSender.send(mimeMessage);

            log.info("Успешно отправлено письмо на {}", to);
        }catch (MailException | MessagingException e){
            log.error("Ошибка при отправке письма на {}: {}", to, e.getMessage());
        }
    }


    @Async("mailExecutor")
    public void sendVerificationEmail(String to, String token){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Подтверждение регистрации");

        String confirmationUrl = "http://localhost:8080/polyclinic34/auth/confirm?token="+token;

        message.setText("Для подтверждения email перейдите по ссылке:\n"+confirmationUrl);

        emailSender.send(message);
    }
}
