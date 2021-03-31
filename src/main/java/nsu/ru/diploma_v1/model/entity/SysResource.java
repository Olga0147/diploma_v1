package nsu.ru.diploma_v1.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import nsu.ru.diploma_v1.model.enums.resource_types.SysResourceType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Resource")
public class SysResource {
    @Id
    @GenericGenerator(name="keygen_res" , strategy="increment")
    @GeneratedValue(generator="keygen_res")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] data;

    @Enumerated(EnumType.ORDINAL)
    private SysResourceType resourceType;

    private Integer ownerClassId;

    @ManyToOne
    @JoinColumn(name="ownerClassId", nullable=false, insertable = false,updatable = false)
    private SysClass ownerSysClass;

}
