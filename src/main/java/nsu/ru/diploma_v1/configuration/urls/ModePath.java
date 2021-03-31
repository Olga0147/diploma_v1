package nsu.ru.diploma_v1.configuration.urls;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ModePath {
    public static final String VIEW_MODE = "/view-mode";
    public static final String EDIT_MODE = "/edit-mode";
    public static final String USER_MODE = "/user-mode";
    public static final String DOWNLOAD_MODE = "/download-mode";
}
