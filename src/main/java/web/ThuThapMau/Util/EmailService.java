package web.ThuThapMau.Util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailService {
    private  JavaMailSender javaMailSender;


    public  EmailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }
    public void sendEmail(String user_email, Long user_id) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom("spsuperprosp@gmail.com");
        mimeMessageHelper.setTo(user_email.replaceAll("\\s", ""));
        mimeMessageHelper.setText("http://localhost:10000/reset-password?user_id="+user_id);
        mimeMessageHelper.setSubject("ROOMMM Please reset your password");
        javaMailSender.send(mimeMessage);
        System.out.println("Sent mail to " + user_email);
    }
}
