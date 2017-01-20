package org.dexmedia.titan.validation;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.dexmedia.titan.droolsresource.ExecuteDrools;
import org.dexmedia.titan.persistance.ProductServiceImpl;
import org.drools.compiler.compiler.DroolsParserException;
import org.hibernate.Session;

public class Validation {

	public Response ValidationDrools(JSONObject input)
			throws JSONException, DroolsParserException, IOException, ParseException {
		JSONObject response = new JSONObject();
		ExecuteDrools ExecuteDrools = new ExecuteDrools();
		List<String> errorList = new ArrayList<String>();
		ValidationUtility ValidationUtility = new ValidationUtility();

		ExecuteDrools.execute(input, ValidationUtility);
		if (ValidationUtility.getErrorlist().size() > 0) {
			response.put("errors", ValidationUtility.getErrorlist());
			return Response.status(200).entity(response.toString()).build();
		} else {
			ProductServiceImpl productServiceImplobj = new ProductServiceImpl();
			try {
				errorList =productServiceImplobj.createProductData(input);
				if (errorList.size() > 0) {
					response.put("errors", StringUtils.join(errorList, ','));
					return Response.status(200).entity(response.toString()).build();
				}
			} catch (Exception e) {

				e.printStackTrace();
				response.put("error", e.getMessage());
				return Response.status(200).entity(response.toString()).build();
			}
			response.put("isSaveSuccessful", true);
			return Response.status(Response.Status.OK).entity(response.toString()).build();
		}
	}
	
	public boolean isCategoryValid(String categoryId,Session session)
	{
		String hql = "select vz_categoryid from ps_vz_bizcateg where vz_categoryid='" +categoryId + "'";
		// Query result = session.createQuery(hql.toString());
		List<String> categoryList = new ArrayList<String>();
		categoryList = session.createSQLQuery(hql).list();
		if(categoryList.size()>0)
		return true;
		else
		return false;
	}

}
