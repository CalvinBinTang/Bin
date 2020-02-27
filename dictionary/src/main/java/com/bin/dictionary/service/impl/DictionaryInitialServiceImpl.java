package com.bin.dictionary.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bin.dictionary.domain.FieldInfo;
import com.bin.dictionary.scan.FileScanService;
import com.bin.dictionary.service.DictionaryInitialService;
import com.bin.dictionary.task.GetFieldDescriptionTask;

@Service
public class DictionaryInitialServiceImpl implements DictionaryInitialService, InitializingBean {
	
	@Autowired
	FileScanService fileScanSvc;
	
	private Map<String, String> codeDescriptionMap;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		codeDescriptionMap = fileScanSvc.getTransInfo();
		System.out.println(codeDescriptionMap.size());
	}
	
	@Override
	public Map<String, List<FieldInfo>> initialDictionary() throws Exception {
		Map<String, List<FieldInfo>> res = new HashMap<String, List<FieldInfo>>();
		List<FieldInfo> infos = fileScanSvc.getFieldInfoInFile();
		ExecutorService exec = Executors.newFixedThreadPool(2);
		for(FieldInfo info: infos) {
			if(codeDescriptionMap.containsKey(info.getLabelCode().toLowerCase())) {
				info.setLabelDesc(codeDescriptionMap.get(info.getLabelCode().toLowerCase()));
			} else {
				if (info.getLabelCode() == null || "".equalsIgnoreCase(info.getLabelCode())) {
					info.setLabelDesc("No Label Description");
				} else {
					Future<String> future = exec.submit(new GetFieldDescriptionTask(info.getLabelCode()));
					codeDescriptionMap.put(info.getLabelCode(), future.get());
					info.setLabelDesc(future.get());
				}
			}
			addFieldInfoIfExist(res, info, false);
			addFieldInfoIfExist(res, info, true);
		}
		return res;
	}
	
	private void addFieldInfoIfExist(Map<String, List<FieldInfo>> map, FieldInfo info, boolean isStrCode) {
		List<FieldInfo> list = map.get(info.getLabelDesc().toLowerCase());
		if( list != null && list.size() > 0 && !isStrCode) {
			list.add(info);
			map.put(info.getLabelDesc().toLowerCase(), list);
			return;
		}
		list = map.get(info.getStrCode().toLowerCase());
		if( list != null && list.size() > 0 && isStrCode) {
			list.add(info);
			map.put(info.getStrCode().toLowerCase(), list);
			return;
		}
		list = new ArrayList<FieldInfo>();
		list.add(info);
		if ( isStrCode ) {
			map.put(info.getStrCode().toLowerCase(), list);
		} else {
			map.put(info.getLabelDesc().toLowerCase(), list);
		}
	}

}
