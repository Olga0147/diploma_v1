package nsu.ru.diploma_v1.model.enums.resource_types;

import org.w3c.dom.Node;

public interface ResourceTypeHandler {

    ResourceTagType getType();

    /**
     * Получаем ресурс
     * Возвращаем обновленного родителя
     * @return
     */
    Node toNewParentNode(Node resource);
}
