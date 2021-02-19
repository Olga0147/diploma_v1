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

    @Column(nullable = false)
    private boolean isPage;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    /**
     * Агрегации
     */
    @OneToMany(mappedBy="fromSysClass")
    List<SysAggregation> aggregationFromList;//(от текущего класса в лист)

    @OneToMany(mappedBy="toSysClass")
    List<SysAggregation> aggregationToList;//(из листа в текущий класс)

    /**
     * Ассоциации
     */
    @OneToMany(mappedBy="fromSysClass")
    List<SysAssociation> associationFromList;

    @OneToMany(mappedBy="toSysClass")
    List<SysAssociation> associationToList;

    /**
     * Аттрибуты
     */
    @OneToMany(mappedBy="ownerSysClass")
    List<SysAttribute> attributeList;

    /**
     * Объекты
     */
    @OneToMany(mappedBy="ownerSysClass")
    List<SysObject> objectList;

    /**
     * Шаблоны
     */
    @OneToMany(mappedBy="ownerSysClass")
    List<SysTemplate> templateList;

    /**
     * Ресурсы (не продумано)
     */
    @OneToMany(mappedBy="ownerSysClass")
    List<SysResource> resourceList;
}
