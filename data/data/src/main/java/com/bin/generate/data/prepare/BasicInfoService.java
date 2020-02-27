package com.bin.generate.data.prepare;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import com.bin.generate.data.domain.TableInfo;

public interface BasicInfoService {

	public Map<String, TableInfo> getInfoFromFiles(List<String> files) throws ParserConfigurationException;
	
	public Set<String> getFilterFields(String file)  throws IOException;

}
