package com.bin.generate.data.domain;

import java.util.ArrayList;
import java.util.List;

public class SubTableInfo {
	
	private String subTableName;
	private String sbuTableDescription = "N/A";
	private List<FieldInfo> fields = new ArrayList<FieldInfo>();
	
	public List<FieldInfo> getFields() {
		return fields;
	}

	public void setFields(FieldInfo field) {
		this.fields.add(field);
	}
	
	public void setAllFields(List<FieldInfo> fields) {
		this.fields = fields;
	}

	public String getSubTableName() {
		return subTableName;
	}

	public void setSubTableName(String subTableName) {
		this.subTableName = subTableName;
	}

	public String getSbuTableDescription() {
		return sbuTableDescription;
	}

	public void setSbuTableDescription(String sbuTableDescription) {
		this.sbuTableDescription = sbuTableDescription;
	}
	
}
