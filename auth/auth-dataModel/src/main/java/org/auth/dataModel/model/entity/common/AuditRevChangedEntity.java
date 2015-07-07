package org.auth.dataModel.model.entity.common;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.auth.dataModel.data.util.EntityHelper;
import org.auth.dataModel.model.AbstractView;

@Entity
@Table(name="audit_rev_changed", schema = EntityHelper.AUTH_SCHEMA)
public class AuditRevChangedEntity implements AbstractView<BigDecimal> {
    /**
     * @defaultUID
     */
    private static final long serialVersionUID = 1L;
    private static final String SEQ_NAME = "auditRevChangedSeq";

    public static final String AUDIT_REV_A = "auditRev";

    @SequenceGenerator(name = SEQ_NAME, sequenceName = "s$audit_rev_chenged", schema = EntityHelper.AUTH_SCHEMA,
            allocationSize = 1)

    @Id
    @GeneratedValue(strategy =GenerationType.SEQUENCE, generator = SEQ_NAME)
    @Column(name = EntityHelper.ID_F, nullable = false, precision = 38, scale = 0)
    private BigDecimal id;

    @ManyToOne
    @JoinColumn(name = "id_audit_rev_fk", nullable = false)
    private AuditRevEntity auditRev;

    @Column(name = "entity_name", nullable = false, length = 300)
    private String entityName;

    @Column(name = "entity_id", nullable = true, length = 100)
    private String entityId;

    public static AuditRevChangedEntity create(final String entityName, Object entityId) {
        final AuditRevChangedEntity result = new AuditRevChangedEntity();
        result.setEntityName(entityName);
        result.setEntityId(entityId.toString());
        return result;
    }

    @Override
    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public AuditRevEntity getAuditRev() {
        return auditRev;
    }

    public void setAuditRev(AuditRevEntity auditRev) {
        this.auditRev = auditRev;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
}
