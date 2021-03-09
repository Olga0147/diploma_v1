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

    @Column(nullable = false)
    private String path;

    private Integer ownerClassId;

    @ManyToOne
    @JoinColumn(name="ownerClassId", nullable=false, insertable = false,updatable = false)
    private SysClass ownerSysClass;

}
