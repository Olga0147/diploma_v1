package nsu.ru.diploma_v1.controller.edit_mode;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.dto.AnswerMessage;
import nsu.ru.diploma_v1.model.dto.NewClassForm;
import nsu.ru.diploma_v1.model.dto.NewObjectForm;
import nsu.ru.diploma_v1.model.entity.SysAggregation;
import nsu.ru.diploma_v1.model.entity.SysAggregationImpl;
import nsu.ru.diploma_v1.model.entity.SysAssociation;
import nsu.ru.diploma_v1.model.entity.SysComposition;
import nsu.ru.diploma_v1.service.database.SysAggregationService;
import nsu.ru.diploma_v1.service.database.SysAssociationService;
import nsu.ru.diploma_v1.service.database.SysCompositionService;
import nsu.ru.diploma_v1.service.system.CustomService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static nsu.ru.diploma_v1.configuration.urls.mode.EditMode.PostForm;

@RestController
@RequiredArgsConstructor
public class PostFormController {
    private final CustomService customService;
    private final SysAggregationService sysAggregationService;
    private final SysAssociationService sysAssociationService;
    private final SysCompositionService sysCompositionService;

    @PostMapping(PostForm.POST_PAGE)
    public AnswerMessage postNewPage(@RequestBody NewClassForm newClassForm) {
        //TODO ERROR : unsuccessful
        customService.saveClass(newClassForm,true);
        return new AnswerMessage("Удачно!");
    }

    @PostMapping(PostForm.POST_CLASS)
    public AnswerMessage postNewClass(@RequestBody NewClassForm newClassForm) {
        //TODO ERROR : unsuccessful
        customService.saveClass(newClassForm,false);
        return new AnswerMessage("Удачно!");
    }

    @PostMapping(PostForm.POST_OBJECT)
    public AnswerMessage postNewObject(@RequestBody NewObjectForm newObjectForm, @PathVariable Integer classId) {
        //TODO ERROR : unsuccessful
        customService.saveObject(newObjectForm.getAttributes(),classId);
        return new AnswerMessage("Удачно!");
    }

    @PostMapping(PostForm.POST_AGGREGATION)
    public AnswerMessage postNewAggregation(@RequestBody SysAggregation sysAggregation) {
        //TODO ERROR : unsuccessful
        sysAggregationService.saveSysAggregation(sysAggregation);
        return new AnswerMessage("Удачно!");
    }

    @PostMapping(PostForm.POST_ASSOCIATION)
    public AnswerMessage postNewAssociation(@RequestBody SysAssociation sysAssociation) {
        //TODO ERROR : unsuccessful
        sysAssociationService.saveSysAssociation(sysAssociation);
        return new AnswerMessage("Удачно!");
    }

    @PostMapping(PostForm.POST_COMPOSITION)
    public AnswerMessage postNewComposition(@RequestBody SysComposition sysComposition) {
        //TODO ERROR : unsuccessful
        sysCompositionService.saveSysComposition(sysComposition);
        return new AnswerMessage("Удачно!");
    }

    @PostMapping(PostForm.POST_AGGREGATION_IMPL)
    public AnswerMessage postNewAggregationImpl(@RequestBody SysAggregationImpl sysAggregation, @PathVariable Integer aggregationId) {
        //TODO ERROR : unsuccessful
        sysAggregationService.saveSysAggregationImpl(sysAggregation);
        return new AnswerMessage("Удачно!");
    }
}
