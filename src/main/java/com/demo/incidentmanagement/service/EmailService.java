package com.demo.incidentmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

/**
 * Service class to handle email sending
 */
@Service
public class EmailService {
    private final JavaMailSender mailSender;
    @Value("${imsapp.mail.from}")
    private String mailFrom;

    /**
     * Constructor
     * @param mailSender JavaMailSender object
     */
    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Sends password reset email
     * @param email to -> email
     * @param resetPassword new temporary password
     * @param userName User's full name
     * @return Whether mail sent or not
     */
    public boolean sendPasswordResetMail(String email, String resetPassword, String userName) {
        boolean mailSent = false;
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(mailFrom);
            messageHelper.setTo(email);
            messageHelper.setSubject("Incident Management System : Password Reset");
            messageHelper.setText(String.format("""
                            Hi %s

                            As per your request we have reset your password, below are the details :
                            New Password : %s
                            
                            You will be prompted to change you password after login.

                            Best Regards
                            Incident Management System
                            """,
                    userName,
                    resetPassword));
        };
        try {
            mailSender.send(messagePreparator);
            mailSent = true;
        } catch (MailException e) {
            System.out.println("Exception occurred when sending mail : " + e.getMessage());
            throw new RuntimeException("Exception occurred when sending mail please try again");
        }
        return mailSent;
    }
}
