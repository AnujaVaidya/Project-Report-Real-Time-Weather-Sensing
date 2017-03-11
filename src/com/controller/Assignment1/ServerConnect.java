/*package com.controller.Assignment1;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.assignment1.*;

@Controller
public class ServerConnect {

	Mongodb_connect db = new Mongodb_connect();
	NotifyThread NT = new NotifyThread();
	public ServerConnect(){
		NT.start();
	}

	@RequestMapping(value = "/management/read/{objId}", method = RequestMethod.GET)
	public @ResponseBody List<ValueDb> readObject (@PathVariable Integer objId){
		return db.getResourceValue(objId, null, null); 
	}
	@RequestMapping(value = "/management/read/{objId}/{objInstanceId}", method = RequestMethod.GET)
	public @ResponseBody List<ValueDb> readObjectInstance (@PathVariable Integer objId, @PathVariable Integer objInstanceId){
		return db.getResourceValue(objId, objInstanceId, null); 
	}
	@RequestMapping(value = "/management/read/{objId}/{objInstanceId}/{resourceId}", method = RequestMethod.GET)
	public @ResponseBody List<ValueDb> readValue (@PathVariable Integer objId, @PathVariable Integer objInstanceId, @PathVariable Integer resourceId ){
		return db.getResourceValue(objId, objInstanceId, resourceId); 
	}

	@RequestMapping(value = "/management/write", method = RequestMethod.POST)
	public @ResponseBody String writeValue(@RequestBody ValueDb valDb){
		db.writeValue(valDb.getObjId(),valDb.getObjInstanceId(),valDb.getResourceId(), valDb.getValue());
		return "Update Successful";
	}
	
	@RequestMapping(value = "/management/writeAtr", method = RequestMethod.POST)
	public @ResponseBody String writeValue(@RequestBody ResourceAtr resourceAtr){
		db.writeAtr(resourceAtr);
		return "Update Successful";
	}
*/
/*	@RequestMapping(value = "/reporting/observe", method = RequestMethod.POST)
	public @ResponseBody String observe(@RequestBody ResourceAtr resourceAtr){
		resourceAtr.setCancel(0);
		db.writeAtr(resourceAtr);
		return "Update Successful";
	}
	
	@RequestMapping(value = "/reporting/cancelObserve", method = RequestMethod.POST)
	public @ResponseBody String cancelObserve(@RequestBody ResourceAtr resourceAtr){
		resourceAtr.setCancel(1);
		db.writeAtr(resourceAtr);
		return "Update Successful";
	}*/
	/*
	@RequestMapping(value = "/reporting/observe", method = RequestMethod.POST)
	public @ResponseBody String observe(@RequestBody ResourceAtr resourceAtr){
		resourceAtr.setCancel(0);
		db.insert(resourceAtr);
		return "Update Successful";
	}
	
	@RequestMapping(value = "/reporting/cancelObserve", method = RequestMethod.POST)
	public @ResponseBody String cancelObserve(@RequestBody ResourceAtr resourceAtr){
		resourceAtr.setCancel(1);
		db.insert(resourceAtr);
		return "Update Successful";
	}
	
	@RequestMapping(value = "/management/discover/{objId}", method = RequestMethod.GET)
	public @ResponseBody String discoverObject (@PathVariable Integer objId){
		String response = "";
		List<ResourceAtr> resourceAttributes = db.getResources(objId, null, null); 
		ResourceAtr objAtr = db.getResourceAttribute(objId, null, null);
		response += "</"+objId+">;pmin="+objAtr.getMinPeriod()+";pmax="+objAtr.getMaxPeriod();
		for(ResourceAtr resource : resourceAttributes ){
			response += ", </"+resource.getObjId()+"/"+resource.getObjInstanceId()+"/"+resource.getResourceId()+">"; 
		}
		System.out.println(response);
		return response;
	}
	
	@RequestMapping(value = "/management/discover/{objId}/{objInstanceId}", method = RequestMethod.GET)
	public @ResponseBody String discoverObjectInstance (@PathVariable Integer objId, @PathVariable Integer objInstanceId){
		String response = "";
		List<ResourceAtr> resourceAttributes = db.getResources(objId, objInstanceId, null); 
		ResourceAtr objAtr = db.getResourceAttribute(objId,objInstanceId, null);
		response += "</"+objId+"/"+objInstanceId+">;pmin="+objAtr.getMinPeriod()+";pmax="+objAtr.getMaxPeriod();
		for(ResourceAtr resource : resourceAttributes ){
			response += ", </"+resource.getObjId()+"/"+resource.getObjInstanceId()+"/"+resource.getResourceId()+">"; 
		}
		System.out.println(response);
		return response;
	}
	
	@RequestMapping(value = "/management/create/{objId}/{value}", method = RequestMethod.PUT)
	public @ResponseBody String create(@PathVariable Integer objId, @PathVariable Integer value ){
		return db.createObjectInstance(objId, null,value );			
	}
	
	@RequestMapping(value = "/management/create/{objId}/{objInstanceId}/{value}", method = RequestMethod.PUT)
	public @ResponseBody String create(@PathVariable Integer objId,@PathVariable Integer objInstanceId, @PathVariable Integer value ){
		return db.createObjectInstance(objId,objInstanceId,value );			
	}
	
	@RequestMapping(value = "/management/delete/{objId}/{objInstanceId}", method = RequestMethod.DELETE)
	public @ResponseBody String delete(@PathVariable int objId,@PathVariable int objInstanceId){
		return db.deleteInstance(objId, objInstanceId);			
	}

	@RequestMapping(value = "/management/execute/{objId}/{objInstanceId}/{resourceId}", method = RequestMethod.GET)
	public @ResponseBody String execute(@PathVariable Integer objId, @PathVariable Integer objInstanceId, @PathVariable Integer resourceId ){
		return db.checkExecute(objId, objInstanceId, resourceId);
	}
	
	@RequestMapping(value = "/management/execute/{objId}/{objInstanceId}", method = RequestMethod.GET)
	public @ResponseBody String execute(@PathVariable Integer objId, @PathVariable Integer objInstanceId){
		return "Excute Unsuccessful";
	}
	
	@RequestMapping(value = "/management/execute/{objId}", method = RequestMethod.GET)
	public @ResponseBody String execute(@PathVariable Integer objId){
		return "Excute Unsuccessful";
	}
	
	@RequestMapping(value = "/management/discover/{objId}/{objInstanceId}/{resourceId}", method = RequestMethod.GET)
	public @ResponseBody String discoverValue (@PathVariable Integer objId, @PathVariable Integer objInstanceId, @PathVariable Integer resourceId ){
		
		String response = "";
		List<ResourceAtr> resourceAttributes = db.getResources(objId,objInstanceId,resourceId);  
		ResourceAtr objAtr = db.getResourceAttribute(objId,objInstanceId, resourceId);
		response += "</"+objId+"/"+objInstanceId+"/"+resourceId+">;pmin="+objAtr.getMinPeriod()+";pmax="+objAtr.getMaxPeriod();
		for(ResourceAtr resource : resourceAttributes ){
			response += ", </"+resource.getObjId()+"/"+resource.getObjInstanceId()+"/"+resource.getResourceId()+">"; 
		}
		System.out.println(response);
		return response;
	}

*/

	
	
/*	
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public @ResponseBody String updateTheClient(@RequestBody Registration reg){
		return db.updateOnServer(reg);			
	}

	@RequestMapping(value = "/delete/{clientId}", method = RequestMethod.DELETE)
	public @ResponseBody String deleteTheClient(@PathVariable int clientId){
		return db.deleteFromServer(clientId);			
	}
}

*/







