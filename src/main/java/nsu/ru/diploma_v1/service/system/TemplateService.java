package nsu.ru.diploma_v1.service.system;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.exception.TemplateException;
import nsu.ru.diploma_v1.model.entity.AttributeAndValue;
import nsu.ru.diploma_v1.model.entity.SysObject;
import nsu.ru.diploma_v1.model.entity.SysTemplate;
import nsu.ru.diploma_v1.model.enums.database_types.SysTypes;
import nsu.ru.diploma_v1.model.enums.relation.AggregationTypeHandler;
import nsu.ru.diploma_v1.model.enums.relation.AggregationTypes;
import nsu.ru.diploma_v1.service.database.SysTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

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

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final String ROOT_1 = "<root>";
    private final String ROOT_2 = "</root>";

    private final CustomService customService;
    private final SysTemplateService sysTemplateService;

    private final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private final Map<AggregationTypes, AggregationTypeHandler> attributeTypeMap = new HashMap<>();

    @Autowired
    public void setAttributeTypes(List<AggregationTypeHandler> types) {
        types.forEach(type -> attributeTypeMap.put(type.getType(), type));
    }

    public String getObjectInTemplate(Integer objectId, Integer templateId){

        Map<String, AttributeAndValue> object = customService.getObjectForTemplate(objectId);
        SysTemplate template = sysTemplateService.getSysTemplate(templateId);

        return parseTemplate(object,template.getBody());
    }


    public String parseTemplate(Map<String, AttributeAndValue> object, String template){

        Document doc = parseXML(template);

        NodeList fields = doc.getElementsByTagName("field");
        List<NodesToReplace> nodesToReplaceList = new LinkedList<>();

        for (int i = 0; i < fields.getLength(); i++) {

            Node field = fields.item(i);
            String name = field.getTextContent();
            String value = object.get(name).getValue();

            Node newNode;

            if(object.get(name).getType() == SysTypes.XMEMO){

                String parsedValue = parseXMemo(null,value);
                Document docXMemo = parseXML(parsedValue);

                NodeList nodeList = docXMemo.getChildNodes();
                DocumentFragment documentFragment = docXMemo.createDocumentFragment();

                for (int j = 0; j < nodeList.getLength(); j++) {
                    Node n = nodeList.item(j);
                    documentFragment.appendChild(n);
                }
                newNode = documentFragment;
            }
            else{
                newNode = doc.createTextNode(value);
            }

            NodesToReplace nodesToReplace = new NodesToReplace(field.getParentNode(), newNode, field);
            nodesToReplaceList.add(nodesToReplace);
        }

        for (NodesToReplace nodesToReplace : nodesToReplaceList) {
            Node firstDocImportedNode = doc.importNode(nodesToReplace.newNode, true);
            nodesToReplace.parent.replaceChild(firstDocImportedNode,nodesToReplace.oldNode);
        }

        String result = clearXMLMeta(toString(doc));
        return result;
    }

    public String toString(Node node) {
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

    //результат : без root
    public String parseXMemo(SysObject sysObject, String XMemoValue) {

        Document doc = parseXML(XMemoValue);

        NodeList aggregations = doc.getElementsByTagName("aggregation");
        List<NodesToReplace> nodesToReplaceList = new LinkedList<>();

        for (int i = 0; i < aggregations.getLength(); i++) {

            Node aggregation = aggregations.item(i);
            NamedNodeMap map = aggregation.getAttributes();

            Node type = map.getNamedItem("type");
            AggregationTypes aggregationType = AggregationTypes.valueOf(type.getTextContent().toUpperCase(Locale.ROOT));

            AggregationTypeHandler handler = attributeTypeMap.get(aggregationType);
            String value = handler.handle(sysObject,map,aggregation.getTextContent());

            Document docXMemo = parseXML(value);
            Node newNode = docXMemo.getFirstChild();// с root

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
        String withoutXMLInfo = clearXMLMeta(toString(node));
        return withoutXMLInfo;
    }


    private Document parseXML(String text){
        String root = ROOT_1 + text + ROOT_2;
        InputStream is = new ByteArrayInputStream( root.getBytes() );

        DocumentBuilder builder;
        Document doc;

        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            //TODO: logger or msg to user
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

}
