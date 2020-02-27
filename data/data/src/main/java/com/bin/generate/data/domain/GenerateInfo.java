package com.bin.generate.data.domain;

import java.io.File;
import java.util.List;

public class GenerateInfo {

	private String tableName;
	private int records;
	private String fileName;
	private File file;
	private List<GenerateFieldsInfo> fields;
	private boolean isFilter;
	private String preFix;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<GenerateFieldsInfo> getFields() {
		return fields;
	}

	public void setFields(List<GenerateFieldsInfo> fields) {
		this.fields = fields;
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public boolean isFilter() {
		return isFilter;
	}

	public void setFilter(boolean isFilter) {
		this.isFilter = isFilter;
	}

	public String getPreFix() {
		return preFix;
	}

	public void setPreFix(String preFix) {
		this.preFix = preFix;
	}

}
