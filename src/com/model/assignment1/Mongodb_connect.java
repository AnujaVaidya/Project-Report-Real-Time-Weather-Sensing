package com.model.assignment1;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Mongodb_connect {


	MongoClient project281Sensor = new MongoClient();
	MongoDatabase server281Final = project281Sensor.getDatabase("Server281Final");

	Gson gson = new Gson();
	
	//Map<Integer,String> mapBootstrapInfo = new HashMap<Integer,String>();

	public static void main(String args[]){
		Mongodb_connect db = new Mongodb_connect();
	}

/*	public List<ValueDb> getResourceValue (Integer objId, Integer objInstanceId, Integer resourceId ){
		System.out.println("calling db");
		FindIterable<Document> iterable = null;
		if (objInstanceId == null && resourceId ==  null){
			iterable = assignment2ClientDb.getCollection("valueDb").find(
					new Document(OBJID, objId ));
		} else if(objInstanceId != null && resourceId ==  null){
			iterable = assignment2ClientDb.getCollection("valueDb").find(
					new Document(OBJID, objId ).append(OBJINSTANCEID, objInstanceId));
			if(iterable != null && iterable.first() != null) {
			}
		} else{
			iterable = assignment2ClientDb.getCollection("valueDb").find(
					new Document(OBJID, objId ).append(OBJINSTANCEID, objInstanceId).append(RESOURCEID, resourceId));
		}
		List<ValueDb> valueDbList = new ArrayList<ValueDb>();
		if(iterable != null) {
			for(Document document: iterable) {
				valueDbList.add(gson.fromJson(document.toJson(), ValueDb.class));
			}
		}
		return valueDbList;
	}


	
		public List<ResourceAtr> getResources (Integer objId, Integer objInstanceId, Integer resourceId ){
		System.out.println("calling db");
		FindIterable<Document> iterable = null;
		if (objInstanceId == null && resourceId ==  null){
			iterable = assignment2ClientDb.getCollection("resourceAtr").find(
					new Document(OBJID, objId ));
		} else if(objInstanceId != null && resourceId ==  null){
			iterable = assignment2ClientDb.getCollection("resourceAtr").find(
					new Document(OBJID, objId ).append(OBJINSTANCEID, objInstanceId));
			
		} else{
			iterable = assignment2ClientDb.getCollection("resourceAtr").find(
					new Document(OBJID, objId ).append(OBJINSTANCEID, objInstanceId).append(RESOURCEID, resourceId));
		}
		List<ResourceAtr> ResourceAtrDbList = new ArrayList<ResourceAtr>();
		if(iterable != null) {
			for(Document document: iterable) {
				ResourceAtrDbList.add(gson.fromJson(document.toJson(), ResourceAtr.class));
			}
		}
		return ResourceAtrDbList;
	}

	
	
	public void writeValue(Integer objId,Integer objInstanceId, Integer resourceId, Integer value){
		
		MongoCollection<Document> collection =  assignment2ClientDb.getCollection("valueDb");
		if (resourceId != null){
			collection.updateOne(new Document(OBJID, objId).append(OBJINSTANCEID, objInstanceId).append(RESOURCEID, resourceId),new Document().append("$set", new Document(VALUE,value)));			
		}else{
			collection.updateMany(new Document(OBJID, objId).append(OBJINSTANCEID, objInstanceId),new Document().append("$set", new Document(VALUE,value)));	
		}
	}

	public ResourceAtr getResourceAttribute (Integer objId, Integer objInstanceId, Integer resourceId ){
		FindIterable<Document> iterable = null;
		if (objId != null && objInstanceId != null && resourceId !=  null){
			iterable = assignment2ClientDb.getCollection("resourceAtr").find(
					new Document(OBJID, objId ));
		} else if(objInstanceId != null && objId ==  null){
			iterable = assignment2ClientDb.getCollection("objInstanceAtr").find(
					new Document(OBJID, objId ).append(OBJINSTANCEID, objInstanceId));
		
		} else if(objId != null){
			iterable = assignment2ClientDb.getCollection("objAtr").find(
					new Document(OBJID, objId ).append(OBJINSTANCEID, objInstanceId).append(RESOURCEID, resourceId));
		}
		if(iterable != null && iterable.first() != null) {
				return gson.fromJson(iterable.first().toJson(), ResourceAtr.class);
		}
		return null;
	}

	public void writeAtr( ResourceAtr resourceAtr ){
			Integer resourceId = resourceAtr.getResourceId();
			Integer objId = resourceAtr.getObjId();
			Integer objInstanceId = resourceAtr.getObjInstanceId();
			ResourceAtr prevResourceAtr = getResourceAttribute(objId, objInstanceId, resourceId);
		if (objId != null && objInstanceId != null && resourceId != null ){
			MongoCollection<Document> collection =  assignment2ClientDb.getCollection("resourceAtr");
			if(prevResourceAtr != null) {
				ResourceAtr newResourceAtr = ResourceAtr.map(prevResourceAtr, resourceAtr);
				//delete
				collection.deleteMany(new Document(OBJID, objId).append(OBJINSTANCEID, objInstanceId).append(RESOURCEID, resourceId));
				//create
				collection.insertOne(gson.fromJson(gson.toJson(newResourceAtr), Document.class ));
			}
		} else if (objId != null && objInstanceId != null){
			MongoCollection<Document> collection =  assignment2ClientDb.getCollection("objInstanceAtr");
			if(prevResourceAtr != null) {
				ResourceAtr newResourceAtr = ResourceAtr.map(prevResourceAtr, resourceAtr);
				//delete
				collection.deleteMany(new Document(OBJID, objId).append(OBJINSTANCEID, objInstanceId));
				//create
				collection.insertOne(gson.fromJson(gson.toJson(newResourceAtr), Document.class ));
			}
		} else if (objId != null) {
			MongoCollection<Document> collection =  assignment2ClientDb.getCollection("objAtr");
			if(prevResourceAtr != null) {
				ResourceAtr newResourceAtr = ResourceAtr.map(prevResourceAtr, resourceAtr);
				//delete
				collection.deleteMany(new Document(OBJID, objId));
				//create
				collection.insertOne(gson.fromJson(gson.toJson(newResourceAtr), Document.class ));
			}
		}
	}
	
	public String checkExecute(Integer objId,Integer objInstanceId,Integer resourceId){
		
		MongoCollection<Document> collection =  assignment2ClientDb.getCollection("resourceAtr");
		FindIterable<Document> iterable = null;
		iterable = assignment2ClientDb.getCollection("resourceAtr").find(new Document(OBJID, objId ).append(OBJINSTANCEID, objInstanceId).append(RESOURCEID, resourceId));
		if(iterable != null && iterable.first() != null) {
			return "Excute Successful";
		}else{
			return "Excute Unsuccessful";
		}
	}
	
	public void insert(ResourceAtr resourceAtr){
		
		MongoCollection<Document> collection =  assignment2ClientDb.getCollection("resourceAtr");
		collection.updateOne(new Document("objId",resourceAtr.getObjId() ).append("objInstanceId", resourceAtr.getObjInstanceId()).append("resourceId", resourceAtr.getResourceId()),new Document().append("$set", new Document("cancel",resourceAtr.getCancel())));
	
	}
	
	
	public String deleteInstance(Integer objId, Integer objInstanceId){
		
		MongoCollection<Document> collection =  assignment2ClientDb.getCollection("resourceAtr");
		MongoCollection<Document> collectionInstance =  assignment2ClientDb.getCollection("objInstanceAtr");
		MongoCollection<Document> collectionValue =  assignment2ClientDb.getCollection("valueDb");
		FindIterable<Document> iterable = null;
		iterable = assignment2ClientDb.getCollection("resourceAtr").find(new Document(OBJID, objId ).append(OBJINSTANCEID, objInstanceId));
		if(iterable != null && iterable.first() != null) {
			collection.deleteMany(new Document(OBJID, objId).append(OBJINSTANCEID, objInstanceId));
			collectionValue.deleteMany(new Document(OBJID,objId).append(OBJINSTANCEID, objInstanceId));
			iterable = assignment2ClientDb.getCollection("objInstanceAtr").find(new Document(OBJID, objId ).append(OBJINSTANCEID, objInstanceId));
			if(iterable != null && iterable.first() != null){
			collectionInstance.deleteMany(new Document(OBJID,objId).append(OBJINSTANCEID, objInstanceId));
			}
		return "Instance Deleted";	
		}else{
		return "Instance doesn't exist";	
		}
	}
	
	
	public String createObjectInstance(Integer objId, Integer objInstanceIdServer,Integer value){
		
		MongoCollection<Document> collection =  assignment2ClientDb.getCollection("resourceAtr");
		MongoCollection<Document> collectionValue =  assignment2ClientDb.getCollection("valueDb");
		FindIterable<Document> iterable = null;
		ResourceAtr resourceAtr = new ResourceAtr();
		ValueDb valueDb = new ValueDb();
		resourceAtr.setCancel(null);
		resourceAtr.setGreaterThan(null);
		resourceAtr.setLessThan(null);
		resourceAtr.setMaxPeriod(null);
		resourceAtr.setMinPeriod(null);
		resourceAtr.setStep(null);
		resourceAtr.setResourceId(1);
		if(objInstanceIdServer != null){
			iterable = assignment2ClientDb.getCollection("resourceAtr").find(new Document(OBJID, objId ).append(OBJINSTANCEID, objInstanceIdServer));
			if(iterable != null && iterable.first() != null) {
				return "Instance already exists";
			}else{
				resourceAtr.setObjId(objId);
				resourceAtr.setObjInstanceId(objInstanceIdServer);
				collection.insertOne(gson.fromJson(gson.toJson(resourceAtr), Document.class ));
				valueDb.setObjId(objId);
				valueDb.setObjInstanceId(objInstanceIdServer);
				valueDb.setResourceId(1);
				valueDb.setValue(value);
				collectionValue.insertOne(gson.fromJson(gson.toJson(valueDb), Document.class ));
				return "New Instance created";
			}
		}else{
			iterable = assignment2ClientDb.getCollection("resourceAtr").find(new Document(OBJID, objId ));
			if(iterable != null){
				int max = 0;
				for(Document document : iterable) {
					ResourceAtr atr = gson.fromJson(document.toJson(), ResourceAtr.class);
					if(atr.getObjInstanceId() > max) {
						max = atr.getObjInstanceId();
					}
				}
				max++;
				
				resourceAtr.setObjInstanceId(max);
				valueDb.setObjInstanceId(max);
			}else{
				
				resourceAtr.setObjInstanceId(0);
				valueDb.setObjInstanceId(0);
			}
			resourceAtr.setObjId(objId);
			collection.insertOne(gson.fromJson(gson.toJson(resourceAtr), Document.class ));
			valueDb.setObjId(objId);
			valueDb.setResourceId(1);
			valueDb.setValue(value);
			collectionValue.insertOne(gson.fromJson(gson.toJson(valueDb), Document.class ));
			return "New Instance created";
		}
	}
	
	
	
	
/*

	public void updateFlag(int clientId, String flag){

		MongoCollection<Document> collection =  assignment2ClientDb.getCollection("bootstrapServer");
		collection.updateOne(new Document("clientId", clientId),new Document().append("$set", new Document("bootstrapFlag",flag)));

	}



	public String registerOnServer( Registration reg ){

		String result = "create Successful";
		try{
			MongoCollection<Document> collectionReg =  mongoDbRegister.getCollection("clientRegister");
			collectionReg.insertOne(gson.fromJson(gson.toJson(reg), Document.class ));	
		}
		catch( Exception mwe){
			mwe.printStackTrace();
			result = "Creation Failed";

		}
		return result;
	}

	public String updateOnServer(Registration reg){


		String result = "Update Successful";

		try{
			MongoCollection<Document> collectionReg =  mongoDbRegister.getCollection("clientRegister");
			//collectionReg.deleteOne(new Document("clientId", reg.getClientId()));
			collectionReg.updateOne(new Document("clientId", reg.getClientId()),new Document().append("$set", gson.fromJson(gson.toJson(reg), Document.class )));
			//collectionReg.insertOne(gson.fromJson(gson.toJson(reg), Document.class ));
		}
		catch(Exception ex){

			result = "Update Failed";

		}
		return result;

	}

	public String deleteFromServer(int clientId){

		String result = "Delete Successful";
		try{
			MongoCollection<Document> collectionReg =  mongoDbRegister.getCollection("clientRegister");
			collectionReg.deleteMany(new Document("clientId", clientId));
			//updateFlag(1, "n");
		}
		catch(Exception ex){

			result = "Delete Failed";
		}
		return result;

	}*/

}



