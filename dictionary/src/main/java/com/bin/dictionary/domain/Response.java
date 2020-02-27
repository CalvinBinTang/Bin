package com.bin.dictionary.domain;

import java.util.List;

public class Response {

	private String errorMsg;
	private List<FieldInfo> infos;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public List<FieldInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<FieldInfo> infos) {
		this.infos = infos;
	}

}
