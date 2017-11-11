package so.wsm.fq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties
public class UserConfig {

    private List<User> users;

    @Data
    protected static class User{

        private String username;
        private String password;

    }

}
