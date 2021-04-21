package nsu.ru.diploma_v1.template_parse.resource_types;

import nsu.ru.diploma_v1.exception.EntityNotFoundException;
import org.w3c.dom.Node;

public interface ResourceTypeHandler {

    ResourceTagType getType();

    /**
     * Получаем ресурс
     * Возвращаем обновленного родителя
     * @return
     */
    Node toNewParentNode(Node resource);

    void check(Node resource) throws EntityNotFoundException;
}
