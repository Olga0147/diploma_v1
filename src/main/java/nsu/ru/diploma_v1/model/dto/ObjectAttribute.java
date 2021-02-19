package nsu.ru.diploma_v1.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ObjectAttribute {
    public String name;
    public Object value;
}
