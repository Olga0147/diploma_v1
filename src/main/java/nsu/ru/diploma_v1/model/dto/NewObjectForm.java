package nsu.ru.diploma_v1.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class NewObjectForm {
    List<ObjectAttribute> attributes;
}
