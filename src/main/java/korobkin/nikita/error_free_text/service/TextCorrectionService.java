package korobkin.nikita.error_free_text.service;

import korobkin.nikita.error_free_text.entity.enums.TaskLanguage;

public interface TextCorrectionService {

    String correct(String text, TaskLanguage language);
}
