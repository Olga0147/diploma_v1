package nsu.ru.diploma_v1.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "CompositionImplementation")
public class SysCompositionImpl{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String fragment;

    private Integer compositionId;

    private Integer fromObjectId;

    private Integer toObjectId;

    @ManyToOne
    @JoinColumn(name="fromObjectId", nullable=false, insertable = false,updatable = false)
    private SysObject fromSysObject;

    @ManyToOne
    @JoinColumn(name="toObjectId", nullable=false, insertable = false,updatable = false)
    private SysObject toSysObject;

    @ManyToOne
    @JoinColumn(name="compositionId", nullable=false, insertable = false,updatable = false)
    private SysComposition sysComposition;
}
