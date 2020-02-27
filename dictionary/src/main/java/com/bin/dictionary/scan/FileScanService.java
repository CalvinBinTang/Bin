package com.bin.dictionary.scan;

import java.util.List;
import java.util.Map;

import com.bin.dictionary.domain.FieldInfo;

public interface FileScanService {
	
	public List<FieldInfo> getFieldInfoInFile() throws Exception;
	
	public Map<String, String> getTransInfo() throws Exception;

}
