package org.dexmedia.titan.bean;

import java.sql.Date;

@SuppressWarnings("serial")
public class ProductCategory implements java.io.Serializable {
	private int instanceId;
	private String categoryId;
	private String categorydesc;
	private String rowaddedid;
	private Date rowaddeddttm;
	private String rowlastmantid;
	private Date rowlastmantdttm;
//	private Product product;

	public int getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(int instanceId) {
		this.instanceId = instanceId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategorydesc() {
		return categorydesc;
	}

	public void setCategorydesc(String categorydesc) {
		this.categorydesc = categorydesc;
	}

	public String getRowaddedid() {
		return rowaddedid;
	}

	public void setRowaddedid(String rowaddedid) {
		this.rowaddedid = rowaddedid;
	}

	public Date getRowaddeddttm() {
		return rowaddeddttm;
	}

	public void setRowaddeddttm(Date rowaddeddttm) {
		this.rowaddeddttm = rowaddeddttm;
	}

	public String getRowlastmantid() {
		return rowlastmantid;
	}

	public void setRowlastmantid(String rowlastmantid) {
		this.rowlastmantid = rowlastmantid;
	}

	public Date getRowlastmantdttm() {
		return rowlastmantdttm;
	}

	public void setRowlastmantdttm(Date rowlastmantdttm) {
		this.rowlastmantdttm = rowlastmantdttm;
	}

	/*public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
*/
}
