package com.bin.generate.data.prepare.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bin.generate.data.domain.FieldInfo;
import com.bin.generate.data.domain.SubTableInfo;
import com.bin.generate.data.domain.TableInfo;
import com.bin.generate.data.prepare.BasicInfoService;
import com.bin.generate.data.prepare.TableService;
import com.bin.generate.data.util.GenerateDataConstants;
import com.bin.generate.data.util.GenerateDataUtil;

@Service
public class TableServiceImpl implements TableService, InitializingBean {

	@Value("${SvcLocation}")
	private String SvcLocation;

	@Value("${SalesLocation}")
	private String SalesLocation;
	
	@Value("${FilterFile}")
	private String FilterFile;

	@Autowired
	private BasicInfoService basicInfoSvc;

	private Map<String, TableInfo> cacheSvcTableMap;
	private Map<String, TableInfo> cacheSalesTableMap;
	private Set<String> cacheFilter;

	@Override
	public void afterPropertiesSet() throws Exception {
		List<String> files = GenerateDataUtil.getAllFilesByFolderPath(SvcLocation);
		List<String> salesFiles = GenerateDataUtil.getAllFilesByFolderPath(SalesLocation);
		cacheSvcTableMap = basicInfoSvc.getInfoFromFiles(files);
		cacheSalesTableMap = basicInfoSvc.getInfoFromFiles(salesFiles);
		cacheFilter = basicInfoSvc.getFilterFields(FilterFile);
	}

	@Override
	public List<String> getTableList(String serviceName) {
		return new ArrayList<>(getMapFromServiceName(serviceName).keySet()).stream().sorted()
				.collect(Collectors.toList());
	}

	@Override
	public TableInfo getTableDetailByTableName(String tableName, String serviceName, boolean isFilter) {
		Map<String, TableInfo> map = getMapFromServiceName(serviceName);
		TableInfo table = map.get(tableName);
		if (table == null) {
			return new TableInfo();
		} else {
			if (isFilter) {
				//Find a proper way to clone this object
				TableInfo res = new TableInfo();
				res.setAllSubTables(table.getSubTables());
				res.setTableName(table.getTableName());
				res.setTableDescription(res.getTableDescription());
				for(SubTableInfo info : res.getSubTables()) {
					List<FieldInfo> temp = new ArrayList<FieldInfo>();
					for(FieldInfo f : info.getFields()) {
						if(!cacheFilter.contains(f.getName())) {
							temp.add(f);
						}
					}
					info.setAllFields(temp);
				}
				return res;
			}
			return table;
		}
	}

	private Map<String, TableInfo> getMapFromServiceName(String serviceName) {
		Map<String, TableInfo> map = null;
		if (GenerateDataConstants.SERVICE_NAME_SERVICE.equals(serviceName)) {
			map = cacheSvcTableMap;
		} else if (GenerateDataConstants.SERVICE_NAME_SALES.equals(serviceName)) {
			map = cacheSalesTableMap;
		}
		return map;
	}

}
