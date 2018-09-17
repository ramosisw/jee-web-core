package com.ramosisw.jee.web.core.pl.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import com.ramosisw.jee.web.core.api.ex.BLException;
import com.ramosisw.jee.web.core.api.util.DAO;
import com.ramosisw.jee.web.core.pl.ex.DAOException;
import com.ramosisw.jee.web.core.pl.ex.MultipleRecordsFoundException;
import com.ramosisw.jee.web.core.pl.ex.RecordNotFoundException;

/**
 * Clase base para la definición de los DAOs. Esta clase es la que administra al
 * EntityManager y expone operaciones CRUD básicas Session Bean implementation
 * class GenericDAO
 */
public class BaseDAO<E, K> {

	@Resource
	protected EJBContext context;

	private Class<E> entityClass;

	private String entityName;

	@PersistenceContext
	protected EntityManager entityManager;

	protected Logger log;

	/**
	 * Default constructor.
	 */
	public BaseDAO() {
	}

	/**
	 * Metodo generico para obtener el acceso a base de datos Ejemplo
	 * 
	 * <pre>
	 * &#64;EJB
	 * private QueryDAO queryDAO;
	 * 
	 * public QueryDAO getQueryDAO() throws BLException {
	 * 	return getDAO(queryDAO, QueryDAO.class.getName());
	 * }
	 * </pre>
	 * 
	 * @param <T>
	 *            tipo de la instancia <code>dao</code>
	 * @param dao
	 *            instancia a verificar si fue instanciada por los EJB's
	 * @param className
	 *            nombre de la clase de la instancia
	 * @return instancia de tipo T
	 * @throws BLException
	 *             Si dao no esta instanciado
	 */

	public static <T> T getDAO(T dao, String className) throws BLException {
		return DAO.getDAO(dao, className);
	}

	/**
	 * Inicializa la clase con el EntityManager especificado
	 * 
	 * @param entityManager
	 *            EntityManager a utilizar para la conexión a la BD
	 */
	public BaseDAO(EntityManager entityManager) {
		setEntityManager(entityManager);
	}

	/**
	 * Inicializa la información del Entity y el logger de la clase
	 */
	@PostConstruct
	protected void initialize() {
		setEntityClass();
		setEntityName(entityClass.getName());

		// obtener bitacora
		log = Logger.getLogger(entityName);
		log.debug(String.format("%s Initialized!", entityName));
	}

