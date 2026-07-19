package korobkin.nikita.error_free_text.service;

import java.util.List;

public interface TextSplitter {

    List<String> split(String text);
}
