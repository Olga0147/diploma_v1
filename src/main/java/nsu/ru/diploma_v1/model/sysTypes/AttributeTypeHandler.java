package nsu.ru.diploma_v1.model.sysTypes;

import nsu.ru.diploma_v1.exception.ValidationException;
import nsu.ru.diploma_v1.model.entity.SysAttribute;
import nsu.ru.diploma_v1.model.enums.SysTypes;

public interface AttributeTypeHandler {

    public SysTypes getType();

    public Object handle(SysAttribute attribute, Object value) throws ValidationException;
}
