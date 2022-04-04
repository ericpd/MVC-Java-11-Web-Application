#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.hibernate.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;

import ${package}.hibernate.beans.base.BaseEntity;

/**
 * @author ${author}
 * @param <T>
 *
 */
class GenericGenDao<T extends Serializable> extends AbstractBaseDao<T> {

	/***/
	private Class<T> m_entityClass;

	/**
	 * @param clazz Class< ? extends GenericGenDao>
	 * @param entityClass class of T
	 */
	@SuppressWarnings("rawtypes")
	protected GenericGenDao(final Class< ? extends GenericGenDao> clazz,
							final Class<T> entityClass) {
		super(clazz, null);
		this.m_entityClass = entityClass;
	}

	/**
	 * @param clazz Class< ? extends GenericGenDao>
	 * @param entityClass class of T
	 * @param dao GenericGenDao of ? that extends GenericGenDao
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected GenericGenDao(final Class< ? extends GenericGenDao> clazz,
							final Class<T> entityClass,
						 	final GenericGenDao dao) {
		super(clazz, dao);
		this.m_entityClass = entityClass;
	}

	/**
	 * @return the m_entityClass
	 */
	public Class<T> getEntityClass() {
		return m_entityClass;
	}

	/**
	 *
	 * @param entity Object
	 * @return T
	 */
	public T save(final T entity) {
		return super.saveEntity(entity);
	}

	/**
	 *
	 * @param entity Object
	 */
	@SuppressWarnings("rawtypes")
	public void persist(final T entity) {
		if (entity instanceof BaseEntity) {
			final BaseEntity bEntity = (BaseEntity) entity;
			bEntity.setLastModifiedDate(bEntity.getUpdateDate());
			bEntity.setUpdateDate(new Date());
		}
		super.persistEntity(entity);
	}

	/**
	 *
	 * @param id String
	 * @return Object
	 */
	public T findById(final String id) {
		return super.findEntityById(m_entityClass, id);
	}

	/**
	 *
	 * @param id long
	 * @return Object
	 */
	public T findById(final long id) {
		return super.findEntityById(m_entityClass, id);
	}

	/**
	 *
	 * @param id long
	 * @return Object
	 */
	public T findById(final int id) {
		return super.findEntityById(m_entityClass, id);
	}

	/**
	 *
	 * @param id Serializable
	 * @return Object
	 */
	public T findById(final Serializable id) {
		return super.findEntityById(m_entityClass, id);
	}

	/**
	 *
	 * @param entity Object
	 */
	public void delete(final T entity) {
		super.deleteEntity(entity);
	}

	/**
	 *
	 * @return List of objects
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> getAll() {
		final CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		final CriteriaQuery query = builder.createQuery(m_entityClass);
        final Root root = query.from(getEntityClass());
        query.select(root)
        	 .where(builder.isFalse(root.get(BaseEntity.CLM_RETIRED)));
        final Query q = getCurrentSession().createQuery(query);
        return q.getResultList();
	}

}
