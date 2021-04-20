package nsu.ru.diploma_v1.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Class")
public class SysClass {

    @Id
    @GenericGenerator(name="keygen" , strategy="increment")
    @GeneratedValue(generator="keygen")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String systemName;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    /**
     * Агрегации
     */
    @OneToMany(mappedBy="fromSysClass", fetch=FetchType.LAZY)
    List<SysAggregation> aggregationFromList;//(от текущего класса в лист)

    @OneToMany(mappedBy="toSysClass", fetch=FetchType.LAZY)
    List<SysAggregation> aggregationToList;//(из листа в текущий класс)

    /**
     * Ассоциации
     */
    @OneToMany(mappedBy="fromSysClass", fetch=FetchType.LAZY)
    List<SysAssociation> associationFromList;

    @OneToMany(mappedBy="toSysClass", fetch=FetchType.LAZY)
    List<SysAssociation> associationToList;

    /**
     * Аттрибуты
     */
    @OneToMany(mappedBy="ownerSysClass", fetch=FetchType.LAZY)
    List<SysAttribute> attributeList;

    /**
     * Объекты
     */
    @OneToMany(mappedBy="ownerSysClass", fetch=FetchType.LAZY)
    List<SysObject> objectList;

    /**
     * Шаблоны
     */
    @OneToMany(mappedBy="ownerSysClass", fetch=FetchType.LAZY)
    List<SysTemplate> templateList;

    /**
     * Ресурсы
     */
    @OneToMany(mappedBy="ownerSysClass", fetch=FetchType.LAZY)
    List<SysResource> resourceList;


    public void deleteObject(SysObject sysObject){
        this.objectList.remove(sysObject);
    }
}
