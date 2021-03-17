package nsu.ru.diploma_v1.configuration.urls;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class EntityPath {

    public static final String PAGE = "/page";
    public static final String CLASS = "/class";
    public static final String OBJECT = "/object";
    public static final String AGGREGATION = "/aggregation";
    public static final String AGGREGATION_IMPL = "/aggregation-impl";
    public static final String ASSOCIATION = "/association";
    public static final String ASSOCIATION_IMPL = "/association-impl";
    public static final String TEMPLATE = "/template";
}
