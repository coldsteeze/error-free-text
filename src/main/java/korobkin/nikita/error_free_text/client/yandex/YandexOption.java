package korobkin.nikita.error_free_text.client.yandex;

import lombok.Getter;

@Getter
public enum YandexOption {

    IGNORE_DIGITS(2),
    IGNORE_URLS(4);

    private final int value;

    YandexOption(int value) {
        this.value = value;
    }
}
