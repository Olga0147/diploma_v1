package nsu.ru.diploma_v1.model.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewAttributeForm {
    @NotNull
    private String name;
    @NotNull
    private String type;
    private Integer size;
    @NotNull
    private boolean isNull;
}
