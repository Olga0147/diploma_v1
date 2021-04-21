package nsu.ru.diploma_v1.template_parse.associations;

import org.w3c.dom.NamedNodeMap;

public interface AssociationTypeHandler {

    AssociationTypes getType();

    String handle(int objectId, NamedNodeMap attributes, String innerText);

    void check(NamedNodeMap attributes, String innerText);
}
