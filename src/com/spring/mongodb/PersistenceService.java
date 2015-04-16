package com.spring.mongodb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;
import com.spring.mongodb.QueryConstant.FilterOperator;
import com.spring.mongodb.QueryConstant.OPERATOR;
/**
 * 
 * @author suresh
 *
 */

public class PersistenceService {

	private static MongoOperations operation = DBConnection.getConnection();

	/**
	 * add or update single object with specify collection name
	 * If a document does not exist with the specified _id value, the save() method performs an insert with the specified fields in the document 
	 * @param object
	 * @throws PersistenceException
	 */
	public void addOrUpdateObject(Object object,Class<?> clazz)throws PersistenceException {
		try {
			operation.save(object, getCollectionName(clazz));
		}  catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage()); 
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Insert new document in collections
	 * @param object
	 * @throws PersistenceException
	 */
	public void addSingleObject(Object object) throws PersistenceException {
		try {
			operation.insert(object);
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Insert new document in with specify collections
	 * @param listObject
	 * @throws PersistenceException
	 */
	public void addObject(List<?> listObject, Class<?> clazz) throws PersistenceException {
		try {
			operation.insert(listObject, clazz);
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * add single record for multiple object which have different collection
	 * @param listObject
	 * @exception PersistenceException
	 */
	public void addObjects(List<Object> listObject) throws PersistenceException {
		try {
			operation.insertAll(listObject);
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Get all record from Specify Collections
	 * @param clazz
	 * @return
	 */
	public <T> List<T> getRecordsList(Class<T> clazz) throws PersistenceException {
		try {
			return operation.findAll(clazz);
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Returns all record according to search query
	 * @param field
	 * @param fieldValue
	 * @param clazz
	 * @return
	 * @throws PersistenceException
	 */
	public <T> List<T> find(String field, Object fieldValue, Class<T> clazz) throws PersistenceException {
		try {
			Query query = new Query(Criteria.where(field).is(fieldValue));
			return operation.find(query, clazz);
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}
	/**
	 * Returns all record according to query
	 * @param column
	 * @param columnValue
	 * @param clazz
	 * @return
	 * @throws PersistenceException
	 */
	public <T> List<T> findByQuery(Query query, Class<T> clazz) throws PersistenceException {
		try {
			return operation.find(query, clazz);
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Returns all record according to search query by Order(ascending\descending)
	 * @param field
	 * @param fieldValue
	 * @param clazz
	 * @return
	 * @throws PersistenceException
	 */
	public <T> List<T> findByOrder(String field, Object fieldValue, String key, OrderBy order, Class<T> clazz) throws PersistenceException {
		try {
			Criteria criteria = Criteria.where(field).is(fieldValue);
			Query query = new Query();
			query.addCriteria(criteria);
			if (key != null && order != null) 
				query = getOrder(query, key, order);
			return operation.find(query, clazz);
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}
	
	/**
	 * Returns all record using criteria by Order(ascending\descending)
	 * @param criteria
	 * @param clazz
	 * @return
	 * @throws PersistenceException
	 */
	public <T> List<T> findByCriteria(Criteria criteria, String key, OrderBy order, Class<T> clazz) throws PersistenceException {
		try {
			Query query = new Query();
			query.addCriteria(criteria);
			if (key != null && order != null) 
				query = getOrder(query, key, order);
			return operation.find(query, clazz);
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Returns all record according to query by Order(ascending\descending)
	 * @param column
	 * @param columnValue
	 * @param clazz
	 * @return
	 * @throws PersistenceException
	 */
	public <T> List<T> findByQueryOrder(Query query , String key, OrderBy order, Class<T> clazz) throws PersistenceException {
		try {
			if (key != null && order != null) 
				query = getOrder(query, key, order);
			return operation.find(query, clazz);
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Returns only one record Object
	 * @param field
	 * @param fieldValue
	 * @param clazz
	 * @return
	 * @throws PersistenceException
	 */
	public <T> T findOne(String field ,Object fieldValue, Class<T> clazz)  throws PersistenceException {
		try {
			Criteria criteria = Criteria.where(field).is(fieldValue);
			Query query = new Query().addCriteria(criteria);
			return operation.findOne(query, clazz);
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Returns only one record Object
	 * @param column
	 * @param columnValue
	 * @param clazz
	 * @return
	 * @throws PersistenceException
	 */
	public <T> Object findOneByQuery(Query query ,Class<T> clazz) throws PersistenceException {
		try {
			return operation.findOne(query, clazz);
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Returns all records by $IN search query
	 * @param column
	 * @param values
	 * @param clazz
	 * @return
	 * @throws PersistenceException
	 */
	public <T> List<T> findByIn(String column, Object[] values, Class<T> clazz) throws PersistenceException {
		try {
			Criteria criteria = Criteria.where(column).in(values);
			Query query = new Query(criteria);
			return operation.find(query, clazz);
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Returns records by Id
	 * @param column
	 * @param values
	 * @param clazz
	 * @return
	 * @throws PersistenceException
	 */
	public <T> Object findById(String id, Class<T> clazz) throws PersistenceException {
		try {
			return operation.findById(id, clazz);
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Get all records by Paging 
	 * If you want paging by descending or ascending order specify key and order otherwise it should be null.
	 * @param query
	 * @param pageNumber
	 * @param pageSize
	 * @param clazz
	 * @return
	 * @throws PersistenceException
	 */
	public <T> List<T> getListByPaging(Query query, int pageNumber, int pageSize, String key, OrderBy order, Class<T> clazz) 
			throws PersistenceException {
		try {
			if (key != null && order != null)
				query = getOrder(query, key, order);
			query.skip((pageNumber-1)*pageSize).limit(pageSize);
			return operation.find(query, clazz);
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Get the count by query
	 * @param query
	 * @param clazz
	 * @return
	 * @throws PersistenceException
	 */
	public int getCountByQuery(Query query, Class<?> clazz) throws PersistenceException {
		try {
			return (int) operation.count(query, clazz);
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Get the count 
	 * Note: 
	 * Specify the column name and add the column value in List for count
	 * @param field
	 * @param list
	 * @param clazz
	 * @return
	 * @throws PersistenceException
	 */
	public int getCountByIn(String field, List<?> list, Class<?> clazz) throws PersistenceException  {
		try {
			Criteria criteria = Criteria.where(field).in(list);
			Query query = new Query().addCriteria(criteria);
			return (int) operation.count(query, clazz);
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Get total Row count
	 * @param collectionName
	 * @return
	 * @throws PersistenceException
	 */
	public int getRowCount(Class<?> clazz) throws PersistenceException  {
		try {
			return (int) operation.getCollection(getCollectionName(clazz)).count();
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Remove Single record data
	 * @param field
	 * @param fieldValue
	 * @param clazz
	 * @throws PersistenceException
	 */
	public void deleteSingleRecord(String field , Object fieldValue, Class<?> clazz)throws PersistenceException  {
		try {
			operation.remove(new Query(Criteria.where(field).is(fieldValue)), clazz);
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Remove Multiple record data 
	 * @param field
	 * @param columnValue
	 * @param clazz
	 * @throws PersistenceException
	 */
	public void deleteByIn(String field, List<?> list, Class<?> clazz)throws PersistenceException  {
		try {
			operation.remove(new Query(Criteria.where(field).in(list)), clazz);
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Remove Multiple record data 
	 * @param column
	 * @param columnValue
	 * @param clazz
	 * @throws PersistenceException
	 */
	public void deleteByQuery(Query query, Class<?> clazz)throws PersistenceException  {
		try {
			operation.remove(query, clazz);
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Delete Multiple documents by native query
	 * @param query 
	 * @param clazz
	 * @throws PersistenceException
	 */
	public void deleteRecordByNativeQuery(BasicDBObject query, Class<?> clazz)throws PersistenceException  {
		try {
			DBCollection collection = operation.getCollection(getCollectionName(clazz));
			collection.remove(query);
		} catch (MongoException e) {
			throw new PersistenceException(e);
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
	}

	/**
	 * Update multiple column in collection
	 * @param collectionName
	 * @param columnName
	 * @param columnValue
	 * @param map
	 * @return
	 * @throws PersistenceException
	 */
	@SuppressWarnings("deprecation")
	public int updateMultiField(Class<?> clazz,String columnName, Object columnValue, Map<String, Object> map) throws PersistenceException  {
		try {
			DBCollection collection = operation.getCollection(getCollectionName(clazz));
			BasicDBObject searchQuery = new BasicDBObject().append(columnName, columnValue);

			BasicDBObject columnToUpdateQuery = new BasicDBObject();
			Iterator<String> iterator = map.keySet().iterator();
			while(iterator.hasNext()) {
				String key = (String) iterator.next();
				Object value = map.get(key);
				columnToUpdateQuery.append(key, value);
			}
			BasicDBObject updateQuery = new BasicDBObject().append(QueryConstant.SET, columnToUpdateQuery);
			WriteResult res = collection.updateMulti(searchQuery, updateQuery);
			if (res.getError()!=null && res.getN() == 0) {
				System.out.println("Update Failed,  Error:"+res.getError()+", count: "+res.getN()+", LastError"+res.getLastError());
			}
			return res.getN();
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Update single document
	 * @param clazz
	 * @param query
	 * @param map
	 * @return
	 * @throws PersistenceException
	 */
	public int update(Class<?> clazz , BasicDBObject query, Map<String, Object> map) throws PersistenceException {
		return update(clazz, query, map,false);
	}
	/**
	 * Update multiple document
	 * @param clazz
	 * @param query
	 * @param map
	 * @return
	 * @throws PersistenceException
	 */
	public int updateMulti(Class<?> clazz ,BasicDBObject query, Map<String, Object> map) 
			throws PersistenceException {
		return update(clazz, query, map,true);
	}

	/**
	 * Update multiple document and single document
	 * provide boolean flag true to update multiple record
	 * @param collectionName
	 * @param query
	 * @param map
	 * @param multi
	 * @return
	 * @throws PersistenceException
	 */
	@SuppressWarnings("deprecation")
	public int update(Class<?> clazz,BasicDBObject query, Map<String, Object> map,boolean multi) 
			throws PersistenceException  {
		try {
			DBCollection collection = operation.getCollection(getCollectionName(clazz));
			BasicDBObject columnToUpdateQuery = new BasicDBObject();
			Iterator<String> iterator = map.keySet().iterator();
			while(iterator.hasNext()) {
				String key = (String) iterator.next();
				Object value = map.get(key);
				columnToUpdateQuery.append(key, value);
			}
			BasicDBObject updateQuery = new BasicDBObject().append(QueryConstant.SET, columnToUpdateQuery);
			WriteResult res = null;
			if (multi) 
				res = collection.updateMulti(query, updateQuery);//update multiple column
			else
				res = collection.update(query, updateQuery); //update single column
			if (res.getError()!=null && res.getN() == 0) {
				System.out.println("Update Failed,  Error:"+res.getError()+", count: "+res.getN()+", LastError"+res.getLastError());
			}
			return res.getN();
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Update multiple record and single record
	 * provide boolean flag true to update multiple record
	 * @param clazz
	 * @param query
	 * @param map
	 * @param multi
	 * @return
	 * @throws PersistenceException
	 */
	@SuppressWarnings("deprecation")
	public int updateByPush(Class<?> clazz, BasicDBObject query, Map<String, Object> map,boolean multi) 
			throws PersistenceException  {
		try {
			DBCollection collection = operation.getCollection(getCollectionName(clazz));
			BasicDBObject columnToUpdateQuery = new BasicDBObject();
			Iterator<String> iterator = map.keySet().iterator();
			while(iterator.hasNext()) {
				String key = (String) iterator.next();
				Object value = map.get(key);
				columnToUpdateQuery.append(key, value);
			}
			BasicDBObject updateQuery = new BasicDBObject().append(QueryConstant.PUSH, query);
			WriteResult res = null;
			if (multi) 
				res = collection.updateMulti(updateQuery, columnToUpdateQuery);//update multiple column
			else
				res = collection.update(columnToUpdateQuery, updateQuery);
			if (res.getN() == 0)
				System.out.println("Update Failed,  Error:"+res.getError()+", count: "+res.getN()+", LastError"+res.getLastError());
			return res.getN();
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Update record 
	 * @param clazz
	 * @param query
	 * @param updateQuery
	 * @return
	 * @throws PersistenceException
	 */
	@SuppressWarnings("deprecation")
	public int updateByQuery(Class<?> clazz, BasicDBObject query, BasicDBObject updateQuery) throws PersistenceException  {
		try {
			DBCollection collection = operation.getCollection(getCollectionName(clazz));
			WriteResult res = collection.update(query, updateQuery); //update single column
			if (res.getN() == 0) 
				System.err.println("Update Operation Failed,  Error:"+res.getError()+", count: "+res.getN()+", LastError"+res.getLastError());
			return res.getN();
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Update multiple column by Id
	 * @param ids
	 * @param updataField
	 * @param updateValue
	 * @param clazz
	 * @return
	 * @throws PersistenceException
	 */
	@SuppressWarnings("deprecation")
	public int updateMultiById(List<String> ids, String updataField, Object updateValue,Class<?> clazz) throws PersistenceException {
		try {
			Criteria criteria = Criteria.where("_id").in(ids);
			Query query = new Query().addCriteria(criteria);;
			Update update = Update.update(updataField, updateValue);
			WriteResult result = operation.updateMulti(query, update,clazz);
			if (result.getN() == 0) 
				System.err.println("Update Failed,  Error:"+result.getError()+", count: "+result.getN()+", LastError"+result.getLastError());
			return result.getN();
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Performs an upsert. If no document is found that matches the query, a new document is created and inserted by combining the query document and the update document
	 * @param query
	 * @param update
	 * @param claszz
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int upsert(Query query, Update update, Class<?> claszz) {
		WriteResult result  = operation.upsert(query, update, claszz);
		if (result.getN() == 0) 
			System.err.println("Update Failed,  Error:"+result.getError()+", count: "+result.getN()+", LastError"+result.getLastError());
		return result.getN();
	}


	/**
	 * Get all records by Paging using native Query 
	 * If you want paging by descending or ascending order specify key and order otherwise it should be null.
	 * @param query
	 * @param collectionName
	 * @param pageNumber
	 * @param pageSize
	 * @param key If it is null .We will not get order(Ascending/Descending) data.  
	 * @param order If it is null .We will not get order(Ascending/Descending) data.
	 * @return 
	 * @throws PersistenceException
	 */
	public <T> List<T> getListPagingByNativeQuery(BasicDBObject query, int pageNumber, int pageSize ,String key, OrderBy order,Class<T> clazz) 
			throws PersistenceException {
		try {
			List<DBObject> dbObjectList = null;
			DBCollection collection = operation.getCollection(getCollectionName(clazz));
			BasicDBObject orderBy = null;
			if (key != null && order != null)
				orderBy = new BasicDBObject().append(key, order.value);	
			if (orderBy != null)
				dbObjectList = collection.find(query).sort(orderBy).skip((pageNumber-1)*pageSize).limit(pageSize).toArray();
			else 
				dbObjectList = collection.find(query).skip((pageNumber-1)*pageSize).limit(pageSize).toArray();
			return convertDbObjectToObject(clazz, dbObjectList);
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Get all record 
	 * @param query
	 * @param key if it is null. We are not get sorted data. 
	 * @param order if it is null. We are not get sorted data.
	 * @param limit if limit is -1 .We gets all record
	 * @param clazz
	 * @return
	 * @throws PersistenceException
	 */
	public <T> List<T> getListByNativeQuery(BasicDBObject query,String key, OrderBy order,int limit, Class<T> clazz) throws PersistenceException {
		try {
			List<DBObject> listDbObj = null;
			DBCollection collection = operation.getCollection(getCollectionName(clazz));
			BasicDBObject orderBy = null;
			if (key != null && order != null)
				orderBy = new BasicDBObject().append(key, order.value);	
			if (orderBy != null) {
				if (limit == -1) 
					listDbObj = collection.find(query).sort(orderBy).toArray();
				else
					listDbObj = collection.find(query).sort(orderBy).limit(limit).toArray();
			} else {
				if (limit == -1)
					listDbObj = collection.find(query).toArray();
				else
					listDbObj = collection.find(query).limit(limit).toArray();
			}
			return convertDbObjectToObject(clazz, listDbObj);
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * To create indexing
	 * Note:If you provide index annotation in pojo it should become more faster.
	 * @param clazz
	 * @param Order if OrderBy is null.Index will create in ascending order.
	 * @param columnName column name for one or more indexes
	 * @throws PersistenceException
	 */
	public void createIndex(Class<?> clazz, OrderBy Order, String... columnName) throws PersistenceException {
		try {
			DBCollection collection = operation.getCollection(getCollectionName(clazz));
			if (Order == null) {
				for (String column : columnName) {
					BasicDBObject queryIndex = new BasicDBObject();
					queryIndex.put(column, 1);
					collection.createIndex(queryIndex);
				}
			} else {
				for (String column : columnName) {
					BasicDBObject queryIndex = new BasicDBObject();
					queryIndex.put(column, Order.value);
					collection.createIndex(queryIndex);
				}
			}
		} catch (MongoException e) {
			throw new PersistenceException(e);
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
	}

	/**
	 * To create compound Indexes
	 * Note:If you provide index annotation in pojo it should become more faster.
	 * @param clazz
	 * @param Order if OrderBy is null.Index will create in ascending order.
	 * @param ascField column name for create ascending index
	 * @param dscField column name for create descending index
	 * @throws PersistenceException
	 */
	public void createCompoundIndex(Class<?> clazz, String ascField, String dscField) throws PersistenceException {
		try {
			DBCollection collection = operation.getCollection(getCollectionName(clazz));
			BasicDBObject queryIndex = new BasicDBObject();
			if (ascField!=null)
				createIndexObject(queryIndex, 1, ascField);
			if (dscField!=null)
				createIndexObject(queryIndex, -1, dscField);
			collection.createIndex(queryIndex);
		} catch (DataAccessResourceFailureException e) {
			e.printStackTrace();
			throw new PersistenceException(e.getMessage()); 
		} catch (MongoException e) {
			e.printStackTrace();
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Create DBObject for indexing
	 * @param queryIndex
	 * @param indexOrder
	 * @param columnName
	 */
	private void  createIndexObject(BasicDBObject queryIndex,int indexOrder, String...columnName) {
		for (String column : columnName) 
			queryIndex.put(column, indexOrder);
	}

	/**
	 * Drop all indexes
	 * @param clazz clazz name which you want to drop all indexes for that class
	 * @throws PersistenceException
	 */
	public void dropIndexes(Class<?> clazz) throws PersistenceException {
		try {
			DBCollection collection = operation.getCollection(getCollectionName(clazz));
			collection.dropIndexes();
		} catch (DataAccessResourceFailureException e) {
			e.printStackTrace();
			throw new PersistenceException(e.getMessage()); 
		} catch (MongoException e) {
			e.printStackTrace();
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenceException(e.getMessage());
		}
	}


	/**
	 * Find the data by Java script
	 * @param clazz
	 * @param javaScript
	 * @return
	 * @throws PersistenceException
	 */
	public <T> Object findByJS(Class<T> clazz, String javaScript) throws PersistenceException {
		try {
			DBCollection collection = operation.getCollection(getCollectionName(clazz));
			return collection.getDB().eval(javaScript);
		} catch (DataAccessResourceFailureException e) {
			e.printStackTrace();
			throw new PersistenceException(e.getMessage()); 
		} catch (MongoException e) {
			e.printStackTrace();
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Get List of Records by Projection
	 * @param query
	 * @param projection
	 * @param key
	 * @param order
	 * @param clazz
	 * @return
	 * @throws PersistenceException
	 */
	public <T> List<T> getListByProjection(BasicDBObject query, BasicDBObject projection, String key, OrderBy order, Class<T> clazz) 
			throws PersistenceException {
		try {
			List<DBObject> dbObjectList = null;
			DBCollection collection = operation.getCollection(getCollectionName(clazz));
			BasicDBObject orderBy = null;
			if (key != null && order != null)
				orderBy = new BasicDBObject().append(key, order.value);	
			if (orderBy == null)
				dbObjectList = collection.find(query, projection).toArray();
			else
				dbObjectList = collection.find(query, projection).sort(orderBy).toArray();
			return convertDbObjectToObject(clazz, dbObjectList);
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Returns only one record Object by Query
	 * Note : Use Operator <B> enum </B> for get data accodring to condtion
	 * like: OPERATOR.EQUAL.getName()
	 * @param field key
	 * @param value value
	 * @param operator operator enum for condition check
	 * @param clazz
	 * @return
	 * @throws PersistenceException
	 */
	public <T> T findOneByCondition(String field ,Object value, OPERATOR operator,String key, OrderBy order, Class<T> clazz) 
			throws PersistenceException {
		try {
			Query query = new Query();
			String condition = field + " " + operator.getName();
			addCondition(condition, value, query);
			if (key != null && order != null) 
				query = getOrder(query, key, order);
			return operation.findOne(query, clazz);
		} catch (MongoException e) {
			throw new PersistenceException(e);
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
	}

	/**
	 * Returns only one record Object by Query
	 * Note : Use Operator <B> enum </B> for get data accodring to condtion
	 * like: OPERATOR.EQUAL.getName()
	 * @param field key
	 * @param value value
	 * @param operator operator enum for condition check
	 * @param clazz
	 * @return
	 * @throws PersistenceException
	 */
	public <T> List<T> findByCondition(String field ,Object value, OPERATOR operator, String key, OrderBy order, Class<T> clazz) 
			throws PersistenceException {
		try {
			Query query = new Query();
			String condition = field + " " + operator.getName();
			addCondition(condition, value, query);
			if (key != null && order != null) 
				query = getOrder(query, key, order);
			return operation.find(query, clazz);
		} catch (MongoException e) {
			throw new PersistenceException(e);
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
	}

	/**
	 * Drop Collection
	 * @param clazz
	 */
	public void dropCollection(Class<?> clazz) throws PersistenceException {
		try {
			operation.dropCollection(clazz);
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Get all collection name from database 
	 * @return
	 */
	public Set<String> getCollectionsName() throws PersistenceException {
		try {
			return operation.getCollectionNames();
		} catch (DataAccessResourceFailureException e) {
			throw new PersistenceException(e.getMessage());
		} catch (MongoException e) {
			throw new PersistenceException(e.getMessage());
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	/**
	 * Get the collection name
	 * @param entityClass
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String getCollectionName(Class<?> entityClass) {
		if (entityClass == null) 
			throw new InvalidDataAccessApiUsageException("No class parameter provided, entity collection can't be determined!");
		MongoPersistentEntity entity = (MongoPersistentEntity)DBConnection.mappingContext.getPersistentEntity(entityClass);
		if (entity == null) 
			throw new InvalidDataAccessApiUsageException("No Persitent Entity information found for the class " + entityClass.getName());
		return entity.getCollection();
	}

	/**
	 * 
	 * @author suresh chaudhari
	 *
	 */
	public enum OrderBy {
		ASCENDING(1),
		DESCENDING(-1);

		private final int value;

		private OrderBy(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}

	}
	/**
	 * Get the Order query object
	 * @param query
	 * @param key
	 * @param order
	 * @return
	 */
	private Query getOrder(Query query, String key, OrderBy order) {
		if(order == OrderBy.ASCENDING) 
			query.with(new Sort(Sort.Direction.ASC, key));
		else if(order == OrderBy.DESCENDING)
			query.with(new Sort(Sort.Direction.DESC, key));
		return query;
	}

	/**
	 * Convert into POJO object
	 * @param clazz
	 * @param dbObjectList
	 * @return
	 */
	private <T> List<T> convertDbObjectToObject(Class<T> clazz, List<DBObject> dbObjectList) {
		List<T> list = new ArrayList<T>();
		for (DBObject dbObject : dbObjectList) {
			list.add(DBConnection.mongoConverter.read(clazz, dbObject));
		}
		return list;
	}

	/**
	 * Converts the textual operator (">", "<=", etc) into a FilterOperator. Forgiving about the syntax; != and <> are NOT_EQUAL, = and ==
	 * are EQUAL.
	 */
	private FilterOperator translate(final String operator) {
		return FilterOperator.fromString(operator);
	}

	/**
	 * Filter Condtion
	 * @param condition
	 * @param value
	 * @param query
	 * @return
	 */
	private Query addCondition(final String condition, final Object value, Query query) {
		final String[] parts = condition.trim().split(" ");
		if (parts.length < 1 || parts.length > 6) 
			throw new IllegalArgumentException("'" + condition + "' is not a legal filter condition");
		final String prop = parts[0].trim();
		final FilterOperator op = (parts.length == 2) ? translate(parts[1]) : FilterOperator.EQUAL;

		Criteria criteria = createCriteria(prop, value, op);
		query.addCriteria(criteria);
		return query;
	}
	/**
	 * Create Criteria condition
	 * @param prop
	 * @param value
	 * @param op
	 * @return
	 */
	private Criteria createCriteria(final String prop, final Object value, FilterOperator op) {
		if (op == FilterOperator.EQUAL)
			return Criteria.where(prop).is(value);
		else if (op == FilterOperator.NOT_EQUAL)
			return Criteria.where(prop).ne(value);
		else if (op == FilterOperator.GREATER_THAN)
			return Criteria.where(prop).gt(value);
		else if (op == FilterOperator.GREATER_THAN_OR_EQUAL)
			return Criteria.where(prop).gte(value);
		else if (op == FilterOperator.LESS_THAN)
			return Criteria.where(prop).lt(value);
		else if (op == FilterOperator.LESS_THAN_OR_EQUAL)
			return Criteria.where(prop).lte(value);
		else if (op == FilterOperator.IN)
			return Criteria.where(prop).in(value);
		else if (op == FilterOperator.NOT_IN)
			return Criteria.where(prop).nin(value);
		else if (op == FilterOperator.ALL)
			return Criteria.where(prop).all(value);
		else if (op == FilterOperator.SIZE)
			return Criteria.where(prop).size(Integer.parseInt(value.toString()));
		else if (op == FilterOperator.EXISTS)
			return Criteria.where(prop).exists(Boolean.parseBoolean(value.toString()));
		return null;
	}

	public PersistenceService() {

	}

}
