package nsu.ru.diploma_v1.configuration;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import static nsu.ru.diploma_v1.configuration.PathsModeConstants.*;

@NoArgsConstructor
@Component
public class SysUrl {

    public static final String PAGE = "/page";
    public static final String CLASS = "/class";
    public static final String OBJECT = "/object";
    public static final String AGGREGATION = "/aggregation";
    public static final String AGGREGATION_IMPL = "/aggregation-impl";
    public static final String ASSOCIATION = "/association";
    public static final String ASSOCIATION_IMPL = "/association-impl";
    public static final String TEMPLATE = "/template";


    /**
     * view mode
     */
    public static final String GET_INFO_PREF = GET_VIEW_MODE_PREF+"/info";
        public static final String GET_INFO_PAGE = GET_INFO_PREF+PAGE;
        public static final String GET_INFO_CLASS = GET_INFO_PREF+CLASS;
        public static final String GET_INFO_OBJECT = GET_INFO_PREF+OBJECT;
        public static final String GET_INFO_AGGREGATION = GET_INFO_PREF+AGGREGATION;
        public static final String GET_INFO_AGGREGATION_IMPL = GET_INFO_PREF+AGGREGATION_IMPL;
        public static final String GET_INFO_ASSOCIATION = GET_INFO_PREF+ASSOCIATION;
        public static final String GET_INFO_ASSOCIATION_IMPL = GET_INFO_PREF+ASSOCIATION_IMPL;
        public static final String GET_INFO_TEMPLATE = GET_INFO_PREF+TEMPLATE;

    public static final String GET_DETAIL_INFO_PREF = GET_VIEW_MODE_PREF+"/detail-info";
        public static final String GET_DETAIL_INFO_PAGE = GET_DETAIL_INFO_PREF+PAGE+"/{id}";
        public static final String GET_DETAIL_INFO_CLASS = GET_DETAIL_INFO_PREF+CLASS+"/{id}";
        public static final String GET_DETAIL_INFO_OBJECT = GET_DETAIL_INFO_PREF+OBJECT+"/{id}";
        public static final String GET_DETAIL_INFO_AGGREGATION = GET_DETAIL_INFO_PREF+AGGREGATION+"/{id}";
        public static final String GET_DETAIL_INFO_AGGREGATION_IMPL = GET_DETAIL_INFO_PREF+AGGREGATION_IMPL+"/{id}";
        public static final String GET_DETAIL_INFO_ASSOCIATION = GET_DETAIL_INFO_PREF+ASSOCIATION+"/{id}";
        public static final String GET_DETAIL_INFO_ASSOCIATION_IMPL = GET_DETAIL_INFO_PREF+ASSOCIATION_IMPL+"/{id}";
        public static final String GET_DETAIL_INFO_TEMPLATE = GET_DETAIL_INFO_PREF+TEMPLATE+"/{id}";

    /**
     * edit mode
     */
    public static final String GET_NEW_PREF = GET_EDIT_MODE_PREF+"/new";
        public static final String GET_NEW_PAGE = GET_NEW_PREF+PAGE;
        public static final String GET_NEW_CLASS = GET_NEW_PREF+CLASS;
        public static final String GET_NEW_OBJECT = GET_NEW_PREF+OBJECT;
    public static final String GET_NEW_OBJECT_BY_CLASS = GET_NEW_PREF+OBJECT+"/{classId}";
        public static final String GET_NEW_AGGREGATION = GET_NEW_PREF+AGGREGATION;
        public static final String GET_NEW_AGGREGATION_IMPL = GET_NEW_PREF+AGGREGATION_IMPL;
        public static final String GET_NEW_ASSOCIATION = GET_NEW_PREF+ASSOCIATION;
        public static final String GET_NEW_ASSOCIATION_IMPL = GET_NEW_PREF+ASSOCIATION_IMPL;
        public static final String GET_NEW_TEMPLATE = GET_NEW_PREF+TEMPLATE;

    public static final String POST_NEW_PREF = GET_EDIT_MODE_PREF+"/check";
    public static final String POST_NEW_PAGE = POST_NEW_PREF+PAGE;
    public static final String POST_NEW_CLASS = POST_NEW_PREF+CLASS;
    public static final String POST_NEW_OBJECT = POST_NEW_PREF+OBJECT+"/{classId}";
    public static final String POST_NEW_AGGREGATION = POST_NEW_PREF+AGGREGATION;
    public static final String POST_NEW_AGGREGATION_IMPL = POST_NEW_PREF+AGGREGATION_IMPL;
    public static final String POST_NEW_ASSOCIATION = POST_NEW_PREF+ASSOCIATION;
    public static final String POST_NEW_ASSOCIATION_IMPL = POST_NEW_PREF+ASSOCIATION_IMPL;
    public static final String POST_NEW_TEMPLATE = POST_NEW_PREF+TEMPLATE;

    public static final String GET_UTIL_PREF = GET_EDIT_MODE_PREF+"/util";
    public static final String GET_UTIL_ATTRIBUTE_FORM = GET_UTIL_PREF+"/attribute/{length}";

    /**
     * user mode
     */
    public static final String GET_SHOW_PREF = GET_USER_MODE_PREF;
        public static final String GET_SHOW_OBJECT = GET_SHOW_PREF+"/{object_id}/{id}";


}
