package nsu.ru.diploma_v1.model.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class NewClassForm {
    @NotNull
    private String name;
    private String description;
    @NotNull
    private boolean isPage;
    private List<NewAttributeForm> attributes;
}
