package nsu.ru.diploma_v1.model.entity;

import lombok.Data;
import nsu.ru.diploma_v1.model.enums.database_types.SysTypes;

@Data
public class AttributeAndValue {

    private final String name;

    private final String value;

    private final SysTypes type;

    private final Integer attributeId;
}
