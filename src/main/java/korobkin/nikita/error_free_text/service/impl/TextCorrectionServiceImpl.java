package korobkin.nikita.error_free_text.service.impl;

import korobkin.nikita.error_free_text.client.yandex.YandexSpellerClient;
import korobkin.nikita.error_free_text.client.yandex.dto.SpellingError;
import korobkin.nikita.error_free_text.client.yandex.dto.YandexCheckTextRequest;
import korobkin.nikita.error_free_text.entity.enums.TaskLanguage;
import korobkin.nikita.error_free_text.service.CorrectionOptionsResolver;
import korobkin.nikita.error_free_text.service.TextCorrectionService;
import korobkin.nikita.error_free_text.service.TextSplitter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class TextCorrectionServiceImpl implements TextCorrectionService {

    private final TextSplitter textSplitter;
    private final CorrectionOptionsResolver correctionOptionsResolver;
    private final YandexSpellerClient yandexSpellerClient;

    @Override
    public String correct(String text, TaskLanguage language) {
        List<String> chunks = textSplitter.split(text);

        int options =
                correctionOptionsResolver.resolve(text);


        return processChunks(
                chunks,
                language,
                options
        );
    }

    private String processChunks(
            List<String> chunks,
            TaskLanguage language,
            int options
    ) {

        List<String> correctedChunks = new ArrayList<>();

        for (String chunk : chunks) {

            YandexCheckTextRequest request =
                    new YandexCheckTextRequest(
                            List.of(chunk),
                            language.name().toLowerCase(Locale.ROOT),
                            options
                    );


            List<List<SpellingError>> errors =
                    yandexSpellerClient.checkText(request);

            if (errors == null || errors.isEmpty()) {
                correctedChunks.add(chunk);
                continue;
            }

            List<SpellingError> chunkErrors = errors.getFirst();

            log.debug(
                    "Yandex returned {} spelling errors for chunk",
                    chunkErrors.size()
            );

            correctedChunks.add(
                    applyCorrections(chunk, chunkErrors)
            );
        }

        return String.join("", correctedChunks);
    }

    private String applyCorrections(
            String text,
            List<SpellingError> errors
    ) {

        log.debug(
                "Applying {} corrections to text chunk. Size={}",
                errors.size(),
                text.length()
        );

        StringBuilder builder = new StringBuilder(text);

        errors.stream()
                .sorted(
                        Comparator.comparingInt(SpellingError::pos)
                                .reversed()
                )
                .forEach(error -> {
                    if (error.suggestions().isEmpty()) {
                        return;
                    }

                    log.debug(
                            "Replacing word '{}' with '{}' at position {}",
                            error.word(),
                            error.suggestions().getFirst(),
                            error.pos()
                    );

                    builder.replace(
                            error.pos(),
                            error.pos() + error.len(),
                            error.suggestions().getFirst()
                    );
                });

        log.debug("Text chunk correction completed");

        return builder.toString();
    }
}
