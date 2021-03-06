package nsu.ru.diploma_v1.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "AssociationImplementation")
public class SysAssociationImpl {

    @Id
    @GenericGenerator(name="keygen_acc_im" , strategy="increment")
    @GeneratedValue(generator="keygen_acc_im")
    private Integer id;

    private Integer associationId;

    private Integer fromObjectId;

    private Integer toObjectId;

    @ManyToOne
    @JoinColumn(name="fromObjectId", nullable=false, insertable = false,updatable = false)
    private SysObject fromSysObject;

    @ManyToOne
    @JoinColumn(name="toObjectId", nullable=false, insertable = false,updatable = false)
    private SysObject toSysObject;

    @ManyToOne
    @JoinColumn(name="associationId", nullable=false, insertable = false,updatable = false)
    private SysAssociation sysAssociation;
}
