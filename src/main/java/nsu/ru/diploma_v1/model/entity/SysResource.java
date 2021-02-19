package nsu.ru.diploma_v1.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Resource")
public class SysResource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private Integer ownerClassId;

    private Integer ownerObjectId;

    @ManyToOne
    @JoinColumn(name="ownerClassId", nullable=false, insertable = false,updatable = false)
    private SysClass ownerSysClass;

    @ManyToOne
    @JoinColumn(name="ownerObjectId", nullable=false, insertable = false,updatable = false)
    private SysObject ownerSysObject;

    /**
     * Ссылка на файл
     */
    private String body;
}
