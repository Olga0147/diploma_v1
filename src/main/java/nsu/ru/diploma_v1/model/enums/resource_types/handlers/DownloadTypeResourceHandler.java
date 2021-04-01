package nsu.ru.diploma_v1.model.enums.resource_types.handlers;

import nsu.ru.diploma_v1.configuration.urls.mode.UserMode;
import nsu.ru.diploma_v1.model.enums.resource_types.ResourceTagType;
import nsu.ru.diploma_v1.model.enums.resource_types.ResourceTypeHandler;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Вставить ресурс для скачивания
 */
@Component
public class DownloadTypeResourceHandler implements ResourceTypeHandler {

    //  <resource resourceId="2" type="download"></resource>

    @Override
    public ResourceTagType getType() {
        return ResourceTagType.DOWNLOAD;
    }

    @Override
    public Node toNewParentNode(Node resource){
        NamedNodeMap map = resource.getAttributes();
        Node resourceId = map.getNamedItem("resourceId");
        String id = resourceId.getTextContent();

        Document doc = resource.getOwnerDocument();
        Element href = doc.createElement("a");
        href.setAttribute("href", UserMode.GetFile.GET_RESOURCE.replace("{id}",id));
        href.setAttribute("download","");
        href.setTextContent(id);

        Node parent = resource.getParentNode();
        parent.replaceChild(href, resource);
        return parent;
    }

}
