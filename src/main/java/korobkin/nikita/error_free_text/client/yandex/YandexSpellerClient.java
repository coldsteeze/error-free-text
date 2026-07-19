package korobkin.nikita.error_free_text.client.yandex;

import korobkin.nikita.error_free_text.client.yandex.dto.SpellingError;
import korobkin.nikita.error_free_text.client.yandex.dto.YandexCheckTextRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class YandexSpellerClient {

    private final WebClient yandexWebClient;

    public List<List<SpellingError>> checkText(
            YandexCheckTextRequest request
    ) {

        log.debug("Sending request to Yandex Speller API");


        return yandexWebClient.post()
                .uri("/services/spellservice.json/checkTexts")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(
                        BodyInserters.fromFormData(
                                createFormData(request)
                        )
                )
                .retrieve()
                .bodyToMono(
                        new ParameterizedTypeReference<List<List<SpellingError>>>() {}
                )
                .block(Duration.ofSeconds(10));
    }


    private MultiValueMap<String, String> createFormData(YandexCheckTextRequest request) {

        MultiValueMap<String, String> data =
                new LinkedMultiValueMap<>();

        request.text().forEach(chunk ->
                data.add("text", chunk)
        );

        data.add("lang", request.lang());
        data.add("options", String.valueOf(request.options()));

        return data;
    }
}
