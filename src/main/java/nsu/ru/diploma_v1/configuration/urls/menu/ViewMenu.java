package nsu.ru.diploma_v1.configuration.urls.menu;

import lombok.NoArgsConstructor;
import nsu.ru.diploma_v1.configuration.urls.EntityPath;
import nsu.ru.diploma_v1.configuration.urls.mode.ViewMode;
import org.springframework.stereotype.Component;

import static nsu.ru.diploma_v1.configuration.urls.ModePath.EDIT_MODE;
import static nsu.ru.diploma_v1.configuration.urls.ModePath.VIEW_MODE;

@Component
@NoArgsConstructor
public class ViewMenu extends EntityPath {
    public final String view_mode = VIEW_MODE;
    public final String edit_mode = EDIT_MODE;
    public final String action = ViewMode.Info.PREF;

    public final String pagePath =            action + PAGE;
    public final String classPath =           action + CLASS;
    public final String objectPath =          action + OBJECT;
    public final String aggregationPath =     action + AGGREGATION;
    public final String aggregationImplPath = action + AGGREGATION_IMPL;
    public final String associationPath =     action + ASSOCIATION;
    public final String associationImplPath = action + ASSOCIATION_IMPL;
    public final String templatePath =        action + TEMPLATE;

}
