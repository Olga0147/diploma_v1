package nsu.ru.diploma_v1.configuration.urls.mode;

import nsu.ru.diploma_v1.configuration.urls.EntityPath;

import static nsu.ru.diploma_v1.configuration.urls.ModePath.EDIT_MODE;

public class EditMode extends EntityPath {

    public static class GetForm{
        public static final String PREF = EDIT_MODE +"/new";

        public static final String GET_CLASS =            PREF + CLASS;
        public static final String GET_OBJECT =           PREF + OBJECT;
        public static final String GET_AGGREGATION =      PREF + AGGREGATION;
        public static final String GET_ASSOCIATION =      PREF + ASSOCIATION;
        public static final String GET_ASSOCIATION_IMPL = PREF + ASSOCIATION_IMPL;
        public static final String GET_TEMPLATE =         PREF + TEMPLATE;
        public static final String GET_RESOURCE =         PREF + RESOURCE;
    }

    public static class PostForm{
        public static final String PREF = EDIT_MODE +"/check";

        public static final String POST_CLASS =            PREF + CLASS;
        public static final String POST_OBJECT =           PREF + OBJECT + "/{classId}";
        public static final String POST_AGGREGATION =      PREF + AGGREGATION;
        public static final String POST_ASSOCIATION =      PREF + ASSOCIATION;
        public static final String POST_ASSOCIATION_IMPL = PREF + ASSOCIATION_IMPL +"/{associationId}";
        public static final String POST_TEMPLATE =         PREF + TEMPLATE + "/{classId}";
        public static final String POST_RESOURCE =         PREF + RESOURCE + "/{classId}";
        public static final String POST_MMEDIA =         PREF + MMEDIA + "/{objectId}"+"/{attributeName}";
    }

    public static class PartForm{
        public static final String PREF = EDIT_MODE +"/part-form";

        public static final String ATTRIBUTE_FORM =           PREF +"/attribute/{length}";
        public static final String OBJECT_FORM =              PREF + OBJECT+"/{classId}";
        public static final String ASSOCIATION_IMPL_FORM =    PREF + ASSOCIATION_IMPL+"/{associationId}";
        public static final String TEMPLATE_FORM =            PREF + TEMPLATE+"/{classId}";
    }

    public static class Delete{
        public static final String PREF = EDIT_MODE +"/delete";

        public static final String DELETE_CLASS =            PREF + CLASS            + "/{id}";
        public static final String DELETE_OBJECT =           PREF + OBJECT           + "/{id}";
        public static final String DELETE_AGGREGATION =      PREF + AGGREGATION      + "/{id}";
        public static final String DELETE_ASSOCIATION =      PREF + ASSOCIATION      + "/{id}";
        public static final String DELETE_ASSOCIATION_IMPL = PREF + ASSOCIATION_IMPL + "/{id}";
        public static final String DELETE_TEMPLATE =         PREF + TEMPLATE         + "/{id}";
        public static final String DELETE_RESOURCE =         PREF + RESOURCE         + "/{id}";
    }

    public static class UpdateForm {
        public static final String PREF = EDIT_MODE +"/update";

        public static final String UPDATE_CLASS =            PREF + CLASS            + "/{id}";
        public static final String UPDATE_OBJECT =           PREF + OBJECT           + "/{id}";
        public static final String UPDATE_AGGREGATION =      PREF + AGGREGATION      + "/{id}";
        public static final String UPDATE_ASSOCIATION =      PREF + ASSOCIATION      + "/{id}";
        public static final String UPDATE_ASSOCIATION_IMPL = PREF + ASSOCIATION_IMPL + "/{id}";
        public static final String UPDATE_TEMPLATE =         PREF + TEMPLATE         + "/{id}";
        public static final String UPDATE_RESOURCE =         PREF + RESOURCE         + "/{id}";
    }

    public static class UpdateCheck {
        public static final String PREF = EDIT_MODE +"/update-check";

        public static final String UPDATE_CHECK_CLASS =            PREF + CLASS            + "/{id}";
        public static final String UPDATE_CHECK_OBJECT =           PREF + OBJECT           + "/{id}";
        public static final String UPDATE_CHECK_AGGREGATION =      PREF + AGGREGATION      + "/{id}";
        public static final String UPDATE_CHECK_ASSOCIATION =      PREF + ASSOCIATION      + "/{id}";
        public static final String UPDATE_CHECK_ASSOCIATION_IMPL = PREF + ASSOCIATION_IMPL + "/{id}";
        public static final String UPDATE_CHECK_TEMPLATE =         PREF + TEMPLATE         + "/{id}";
        public static final String UPDATE_CHECK_RESOURCE =         PREF + RESOURCE         + "/{id}";
        public static final String UPDATE_CHECK_MMEDIA   =         PREF + MMEDIA         + "/{objectId}/{attributeName}";
    }
}
