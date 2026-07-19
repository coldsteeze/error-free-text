package korobkin.nikita.error_free_text.client.yandex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SpellingError(

        int code,
        int pos,
        int row,
        int col,
        int len,
        String word,

        @JsonProperty("s")
        List<String> suggestions
) {
}
