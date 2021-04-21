package nsu.ru.diploma_v1.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Template")
public class SysTemplate {
    @Id
    @GenericGenerator(name="keygen_temp" , strategy="increment")
    @GeneratedValue(generator="keygen_temp")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String body;

    private Integer ownerClassId;

    @ManyToOne
    @JoinColumn(name="ownerClassId", nullable=false, insertable = false,updatable = false)
    private SysClass ownerSysClass;

    @OneToMany(mappedBy="toSysTemplate", fetch=FetchType.LAZY)
    List<SysAggregationImpl> notNeedAtAll;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;
}
