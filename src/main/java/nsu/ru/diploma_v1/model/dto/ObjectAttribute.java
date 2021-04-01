package nsu.ru.diploma_v1.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjectAttribute {
    public String name;
    public Object value;
}
