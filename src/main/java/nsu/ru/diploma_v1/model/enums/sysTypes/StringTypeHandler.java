package nsu.ru.diploma_v1.model.enums.sysTypes;

import nsu.ru.diploma_v1.exception.ValidationException;
import nsu.ru.diploma_v1.model.entity.SysAttribute;
import org.springframework.stereotype.Component;

@Component
public class StringTypeHandler implements AttributeTypeHandler {

    @Override
    public SysTypes getType() {
        return SysTypes.STRING;
    }

    @Override
    public Object handle(SysAttribute attribute, Object value) {
        String str = (String) value;
        if(str.length()>attribute.getAttributeSize()){
            throw new ValidationException(String.format("Длина строки %s превышает допустимое значение %d",str,attribute.getAttributeSize()));
        }
        return (String) value;
    }
}
