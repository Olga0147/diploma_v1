package nsu.ru.diploma_v1.controller.edit_mode;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.exception.EditException;
import nsu.ru.diploma_v1.model.dto.AnswerMessage;
import nsu.ru.diploma_v1.model.dto.NewClassForm;
import nsu.ru.diploma_v1.model.dto.NewObjectForm;
import nsu.ru.diploma_v1.model.dto.ObjectAttribute;
import nsu.ru.diploma_v1.model.entity.*;
import nsu.ru.diploma_v1.template_parse.resource_types.SysResourceType;
import nsu.ru.diploma_v1.database.sys.*;
import nsu.ru.diploma_v1.database.custom.CustomService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static nsu.ru.diploma_v1.configuration.urls.mode.EditMode.PostForm;

@RestController
@RequiredArgsConstructor
public class PostFormController {
    private final CustomService customService;
    private final SysAggregationService sysAggregationService;
    private final SysAssociationService sysAssociationService;
    private final SysTemplateService sysTemplateService;
    private final SysResourceService sysResourceService;
    private final SysMMediaService sysMMediaService;

    @PostMapping(PostForm.POST_CLASS)
    public AnswerMessage postNewClass(@RequestBody NewClassForm newClassForm) throws EditException {
        SysClass sysClass = customService.saveClass(newClassForm);// throws EditException
        return new AnswerMessage("Удачно!", String.valueOf(sysClass.getId()));
    }

    @PostMapping(PostForm.POST_OBJECT)
    public AnswerMessage postNewObject(@RequestBody NewObjectForm newObjectForm, @PathVariable Integer classId) throws EditException {
        //TODO check all aggregatioins : use  parseXMemoToSaveObject
        SysObject sysObject = customService.saveObject(newObjectForm.getAttributes(),classId);// throws EditException
        return new AnswerMessage("Удачно!",String.valueOf(sysObject.getId()));
    }

    @PostMapping(PostForm.POST_AGGREGATION)
    public AnswerMessage postNewAggregation(@RequestBody SysAggregation sysAggregation) {
        //TODO ERROR : unsuccessful
        SysAggregation aggregation = sysAggregationService.saveSysAggregation(sysAggregation);
        return new AnswerMessage("Удачно!", String.valueOf(aggregation.getId()));
    }

    @PostMapping(PostForm.POST_ASSOCIATION)
    public AnswerMessage postNewAssociation(@RequestBody SysAssociation sysAssociation) {
        //TODO ERROR : unsuccessful
        SysAssociation association = sysAssociationService.saveSysAssociation(sysAssociation);
        return new AnswerMessage("Удачно!",String.valueOf(association.getId()));
    }

    @PostMapping(PostForm.POST_ASSOCIATION_IMPL)
    public AnswerMessage postNewAssociationImpl(@RequestBody SysAssociationImpl sysAssociation, @PathVariable Integer associationId) {
        //TODO ERROR : unsuccessful
        sysAssociation.setAssociationId(associationId);
        SysAssociationImpl association = sysAssociationService.saveSysAssociationImpl(sysAssociation);
        return new AnswerMessage("Удачно!",String.valueOf(association.getId()));
    }

    @PostMapping(PostForm.POST_TEMPLATE)
    public AnswerMessage postNewTemplate(@RequestBody SysTemplate sysTemplate, @PathVariable Integer classId) {
        //TODO ERROR : unsuccessful
        sysTemplate.setOwnerClassId(classId);
        SysTemplate template = sysTemplateService.saveSysTemplate(sysTemplate);
        return new AnswerMessage("Удачно!",String.valueOf(template.getId()));
    }

    @PostMapping(path = PostForm.POST_RESOURCE, consumes = {"multipart/form-data"})
    public AnswerMessage postNewResource(
            @PathVariable Integer classId,
            @RequestPart(name = "file", required = false) MultipartFile file) {
        //TODO ERROR : unsuccessful
        SysResource resource = new SysResource();
        resource.setName(file.getOriginalFilename());
        try {
            resource.setData(file.getBytes());
        } catch (IOException e) {
            return new AnswerMessage("Ошибка!","-1");
        }
        String  mime = file.getContentType();
        resource.setResourceType(SysResourceType.getByMime(mime));
        resource.setOwnerClassId(classId);

        //TODO : error
        SysResource sysResource = sysResourceService.saveSysResource(resource);

        return new AnswerMessage("Удачно!",String.valueOf(sysResource.getId()));
    }

    @PostMapping(path = PostForm.POST_MMEDIA, consumes = {"multipart/form-data"})
    public AnswerMessage postNewMmedia(
            @PathVariable Integer objectId,
            @PathVariable String attributeName,
            @RequestPart(name = "file", required = false) MultipartFile file) throws EditException {
        //TODO ERROR : unsuccessful
        SysMmedia mmedia = new SysMmedia();
        mmedia.setName(file.getOriginalFilename());
        try {
            mmedia.setData(file.getBytes());
        } catch (IOException e) {
            return new AnswerMessage("Ошибка!","-1");
        }
        String  mime = file.getContentType();
        mmedia.setResourceType(SysResourceType.getByMime(mime));
        mmedia.setOwnerObjectId(objectId);

        //TODO : error
        SysMmedia sysMmedia = sysMMediaService.saveSysMmedia(mmedia);
        List<ObjectAttribute> list = new LinkedList<>();
        list.add(new ObjectAttribute(attributeName,sysMmedia.getId()));
        SysObject sysObject = customService.updateObject(list,objectId);// throws EditException

        return new AnswerMessage("Удачно!",String.valueOf(sysMmedia.getId()));
    }


}
