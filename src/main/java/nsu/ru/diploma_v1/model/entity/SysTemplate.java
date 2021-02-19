package nsu.ru.diploma_v1.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Template")
public class SysTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String systemName;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String body;

    private Integer ownerClassId;

    @ManyToOne
    @JoinColumn(name="ownerClassId", nullable=false, insertable = false,updatable = false)
    private SysClass ownerSysClass;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;
}
