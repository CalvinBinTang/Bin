package com.bin.generate.data.prepare;

import java.util.List;

import com.bin.generate.data.domain.TableInfo;

public interface TableService {
	
	public List<String> getTableList(String serviceName);
	
	public TableInfo getTableDetailByTableName(String tableName, String serviceName, boolean isFilter);
}
