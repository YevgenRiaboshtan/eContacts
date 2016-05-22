package com.econtact.dataModel.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.proxy.HibernateProxyHelper;

import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.AbstractView;

/**
 * Abstract class for all entities.
 * Абстрактный класс для всех сущностей
 * @author evgeniy
 *
 * @param <PK> - Id`s type.
 * 				- тип идентификатора сущности
 */
@MappedSuperclass
public abstract class AbstractEntity<PK extends Serializable> implements AbstractView<PK> {
	private static final long serialVersionUID = 1L;

	@Transient
	private Long uid;
	
	/**
	 * Version field for optimistic lock.
	 * Поле версии для оптимистической блокировки.
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

	/**
	 * Return entity version.
	 * Возвращает версию объекта.
	 * @return - Entity version.
	 * 			- версия объекта. 
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * Set entity version.
	 * Устанавливает версию объекта
	 * @param version - Entity version.
	 * 					- версия объекта.
	 */
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
        if (obj == null 
        	|| HibernateProxyHelper.getClassWithoutInitializingProxy(this) != HibernateProxyHelper.getClassWithoutInitializingProxy(obj)) {
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
