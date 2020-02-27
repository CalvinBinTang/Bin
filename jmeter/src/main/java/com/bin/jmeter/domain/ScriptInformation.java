package com.bin.jmeter.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScriptInformation {

	private String exportType;
	private String interfaceName;
	private String methodName;
	private String soapBody;
	private String dataComponentName;
	private List<String> fieldName = new ArrayList<>();
	private List<String> exFieldName = new ArrayList<>();
	private List<String> spFieldName = new ArrayList<>();
	private File file;
	private String fileName;

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public List<String> getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName.add(fieldName);
	}

	public String getSoapBody() {
		return soapBody;
	}

	public void setSoapBody(String soapBody) {
		this.soapBody = soapBody;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<String> getExFieldName() {
		return exFieldName;
	}

	public void setExFieldName(String exFieldName) {
		this.exFieldName.add(exFieldName);
	}

	public List<String> getSpFieldName() {
		return spFieldName;
	}

	public void setSpFieldName(String spFieldName) {
		this.spFieldName.add(spFieldName);
	}

	public String getDataComponentName() {
		return dataComponentName;
	}

	public void setDataComponentName(String dataComponentName) {
		this.dataComponentName = dataComponentName;
	}

}
