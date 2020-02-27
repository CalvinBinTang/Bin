package com.bin.dictionary.service;

import java.util.List;

import com.bin.dictionary.domain.FieldInfo;

public interface DictionaryService {
	
	public List<FieldInfo> getFieldInfo(String fieldName);

}
