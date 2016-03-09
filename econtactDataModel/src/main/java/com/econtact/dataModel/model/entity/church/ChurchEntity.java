package com.econtact.dataModel.model.entity.church;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.econtact.dataModel.data.context.EJBContext;
import com.econtact.dataModel.data.util.EntityHelper;
import com.econtact.dataModel.model.entity.AbstractEntity;
import com.econtact.dataModel.model.entity.AuditSupport;
import com.econtact.dataModel.model.entity.access.AccessChurchEntity;
import com.econtact.dataModel.model.entity.accout.SessionUserEntity;

@Entity
@Table(name = "church", schema = EntityHelper.E_CONTACT_SCHEMA, uniqueConstraints = { @UniqueConstraint(name = ChurchEntity.CHURCH_NAME_SIGN_UNIQUE_CONSTRAINT, columnNames = {
		"name_church", EntityHelper.SIGN_F }) })
@Audited
@AuditTable(value = "church_aud", schema = EntityHelper.E_CONTACT_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
// FIXME not work with cache. Load only root entity from cache without graph attribute.
/*
 * @NamedEntityGraphs({
 * 
 * @NamedEntityGraph(name = ChurchEntity.CHURCH_ACCESS_GRAPH, attributeNodes = { @NamedAttributeNode(value =
 * ChurchEntity.ACCESS_A) }),
 * 
 * @NamedEntityGraph(name = ChurchEntity.CHURCH_GROUPS_GRAPH, attributeNodes = { @NamedAttributeNode(value =
 * ChurchEntity.GROUPS_A) }),
 * 
 * @NamedEntityGraph(name = ChurchEntity.CHURCH_ACCESS_GROUPS_GRAPH, attributeNodes = {
 * 
 * @NamedAttributeNode(value = ChurchEntity.ACCESS_A), @NamedAttributeNode(value = ChurchEntity.GROUPS_A) }) })
 */
public class ChurchEntity extends AbstractEntity<BigDecimal> implements AuditSupport {
	private static final long serialVersionUID = 6692176964337384451L;
	private static final String SEQ_NAME = "churchSeq";
	private static final String NOTE_PATTERN = "Церковь ID: '%s'";

	/*
	 * public static final String CHURCH_ACCESS_GRAPH = "churchAccessGraph"; public static final String
	 * CHURCH_GROUPS_GRAPH = "churchGroupsGraph"; public static final String CHURCH_ACCESS_GROUPS_GRAPH =
	 * "churchAccessGroupsGraph";
	 */

	public static final String CHURCH_NAME_SIGN_UNIQUE_CONSTRAINT = "church_name_sign_unique_constraint";
	public static final String NAME_CHURCH_A = "nameChurch";
	public static final String OWNER_A = "owner";
	public static final String ACCESS_A = "access";
	public static final String GROUPS_A = "groups";

	/**
	 * identifier of the Church.C
	 */
	@Id
	@Access(AccessType.PROPERTY)
	@SequenceGenerator(name = SEQ_NAME, sequenceName = "seq_church_id", schema = EntityHelper.E_CONTACT_SCHEMA, allocationSize = 1)
	@GeneratedValue(generator = SEQ_NAME, strategy = GenerationType.SEQUENCE)
	@Column(name = EntityHelper.ID_F, precision = 38, scale = 0, unique = true, nullable = false)
	private BigDecimal id;

	/**
	 * Наименование церкви. Name of the church.
	 */
	@Column(name = "name_church", nullable = false, length = 255)
	private String nameChurch;

	/**
	 * Описание церкви Description of the church
	 */
	@Column(name = "description_church", length = 1000)
	private String description;

	/**
	 * Главный администратор. Main administrator of the church. See also {@link SessionUserEntity}
	 */
	@ManyToOne
	@JoinColumn(name = "id_owner_fk")
	@Fetch(FetchMode.SELECT)
	@NotAudited
	private SessionUserEntity owner;

	/**
	 * Created date.
	 */
	@Column(name = "create_date", nullable = false)
	private Date createDate;

	/**
	 * Автор последних изменений Last update author.
	 */
	@Column(name = EntityHelper.UPD_AUTHOR_F, nullable = false, length = 200)
	private String updAuthor;

	/**
	 * Дата послдних изменений Last update date.
	 */
	@Column(name = EntityHelper.UPD_DATE_F, nullable = false)
	private Date updDate;

	/**
	 * Поле указывающее актуальность записи. sign = 0 - запись актуальная, sign = id - запись удалена. Field for mark
	 * row as deleted. If sign = 0 actual row, if sign = id - row is delete.
	 */
	@Column(name = EntityHelper.SIGN_F, nullable = false, precision = 38, scale = 0)
	private BigDecimal sign;

	/**
	 * Список прав доступа к общине
	 */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = AccessChurchEntity.CHURCH_A)
	@NotAudited
	@Where(clause = "sign = 0")
	private List<AccessChurchEntity> access = new ArrayList<>();

	/**
	 * Список групп общины
	 */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = GroupEntity.CHURCH_A)
	@Where(clause = "sign = 0")
	private List<GroupEntity> groups = new ArrayList<>();

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getNameChurch() {
		return nameChurch;
	}

	public void setNameChurch(String nameChurch) {
		this.nameChurch = nameChurch;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SessionUserEntity getOwner() {
		return owner;
	}

	public void setOwner(SessionUserEntity owner) {
		this.owner = owner;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdAuthor() {
		return updAuthor;
	}

	public void setUpdAuthor(String updAuthor) {
		this.updAuthor = updAuthor;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public BigDecimal getSign() {
		return sign;
	}

	public void setSign(BigDecimal sign) {
		this.sign = sign;
	}

	public List<AccessChurchEntity> getAccess() {
		return access;
	}

	public void setAccess(List<AccessChurchEntity> access) {
		this.access = access;
	}

	public boolean addAccess(final AccessChurchEntity access) {
		if (!this.access.contains(access)) {
			access.setChurch(this);
			return this.access.add(access);
		}
		return false;
	}

	public boolean removeAccess(final AccessChurchEntity access) {
		if (this.access.contains(access)) {
			access.setChurch(null);
			return this.access.remove(access);
		}
		return false;
	}

	public List<GroupEntity> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupEntity> groups) {
		this.groups = groups;
	}

	public boolean addGroup(final GroupEntity group) {
		if (!this.groups.contains(group)) {
			group.setChurch(this);
			return this.groups.add(group);
		}
		return false;
	}

	public boolean removeAccess(final GroupEntity group) {
		if (this.groups.contains(group)) {
			group.setChurch(null);
			return this.groups.remove(group);
		}
		return false;
	}

	@Override
	public String getEnversNote() {
		return String.format(NOTE_PATTERN, getId());
	}

	@PrePersist
	public void prePersist() {
		setSign(EntityHelper.ACTUAL_SIGN);
		setUpdData();
	}

	@PreUpdate
	protected void setUpdData() {
		setUpdAuthor(EJBContext.get().getUser().getUpdData());
		setUpdDate(new Date());
	}
}
