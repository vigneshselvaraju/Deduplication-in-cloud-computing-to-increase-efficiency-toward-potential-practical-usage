package rest;

import java.io.IOException;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import rest.ReadProperty;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;

public class ServerCall {
	static WebResource service = null;

	public ServerCall() throws IOException {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		client.addFilter(new LoggingFilter());
		ReadProperty property = new ReadProperty();
		String url = property.getURLDetails();
		service = client.resource(url);
	}
	public JSONObject sendHash(JSONObject jsonObject)throws JSONException {
		JSONObject sendJson = service.path("services").path("qr").path("qrcall")
		.accept(MediaType.APPLICATION_JSON).type(
				MediaType.APPLICATION_JSON).post(JSONObject.class,
				jsonObject);
return sendJson;
	}

}
