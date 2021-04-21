package nsu.ru.diploma_v1.template_parse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.ru.diploma_v1.exception.EntityNotFoundException;
import nsu.ru.diploma_v1.exception.TemplateException;
import nsu.ru.diploma_v1.model.entity.*;
import nsu.ru.diploma_v1.template_parse.associations.AssociationTypeHandler;
import nsu.ru.diploma_v1.template_parse.associations.AssociationTypes;
import nsu.ru.diploma_v1.model.enums.database_types.SysTypes;
import nsu.ru.diploma_v1.template_parse.aggregations.AggregationTypeHandler;
import nsu.ru.diploma_v1.template_parse.aggregations.AggregationTypes;
import nsu.ru.diploma_v1.template_parse.resource_types.ResourceTagType;
import nsu.ru.diploma_v1.template_parse.resource_types.ResourceTypeHandler;
import nsu.ru.diploma_v1.database.sys.SysAggregationService;
import nsu.ru.diploma_v1.database.sys.SysTemplateService;
import nsu.ru.diploma_v1.database.custom.CustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateService {

    private final String ROOT_1 = "<root>";
    private final String ROOT_2 = "</root>";

    private final CustomService customService;
    private final SysTemplateService sysTemplateService;
    private final SysAggregationService sysAggregationService;

    private final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    private final Map<AggregationTypes, AggregationTypeHandler> aggregationHandlerMap = new HashMap<>();
    @Autowired
    public void setAggregationTypes(List<AggregationTypeHandler> types) {
        types.forEach(type -> aggregationHandlerMap.put(type.getType(), type));
    }

    private final Map<AssociationTypes, AssociationTypeHandler> associationHandlerMap = new HashMap<>();
    @Autowired
    public void setAssociationTypes(List<AssociationTypeHandler> types) {
        types.forEach(type -> associationHandlerMap.put(type.getType(), type));
    }

    private final Map<ResourceTagType, ResourceTypeHandler> resourceHandlerMap = new HashMap<>();
    @Autowired
    public void setResourceTypes(List<ResourceTypeHandler> types) {
        types.forEach(type -> resourceHandlerMap.put(type.getType(), type));
    }

    @PostConstruct
    void setLink(){
        customService.setTemplateService(this);
    }

    //MAIN METHOD
    public String getObjectInTemplate(Integer objectId, Integer templateId) throws EntityNotFoundException, TemplateException {
        Map<String, AttributeAndValue> object = customService.getObjectForTemplate(objectId,templateId);// throws EntityNotFoundException
        SysTemplate template = sysTemplateService.getSysTemplate(templateId);//// throws EntityNotFoundException
        return parseTemplate(object,template.getBody(),objectId);// throws EntityNotFoundException, TemplateException
    }

    public void checkTemplate(SysTemplate sysTemplate,SysClass sysClass){
        parseTemplate(sysTemplate.getBody(),sysClass);
    }

    private String parseTemplate(Map<String, AttributeAndValue> object, String template,Integer objectId) throws EntityNotFoundException, TemplateException{
        Document doc = parseXML(template);// throws TemplateException

        //1) вставляем метки <field>fieldName</field>
        NodeList fields = doc.getElementsByTagName("field");
        List<NodesToReplace> nodesToReplaceList = new LinkedList<>();
        for (int i = 0; i < fields.getLength(); i++) {
            Node field = fields.item(i);
            String name = field.getTextContent();
            String value = object.get(name).getValue();
            Integer attributeId = object.get(name).getAttributeId();
            Node newNode = (object.get(name).getType() == SysTypes.XMEMO ||
                            object.get(name).getType() == SysTypes.MMEDIA) ?
                    parseXMemo(value, attributeId, objectId) : doc.createTextNode(value);//throws EntityNotFoundException,TemplateException

            NodesToReplace nodesToReplace = new NodesToReplace(field.getParentNode(), newNode, field);
            nodesToReplaceList.add(nodesToReplace);
        }

        //2)вставляем метки <association></association>
        NodeList associations = doc.getElementsByTagName("association");
        for (int i = 0; i < associations.getLength(); i++) {
            Node association = associations.item(i);
            NamedNodeMap map = association.getAttributes();

            Node type = map.getNamedItem("type");
            AssociationTypes associationType = AssociationTypes.valueOf(type.getTextContent().toUpperCase(Locale.ROOT));
            AssociationTypeHandler handler = associationHandlerMap.get(associationType);

            String value = handler.handle(objectId,map,association.getTextContent());// throws EntityNotFoundException,TemplateException
            Document docAssociation = parseXML(value);// throws TemplateException

            NodeList nodeList = docAssociation.getChildNodes();
            DocumentFragment documentFragment = docAssociation.createDocumentFragment();
            for (int j = 0; j < nodeList.getLength(); j++) {
                Node n = nodeList.item(j);
                documentFragment.appendChild(n);
            }

            NodesToReplace nodesToReplace = new NodesToReplace(association.getParentNode(), documentFragment, association);
            nodesToReplaceList.add(nodesToReplace);
        }

        //2)вставляем метки <resource></resource>
        NodeList resources = doc.getElementsByTagName("resource");
        for (int i = resources.getLength()-1; i >=0; i--) {

            Node resource = resources.item(i);
            String res = toString(resource);
            //Node oldParent = resource.getParentNode();
            //Node grandPa = oldParent.getParentNode();

            NamedNodeMap map = resource.getAttributes();

            Node type = map.getNamedItem("type");
            ResourceTagType resourceType = ResourceTagType.valueOf(type.getTextContent().toUpperCase(Locale.ROOT));
            ResourceTypeHandler handler = resourceHandlerMap.get(resourceType);
            Node newParent = handler.toNewParentNode(resource);

            resources = doc.getElementsByTagName("resource");

           // NodesToReplace nodesToReplace = new NodesToReplace(grandPa, newParent, oldParent);
            //nodesToReplaceList.add(nodesToReplace);
        }

        //обработка подготовленных данных
        for (NodesToReplace nodesToReplace : nodesToReplaceList) {
            Node firstDocImportedNode = doc.importNode(nodesToReplace.newNode, true);
            nodesToReplace.parent.replaceChild(firstDocImportedNode,nodesToReplace.oldNode);
        }
        String result = clearXMLMeta(toString(doc));
        return result;
    }

    private void parseTemplate(String template, SysClass sysClass) throws EntityNotFoundException, TemplateException{
        Document doc = parseXML(template);// throws TemplateException

        List<SysAttribute> list = sysClass.getAttributeList();
        List<String> names = new LinkedList<>();
        list.forEach(e -> names.add(e.getName()));

        //1) вставляем метки <field>fieldName</field>
        NodeList fields = doc.getElementsByTagName("field");
        List<NodesToReplace> nodesToReplaceList = new LinkedList<>();
        for (int i = 0; i < fields.getLength(); i++) {
            Node field = fields.item(i);
            String name = field.getTextContent();
            if(!names.contains(name)){
                throw new TemplateException(String.format("Поля %s не существует",name));
            }
        }

        //2)вставляем метки <association></association>
        NodeList associations = doc.getElementsByTagName("association");
        for (int i = 0; i < associations.getLength(); i++) {
            Node association = associations.item(i);
            NamedNodeMap map = association.getAttributes();
            Node type = map.getNamedItem("type");
            AssociationTypes associationType = AssociationTypes.valueOf(type.getTextContent().toUpperCase(Locale.ROOT));
            AssociationTypeHandler handler = associationHandlerMap.get(associationType);
            handler.check(map,association.getTextContent());// throws EntityNotFoundException,TemplateException
        }

        //2)вставляем метки <resource></resource>
        NodeList resources = doc.getElementsByTagName("resource");
        for (int i = resources.getLength()-1; i >=0; i--) {
            Node resource = resources.item(i);
            NamedNodeMap map = resource.getAttributes();
            Node type = map.getNamedItem("type");
            ResourceTagType resourceType = ResourceTagType.valueOf(type.getTextContent().toUpperCase(Locale.ROOT));
            ResourceTypeHandler handler = resourceHandlerMap.get(resourceType);
            handler.check(resource);//EntityNotFoundException
        }
    }


    private String toString(Node node) {
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();

            transformer.transform(new DOMSource(node), new StreamResult(sw));
            return clearXMLMeta(sw.toString());
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
    }

    //так как тут новая нода - это root, то далее задействован костыль по очистке от root
    private Node parseXMemo(String XMemoValue, Integer attributeId, Integer objectId)throws EntityNotFoundException,TemplateException {
        Document doc = parseXML(XMemoValue);//throws TemplateException

        NodeList aggregations = doc.getElementsByTagName("aggregation");
        List<NodesToReplace> nodesToReplaceList = new LinkedList<>();

        for (int i = 0; i < aggregations.getLength(); i++) {

            Node aggregation = aggregations.item(i);
            NamedNodeMap map = aggregation.getAttributes();

            Node type = map.getNamedItem("type");
            AggregationTypes aggregationType = AggregationTypes.valueOf(type.getTextContent().toUpperCase(Locale.ROOT));

            AggregationTypeHandler handler = aggregationHandlerMap.get(aggregationType);
            String value = handler.handle(map,aggregation.getTextContent(),attributeId,objectId);// throws EntityNotFoundException,TemplateException

            Document docXMemo = parseXML(value);//throws TemplateException
            Node newNode = docXMemo.getFirstChild();

            NodeList nodeList = newNode.getChildNodes();
            DocumentFragment documentFragment = docXMemo.createDocumentFragment();

            for (int j = 0; j < nodeList.getLength(); j++) {
                Node n = nodeList.item(j);
                documentFragment.appendChild(n);
            }

            NodesToReplace nodesToReplace = new NodesToReplace(aggregation.getParentNode(), documentFragment, aggregation);
            nodesToReplaceList.add(nodesToReplace);
        }

        for (NodesToReplace nodesToReplace : nodesToReplaceList) {
            Node firstDocImportedNode = doc.importNode(nodesToReplace.newNode, true);
            nodesToReplace.parent.replaceChild(firstDocImportedNode,nodesToReplace.oldNode);
        }

        Node node = doc.getFirstChild();
        return node;
    }

    public void parseXMemoToSaveObject(String XMemoValue, Integer attributeId, Integer objectId) throws TemplateException {


        Map<Integer, SysAggregationImpl> aggregationMap = sysAggregationService.getXMemoAggregationList(attributeId,objectId);//can be null

        Document doc = parseXML(XMemoValue);//throws TemplateException
        NodeList aggregations = doc.getElementsByTagName("aggregation");

        for (int i = 0; i < aggregations.getLength(); i++) {

            Node aggregation = aggregations.item(i);
            NamedNodeMap map = aggregation.getAttributes();

            Node type = map.getNamedItem("type");
            AggregationTypes aggregationType;
            try {
                aggregationType = AggregationTypes.valueOf(type.getTextContent().toUpperCase(Locale.ROOT));
            }catch (Exception e){
                log.info(String.format("Типа %s не существует",type.getTextContent().toUpperCase(Locale.ROOT)));
                throw new TemplateException(String.format("Типа %s не существует",type.getTextContent().toUpperCase(Locale.ROOT)));
            }
            AggregationTypeHandler handler = aggregationHandlerMap.get(aggregationType);

            Integer check = handler.checkExist(map,attributeId,objectId);//throws TemplateException
            if(check != null){
                aggregationMap.remove(check);
            }
        }
        sysAggregationService.deleteAll(aggregationMap);
    }



    private Document parseXML(String text) throws TemplateException{
        String root = ROOT_1 + text + ROOT_2;
        InputStream is = new ByteArrayInputStream( root.getBytes() );

        DocumentBuilder builder;
        Document doc;

        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            log.info(String.format("При построение документа из xml произошла ошибка : %s.\nДокумент : %s",e.getMessage(), root));
            throw new TemplateException( String.format("При построение документа из xml произошла ошибка : %s.\nДокумент : %s",e.getMessage(), root) );
        }
        doc.getDocumentElement().normalize();
        return doc;
    }

    static class NodesToReplace {
        public Node parent;
        public Node newNode;
        public Node oldNode;

        public NodesToReplace(Node parent, Node newNode, Node oldNode) {
            this.parent = parent;
            this.newNode = newNode;
            this.oldNode = oldNode;
        }
    }

    public String clearXMLMeta(String s){
        String XML_REGEX = "<\\?xml[^>]*\\?>";
        return s.
                replace(ROOT_1,"").
                replace(ROOT_2,"").
                replaceAll(XML_REGEX,"");
    }

    public String getObjectFields(String[] fields,int objectId,Integer templateId) throws EntityNotFoundException{
        Map<String, AttributeAndValue> object = customService.getObjectForTemplate(objectId,templateId);// throws EntityNotFoundException
        StringBuilder result = new StringBuilder();

        for (String field : fields) {
            AttributeAndValue value = object.get(field);
            if(value.getType()!= SysTypes.XMEMO && value.getType()!= SysTypes.MMEDIA){
                result.append(" ").append(value.getValue());
            }
        }
        return result.toString();
    }

}
