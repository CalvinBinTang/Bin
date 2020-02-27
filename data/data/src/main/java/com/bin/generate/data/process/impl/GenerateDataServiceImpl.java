package com.bin.generate.data.process.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bin.generate.data.domain.GenerateFieldsInfo;
import com.bin.generate.data.domain.GenerateInfo;
import com.bin.generate.data.domain.TableInfo;
import com.bin.generate.data.prepare.TableService;
import com.bin.generate.data.process.GenerateDataService;
import com.bin.generate.data.util.GenerateDataUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Service
public class GenerateDataServiceImpl implements GenerateDataService {

	@Autowired
	TableService tableSvc;
	
	@Value("${outputFile}")
	private String outputFile;
	
	private Cache<String, File> storedFile = CacheBuilder.newBuilder()
		    .maximumSize(10000)
		    .expireAfterWrite(24, TimeUnit.HOURS)
		    .build();
	
	@Override
	public void GenerateData(GenerateInfo gen, String serviceName) {
		List<String> output = new ArrayList<String>();
		output.add(generateDataHeader(gen.getTableName(), serviceName, gen.isFilter(), gen.getPreFix()));
		for(int i = 0; i <gen.getRecords(); i++) {
			output.add(generateDataBody(gen.getFields(), i));
		}
		StringBuffer sb = new StringBuffer();
		sb.append(gen.getTableName()).append(GenerateDataUtil.getCurrentDateAsString()).append(".csv");
		gen.setFile(writeOutputFile(output, sb.toString()));
		gen.setFileName(sb.toString());
	}
	
	private File writeOutputFile(List<String> output, String fileName) {
		Path path = Paths.get(outputFile + fileName);
		File f = null;
		try {
			Files.write(path, output, StandardCharsets.UTF_8);
		    f = new File(outputFile + fileName);
			storedFile.put(fileName, f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f;
	}
	
	private String generateDataBody(List<GenerateFieldsInfo> fields, int line) {
		StringBuffer record = new StringBuffer();
		for(GenerateFieldsInfo info : fields) {
			switch (info.getType()) {
			case "fixed":
				record.append(generateFixRecrod(info));
				break;
			case "random":
				record.append(generateRandomRecrod(info));
				break;
			case "auto-growth":
				record.append(generateAutoGrowthRecrod(info, line));
				break;
			default:
				break;
			}
			record.append(",");
		}
		if (record.length() > 0) {
			return record.substring(0, record.length()-1).toString();
		}
		return "";
	}

	private String generateAutoGrowthRecrod(GenerateFieldsInfo info, int line) {
		StringBuffer res = new StringBuffer();
		int start = Integer.parseInt(info.getRangeBegin());
		res.append(info.getPrefix()).append(start + line);
		return res.toString();
	}

	private String generateRandomRecrod(GenerateFieldsInfo info) {
		StringBuffer res = new StringBuffer();
		int start = Integer.parseInt(info.getRangeBegin());
		int end = Integer.parseInt(info.getRangeEnd());
		res.append(info.getPrefix()).append(new Random().ints(start, (end + 1)).findFirst().getAsInt());
		return res.toString();
	}

	private String generateFixRecrod(GenerateFieldsInfo info) {
		StringBuffer res = new StringBuffer();
		res.append(info.getPrefix());
		return res.toString();
	}

	private String generateDataHeader(String tableName, String serviceName, boolean isFilter, String preFix) {
		TableInfo table = tableSvc.getTableDetailByTableName(tableName, serviceName, isFilter);
		StringBuffer header = new StringBuffer();
		table.getSubTables().forEach(x -> {
			x.getFields().forEach(y -> {
				header.append(preFix);
				header.append(y.getName());
				header.append(",");
			});
		});
		if (header.length() > 0) {
			return header.substring(0, header.length()-1).toString();
		}
		return "";
	}

}
