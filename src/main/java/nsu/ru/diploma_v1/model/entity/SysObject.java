package nsu.ru.diploma_v1.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Object")
public class SysObject {

    @Id
    @GenericGenerator(name="keygen_obj" , strategy="increment")
    @GeneratedValue(generator="keygen_obj")
    private Integer id;

    //@Column(insertable = false,updatable = false)
    private Integer ownerClassId;

    @ManyToOne
    @JoinColumn(name="ownerClassId", nullable=false, insertable = false,updatable = false)
    private SysClass ownerSysClass;

    @OneToMany(mappedBy="ownerSysObject")
    List<SysResource> resourceList;

    /**
     * Агрегации
     */
    @OneToMany(mappedBy="fromSysObject")
    List<SysAggregationImpl> aggregationImplFromList;//(от текущего класса в лист)

    @OneToMany(mappedBy="toSysObject")
    List<SysAggregationImpl> aggregationImplToList;//(из листа в текущий класс)

    /**
     * Ассоциации
     */
    @OneToMany(mappedBy="fromSysObject")
    List<SysAssociationImpl> associationImplFromList;

    @OneToMany(mappedBy="toSysObject")
    List<SysAssociationImpl> associationImplToList;
}
