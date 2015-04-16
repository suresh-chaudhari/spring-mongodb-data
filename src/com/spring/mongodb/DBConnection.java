package com.spring.mongodb;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
/**
 * 
 * @author suresh
 *
 */
public class DBConnection {

	private static final String CONFIG_FILE_LOCATION= "conf/MongoPersistenceService.xml";
	private static final String MONGO_TEMPLATE_ID_NAME= "MongoOpetation";
	private static final String MONGO_CONVERTER_ID_NAME= "mappingContext";
	private static final String MONGO_CONVERTER_ID= "mappingMongoConverter";

	private static MongoOperations mongoOperations = null;
	public static MongoMappingContext mappingContext;
	public static MongoConverter mongoConverter;

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("resource")
	public static MongoOperations getConnection(){
		if(mongoOperations!=null)
			return mongoOperations;
		ApplicationContext ctx = new ClassPathXmlApplicationContext(CONFIG_FILE_LOCATION);
		mongoOperations = (MongoOperations) ctx.getBean(MONGO_TEMPLATE_ID_NAME);
		mappingContext = (MongoMappingContext) ctx.getBean(MONGO_CONVERTER_ID_NAME);
		mongoConverter = (MongoConverter) ctx.getBean(MONGO_CONVERTER_ID);
		return mongoOperations;
	}

	private DBConnection(){

	}

}
