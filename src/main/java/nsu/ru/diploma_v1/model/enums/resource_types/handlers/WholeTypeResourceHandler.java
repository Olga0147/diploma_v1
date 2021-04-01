package nsu.ru.diploma_v1.model.enums.resource_types.handlers;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.enums.resource_types.ResourceTagType;
import nsu.ru.diploma_v1.model.enums.resource_types.ResourceTypeHandler;
import nsu.ru.diploma_v1.service.database.SysResourceService;
import org.springframework.stereotype.Component;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Вставить ресурс по стандартным тегам
 */
@Component
@RequiredArgsConstructor
public class WholeTypeResourceHandler implements ResourceTypeHandler {

    //  <resource resourceId="2" type="whole"></resource>

    private final SysResourceService sysResourceService;

    @Override
    public ResourceTagType getType() {
        return ResourceTagType.WHOLE;
    }

    @Override
    public Node toNewParentNode(Node resource){
        NamedNodeMap map = resource.getAttributes();
        Node resourceId = map.getNamedItem("resourceId");
        String id = resourceId.getTextContent();

        Node href =  sysResourceService.getTag(id, resource.getOwnerDocument());

        Node parent = resource.getParentNode();
        parent.replaceChild(href, resource);
        return parent;
    }

}
