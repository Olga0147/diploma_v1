package nsu.ru.diploma_v1.controller.edit_mode;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.configuration.urls.mode.EditMode;
import nsu.ru.diploma_v1.database.custom.CustomService;
import nsu.ru.diploma_v1.database.sys.*;
import nsu.ru.diploma_v1.exception.EditException;
import nsu.ru.diploma_v1.exception.EntityNotFoundException;
import nsu.ru.diploma_v1.model.dto.AnswerMessage;
import nsu.ru.diploma_v1.model.dto.NewObjectForm;
import nsu.ru.diploma_v1.model.entity.SysObject;
import nsu.ru.diploma_v1.model.entity.SysResource;
import nsu.ru.diploma_v1.model.entity.SysTemplate;
import nsu.ru.diploma_v1.template_parse.resource_types.SysResourceType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

}
