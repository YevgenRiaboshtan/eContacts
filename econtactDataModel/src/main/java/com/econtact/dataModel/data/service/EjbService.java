package com.econtact.dataModel.data.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.annotations.QueryHints;

import com.econtact.dataModel.data.context.UserContext;
import com.econtact.dataModel.data.query.SearchCriteria;
import com.econtact.dataModel.data.util.UniqueConstraintException;
import com.econtact.dataModel.model.AbstractView;
import com.econtact.dataModel.model.entity.AbstractEntity;

/**
 * Base system interface define generic system operations with entities.
 * @author Yevgen Riaboshtan
 *
 */
public interface EjbService {
	/**
	 * Find by primary key.
     * Search for an entity of the specified class and primary key.
     * If the entity instance is contained in the persistence context,
     * it is returned from there.
	 * @param findClass - entity class
	 * @param id - primary key
	 * @return the found entity instance or null if the entity does
     *         not exist
     * @throws IllegalArgumentException if the first argument does
     *         not denote an entity type or the second argument is 
     *         is not a valid type for that entity's primary key or
     *         is null
     *         
     * For details @see {@link EntityManager#find(Class, Object)}
	 */
	<T extends AbstractView> T findById(Class<T> findClass, Object id);

	/**
	 * Find by primary key.
     * Search for an entity of the specified class and primary key.
     * If the entity instance is contained in the persistence context,
     * it is returned from there.
     * Add {@link QueryHints#LOADGRAPH} hint to the find request.
     * 
	 * @param findClass - entity class
	 * @param id - primary key
	 * @param graphName - entity graph
	 * @return the found entity instance or null if the entity does
     *         not exist
	 * 
	 * For details @See {@link EntityManager#find(Class, Object, java.util.Map)}
	 */
	<T extends AbstractView> T findById(Class<T> findClass, Object id, String graphName);
	
	/**
	 * Save or create (if new) entity into database.
	 * @param entity - target entity
	 * @param userContext - User context {@link UserContext} that provide changes.
	 * @return - modification state of the entity.
	 * @throws UniqueConstraintException - occurs if violation of the unique fields 
	 * 										need be handle.  
	 */
	<T extends AbstractEntity> T saveOrUpdate(T entity, UserContext userContext) throws UniqueConstraintException;

	/**
	 * Remove entity from database.
	 * @param entity - target entity.
	 * @param userContext - User context {@link UserContext} that provide changes.
	 */
	void remove(AbstractEntity entity, UserContext userContext);

	/**
	 * Find entity by search criteria {@link SearchCriteria}.
	 * @param criteria - search criteria {@link SearchCriteria}.
	 * @return entity.
	 * 	Can throw exception, for detail @see {@link TypedQuery#getSingleResult()}
	 */
	<T> T findSingleResult(SearchCriteria<T> criteria);

	/**
	 * Find entities by search criteria {@link SearchCriteria}
	 * @param criteria - search criteria {@link SearchCriteria}.
	 * @return - list with entities or empty list.
	 */
	<T> List<T> find(SearchCriteria<T> criteria);
	
	/**
	 * Find entities by search criteria with entity graph.
	 * Add {@link QueryHints#LOADGRAPH} hint to the find request.
	 *  
	 * @param criteria - search criteria {@link SearchCriteria}
	 * @param graphName - entity graph.
	 * @return - list with entities or empty list
	 */
	<T> List<T> find(SearchCriteria<T> criteria, String graphName);

	/**
	 * Find entities by search criteria with apply start position and max result.
	 * @param criteria - search criteria {@link SearchCriteria}
	 * @param from - start position
	 * @param count - max number of results
	 * @return - list with entities or empty list
	 */
	<T> List<T> find(SearchCriteria<T> criteria, Integer from, Integer count);
	
	/**
	 * Find entities by search criteria with apply start position and max result.
	 * Add {@link QueryHints#LOADGRAPH} hint to the find request.
	 * 
	 * @param criteria - search criteria {@link SearchCriteria}
	 * @param from - start position
	 * @param count - max number of results
	 * @param graphName - graph name
	 * @return - list with entities or empty list.
	 */
	<T> List<T> find(SearchCriteria<T> criteria, Integer from, Integer count, String graphName);

	/**
	 * Get rows count by search criteria.
	 * @param criteria - search criteria  {@link SearchCriteria}
	 * @return - rows count
	 */
	<T> Long getRowCount(SearchCriteria<T> criteria);
}
