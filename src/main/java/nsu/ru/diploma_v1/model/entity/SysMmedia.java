package nsu.ru.diploma_v1.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Mmedia")
public class SysMmedia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] data;

    private Integer ownerObjectId;

    @ManyToOne
    @JoinColumn(name="ownerObjectId", nullable=false, insertable = false,updatable = false)
    private SysObject ownerSysObject;

}
