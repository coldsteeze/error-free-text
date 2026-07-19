package korobkin.nikita.error_free_text.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "yandex.speller")
@Getter
@Setter
public class YandexProperties {

    private String url;
}
