package org.dexmedia.titan.persistance;

import java.util.ArrayList;
import java.util.List;

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
		Validation ValidationObj = new Validation();
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Product product = new Product();
		ProductCategory productcategory = new ProductCategory();
		List<String> validationList =new ArrayList<String>();
		try {
			content = input.getJSONObject("Content");
			Producthdr = input.getJSONObject("ProductHeader");
			@SuppressWarnings("deprecation")
			java.util.Date myDate = new java.util.Date(content.getString("Start Date"));
			java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
			product.setBusinessDesr(content.getString("Business Description"));
			product.setStartDate(sqlDate);
			product.setWebsiteURL(content.getString("Destination URL"));
			product.setBusinessPhone(content.getString("Phone Number"));
			product.setStatus("NEW");
			product.setItemId(Producthdr.getString("enterpriseItemId"));
			session.save(product);
			System.out.println(product.getInstanceId());
			JSONArray category = null;
			category = content.getJSONArray("CategoryList");
			for (int i = 1; i <= category.length(); i++) {
			    
				JSONObject categories = category.getJSONObject(i-1);
				if(ValidationObj.isCategoryValid(categories.getString("CategoryId"),session))
				{				
					productcategory.setInstanceId(product.getInstanceId());
					productcategory.setCategoryId(categories.getString("CategoryId"));
					productcategory.setCategorydesc(categories.getString("CategoryName"));
					session.save(productcategory);	
					session.flush();
				    session.clear();
				}else
				{
					System.out.println("Category not avaialbe");
					validationList.add("Invalid cCategory "+categories.getString("CategoryId"));
				}
				
			}
			session.getTransaction().commit();
			session.close();
			HibernateUtil.getSessionFactory().close();
		} catch (Exception e) {
			session.getTransaction().rollback();
			System.out.println("Exception while executing DB operations " +e);
			throw new Exception("Persistence Layer Error", e);
		}	
		return validationList;
	}

}
