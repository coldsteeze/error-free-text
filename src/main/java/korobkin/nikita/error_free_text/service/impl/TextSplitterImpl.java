package korobkin.nikita.error_free_text.service.impl;

import korobkin.nikita.error_free_text.service.TextSplitter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TextSplitterImpl implements TextSplitter {

    private static final int YANDEX_MAX_TEXT_LENGTH = 10_000;

    @Override
    public List<String> split(String text) {

        List<String> chunks = new ArrayList<>();

        if (text.length() <= YANDEX_MAX_TEXT_LENGTH) {
            chunks.add(text);
            return chunks;
        }

        int start = 0;

        while (start < text.length()) {

            int end = Math.min(
                    start + YANDEX_MAX_TEXT_LENGTH,
                    text.length()
            );

            if (end < text.length()) {

                int lastSpace =
                        text.lastIndexOf(' ', end);

                if (lastSpace > start) {
                    end = lastSpace;
                }
            }

            chunks.add(text.substring(start, end));

            start = end;
        }

        return chunks;
    }
}
