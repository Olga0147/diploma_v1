package nsu.ru.diploma_v1.service.system;

import nsu.ru.diploma_v1.exception.TemplateException;
import nsu.ru.diploma_v1.model.entity.AttributeAndValue;
import nsu.ru.diploma_v1.model.entity.SysObject;
import nsu.ru.diploma_v1.model.enums.database_types.AttributeTypeHandler;
import nsu.ru.diploma_v1.model.enums.database_types.SysTypes;
import nsu.ru.diploma_v1.model.enums.relation.AggregationTypeHandler;
import nsu.ru.diploma_v1.model.enums.relation.AggregationTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
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
public class TemplateService {

    private final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private final Map<AggregationTypes, AggregationTypeHandler> attributeTypeMap = new HashMap<>();

    @Autowired
    public void setAttributeTypes(List<AggregationTypeHandler> types) {
        types.forEach(type -> attributeTypeMap.put(type.getType(), type));
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
                Document docXMEMO = parseXML(value);
                newNode = docXMEMO.getFirstChild();
            }
            else{
                newNode = doc.createTextNode(value);
            }

            NodesToReplace nodesToReplace = new NodesToReplace(field.getParentNode(),newNode,field);
            nodesToReplaceList.add(nodesToReplace);
        }

        for (NodesToReplace nodesToReplace : nodesToReplaceList) {
            Node firstDocImportedNode = doc.importNode(nodesToReplace.newNode, true);
            nodesToReplace.parent.replaceChild(firstDocImportedNode,nodesToReplace.oldNode);
        }

        return toString(doc);
    }


    public static String toString(Document doc) {
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
    }

    public static String toString(Node node) {
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();

            transformer.transform(new DOMSource(node), new StreamResult(sw));
            return sw.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
    }

    public String parseXmemo(SysObject sysObject, String xmemoValue) {

        Document doc = parseXML(xmemoValue);

        NodeList aggregations = doc.getElementsByTagName("aggregation");
        List<NodesToReplace> nodesToReplaceList = new LinkedList<>();


        for (int i = 0; i < aggregations.getLength(); i++) {

            Node aggregation = aggregations.item(i);
            NamedNodeMap map = aggregation.getAttributes();

            Node type = map.getNamedItem("type");
            AggregationTypes aggregationType = AggregationTypes.valueOf(type.getTextContent().toUpperCase(Locale.ROOT));

            AggregationTypeHandler handler = attributeTypeMap.get(aggregationType);
            String value = handler.handle(sysObject,map,aggregation.getTextContent());

            Document docXMEMO = parseXML(value);
            Node newNode = docXMEMO.getFirstChild();// с root
            Node newNode1 = newNode.getFirstChild();//без root
            NodeList newNode2 = docXMEMO.getChildNodes();//возможно поможет

            NodesToReplace nodesToReplace = new NodesToReplace(aggregation.getParentNode(),newNode1,aggregation);
            nodesToReplaceList.add(nodesToReplace);
        }

        for (NodesToReplace nodesToReplace : nodesToReplaceList) {
            Node firstDocImportedNode = doc.importNode(nodesToReplace.newNode, true);
            nodesToReplace.parent.replaceChild(firstDocImportedNode,nodesToReplace.oldNode);
        }

        Node node = doc.getFirstChild();
        String s = toString(node);
        String withoutXMLInfo = s.split("<root>")[1].split("</root>")[0];


        return withoutXMLInfo;
    }


    private Document parseXML(String text){
        String root = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root>" + text + "</root>";
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


    class NodesToReplace {
        public Node parent;
        public Node newNode;
        public Node oldNode;

        public NodesToReplace(Node parent, Node newNode, Node oldNode) {
            this.parent = parent;
            this.newNode = newNode;
            this.oldNode = oldNode;
        }
    }
}
