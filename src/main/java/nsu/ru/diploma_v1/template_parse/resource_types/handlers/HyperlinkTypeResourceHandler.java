package nsu.ru.diploma_v1.template_parse.resource_types.handlers;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.configuration.urls.mode.UserMode;
import nsu.ru.diploma_v1.database.sys.SysResourceService;
import nsu.ru.diploma_v1.exception.EditException;
import nsu.ru.diploma_v1.exception.EntityNotFoundException;
import nsu.ru.diploma_v1.template_parse.resource_types.ResourceTagType;
import nsu.ru.diploma_v1.template_parse.resource_types.ResourceTypeHandler;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

@Component

@RequiredArgsConstructor
public class HyperlinkTypeResourceHandler implements ResourceTypeHandler {

    private final SysResourceService sysResourceService;

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

    @Override
    public void check(Node resource) {
        try {
            NamedNodeMap map = resource.getAttributes();
            Node resourceId = map.getNamedItem("resourceId");
            String id = resourceId.getTextContent();
            int id1 = Integer.parseInt(id);
            sysResourceService.getSysResourcesByResourceId(id1);
        }catch (EntityNotFoundException e){
            throw new EntityNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new EditException("Ошибка ресурса.");
        }
    }

}
