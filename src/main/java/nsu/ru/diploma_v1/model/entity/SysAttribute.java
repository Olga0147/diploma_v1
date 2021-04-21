package nsu.ru.diploma_v1.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import nsu.ru.diploma_v1.template_parse.resource_types.SysResourceType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Attribute")
public class SysAttribute {
    @Id
    @GenericGenerator(name="keygen_att" , strategy="increment")
    @GeneratedValue(generator="keygen_att")
    private Integer id;

    @Column(nullable = false)
    private String name;

    private Integer ownerClassId;

    private Integer attributeType;

    private Integer attributeSize;

    @Enumerated(EnumType.ORDINAL)
    private SysResourceType resourceType;

    @Column(nullable = false)
    private boolean canBeNull;

    /**
     * Класс Объекта
     */
    @ManyToOne
    @JoinColumn(name="ownerClassId", nullable=false, insertable = false,updatable = false)
    private SysClass ownerSysClass;

    /**
     * Реализации Агрегаций Объекта
     */
    @OneToMany(mappedBy="sysAttribute", fetch=FetchType.LAZY)
    List<SysAggregationImpl> notNeedAtAll;

    /**
     * Поля MMedia
     */
    @OneToMany(mappedBy="id", fetch=FetchType.LAZY)
    List<SysMmedia> contentList;

    public SysAttribute(Integer id, String name, Integer ownerClassId, Integer attributeType, Integer attributeSize, boolean canBeNull) {
        this.id = id;
        this.name = name;
        this.ownerClassId = ownerClassId;
        this.attributeType = attributeType;
        this.attributeSize = attributeSize;
        this.canBeNull = canBeNull;
    }

}
