package com.controller.Assignment1;

//import ClientData;





import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.bson.Document;

import com.google.gson.Gson;
import com.model.assignment1.Mongodb_connect;
import com.model.assignment1.SensorData;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

public class NotifyThread extends Thread{
	Mongodb_connect db = new Mongodb_connect();
	MongoClient project281Sensor = new MongoClient();
	Gson gson = new Gson();
	MongoDatabase client281Final = project281Sensor.getDatabase("Client281Final");			
	public void run(){
		while(true){ 
			
			/*FindIterable<Document> iterableSensors = null;
			iterableSensors = client281Final.getCollection("sensorData").find(new Document());

			for(Document document : iterableSensors){
				
				int humidityRandom;
				int tempRandom;
				int windRandom;
				int precipitationRandom;	
				
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				//Date date = new Date();
				Calendar cal = Calendar.getInstance();
				
				Random randomGenerator = new Random();

				humidityRandom = randomGenerator.nextInt((83-59)+1)+59;
				tempRandom = randomGenerator.nextInt((71-50)+1)+50;
				windRandom = randomGenerator.nextInt(17-10+1)+10;
				precipitationRandom = randomGenerator.nextInt(16);

				SensorData sd = gson.fromJson(document.toJson(), SensorData.class);
				
				//sd.setDay(dateFormat.format(date));
				sd.setTimeOfDay(dateFormat.format(cal.getTime()));
				sd.setHumidity(humidityRandom);
				sd.setPrecipitation(precipitationRandom);
				sd.setTemp(tempRandom);
				sd.setWind(windRandom);
				
				MongoCollection<Document> sensorDataCollection = client281Final.getCollection("sensorData");
				sensorDataCollection.updateOne(new Document("sensorId",sd.getSensorId()),new Document().append("$set",new Document("humidity",sd.getHumidity()).append("wind", sd.getWind()).append("precipitation", sd.getPrecipitation()).append("temp", sd.getTemp())));
				
			}*/
			
			String openWeatherMapKey= "75ef9ab7d7c56a349a24c9165e5347f0";
			
			//int[] zipcode = new int[] {94089, 95002, 95013, 95050, 95054, 95110, 95111, 95112, 95113, 95116, 95118, 95119, 95120, 95121, 95122, 95123, 95126, 95129, 95130, 95131, 95134, 95135, 95136, 95138, 95139, 95140, 95148};
			String[] cityNames = new String[] {"SanJose", "SantaClara", "milpitas", "mountainview", "paloAlto", "fremont" };
			int zipcode[] = new int[50];
			for(int j =0; j < cityNames.length;j++ ){
				
				try {
					
					zipcode = getOnSensor(cityNames[j]);
					System.out.println(zipcode);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				for (int i=0; i<zipcode.length; i++){				
					final String uriOpenApi = "http://api.openweathermap.org/data/2.5/weather?zip="+zipcode[i]+",us" + "&APPID=" + openWeatherMapKey;
					HttpClient client = new DefaultHttpClient();
					HttpGet get = new HttpGet(uriOpenApi);
					HttpResponse response = null;
					try {
						response = client.execute(get);
						BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
						String toString = "";
						String jsonResp = "";
						ZipcodeApiCall zac = new ZipcodeApiCall(); 
						while ((toString = reader.readLine()) != null) {
							jsonResp += toString;
						}
						System.out.println(jsonResp);
						zac = gson.fromJson(jsonResp, ZipcodeApiCall.class);
						zac.setZipcode(zipcode[i]);
					
						Date date = new Date(System.currentTimeMillis());
						zac.setDay(date.toGMTString());
						zac.setTimeOfDay(String.valueOf(date.getTime()));
						updateServerData(zac);
						MongoCollection<Document> sensorDataCollection = client281Final.getCollection("sensorData");
						sensorDataCollection.updateOne(new Document("zipcode", zac.getZipcode()),new Document().append("$set", gson.fromJson(gson.toJson(zac), Document.class )), new UpdateOptions().upsert(true));
				
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				
				}
				try {
					sleep(2*60*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// Sleep for some time before making next time.
			try {
				int minute = 30 * 60 * 1000;
				sleep(minute);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}			
	}	
		
		public static void main(String args[]){
			
			NotifyThread T = new NotifyThread();
			
				T.start();
			
		}
		
		public void updateServerData(ZipcodeApiCall zac) throws ClientProtocolException, IOException{
			
			final String uriSensorUpdate = "http://localhost:8080/FinalProject281Server/sensor";
			
				HttpClient sensorClient = new DefaultHttpClient();
				HttpPost post = new HttpPost(uriSensorUpdate);
				
				StringEntity input = new StringEntity(gson.toJson(zac)); // { "clientId":1, "data":"value2"}
				System.out.println(gson.toJson(zac));
				post.setEntity(input);
				input.setContentType("application/json");
				sensorClient.execute(post);
		}
	
		public int[] getOnSensor(String city) throws ClientProtocolException, IOException{
			
			final String uriOnSensors = "http://localhost:8080/FinalProject281Server/onSensors/"+city;
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(uriOnSensors);
			HttpResponse response = client.execute(get);
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String toString = "";
			String jsonResp = "";
			int[] zipcodes= new int[50];
			int i =0;
			//System.out.println(reader.toString());
			while ((toString = reader.readLine()) != null) {
				//zipcodes[i++]= Integer.parseInt(toString);
				jsonResp += toString;
			}
			zipcodes = gson.fromJson(jsonResp, int[].class);
			System.out.println("Json response: "+jsonResp);
			//System.out.println("ZipCode Array: ");
			
			return zipcodes;
			//da.insertClientData(gson.fromJson(jsonResp, ClientData.class));
			//System.out.println("Client initiated bootstrap for " + device + " Successful");
	
		}
}