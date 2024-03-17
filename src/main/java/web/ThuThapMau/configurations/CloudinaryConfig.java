package web.ThuThapMau.configurations;
import com.cloudinary.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dayljhuxv");
        config.put("api_key", "557985169938267");
        config.put("api_secret", "lB776S2njPjjYaJN7VAFofKBgsg");
        return new Cloudinary(config);
    }
}
