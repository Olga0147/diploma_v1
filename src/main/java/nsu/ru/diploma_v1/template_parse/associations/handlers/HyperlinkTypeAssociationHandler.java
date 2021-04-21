package nsu.ru.diploma_v1.template_parse.associations.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.ru.diploma_v1.configuration.urls.ModePath;
import nsu.ru.diploma_v1.database.sys.SysObjectService;
import nsu.ru.diploma_v1.exception.EntityNotFoundException;
import nsu.ru.diploma_v1.exception.TemplateException;
import nsu.ru.diploma_v1.model.entity.SysAssociationImpl;
import nsu.ru.diploma_v1.model.entity.SysClass;
import nsu.ru.diploma_v1.model.entity.SysObject;
import nsu.ru.diploma_v1.template_parse.TemplateService;
import nsu.ru.diploma_v1.template_parse.associations.AssociationTypeHandler;
import nsu.ru.diploma_v1.template_parse.associations.AssociationTypes;
import org.springframework.stereotype.Component;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class HyperlinkTypeAssociationHandler implements AssociationTypeHandler {

    private final TemplateService templateService;
    private final SysObjectService sysObjectService;

    //<div><association associationId="2" templateId="5" type="hyperlink">TITLE AUTHOR</association></div>

    @Override
    public AssociationTypes getType(){
        return AssociationTypes.HYPERLINK;
    }

    @Override
    public String handle(int objectId, NamedNodeMap attributes, String innerText) throws EntityNotFoundException, TemplateException {

    //<association associationId="1" templateId="2" delimiter="<br>" type="hyperlink">TITLE AUTHOR</association>

        Node association = attributes.getNamedItem("associationId");
        Node template = attributes.getNamedItem("templateId");
        Node delimiter = attributes.getNamedItem("delimiter");
        String[] fields = innerText.split(" ");

        if(association == null || template == null ){
            log.info("Ассоциация не верна");
            throw new TemplateException("Ассоциация не верна");
        }
        String del = "";
        if(delimiter != null){
            del = delimiter.getTextContent();
        }

        Integer templateId = Integer.parseInt(template.getTextContent());

        SysObject sysObject = sysObjectService.getSysObjectById(objectId);// throws EntityNotFoundException

        List<SysAssociationImpl> list = sysObject.getAssociationImplFromList();
        if(list.isEmpty()){
            log.info("Ассоциация не была зарегистрирована");
            throw new TemplateException("Ассоциация не была зарегистрирована");
        }

        StringBuilder result = new StringBuilder();

        for (SysAssociationImpl sysAssociation : list) {

            Integer toSysObjectId = sysAssociation.getToSysObject().getId();
            String inner = templateService.getObjectFields(fields,toSysObjectId,templateId);// throws EntityNotFoundException

            //проверка, что корректен шаблон для объекта
            SysObject toObj = sysObjectService.getSysObjectById(toSysObjectId);// throws EntityNotFoundException
            SysClass toClass = toObj.getOwnerSysClass();
            if(!toClass.checkTemplateExist(templateId)){
                throw new TemplateException(String.format("Шаблон %s не может быть использован для Объекта %d",templateId,toSysObjectId));
            }

            String currentResult = String.format("<a href=\"%s/%d/%d\"> %s </a>",ModePath.USER_MODE,toSysObjectId,templateId,inner);
            result.append(currentResult).append(del);
        }
        if(delimiter != null) {
            result.delete(result.length() - del.length() - 1, result.length() - 1);
        }
        return templateService.clearXMLMeta(result.toString());

    }

    @Override
    public void check(NamedNodeMap attributes, String innerText) {
        Node association = attributes.getNamedItem("associationId");
        Node template = attributes.getNamedItem("templateId");

        if(association == null || template == null ){
            log.info("Ассоциация не верна");
            throw new TemplateException("Ассоциация не верна");
        }
    }
}
