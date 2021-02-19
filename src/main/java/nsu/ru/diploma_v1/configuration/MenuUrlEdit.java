package nsu.ru.diploma_v1.configuration;

import lombok.Getter;
import org.springframework.stereotype.Component;

import static nsu.ru.diploma_v1.configuration.PathsModeConstants.GET_EDIT_MODE_PREF;
import static nsu.ru.diploma_v1.configuration.PathsModeConstants.GET_VIEW_MODE_PREF;
import static nsu.ru.diploma_v1.configuration.SysUrl.GET_NEW_PREF;

@Getter
@Component
public class MenuUrlEdit {
    public final String view_mode = GET_VIEW_MODE_PREF;
    public final String edit_mode = GET_EDIT_MODE_PREF;
    public final String action = GET_NEW_PREF;
}
