package com.bin.dictionary.domain;

public class FieldInfo {

	private String strCode;
	private String strDesc;
	private String labelCode;
	private String labelDesc;
	private String dataType;
	private String entityName;
	private String displayFormat;
	private String length;
	private boolean mandatory;
	private boolean isReadOnly;
	private boolean isSortable;
	private boolean isHidden;
	private boolean isHiddenForUI;

	public String getStrCode() {
		return strCode;
	}

	public void setStrCode(String strCode) {
		this.strCode = strCode;
	}

	public String getStrDesc() {
		return strDesc;
	}

	public void setStrDesc(String strDesc) {
		this.strDesc = strDesc;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public boolean isMandantory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public String getLabelCode() {
		return labelCode;
	}

	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}

	public String getLabelDesc() {
		return labelDesc;
	}

	public void setLabelDesc(String labelDesc) {
		this.labelDesc = labelDesc;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDisplayFormat() {
		return displayFormat;
	}

	public void setDisplayFormat(String displayFormat) {
		this.displayFormat = displayFormat;
	}

	public boolean isReadOnly() {
		return isReadOnly;
	}

	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	public boolean isSortable() {
		return isSortable;
	}

	public void setSortable(boolean isSortable) {
		this.isSortable = isSortable;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	public boolean isHiddenForUI() {
		return isHiddenForUI;
	}

	public void setHiddenForUI(boolean isHiddenForUI) {
		this.isHiddenForUI = isHiddenForUI;
	}

}
