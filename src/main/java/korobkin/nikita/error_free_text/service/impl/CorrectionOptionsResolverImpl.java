package korobkin.nikita.error_free_text.service.impl;

import korobkin.nikita.error_free_text.client.yandex.YandexOption;
import korobkin.nikita.error_free_text.service.CorrectionOptionsResolver;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class CorrectionOptionsResolverImpl implements CorrectionOptionsResolver {

    private static final Pattern DIGITS_PATTERN = Pattern.compile("\\d");
    private static final Pattern URL_PATTERN = Pattern.compile("(https?://|www\\.)");

    @Override
    public int resolve(String text) {

        int options = 0;


        if (DIGITS_PATTERN.matcher(text).find()) {
            options += YandexOption.IGNORE_DIGITS.getValue();
        }


        if (URL_PATTERN.matcher(text).find()) {
            options += YandexOption.IGNORE_URLS.getValue();
        }

        return options;
    }
}
