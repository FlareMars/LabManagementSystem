package com.libmanagement.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;

public class Mail {

    private static Log logger = LogFactory.getLog(Mail.class);

    public static void sendMessage(String smtpHost, String port,
                                   String from, String user, String pwd, String to,
                                   String subject, String messageText)
            throws MessagingException, UnsupportedEncodingException {

        // Step 1: Configure the mail session
        logger.debug("Configuring mail session for:" + smtpHost);
        java.util.Properties props = new java.util.Properties();
        props.put("mail.smtp.auth", "true");// 指定是否需要SMTP验证
        props.put("mail.smtp.host", smtpHost);// 指定SMTP服务器
        props.put("mail.smtp.port", port);
        props.put("mail.transport.protocol", "smtp");
        Session mailSession = Session.getDefaultInstance(props);
        mailSession.setDebug(false);// 是否在控制台显示debug信息
        // Step 2: Construct the message
        logger.debug("Constructing message -  from=" + from + "  to=" + to);
        InternetAddress fromAddress = new InternetAddress(from);
        InternetAddress toAddress = new InternetAddress(to);
        MimeMessage testMessage = new MimeMessage(mailSession);
        testMessage.setFrom(fromAddress);
        testMessage.addRecipient(javax.mail.Message.RecipientType.TO, toAddress);
        testMessage.setSentDate(new java.util.Date());
        testMessage.setSubject(MimeUtility.encodeText(subject, "gb2312", "B"));
        testMessage.setContent(messageText, "text/html;charset=gb2312");
        logger.debug("Message constructed");
        // Step 3: Now send the message
        Transport transport = mailSession.getTransport("smtp");
        transport.connect(smtpHost, user, pwd);
        transport.sendMessage(testMessage, testMessage.getAllRecipients());
        transport.close();
        logger.debug("Message sent!");
    }
}
