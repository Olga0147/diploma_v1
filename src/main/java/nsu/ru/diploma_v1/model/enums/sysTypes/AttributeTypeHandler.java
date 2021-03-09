package nsu.ru.diploma_v1.model.enums.sysTypes;

import nsu.ru.diploma_v1.exception.ValidationException;
import nsu.ru.diploma_v1.model.entity.SysAttribute;

public interface AttributeTypeHandler {

    public SysTypes getType();

    public Object handle(SysAttribute attribute, Object value) throws ValidationException;
}
