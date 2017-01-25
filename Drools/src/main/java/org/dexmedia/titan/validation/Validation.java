package org.dexmedia.titan.validation;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.dexmedia.titan.bean.Product;
import org.dexmedia.titan.droolsresource.ExecuteDrools;
import org.dexmedia.titan.persistance.ProductServiceImpl;
import org.drools.compiler.compiler.DroolsParserException;
import org.glassfish.jersey.internal.util.Base64;
import org.hibernate.Session;

public class Validation {
	public Validation() {
	}

	Product product = new Product();

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
				errorList = productServiceImplobj.createProductData(input);
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

	@SuppressWarnings("deprecation")
	public boolean isCategoryValid(JSONObject input, Session session) {
		JSONObject content = new JSONObject();
		JSONArray categories = new JSONArray();
		ValidationUtility validationUtility = new ValidationUtility();
		int sum = 0;
		try {
			content = input.getJSONObject("Content");
			categories = content.getJSONArray("CategoryList");
			if (categories != null && categories.length() > 0) {
				for (int i = 0; i < categories.length(); i++) {
					if (StringUtils.isNotBlank(categories.getString(i))) {
						JSONObject category = new JSONObject();
						try {
							category = categories.getJSONObject(i);
							if (category.has("CategoryId")) {
								String hql = "select vz_categoryid from ps_vz_category where vz_categoryid='"
										+ category.getString("CategoryId").trim() + "'";
								List<String> categoryList = new ArrayList<String>();
								categoryList = session.createSQLQuery(hql).list();
								if (categoryList.size() < 0 || categoryList.isEmpty()) {
									validationUtility.adderrors(category.getString("CategoryId").trim() + "\n");
									sum++;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (sum > 0)
			return true;
		else
			return false;
	}

	public boolean pingUrl(JSONObject input) {
		try {
			JSONObject content = new JSONObject();
			content = input.getJSONObject("Content");
			final String address = content.getString("Destination URL");
			System.out.println("url : " + address);

			final URL url = new URL(address);
			final HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setConnectTimeout(1000 * 10);
			final long startTime = System.currentTimeMillis();
			urlConn.connect();
			final long endTime = System.currentTimeMillis();
			if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				System.out.println("Time (ms) : " + (endTime - startTime));
				System.out.println("Ping to " + address + " was success");
				return false;
			}
		} catch (final MalformedURLException e1) {
			e1.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
		return true;
	}

	public boolean itemStatus(String itemId, Session session) {
		if (itemId != null) {
			String hql = "select instance_id FROM product_instance where item_id='" + itemId + "'";
			List<BigDecimal> acountList = new ArrayList<BigDecimal>();
			acountList = session.createSQLQuery(hql).list();

			if (acountList.size() < 0 || acountList.isEmpty()) {
				return true;
			} else
				for (int i = 0; i < acountList.size(); i++) {
					BigDecimal big = acountList.get(i);
					int instid = big.intValue();
					product.setInstanceId(instid);
					System.out.println(product.getInstanceId());

				}
			return false;
		} else
			return false;
	}

	public Product getProductBean() {
		return product;
	}

	public boolean currentDateValidation(JSONObject input) {
		String requestedDate;

		try {
			JSONObject content = new JSONObject();
			content = input.getJSONObject("Content");
			requestedDate = content.getString("Start Date");

			Date dt = new Date();
			Calendar currentDateCal = Calendar.getInstance(TimeZone.getTimeZone("America/Chicago"));
			currentDateCal.setTime(dt);
			currentDateCal.set(Calendar.HOUR_OF_DAY, 0);
			currentDateCal.set(Calendar.MINUTE, 0);
			currentDateCal.set(Calendar.SECOND, 0);
			currentDateCal.set(Calendar.MILLISECOND, 0);
			dt = currentDateCal.getTime();

			Calendar requestDate = Calendar.getInstance(TimeZone.getTimeZone("America/Chicago"));
			requestDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(requestedDate.substring(8, 10)));
			requestDate.set(Calendar.MONTH, Integer.parseInt(requestedDate.substring(5, 7)) - 1);
			requestDate.set(Calendar.YEAR, Integer.parseInt(requestedDate.substring(0, 4)));
			requestDate.set(Calendar.HOUR_OF_DAY, 0);
			requestDate.set(Calendar.MINUTE, 0);
			requestDate.set(Calendar.SECOND, 0);
			requestDate.set(Calendar.MILLISECOND, 0);
			Date reqDt = requestDate.getTime();
			if (dt.after(reqDt))
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}

	public void statusCall(String methodType) {
		try {
			URL url = new URL("http://localhost:8080/DroolsDemo/rest/Drools/externalServiceCall");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod(methodType);
			conn.setRequestProperty("Content-Type", "application/json");
			String userCredentials = "user:password";
			String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
			conn.setRequestProperty("Authorization", basicAuth);
			System.out.println("temp");
			String input1 = "{\"Business Description\":\"Test\",\"Phone Number\":\"80088886000\"}";

			OutputStream os = conn.getOutputStream();
			os.write(input1.getBytes());
			os.flush();
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
