package com.bin.generate.data.domain;

import java.util.ArrayList;
import java.util.List;

public class TableInfo {

	private String tableName;
	private String tableDescription = "N/A";
	private List<SubTableInfo> subTables = new ArrayList<>();

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableDescription() {
		return tableDescription;
	}

	public void setTableDescription(String tableDescription) {
		this.tableDescription = tableDescription;
	}
	
	public List<SubTableInfo> getSubTables() {
		return subTables;
	}

	public void setSubTables(SubTableInfo subTable) {
		this.subTables.add(subTable);
	}
	
	public void setAllSubTables(List<SubTableInfo> tables) {
		this.subTables = tables;
	}

}
