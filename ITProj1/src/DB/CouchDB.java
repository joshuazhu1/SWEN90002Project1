package DB;

import org.lightcouch.CouchDbClient;

import Utility.Base64;

import com.google.gson.JsonObject;

public class CouchDB {
	private CouchDbClient dbClient;
	public CouchDB(){
		dbClient = new CouchDbClient("couchdb.properties");
	}
	
	/**
	 * Check the user exists by id
	 * @param email
	 */
	public boolean checkUserExist(String email){
		return dbClient.contains(email);
	}
	
	/**
	 * get the user by id
	 * @param email
	 * @return
	 */
	public JsonObject getUser(String email){
		JsonObject json = dbClient.find(JsonObject.class, email);
		return json;	
	}
	
	public boolean checkUserPasswd(String email, String pswdIn){
		
		JsonObject json = dbClient.find(JsonObject.class, email);
		return ("\""+Base64.EncoderBase64(pswdIn)+"\"").equals(String.valueOf(json.get("passwd")));
		//return MD5Tools.EncoderByMd5(pswdIn).equals(String.valueOf(json.get("password")));		
	}
	
	/**
	 * save a new user
	 * @return
	 */
	public void createUser(String email, String passwd){
		JsonObject  object = new JsonObject ();
		object.addProperty("_id", email);
		String passwdInDB = Base64.EncoderBase64(passwd);
		object.addProperty("passwd", passwdInDB);
		//save the message
		dbClient.save(object);
	}
	
	public void deleteUser(String email){
		JsonObject json = dbClient.find(JsonObject.class, email);
		dbClient.remove(json);
	}
	
	public void updatePasswd(String email, String passwd){
		this.deleteUser(email);
		this.createUser(email, passwd);		
	}
	
	public static String eraseQuotation(String valueInDB){
		int len = valueInDB.length();
		return valueInDB.substring(1,len-1);
		
	}

}
