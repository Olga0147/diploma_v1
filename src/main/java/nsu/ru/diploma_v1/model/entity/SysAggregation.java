package nsu.ru.diploma_v1.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Aggregation")
public class SysAggregation {

    @Id
    @GenericGenerator(name="keygen_aggr" , strategy="increment")
    @GeneratedValue(generator="keygen_aggr")
    private Integer id;

    @Column(nullable = false)
    private String name;

    private Integer fromClassId;

    private Integer toClassId;

    @ManyToOne
    @JoinColumn(name="fromClassId", nullable=false, insertable = false,updatable = false)
    private SysClass fromSysClass;

    @ManyToOne
    @JoinColumn(name="toClassId", nullable=false ,insertable = false,updatable = false)
    private SysClass toSysClass;

    @OneToMany(mappedBy="sysAggregation")
    private List<SysAggregationImpl> sysAggregationList;
}
