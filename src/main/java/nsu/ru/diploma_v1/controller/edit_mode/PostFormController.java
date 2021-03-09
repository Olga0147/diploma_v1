package nsu.ru.diploma_v1.controller.edit_mode;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.dto.AnswerMessage;
import nsu.ru.diploma_v1.model.dto.NewClassForm;
import nsu.ru.diploma_v1.model.dto.NewObjectForm;
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
}
