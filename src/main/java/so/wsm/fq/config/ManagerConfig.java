package so.wsm.fq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "manager")
public class ManagerConfig {

    private Boolean enable = Boolean.FALSE;

}
