package nsu.ru.diploma_v1.configuration.urls.mode;

import nsu.ru.diploma_v1.configuration.urls.EntityPath;

import static nsu.ru.diploma_v1.configuration.urls.ModePath.VIEW_MODE;

public class ViewMode extends EntityPath {

    public static class Info {
        public static final String PREF = VIEW_MODE +"/info";

        public static final String GET_PAGE =             PREF + PAGE;
        public static final String GET_CLASS =            PREF + CLASS;
        public static final String GET_OBJECT =           PREF + OBJECT;
        public static final String GET_AGGREGATION =      PREF + AGGREGATION;
        public static final String GET_AGGREGATION_IMPL = PREF + AGGREGATION_IMPL;
        public static final String GET_ASSOCIATION =      PREF + ASSOCIATION;
        public static final String GET_ASSOCIATION_IMPL = PREF + ASSOCIATION_IMPL;
        public static final String GET_TEMPLATE =         PREF + TEMPLATE;
        public static final String GET_COMPOSITION =      PREF + COMPOSITION;
        public static final String GET_COMPOSITION_IMPL = PREF + COMPOSITION_IMPL;
    }

    public static class DetailInfo{
        public static final String PREF = VIEW_MODE +"/detail-info";

        public static final String GET_PAGE =             PREF +             PAGE + "/{id}";
        public static final String GET_CLASS =            PREF +            CLASS + "/{id}";
        public static final String GET_OBJECT =           PREF +           OBJECT + "/{id}";
        public static final String GET_AGGREGATION =      PREF +      AGGREGATION + "/{id}";
        public static final String GET_AGGREGATION_IMPL = PREF + AGGREGATION_IMPL + "/{id}";
        public static final String GET_ASSOCIATION =      PREF +      ASSOCIATION + "/{id}";
        public static final String GET_ASSOCIATION_IMPL = PREF + ASSOCIATION_IMPL + "/{id}";
        public static final String GET_TEMPLATE =         PREF +         TEMPLATE + "/{id}";
        public static final String GET_COMPOSITION =      PREF +      COMPOSITION + "/{id}";
        public static final String GET_COMPOSITION_IMPL = PREF + COMPOSITION_IMPL + "/{id}";

    }
}
