package nsu.ru.diploma_v1.template_parse.aggregations.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.ru.diploma_v1.configuration.urls.ModePath;
import nsu.ru.diploma_v1.exception.EntityNotFoundException;
import nsu.ru.diploma_v1.exception.TemplateException;
import nsu.ru.diploma_v1.model.entity.SysAggregationImpl;
import nsu.ru.diploma_v1.template_parse.aggregations.AggregationTypeHandler;
import nsu.ru.diploma_v1.template_parse.aggregations.AggregationTypes;
import nsu.ru.diploma_v1.database.sys.SysAggregationService;
import org.springframework.stereotype.Component;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

@Slf4j
@Component
@RequiredArgsConstructor
public class HyperlinkTypeAggregationHandler implements AggregationTypeHandler {

    private final SysAggregationService sysAggregationService;

    //<div>She reads: <aggregation aggregationId="1" templateId="2" objectId="3" type="hyperlink">Book</aggregation></div>

    //<div>He reads: <aggregation aggregationId="1" templateId="2" objectId="4" type="hyperlink">Bad Book</aggregation></div>

    @Override
    public AggregationTypes getType(){
        return AggregationTypes.HYPERLINK;
    }

    @Override
    public String handle(NamedNodeMap attributes, String innerText, Integer attributeId, Integer fromObjectId)throws TemplateException{

        Node template = attributes.getNamedItem("templateId");
        Node object = attributes.getNamedItem("objectId");

        if(attributes.getNamedItem("aggregationId") == null || template == null || object == null){
            log.info("Агрегация не верна");
            throw new TemplateException("Агрегация не верна");
        }

        Integer toTemplateId = Integer.parseInt(template.getTextContent());
        Integer toObjectId = Integer.parseInt(object.getTextContent());

        return String.format("<a href=\"%s/%d/%d\"> %s </a>",ModePath.USER_MODE,toObjectId,toTemplateId,innerText);
    }

    @Override
    public Integer checkExist(NamedNodeMap attributes, Integer attributeId, Integer fromObjectId)throws EntityNotFoundException {
        SysAggregationImpl sysAggregation = getAggregation(attributes, attributeId, fromObjectId);
        boolean check = sysAggregationService.checkExist(sysAggregation);//EntityNotFoundException
        if(!check){
            sysAggregationService.saveSysAggregationImpl(sysAggregation);
        }
        return check ? sysAggregation.getId() : null;
    }

    private SysAggregationImpl getAggregation(NamedNodeMap attributes, Integer attributeId, Integer fromObjectId){
        Node aggregation = attributes.getNamedItem("aggregationId");
        Node template = attributes.getNamedItem("templateId");
        Node object = attributes.getNamedItem("objectId");

        if(aggregation == null || template == null || object == null){
            log.info("Агрегация не верна");
            throw new TemplateException("Агрегация не верна");
        }

        if(aggregation.getTextContent() == null || template.getTextContent() == null || object.getTextContent() == null){
            log.info("Агрегация не верна");
            throw new TemplateException("Агрегация не верна");
        }
        int aggregationId,toTemplateId,toObjectId;
        try {
            aggregationId = Integer.parseInt(aggregation.getTextContent());
            toTemplateId = Integer.parseInt(template.getTextContent());
            toObjectId = Integer.parseInt(object.getTextContent());
        }catch (Exception e){
            log.info("Агрегация не верна");
            throw new TemplateException("Агрегация не верна");
        }

        return new SysAggregationImpl(
                aggregationId,
                fromObjectId,
                toObjectId,
                toTemplateId,
                attributeId,
                AggregationTypes.HYPERLINK);
    }
}
