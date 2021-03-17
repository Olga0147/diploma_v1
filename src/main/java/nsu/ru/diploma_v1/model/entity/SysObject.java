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

    /**
     * Агрегации
     */
    @OneToMany(mappedBy="fromSysObject", fetch=FetchType.LAZY)
    List<SysAggregationImpl> aggregationImplFromList;//(от текущего класса в лист)

    @OneToMany(mappedBy="toSysObject", fetch=FetchType.LAZY)
    List<SysAggregationImpl> aggregationImplToList;//(из листа в текущий класс)

    /**
     * Ассоциации
     */
    @OneToMany(mappedBy="fromSysObject", fetch=FetchType.LAZY)
    List<SysAssociationImpl> associationImplFromList;

    @OneToMany(mappedBy="toSysObject", fetch=FetchType.LAZY)
    List<SysAssociationImpl> associationImplToList;
}
