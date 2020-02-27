package com.bin.dictionary.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bin.dictionary.domain.FieldInfo;
import com.bin.dictionary.service.DictionaryInitialService;
import com.bin.dictionary.service.DictionaryService;

@Service
public class DictionaryServiceImpl implements DictionaryService, InitializingBean {
	
	@Autowired
	DictionaryInitialService initSvc;
	
	private Map<String, List<FieldInfo>> fieldMap = new HashMap<String, List<FieldInfo>>();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		fieldMap.putAll(initSvc.initialDictionary());
		System.out.println("+++++++++++++++++++ " + fieldMap.size());
	}

	@Override
	public List<FieldInfo> getFieldInfo(String info) {
		return fieldMap.get(info.toLowerCase());
	}

}
