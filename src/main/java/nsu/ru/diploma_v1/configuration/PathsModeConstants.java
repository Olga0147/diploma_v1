package nsu.ru.diploma_v1.configuration;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class PathsModeConstants {
    public static final String API_VERSION_PREFIX_1 = "/api";

    public static final String GET_VIEW_MODE_PREF = "/view-mode";
    public static final String GET_EDIT_MODE_PREF = "/edit-mode";
    public static final String GET_USER_MODE_PREF = "/user-mode";
}
