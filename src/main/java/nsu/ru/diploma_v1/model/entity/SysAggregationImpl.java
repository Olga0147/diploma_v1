package nsu.ru.diploma_v1.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import nsu.ru.diploma_v1.template_parse.aggregations.AggregationTypes;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

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

    private Integer toTemplateId;

    private Integer attributeId;

    @Enumerated(EnumType.ORDINAL)
    private AggregationTypes type;

    @ManyToOne
    @JoinColumn(name="fromObjectId", nullable=false, insertable = false,updatable = false)
    private SysObject fromSysObject;

    @ManyToOne
    @JoinColumn(name="toObjectId", nullable=false, insertable = false,updatable = false)
    private SysObject toSysObject;

    @ManyToOne
    @JoinColumn(name="toTemplateId", nullable=false, insertable = false,updatable = false)
    private SysTemplate toSysTemplate;

    @ManyToOne
    @JoinColumn(name="aggregationId", nullable=false, insertable = false,updatable = false)
    private SysAggregation sysAggregation;

    @ManyToOne
    @JoinColumn(name="AttributeId", nullable=false, insertable = false,updatable = false)
    private SysAttribute sysAttribute;

    public SysAggregationImpl(Integer aggregationId, Integer fromObjectId, Integer toObjectId, Integer toTemplateId, Integer attributeId, AggregationTypes type) {
        this.aggregationId = aggregationId;
        this.fromObjectId = fromObjectId;
        this.toObjectId = toObjectId;
        this.toTemplateId = toTemplateId;
        this.attributeId = attributeId;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysAggregationImpl that = (SysAggregationImpl) o;
        return Objects.equals(getAggregationId(), that.getAggregationId())
                && Objects.equals(getFromObjectId(), that.getFromObjectId())
                && Objects.equals(getToObjectId(), that.getToObjectId())
                && Objects.equals(getToTemplateId(), that.getToTemplateId())
                && Objects.equals(getAttributeId(), that.getAttributeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAggregationId(), getFromObjectId(), getToObjectId(), getToTemplateId(), getAttributeId());
    }
}
