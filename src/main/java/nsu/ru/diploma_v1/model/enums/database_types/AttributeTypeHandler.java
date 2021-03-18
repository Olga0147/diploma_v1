package nsu.ru.diploma_v1.model.enums.database_types;

import nsu.ru.diploma_v1.exception.ValidationException;
import nsu.ru.diploma_v1.model.entity.SysAttribute;
import nsu.ru.diploma_v1.model.enums.database_types.SysTypes;

public interface AttributeTypeHandler {

    SysTypes getType();

    /**
     * Возвращает Объект (Integer,String...) для БД
     */
    Object handle(SysAttribute attribute, Object value) throws ValidationException;

    /**
     * Получаем объект как строку :
     * число в строку
     * картинка\файл в html запрос в зависимости от типа
     * строка в строку
     * @param object объект
     * @return
     */
    String toString(Object object);
}
