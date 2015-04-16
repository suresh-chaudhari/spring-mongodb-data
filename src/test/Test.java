package test;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Query;

import com.dataobject.Employee;
import com.dataobject.User;
import com.mongodb.BasicDBObject;
import com.spring.mongodb.PersistenceService;
import com.spring.mongodb.PersistenceService.OrderBy;
import com.spring.mongodb.QueryConstant.OPERATOR;
import com.spring.mongodb.PersistenceException;

/**
 * 
 * @author suresh
 *
 */
public class Test {
	private static PersistenceService persistenceService = new PersistenceService();

	public static void main(String[] args) {
		try {
//			addorUpdateSingleObject();
//			addMultipleObjects();
//			fetchRecords();
//			checkFindOneMethod();
//			checkFindMethod();
//			getListPaging();
//			getListPagingNative();
//			getListbyNativeQuery();
//			deleteRecords();
			System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * add single object
	 * @throws PersistenceException 
	 */
	private static void addorUpdateSingleObject() throws PersistenceException {
		User user = new User();
		user.setFirstName("suresh");
		user.setLastName("chaudhari");
		user.setCreateDate(System.currentTimeMillis());
//		user.setId(new ObjectId("5405a2b93894e9be5e6f2430"));

		/**
		 * add single object
		 */
		persistenceService.addSingleObject(user);
		System.out.println("Object added successfully");
		
		
		/**
		 * Update Single object with ID exist
		 * 
		 */
		user.setFirstName("Update test");
		persistenceService.addOrUpdateObject(user, User.class);
	}

	/**
	 * add single object
	 * @throws PersistenceException 
	 */
	
	private static void addMultipleObjects() throws PersistenceException {
		/**
		 * Create new document
		 */
		User user = new User();
		user.setFirstName("karan");
		user.setLastName("patel");

		/**
		 * If objectId exist then it updates document
		 */
		Employee employee = new Employee();
		employee.setName("suresh");
		employee.setDesignation("Software Engineer");
		employee.setEmpId(1);
		/**
		 * add Multiplse objects
		 */
		
		List<Object> list = new ArrayList<Object>();
		list.add(user);
		list.add(employee);
		
		//add multiple objects
		persistenceService.addObjects(list);
		System.out.println("Mulitple Objects added successfully");
	}

	/**
	 * fetch all Records
	 * @throws PersistenceException
	 */
	private static void fetchRecords() throws PersistenceException {
		//fetch all documents
		List<User> user = persistenceService.getRecordsList(User.class);
		for (User obj : user) {
			System.out.println(obj);
		}
	}

	/**
	 * fetch all Records
	 * @throws PersistenceException
	 */
	private static void checkFindOneMethod() throws PersistenceException {
		User user = persistenceService.findOne("first_name", "test", User.class);
		System.out.println(user);

		//findOne by Condition operator <>,>=
		Employee employee = persistenceService.findOneByCondition("empId", 1,OPERATOR.LESS_THAN_EQUAL, null, null,Employee.class);
		System.out.println(employee);
	}

	/**
	 * check the find methods for persistence service
	 * @throws PersistenceException
	 */
	private static void checkFindMethod() throws PersistenceException {
		PersistenceService persistenceService = new PersistenceService();
		List<User> user =  persistenceService.find("first_name", "suresh", User.class);
		System.out.println(user);

		
		//find By Order
		List<User> orderUser =  persistenceService.findByOrder("first_name", "suresh", "_id",OrderBy.DESCENDING, User.class);
		System.out.println(orderUser);
		
		//find by Condition operator <>,>=
		List<Employee> employee = persistenceService.findByCondition("empId", 0, OPERATOR.GREATER_THAN, null, null, Employee.class);
		System.out.println(employee);
	}

	/**
	 * fetch all Records By pagination
	 * @throws PersistenceException
	 */
	private static void getListPaging() throws PersistenceException {
		Query query = new Query();
		List<User> user =  persistenceService.getListByPaging(query, 1, 2, null, null, User.class);
		for (User obj : user) {
			System.out.println(obj.getId());
		}
	}
	
	/**
	 * fetch all Records By pagination
	 * @throws PersistenceException
	 */
	private static void getListPagingNative() throws PersistenceException {
		List<User> user =  persistenceService.getListByNativeQuery(new BasicDBObject(),null,null,3, User.class);
		for (User obj : user) {
			System.out.println(obj.getId());
		}
	}
	
	/**
	 * Get documents by using Native query (Mongo Java driver api)
	 * @throws PersistenceException
	 */
	private static void getListbyNativeQuery() throws PersistenceException {
		BasicDBObject query = new BasicDBObject();
		query.append("first_name", "suresh");
		List<User> user =  persistenceService.getListPagingByNativeQuery(query, 1, 2, "_id" , OrderBy.ASCENDING, User.class);
		for (User obj : user) {
			System.out.println(obj.getId());
		}
		
		/**
		 * Projection
		 * Include use-1
		 * Excluse use-0
		 */
		
		BasicDBObject projection = new BasicDBObject("first_name",1);
		List<User> user1 =  persistenceService.getListByProjection(query, projection,null,null,User.class);
		for (User obj : user1) {
			System.out.println(obj.getId() +" firstname:"+obj.getFirstName() + " Lastname:"+obj.getLastName());
		}
	}
	/**
	 * Delete documents form collection
	 * @throws PersistenceException
	 */
	private static void deleteRecords() throws PersistenceException {
//		persistenceService.deleteRecord("first_name", "karan12", User.class);
		
		BasicDBObject query = new BasicDBObject("first_name", "karan1");
		persistenceService.deleteRecordByNativeQuery(query, User.class);
	}

}
