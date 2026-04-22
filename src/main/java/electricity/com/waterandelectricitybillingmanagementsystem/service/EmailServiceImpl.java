package electricity.com.waterandelectricitybillingmanagementsystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendOtpEmail(String to, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Registration OTP - Water & Electricity Billing");
            message.setText("Thank you for registering. Your OTP for verification is: " + otp);
            mailSender.send(message);
            
            logger.info("Email successfully sent to: {}", to);
            logger.debug("OTP sent: {}", otp);
        } catch (Exception e) {
            logger.error("Failed to send email to: {}. Reason: {}", to, e.getMessage());
            logger.warn("OTP (FALLBACK FOR LOGS): {}", otp);
        }
    }
}
