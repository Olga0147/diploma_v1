package nsu.ru.diploma_v1.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "AggregationImplementation")
public class SysAggregationImpl {

    @Id
    @GenericGenerator(name="keygen_aggr_im" , strategy="increment")
    @GeneratedValue(generator="keygen_aggr_im")
    private Integer id;

    private Integer aggregationId;

    private Integer fromObjectId;

    private Integer toObjectId;

    @ManyToOne
    @JoinColumn(name="fromObjectId", nullable=false, insertable = false,updatable = false)
    private SysObject fromSysObject;

    @ManyToOne
    @JoinColumn(name="toObjectId", nullable=false, insertable = false,updatable = false)
    private SysObject toSysObject;

    @ManyToOne
    @JoinColumn(name="aggregationId", nullable=false, insertable = false,updatable = false)
    private SysAggregation sysAggregation;
}
