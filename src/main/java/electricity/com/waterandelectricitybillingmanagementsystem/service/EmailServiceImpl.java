package electricity.com.waterandelectricitybillingmanagementsystem.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

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
            
            System.out.println("--------------------------------------------------");
            System.out.println("[SUCCESS] EMAIL SENT TO: " + to);
            System.out.println("OTP: " + otp);
            System.out.println("--------------------------------------------------");
        } catch (Exception e) {
            System.err.println("--------------------------------------------------");
            System.err.println("[ERROR] FAILED TO SEND EMAIL TO: " + to);
            System.err.println("REASON: " + e.getMessage());
            System.err.println("OTP (FALLBACK): " + otp);
            System.err.println("--------------------------------------------------");
        }
    }
}
