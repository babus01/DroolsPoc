package org.dexmedia.titan.bean;

import java.sql.Date;

@SuppressWarnings("serial")
public class Product implements java.io.Serializable {
	private int instanceId;
	private String businessDesr;
	private Date startDate;
	private String websiteURL;
	private String businessPhone;
	private String status;
	private String ItemId;
	private String rowaddedid;
	private Date rowaddeddttm;
	private String rowlastmantid;
	private java.util.Date rowlastmantdttm;
//	private Set<ProductCategory> ProdCategory;

	public int getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(int instanceId) {
		this.instanceId = instanceId;
	}

	public String getBusinessDesr() {
		return businessDesr;
	}

	public void setBusinessDesr(String businessDesr) {
		this.businessDesr = businessDesr;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date sqlDate) {
		this.startDate = sqlDate;
	}

	public String getWebsiteURL() {
		return websiteURL;
	}

	public void setWebsiteURL(String websiteURL) {
		this.websiteURL = websiteURL;
	}

	public String getBusinessPhone() {
		return businessPhone;
	}

	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getItemId() {
		return ItemId;
	}

	public void setItemId(String itemId) {
		ItemId = itemId;
	}

	public String getRowaddedid() {
		return rowaddedid;
	}

	public void setRowaddedid(String rowaddedid) {
		this.rowaddedid = rowaddedid;
	}


	public String getRowlastmantid() {
		return rowlastmantid;
	}

	public void setRowlastmantid(String rowlastmantid) {
		this.rowlastmantid = rowlastmantid;
	}

	public Date getRowaddeddttm() {
		return rowaddeddttm;
	}

	public void setRowaddeddttm(Date rowaddeddttm) {
		this.rowaddeddttm = rowaddeddttm;
	}

	public java.util.Date getRowlastmantdttm() {
		return rowlastmantdttm;
	}

	public void setRowlastmantdttm(java.util.Date rowlastmantdttm) {
		this.rowlastmantdttm = rowlastmantdttm;
	}



/*	public Set<ProductCategory> getProdCategory() {
		return ProdCategory;
	}

	public void setProdCategory(Set<ProductCategory> prodCategory) {
		ProdCategory = prodCategory;
	}
	
*/
}
