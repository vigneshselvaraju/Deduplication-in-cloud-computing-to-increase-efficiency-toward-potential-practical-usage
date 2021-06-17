package rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.UploadPojo;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import DB.DbQueries;


@Path("qr")
public class Requests {
	@Path("qrcall")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)  
	public JSONObject prrofRequests(JSONObject jsonObject) throws JSONException {
		String filename="";
		String path="";
		String arr[];
		String hash = (String) jsonObject.get("hash");
		System.out.println("<-------cloudserver==hash------>" + hash);
		JSONObject returnObj = new JSONObject();
		UploadPojo qrPojo = new UploadPojo(filename, path, hash);
		
		DbQueries db = new DbQueries();
		String Id = db.hashcheck(hash);
		System.out.println("==========="+Id);
		arr=Id.split("@");
		
		if(arr[0].equalsIgnoreCase("no")){
			returnObj.put("response", arr[0]);
			returnObj.put("filepath", arr[1]);
			
		}else{
			returnObj.put("response", arr[0]);
			returnObj.put("filepath", arr[1]);
			UploadPojo user = new UploadPojo(filename, path, hash);
			System.out.println("Filename" + filename + "-->"
					+ path + "--->" + hash);
		}
		return returnObj;
		
	}
}