package korobkin.nikita.error_free_text.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient yandexWebClient(
            WebClient.Builder builder,
            YandexProperties properties
    ) {
        return builder
                .baseUrl(properties.getUrl())
                .build();
    }
}
