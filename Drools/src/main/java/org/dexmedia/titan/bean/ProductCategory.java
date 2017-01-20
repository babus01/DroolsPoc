package org.dexmedia.titan.bean;

@SuppressWarnings("serial")
public class ProductCategory implements java.io.Serializable {
	private int instanceId;
	private String categoryId;
	private String categorydesc;
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
	
}
