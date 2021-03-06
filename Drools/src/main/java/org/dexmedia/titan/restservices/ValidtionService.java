package org.dexmedia.titan.restservices;

import java.io.IOException;
import java.text.ParseException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
	public Response DroolsDemoPost(String json)
			throws JSONException, DroolsParserException, IOException, ParseException {
		JSONObject input = new JSONObject(json);
		Validation ValidationDrools = new Validation();
		return ValidationDrools.ValidationDrools(input);
	}

	@POST
	@Path("/externalServiceCall")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertProductPOST(String json)
			throws JSONException, DroolsParserException, IOException, ParseException {
		System.out.println("ResponsPOST");
		return Response.status(Response.Status.OK).entity("ResponsPOST").build();
	}

	@PUT
	@Path("/externalServiceCall")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertProductPUT(String json)
			throws JSONException, DroolsParserException, IOException, ParseException {
		System.out.println("ResponsPUT");
		return Response.status(Response.Status.OK).entity("ResponsPUT").build();
	}
}
