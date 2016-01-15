package com.econtact.dataModel.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.AbstractView;

@MappedSuperclass
public abstract class AbstractEntity<PK extends Serializable> implements AbstractView<PK> {
	private static final long serialVersionUID = 1L;

	@Transient
	private Long uid;
	
	/**
	 * Поле версии для оптимистической блокировки.
	 * Version field for optimistic lock.
	 */
	@Version
	@Column(name="version")
	private Long version;
	
	private Long getUid() {
		if (uid == null) {
			uid = EntityHelper.getUid();
		}
		return uid;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
        int result = getId() == null ? getUid().hashCode() : getId().hashCode();
        result = result * 17 + (version == null ? 0 : version.hashCode());
        return result;
    }

	@Override
	public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final AbstractEntity other = (AbstractEntity) obj;
        boolean isEquals;
        if (getId() == null && other.getId() == null) {
            isEquals = getUid().equals(other.getUid());
        } else {
            isEquals = getId() == null ? false : getId().equals(other.getId());
        }
        if (isEquals) {
            isEquals = version == null ? other.version == null : version.equals(other.version);
        }
        return isEquals;
    }
	
	
}
