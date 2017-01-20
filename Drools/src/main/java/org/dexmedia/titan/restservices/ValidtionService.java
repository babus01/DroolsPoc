package org.dexmedia.titan.restservices;

import java.io.IOException;
import java.text.ParseException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.dexmedia.titan.validation.Validation;
import org.drools.compiler.compiler.DroolsParserException;

@Path("Drools")
public class ValidtionService {
	@POST	
	@Path("/Validate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public Response DroolsDemoPost(String json) throws JSONException, DroolsParserException, IOException, ParseException {
		JSONObject input = new JSONObject(json);
		Validation ValidationDrools = new Validation();
		return ValidationDrools.ValidationDrools(input);
	}
	
	/*@POST
	@Path("/insertProduct")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public Response insertProduct(String json) throws JSONException, DroolsParserException, IOException, ParseException {
		JSONObject input = new JSONObject(json);
		ProductServiceImpl productServiceImplobj = new ProductServiceImpl();
		return productServiceImplobj.createProductData(input);
	}*/
}
