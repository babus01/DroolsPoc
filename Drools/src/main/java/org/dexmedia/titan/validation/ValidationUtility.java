package org.dexmedia.titan.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationUtility {

	public ValidationUtility() {
	}

	private List<String> errorlist = new ArrayList<String>();

	public List<String> getErrorlist() {
		return errorlist;
	}

	public void setErrorlist(List<String> errorlist) {
		this.errorlist = errorlist;
	};

	public void adderrors(String errors) {
		this.errorlist.add(errors);
	}
}
