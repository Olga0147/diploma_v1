package nsu.ru.diploma_v1.controller.edit_mode;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.configuration.urls.mode.EditMode;
import nsu.ru.diploma_v1.database.custom.CustomService;
import nsu.ru.diploma_v1.database.sys.*;
import nsu.ru.diploma_v1.exception.EditException;
import nsu.ru.diploma_v1.exception.EntityNotFoundException;
import nsu.ru.diploma_v1.exception.TemplateException;
import nsu.ru.diploma_v1.model.dto.AnswerMessage;
import nsu.ru.diploma_v1.model.dto.NewObjectForm;
import nsu.ru.diploma_v1.model.dto.ObjectAttribute;
import nsu.ru.diploma_v1.model.entity.SysMmedia;
import nsu.ru.diploma_v1.model.entity.SysObject;
import nsu.ru.diploma_v1.model.entity.SysResource;
import nsu.ru.diploma_v1.model.entity.SysTemplate;
import nsu.ru.diploma_v1.template_parse.resource_types.SysResourceType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UpdateController {

    private final SysClassService sysClassService;
    private final SysAssociationService sysAssociationService;
    private final SysAggregationService sysAggregationService;
    private final SysResourceService sysResourceService;
    private final SysTemplateService sysTemplateService;
    private final SysObjectService sysObjectService;
    private final CustomService customService;
    private final SysMMediaService sysMMediaService;


    @PostMapping(EditMode.UpdateCheck.UPDATE_CHECK_RESOURCE)
    public AnswerMessage updateResource(
            @PathVariable Integer id,
            @RequestPart(name = "file", required = false) MultipartFile file) {

        SysResource resource = sysResourceService.getSysResourcesByResourceId(id);
        resource.setName(file.getOriginalFilename());
        try {
            resource.setData(file.getBytes());
        } catch (IOException e) {
            return new AnswerMessage("Ошибка!","-1");
        }
        String  mime = file.getContentType();
        resource.setResourceType(SysResourceType.getByMime(mime));

        SysResource sysResource = sysResourceService.saveSysResource(resource);

        return new AnswerMessage("Удачно!",String.valueOf(sysResource.getId()));
    }

    @PostMapping(EditMode.UpdateCheck.UPDATE_CHECK_TEMPLATE)
    public AnswerMessage updateTemplate(@RequestBody SysTemplate sysTemplate, @PathVariable Integer id) {
        SysTemplate old = sysTemplateService.getSysTemplate(id);
        sysTemplate.setOwnerClassId(old.getOwnerClassId());

        SysTemplate template = sysTemplateService.saveSysTemplate(sysTemplate);
        return new AnswerMessage("Удачно!",String.valueOf(template.getId()));
    }

    @PostMapping(EditMode.UpdateCheck.UPDATE_CHECK_OBJECT)
    public AnswerMessage updateObject(@RequestBody NewObjectForm newObjectForm, @PathVariable Integer id) throws EditException, EntityNotFoundException {

        customService.updateObject(newObjectForm.getAttributes(),id);//// throws EditException,EntityNotFoundException
        return new AnswerMessage("Удачно!",String.valueOf(id));
    }

    @PostMapping(EditMode.UpdateCheck.UPDATE_CHECK_MMEDIA)
    public AnswerMessage updateMmedia(
            @PathVariable Integer objectId,
            @PathVariable String attributeName,
            @RequestPart(name = "file", required = false) MultipartFile file) throws EditException,EntityNotFoundException, TemplateException {

        //получили объект
        SysObject obj = sysObjectService.getSysObjectById(objectId);
        Map<String, Object> objectMap = customService.getObject(obj.getOwnerSysClass().getSystemName(),objectId);
        int mmediaId = Integer.parseInt(String.valueOf(objectMap.get(attributeName)));
        SysMmedia mmedia = sysMMediaService.getSysMMediaByMMediaId(mmediaId);

        mmedia.setName(file.getOriginalFilename());
        try {
            mmedia.setData(file.getBytes());
        } catch (IOException e) {
            throw  new EditException("Ошибка при обработке файла.");
        }
        String mime = file.getContentType();
        if(mime == null){
            throw  new EditException("Ошибка при обработке файла.");
        }
        mmedia.setResourceType(SysResourceType.getByMime(mime));
        SysMmedia sysMmedia = sysMMediaService.saveSysMmedia(mmedia);
        return new AnswerMessage("Удачно!",String.valueOf(sysMmedia.getId()));
    }


}