	/***
	 * Especificar clase del objeto instanciado
	 * 
	 * @see <a href=
	 *      "http://blog.xebia.com/2009/02/07/acessing-generic-types-at-runtime-in-java/">Accessing
	 *      generic types at runtime in Java</a>
	 */
	@SuppressWarnings("unchecked")
	protected void setEntityClass() {
		this.entityClass = ((Class<E>) ((java.lang.reflect.ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0]);
	}

	/**
	 * Obtiene la clase del objeto instanciado
	 * 
	 * @return
	 */
	protected Class<E> getEntityClass() {
		return entityClass;
	}

	/**
	 * @return El nombre de la clase instanciada
	 */
	public String getEntityName() {
		return this.entityName;
	}

	/**
	 * Establece el nombre de la clase inicializada
	 * 
	 * @param entityName
	 *            nombre de la clase/entity
	 */
	protected void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	/**
	 * Establece la referencia al objeto EntityManager a usar para la ejecución de
	 * 
	 * @param entityManager
	 *            manejador los querys
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * @return Referencia al objeto EntityManager instanciado
	 */
	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	/**
	 * Realiza la persistencia del objeto Entity especificado
	 * 
	 * @param entity
	 *            Objeto a guardar en la BD
	 * @throws DAOException
	 */
	protected void save(E entity) throws DAOException {
		log.trace("saving " + entityName + " instance");
		try {
			entityManager.persist(entity);
			log.trace("save successful");
		} catch (RuntimeException re) {
			log.error(re);
			throw new DAOException(re);
		}
	}

	/**
	 * Actualizar registro
	 * 
	 * @param entity
	 * @throws DAOException
	 */
	protected void update(E entity) throws DAOException {
		log.trace("updating " + entityName + " instance");
		try {
			entityManager.merge(entity);
			log.trace("update successful");
		} catch (RuntimeException re) {
			log.error(re);
			throw new DAOException(re);
		}
	}

	/**
	 * Eliminar registro
	 * 
	 * @param entity
	 * @throws DAOException
	 */
	protected void delete(E entity) throws DAOException {
		log.trace("deleting " + entityName + " instance");
		try {
			entityManager.remove(entity);
			log.trace("delete successful");
		} catch (RuntimeException re) {
			log.error(re);
			throw new DAOException(re);
		}
	}

	/**
	 * find the <E> Entity denoted by specified Primary Key
	 * 
	 * @param id
	 *            Primary Key value
	 * @return Reference to Entity object with specified PK
	 * @throws DAOException
	 */
	protected E findById(K id) throws DAOException {
		return findById(id, true);
	}

	/**
	 * find the <E> Entity denoted by specified Primary Key
	 * 
	 * @param id
	 *            Primary Key value
	 * @return Reference to Entity object with specified PK
	 * @throws DAOException
	 */
	protected E findById(K id, boolean canThrow) throws DAOException {
		E entity = null;
		log.trace("finding " + entityName + " instance with id: " + id);
		try {
			entity = entityManager.find(getEntityClass(), id);
			if (entity == null && canThrow)
				throw new RecordNotFoundException("Entity not found for id " + id);
		} catch (RuntimeException re) {
			throw new DAOException("findById entity failed for id:" + id, re);
		}

		return entity;
	}

	/**
	 * Find all <E> entities with a specific property value.
	 * 
	 * @param propertyName
	 *            the name of the <E> property to query
	 * @param value
	 *            the property value to match
	 * @param rowStartIdxAndCount
	 *            Optional int varargs. rowStartIdxAndCount[0] specifies the the row
	 *            index in the query result-set to begin collecting the results.
	 *            rowStartIdxAndCount[1] specifies the the maximum number of results
	 *            to return.
	 * @return List<E> found by query
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	protected List<E> findByProperty(String propertyName, final Object value, final int... rowStartIdxAndCount)
			throws DAOException {
		log.trace("finding " + entityName + " instance with property: " + propertyName + ", value: " + value);
		try {
			final String queryString = "select model from " + entityName + " model where model." + propertyName
					+ "= :propertyValue";
			javax.persistence.Query query = entityManager.createQuery(queryString);
			query.setParameter("propertyValue", value);
			if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
				int rowStartIdx = Math.max(0, rowStartIdxAndCount[0]);
				if (rowStartIdx > 0) {
					query.setFirstResult(rowStartIdx);
				}

				if (rowStartIdxAndCount.length > 1) {
					int rowCount = Math.max(0, rowStartIdxAndCount[1]);
					if (rowCount > 0) {
						query.setMaxResults(rowCount);
					}
				}
			}
			return query.getResultList();
		} catch (RuntimeException re) {
			throw new DAOException("findByProperty entity failed for property " + propertyName + " value " + value, re);
		}
	}

	/**
	 * Find all <E> entities.
	 * 
	 * @param rowStartIdxAndCount
	 *            Optional int varargs. rowStartIdxAndCount[0] specifies the the row
	 *            index in the query result-set to begin collecting the results.
	 *            rowStartIdxAndCount[1] specifies the the maximum count of results
	 *            to return.
	 * @return List<E> all <E> entities
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	protected List<E> findAll(final int... rowStartIdxAndCount) throws DAOException {
		log.trace("finding all " + entityName + " instances");
		try {
			final String queryString = "select model from " + entityName + " model";
			javax.persistence.Query query = entityManager.createQuery(queryString);
			if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
				int rowStartIdx = Math.max(0, rowStartIdxAndCount[0]);
				if (rowStartIdx > 0) {
					query.setFirstResult(rowStartIdx);
				}

				if (rowStartIdxAndCount.length > 1) {
					int rowCount = Math.max(0, rowStartIdxAndCount[1]);
					if (rowCount > 0) {
						query.setMaxResults(rowCount);
					}
				}
			}
			return query.getResultList();
		} catch (RuntimeException re) {
			throw new DAOException("find all fails", re);
		}
	}

	/**
	 * Ejecuta el query especificado y retorna 1 registro o null si no se encuentra
	 * 
	 * @param query
	 *            Query a ejecutar, ya con los parámetros establecidos
	 * @return El registro encontrado o null
	 * @throws DAOException
	 *             Si se encuentra más de un registro
	 */
	protected E getSingleResultOrNull(Query query) throws DAOException {
		return getSingleResultOrNull(query, entityClass);
	}

	/**
	 * @param query
	 * @param typeResult
	 * @return
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	protected <T> T getSingleResultOrNull(Query query, Class<T> typeResult) throws DAOException {
		List<T> results = null;
		try {
			results = query.getResultList();
			if (results.isEmpty())
				return null;
			else if (results.size() == 1)
				return (T) results.get(0);
		} catch (RuntimeException re) {
			throw new DAOException("getSingleResultOrNull fails", re);
		}

		throw new MultipleRecordsFoundException("Multiple records found for query " + query.toString());
	}

	/**
	 * Ejecuta el query especificado y retorna 1 registro o null si no se encuentra
	 * 
	 * @param query
	 *            Query a ejecutar, ya con los parámetros establecidos
	 * @return El registro encontrado o null
	 * @throws DAOException
	 *             Si se encuentra más de un registro
	 */
	protected List<E> listResultFromQuery(Query query) throws DAOException {
		return listResultFromQuery(query, entityClass);
	}

	/**
	 * 
	 * @param query
	 * @param typeResult
	 * @return
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	protected <T> List<T> listResultFromQuery(Query query, Class<T> typeResult) throws DAOException {
		List<T> results = null;
		try {
			results = query.getResultList();
		} catch (RuntimeException re) {
			throw new DAOException("getSingleResultOrNull fails", re);
		}
		return results;
	}

}
