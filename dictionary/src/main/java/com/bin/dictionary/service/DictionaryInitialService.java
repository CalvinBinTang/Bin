package com.bin.dictionary.service;

import java.util.List;
import java.util.Map;

import com.bin.dictionary.domain.FieldInfo;

public interface DictionaryInitialService {
	
	public Map<String, List<FieldInfo>> initialDictionary() throws Exception;
	
}
