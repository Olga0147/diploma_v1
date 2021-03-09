package nsu.ru.diploma_v1.configuration.urls.mode;

import nsu.ru.diploma_v1.configuration.urls.EntityPath;

import static nsu.ru.diploma_v1.configuration.urls.ModePath.EDIT_MODE;

public class EditMode extends EntityPath {

    public static class GetForm{
        public static final String PREF = EDIT_MODE +"/new";

        public static final String GET_PAGE =             PREF + PAGE;
        public static final String GET_CLASS =            PREF + CLASS;
        public static final String GET_OBJECT =           PREF + OBJECT;
        public static final String GET_AGGREGATION =      PREF + AGGREGATION;
        public static final String GET_AGGREGATION_IMPL = PREF + AGGREGATION_IMPL;
        public static final String GET_ASSOCIATION =      PREF + ASSOCIATION;
        public static final String GET_ASSOCIATION_IMPL = PREF + ASSOCIATION_IMPL;
        public static final String GET_TEMPLATE =         PREF + TEMPLATE;
        public static final String GET_COMPOSITION =      PREF + COMPOSITION;
    }

    public static class PostForm{
        public static final String PREF = EDIT_MODE +"/check";

        public static final String POST_PAGE =             PREF + PAGE;
        public static final String POST_CLASS =            PREF + CLASS;
        public static final String POST_OBJECT =           PREF + OBJECT + "/{classId}";
        public static final String POST_AGGREGATION =      PREF + AGGREGATION;
        public static final String POST_AGGREGATION_IMPL = PREF + AGGREGATION_IMPL +"/{aggregationId}";
        public static final String POST_ASSOCIATION =      PREF + ASSOCIATION;
        public static final String POST_ASSOCIATION_IMPL = PREF + ASSOCIATION_IMPL +"/{associationId}";
        public static final String POST_TEMPLATE =         PREF + TEMPLATE + "/{classId}";
        public static final String POST_COMPOSITION =       PREF + COMPOSITION;
    }

    public static class PartForm{
        public static final String PREF = EDIT_MODE +"/part-form";

        public static final String ATTRIBUTE_FORM =           PREF +"/attribute/{length}";
        public static final String OBJECT_FORM =              PREF + OBJECT+"/{classId}";
        public static final String AGGREGATION_IMPL_FORM =    PREF + AGGREGATION_IMPL+"/{aggregationId}";
        public static final String ASSOCIATION_IMPL_FORM =    PREF + ASSOCIATION_IMPL+"/{associationId}";
        public static final String TEMPLATE_FORM =            PREF + TEMPLATE+"/{classId}";
    }
}
