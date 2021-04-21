package nsu.ru.diploma_v1.controller.edit_mode;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.configuration.urls.mode.EditMode;
import nsu.ru.diploma_v1.database.sys.*;
import nsu.ru.diploma_v1.exception.EditException;
import nsu.ru.diploma_v1.exception.EntityNotFoundException;
import nsu.ru.diploma_v1.model.dto.AnswerMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteController {

    private final SysClassService sysClassService;
    private final SysAssociationService sysAssociationService;
    private final SysAggregationService sysAggregationService;
    private final SysResourceService sysResourceService;
    private final SysTemplateService sysTemplateService;
    private final SysObjectService sysObjectService;

    @GetMapping(EditMode.Delete.DELETE_AGGREGATION)
    public AnswerMessage deleteAggregation(@PathVariable Integer id) throws EntityNotFoundException, EditException {
        String message = sysAggregationService.deleteAggregation(id);//throws EntityNotFoundException, EditException
        return new AnswerMessage(message,String.valueOf(id));
    }

    @GetMapping(EditMode.Delete.DELETE_ASSOCIATION)
    public AnswerMessage deleteAssociation(@PathVariable Integer id)throws EntityNotFoundException, EditException {
        String message = sysAssociationService.delete(id);//throws EntityNotFoundException, EditException
        return new AnswerMessage(message,String.valueOf(id));
    }

    @GetMapping(EditMode.Delete.DELETE_ASSOCIATION_IMPL)
    public AnswerMessage deleteAssociationImpl(@PathVariable Integer id) {
        String message = sysAssociationService.deleteImpl(id);
        return new AnswerMessage(message,String.valueOf(id));
    }

    @GetMapping(EditMode.Delete.DELETE_RESOURCE)
    public AnswerMessage deleteResource(@PathVariable Integer id) {
        String message = sysResourceService.delete(id);
        return new AnswerMessage(message,String.valueOf(id));
    }

    @GetMapping(EditMode.Delete.DELETE_TEMPLATE)
    public AnswerMessage deleteTemplate(@PathVariable Integer id) {
        String message = sysTemplateService.delete(id);
        return new AnswerMessage(message,String.valueOf(id));
    }

    @GetMapping(EditMode.Delete.DELETE_OBJECT)
    public AnswerMessage deleteObject(@PathVariable Integer id) throws EditException {
        String message = sysObjectService.delete(id);
        return new AnswerMessage(message,String.valueOf(id));
    }

    @GetMapping(EditMode.Delete.DELETE_CLASS)
    public AnswerMessage deleteClass(@PathVariable Integer id) throws EditException,EntityNotFoundException {
        String message = sysClassService.delete(id);//throws EditException,EntityNotFoundException
        return new AnswerMessage(message,String.valueOf(id));
    }
}
