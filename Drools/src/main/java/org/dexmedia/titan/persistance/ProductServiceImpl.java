package org.dexmedia.titan.persistance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.dexmedia.titan.bean.Product;
import org.dexmedia.titan.bean.ProductCategory;
import org.dexmedia.titan.validation.Validation;
import org.hibernate.Session;

public class ProductServiceImpl {
	@SuppressWarnings("deprecation")
	public List<String> createProductData(JSONObject input) throws Exception {
		JSONObject content = new JSONObject();
		JSONObject Producthdr = new JSONObject();
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Product product = new Product();
		ProductCategory productcategory = new ProductCategory();
		List<String> validationList = new ArrayList<String>();
		Validation validationObj = new Validation();
		try {		
			content = input.getJSONObject("Content");
			Producthdr = input.getJSONObject("ProductHeader");
			
			
			Calendar requestDate = Calendar.getInstance(TimeZone.getTimeZone("America/Chicago"));
            requestDate.set(Calendar.DAY_OF_MONTH,Integer.parseInt( content.getString("Start Date").substring(8,10)));
            requestDate.set(Calendar.MONTH, Integer.parseInt(content.getString("Start Date").substring(5,7))-1);
            requestDate.set(Calendar.YEAR,Integer.parseInt(content.getString("Start Date").substring(0,4)));
            requestDate.set(Calendar.HOUR_OF_DAY, 0);
            requestDate.set(Calendar.MINUTE, 0);
            requestDate.set(Calendar.SECOND, 0);
            requestDate.set(Calendar.MILLISECOND, 0);
            Date reqDt = requestDate.getTime();
            java.sql.Date sqlDate = new java.sql.Date(reqDt.getTime());
            
			product.setBusinessDesr(content.getString("Business Description"));
			product.setStartDate(sqlDate);
			product.setWebsiteURL(content.getString("Destination URL"));
			product.setBusinessPhone(content.getString("Phone Number"));
			if(validationObj.itemStatus(Producthdr.getString("enterpriseItemId"),session)){
				product.setStatus("NEW");
				validationObj.statusCall("POST");
			}
			else{
				product.setStatus("UPDATE");
				validationObj.statusCall("PUT");
			}
			product.setItemId(Producthdr.getString("enterpriseItemId"));
			product.setRowaddedid("CA");
			java.util.Date myDateadded = new java.util.Date();
			java.sql.Date sqlDateadded = new java.sql.Date(myDateadded.getTime());
			product.setRowaddeddttm(sqlDateadded);
			java.util.Date myDatelastmant = new java.util.Date();
			java.sql.Date sqlDatelastmant = new java.sql.Date(myDatelastmant.getTime());
			product.setRowlastmantdttm(sqlDatelastmant);
			product.setRowlastmantid("CA");
			session.save(product);
			JSONArray category = null;
			category = content.getJSONArray("CategoryList");
			for (int i = 0; i < category.length(); i++) {

				JSONObject categories = category.getJSONObject(i);

				productcategory.setInstanceId(product.getInstanceId());
				productcategory.setCategoryId(categories.getString("CategoryId"));
				productcategory.setCategorydesc(categories.getString("CategoryName"));
				productcategory.setRowaddedid("CA");
				productcategory.setRowaddeddttm(sqlDateadded);
				productcategory.setRowlastmantdttm(sqlDatelastmant);
				productcategory.setRowlastmantid("CA");
				session.save(productcategory);
				session.flush();
				session.clear();
			}
			session.getTransaction().commit();
			session.close();
			// HibernateUtil.shutdown();
		} catch (Exception e) {
			session.getTransaction().rollback();
			System.out.println("Exception while executing DB operations " + e);
			throw new Exception("Persistence Layer Error", e);
		}
		return validationList;
	}

}
