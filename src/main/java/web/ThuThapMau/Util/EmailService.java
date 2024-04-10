package web.ThuThapMau.Util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailService {
    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String user_email, Long user_id) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom("spsuperprosp@gmail.com");
        mimeMessageHelper.setTo(user_email.replaceAll("\\s", ""));
        mimeMessageHelper.setSubject("Reset Your ROOMMM Password");

        // Tạo nội dung email
        String emailContent = "<h2>Reset Your ROOMMM Password</h2>"
                + "<p>Hello,</p>"
                + "<p>We received a request to reset the password associated with this email address. If you made this request, please click the link below to reset your password:</p>"
                + "<p><a href='http://localhost:10000/reset-password?user_id=" + user_id + "'>Reset Password</a></p>"
                + "<p>If you didn't make this request, you can safely ignore this email.</p>"
                + "<p>Best regards,<br>ROOMMM Team</p>";

        mimeMessageHelper.setText(emailContent, true);
        javaMailSender.send(mimeMessage);
        System.out.println("Sent mail to " + user_email);
    }
}
