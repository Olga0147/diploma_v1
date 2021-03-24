package nsu.ru.diploma_v1.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import nsu.ru.diploma_v1.model.enums.resource_types.SysResourceType;
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

    @ManyToOne
    @JoinColumn(name="ownerClassId", nullable=false, insertable = false,updatable = false)
    private SysClass ownerSysClass;

    @OneToMany(mappedBy="sysAttribute", fetch=FetchType.LAZY)
    List<SysAggregationImpl> notNeedAtAll;//TODO : understand and delete

    public SysAttribute(Integer id, String name, Integer ownerClassId, Integer attributeType, Integer attributeSize, boolean canBeNull) {
        this.id = id;
        this.name = name;
        this.ownerClassId = ownerClassId;
        this.attributeType = attributeType;
        this.attributeSize = attributeSize;
        this.canBeNull = canBeNull;
    }

}
