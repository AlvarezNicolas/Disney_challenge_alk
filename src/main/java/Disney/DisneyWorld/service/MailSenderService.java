/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Disney.DisneyWorld.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
/**
 *
 * @author anico
 */
@Service
public class MailSenderService {
    
    private JavaMailSender mailSender;
    
    @Async
    public void sender(String body, String title, String email){
     SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("noreply@DisneyWorld.com");
        message.setSubject(title);
        message.setText(body);
        
        mailSender.send(message);
    }
    
}