package note.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

/**
 * @Author: lonelyzhou
 * @ProjectName: RecordAndShare
 * @Package: IntelliJ IDEA
 * @Description: 邮箱工具类
 * @Date: 2022/2/13 下午 12:20
 **/
@Service
public class EmailUtil {
    @Value("${spring.mail.username}")
    private String emailUsername;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发送简单文字邮件
     * @param to 发送邮件地址
     * @param subject 主题
     * @param content 内容
     */
    public  void sendSimpleTextMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(emailUsername);
        mailSender.send(message);
    }

    /**
     * 发送HTML邮件
     * @param to 发送邮件地址
     * @param subject 主题
     * @param content 内容
     * @throws MessagingException 异常
     */
    public  void sendSimpleHtmlMail(String to, String subject, String content) throws MessagingException {
        MimeMessageHelper message = new MimeMessageHelper(mailSender.createMimeMessage(),true);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content,true);
        message.setFrom(emailUsername);
        mailSender.send(message.getMimeMessage());
    }
}
