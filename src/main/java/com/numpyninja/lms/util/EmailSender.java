package com.numpyninja.lms.util;

import com.numpyninja.lms.entity.EmailDetails;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Map;

@Component
public class EmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration freeMarkerConfigurer;

    private static final String EMAIL_SUCCESS_MSG = "Email sent successfully";

    public String sendSimpleEmail(EmailDetails emailDetails) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(emailDetails.getRecipient());
            simpleMailMessage.setSubject(emailDetails.getSubject());
            simpleMailMessage.setText(emailDetails.getContent());
            if (emailDetails.getCc() != null && !emailDetails.getCc().isEmpty()) {
                simpleMailMessage.setCc(emailDetails.getCc());
            }
            javaMailSender.send(simpleMailMessage);
        } catch (MailException e) {
            e.printStackTrace();
            return "Email could not be sent!";
        }
        return EMAIL_SUCCESS_MSG;
    }

    public String sendEmailUsingTemplate(EmailDetails emailDetails) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            Map<String, Object> model = emailDetails.getModel();
            Template template = freeMarkerConfigurer.getTemplate("WelcomeEmail.ftl");
            String htmlEmail = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            mimeMessageHelper.setTo(emailDetails.getRecipient());
            mimeMessageHelper.setSubject(emailDetails.getSubject());
            if (!emailDetails.getCc().isEmpty()) {
                mimeMessageHelper.setCc(emailDetails.getCc());
            }
            mimeMessageHelper.setText(htmlEmail, true);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | TemplateException | IOException e) {
            e.printStackTrace();
            return "Email could not be delivered!";
        }
        return EMAIL_SUCCESS_MSG;
    }

    public String sendEmailUsingTemplateForgotPassword(EmailDetails emailDetails) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            Map<String, Object> model = emailDetails.getModel();
            Template template = freeMarkerConfigurer.getTemplate("ConfirmEmail.ftl");
            String htmlEmail = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            mimeMessageHelper.setTo(emailDetails.getRecipient());
            mimeMessageHelper.setSubject(emailDetails.getSubject());
            if (!emailDetails.getCc().isEmpty()) {
                mimeMessageHelper.setCc(emailDetails.getCc());
            }
            mimeMessageHelper.setText(htmlEmail, true);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | TemplateException | IOException e) {
            e.printStackTrace();
            return "Email could not be delivered!";
        }
        return EMAIL_SUCCESS_MSG;
    }
}
