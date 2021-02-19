package nsu.ru.diploma_v1.controller.edit_mode;

import lombok.RequiredArgsConstructor;
import nsu.ru.diploma_v1.model.dto.AnswerMessage;
import nsu.ru.diploma_v1.model.dto.NewClassForm;
import nsu.ru.diploma_v1.model.dto.NewObjectForm;
import nsu.ru.diploma_v1.service.database.SysClassService;
import nsu.ru.diploma_v1.service.editor.NewService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static nsu.ru.diploma_v1.configuration.SysUrl.*;

@RestController
@RequiredArgsConstructor
public class EditAnswerController {
    private final SysClassService sysClassService;
    private final NewService newService;

    @PostMapping(POST_NEW_PAGE)
    public AnswerMessage postNewPage(@RequestBody NewClassForm newClassForm) {
        newService.saveClass(newClassForm,true);
        return new AnswerMessage("Удачно!");
    }

    @PostMapping(POST_NEW_CLASS)
    public AnswerMessage postNewClass(@RequestBody NewClassForm newClassForm) {
        newService.saveClass(newClassForm,false);
        return new AnswerMessage("Удачно!");
    }

    @PostMapping(POST_NEW_OBJECT)
    public AnswerMessage postNewObject(@RequestBody NewObjectForm newObjectForm, @PathVariable Integer classId) {
        System.out.println(newObjectForm);

        newService.saveObject(newObjectForm.getAttributes(),classId);
        return new AnswerMessage("Удачно!");
    }
}
