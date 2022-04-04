#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.hibernate.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;

import ${package}.hibernate.beans.base.BaseEntity;
import ${package}.util.AppUtil;

/**
 * @author ${author}
 * @param <T>
 *
 */
public class GenericDao<T extends Serializable> extends GenericGenDao<T> {

	/**
	 *
	 * @param entityClass Class of T
	 */
	public GenericDao(final Class<T> entityClass) {
		this(entityClass, null);
	}

	/**
	 * @param entityClass Class of T
	 * @param dao GenericDao of ? that extends GenericDao
	 */
	@SuppressWarnings("rawtypes")
	public GenericDao(final Class<T> entityClass,
					  final GenericDao dao) {
		super(GenericDao.class, entityClass, dao);
	}

	/**
	 * @param clazz Class< ? extends GenericDao>
	 * @param entityClass Class of T
	 * @param dao GenericDao of ? that extends GenericDao
	 */
	@SuppressWarnings("rawtypes")
	protected GenericDao(final Class< ? extends GenericDao> clazz,
						 final Class<T> entityClass,
						 final GenericDao dao) {
		super(clazz, entityClass, dao);
	}

	/**
	 *
	 * @param column String
	 * @param value String
	 * @return Object
	 */
	public T firstByColumnName(final String column, final Serializable value) {
		final List<T> list = findByColumnName(column, value, false, -1, null);
		if (AppUtil.isNullOrEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	/**
	 *
	 * @param column String
	 * @param value String
	 * @param includeRetired boolean
	 * @return Object
	 */
	public T firstByColumnName(final String column, final Serializable value, final boolean includeRetired) {
		final List<T> list = findByColumnName(column, value, includeRetired, -1, null);
		if (AppUtil.isNullOrEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	/**
	 *
	 * @param column String
	 * @param value String
	 * @param limit int
	 * @param order list of {@link SortOrder}
	 * @return Object
	 */
	public T firstByColumnName(final String column, final Serializable value, final int limit, final List<SortOrder> order) {
		final List<T> list = findByColumnName(column, value, false, limit, order);
		if (AppUtil.isNullOrEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	/**
	 *
	 * @param column String
	 * @param value String
	 * @return Object
	 */
	public List<T> findByColumnName(final String column, final Serializable value) {
		return findByColumnName(column, value, false, -1, null);
	}

	/**
	 *
	 * @param column String
	 * @param value String
	 * @param includeRetired boolean
	 * @return Object
	 */
	public List<T> findByColumnName(final String column, final Serializable value, final boolean includeRetired) {
		return findByColumnName(column, value, includeRetired, -1, null);
	}

	/**
	 *
	 * @param column String
	 * @param value String
	 * @param limit int
	 * @param order list of {@link SortOrder}
	 * @return Object
	 */
	public List<T> findByColumnName(final String column, final Serializable value, final int limit, final List<SortOrder> order) {
		return findByColumnName(column, value, false, limit, order);
	}

	/**
	 *
	 * @param column String
	 * @param value String
	 * @param includeRetired boolean
	 * @param limit int
	 * @param order list of {@link SortOrder}
	 * @return Object
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> findByColumnName(final String column, final Serializable value, final boolean includeRetired, final int limit, final List<SortOrder> order) {
		final CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		final CriteriaQuery query = builder.createQuery(getEntityClass());
        final Root root = query.from(getEntityClass());
        query.select(root);
        if (!includeRetired) {
	        query.where(builder.and(
	        		 builder.isFalse(root.get(BaseEntity.CLM_RETIRED)),
	        		 builder.equal(root.get(column), value)
	        	));
        } else {
        	query.where(builder.equal(root.get(column), value));
        }

        if (null != order) {
        	for (final SortOrder sort : order) {
        		if (sort.isAscending()) {
        			query.orderBy(builder.asc(root.get(sort.getColumn())));
        		} else {
        			query.orderBy(builder.desc(root.get(sort.getColumn())));
        		}
        	}
        }
        final Query q = getCurrentSession().createQuery(query);

        if (limit > 0) {
        	q.setMaxResults(limit);
        }
        return q.getResultList();
	}

	/**
	 *
	 * @param columns Map of column names and values
	 * @return Object
	 */
	public T firstByColumns(final Map<String, Serializable> columns) {
		final List<T> list = findByColumns(columns, false, -1, null);
		if (AppUtil.isNullOrEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	/**
	 *
	 * @param columns Map of column names and values
	 * @return Object
	 */
	public List<T> findByColumns(final Map<String, Serializable> columns) {
		return findByColumns(columns, false, -1, null);
	}

	/**
	 *
	 * @param columns Map of column names and values
	 * @param includeRetired boolean
	 * @param limit int
	 * @param order list of SortOrder
	 * @return Object
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> findByColumns(final Map<String, Serializable> columns, final boolean includeRetired, final int limit, final List<SortOrder> order) {
		final CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		final CriteriaQuery query = builder.createQuery(getEntityClass());
        final Root root = query.from(getEntityClass());
        final List<Predicate> filters = new ArrayList<>();
        if (!includeRetired) {
        	filters.add(builder.isFalse(root.get(BaseEntity.CLM_RETIRED)));
        }
        for (final String columnName : columns.keySet()) {
        	filters.add(builder.equal(root.get(columnName), columns.get(columnName)));
        }

        query.select(root)
        	 .where(builder.and(filters.toArray(new Predicate[filters.size()])));

        if (null != order) {
        	for (final SortOrder sort : order) {
        		if (sort.isAscending()) {
        			query.orderBy(builder.asc(root.get(sort.getColumn())));
        		} else {
        			query.orderBy(builder.desc(root.get(sort.getColumn())));
        		}
        	}
        }

        final Query q = getCurrentSession().createQuery(query);

        if (limit > 0) {
        	q.setMaxResults(limit);
        }
        return q.getResultList();
	}

	public static final class SortOrder {

		/***/
		private String m_column;

		/***/
		private boolean m_ascending;

		public static SortOrder asc(final String column) {
			return new SortOrder(column, true);
		}

		public static SortOrder desc(final String column) {
			return new SortOrder(column, false);
		}

		/**
		 *
		 * @param column String
		 * @param ascending boolean
		 */
		private SortOrder(final String column, final boolean ascending) {
			this.m_column = column;
			this.m_ascending = ascending;
		}

		/**
		 * @return the column
		 */
		public String getColumn() {
			return m_column;
		}

		/**
		 * @param column the column to set
		 */
		public void setColumn(final String column) {
			this.m_column = column;
		}

		/**
		 * @return the ascending
		 */
		public boolean isAscending() {
			return m_ascending;
		}

		/**
		 * @param ascending the ascending to set
		 */
		public void setAscending(final boolean ascending) {
			this.m_ascending = ascending;
		}
	}

}
