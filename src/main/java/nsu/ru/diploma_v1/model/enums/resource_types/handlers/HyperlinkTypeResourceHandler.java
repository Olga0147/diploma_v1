package nsu.ru.diploma_v1.model.enums.resource_types.handlers;

import nsu.ru.diploma_v1.configuration.urls.mode.UserMode;
import nsu.ru.diploma_v1.model.enums.resource_types.ResourceTagType;
import nsu.ru.diploma_v1.model.enums.resource_types.ResourceTypeHandler;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

@Component
public class HyperlinkTypeResourceHandler implements ResourceTypeHandler {

    //  <resource resourceId="2" type="hyperlink"></resource>

    //href=""
    //src=""

    @Override
    public ResourceTagType getType() {
        return ResourceTagType.HYPERLINK;
    }

    @Override
    public Node toNewParentNode(Node resource){
        NamedNodeMap map = resource.getAttributes();
        Node resourceId = map.getNamedItem("resourceId");
        String id = resourceId.getTextContent();

        String str = UserMode.GetFile.GET_RESOURCE.replace("{id}",id);

        Element parent = (Element) resource.getParentNode();
        parent.removeChild(resource);

        if( parent.hasAttribute("href")){
            parent.setAttribute("href",str);
        }else if(parent.hasAttribute("src")){
            parent.setAttribute("src",str);
        }

        return parent;
    }

}
