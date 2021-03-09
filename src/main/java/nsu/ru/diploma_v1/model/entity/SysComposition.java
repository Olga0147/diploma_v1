package nsu.ru.diploma_v1.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Composition")
public class SysComposition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String systemName;

    private Integer fromClassId;

    private Integer toClassId;

    @ManyToOne
    @JoinColumn(name="fromClassId", nullable=false, insertable = false,updatable = false)
    private SysClass fromSysClass;

    @ManyToOne
    @JoinColumn(name="toClassId", nullable=false ,insertable = false,updatable = false)
    private SysClass toSysClass;

    @OneToMany(mappedBy="sysComposition")
    private List<SysCompositionImpl> sysCompositionList;
}
