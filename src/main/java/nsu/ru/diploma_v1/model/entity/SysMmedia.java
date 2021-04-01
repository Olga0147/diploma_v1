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
@Table(name = "Mmedia")
public class SysMmedia {
    @Id
    @GenericGenerator(name="keygen_mme" , strategy="increment")
    @GeneratedValue(generator="keygen_mme")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] data;

    @Enumerated(EnumType.ORDINAL)
    private SysResourceType resourceType;

    private Integer ownerObjectId;

    @ManyToOne
    @JoinColumn(name="ownerObjectId", nullable=false, insertable = false,updatable = false)
    private SysObject ownerSysObject;

}
