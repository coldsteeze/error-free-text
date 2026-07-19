package korobkin.nikita.error_free_text.client.yandex.dto;

import java.util.List;

public record YandexCheckTextRequest(

        List<String> text,
        String lang,
        int options
) {
}
