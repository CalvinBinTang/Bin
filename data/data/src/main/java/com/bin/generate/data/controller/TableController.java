package com.bin.generate.data.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bin.generate.data.domain.GenerateInfo;
import com.bin.generate.data.domain.TableInfo;
import com.bin.generate.data.post.DataMaintenanceService;
import com.bin.generate.data.prepare.TableService;
import com.bin.generate.data.process.GenerateDataService;
import com.bin.generate.data.util.GenerateDataConstants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Controller
public class TableController {
	
	@Autowired
	TableService tableSvc;
	
	@Autowired
	GenerateDataService genSvc;
	
	@Autowired
	ServletContext context;
	
	@Autowired
	DataMaintenanceService maintenanceSvc;
	
	@RequestMapping(value="/service", method = RequestMethod.GET)
	public String showServicePage(ModelMap model) {
		List<String> tableList = tableSvc.getTableList(GenerateDataConstants.SERVICE_NAME_SERVICE);
		model.put("tables",tableList);
		return "service";
	}
	
	@RequestMapping(value="/sales", method = RequestMethod.GET)
	public String showSalesPage(ModelMap model) {
		List<String> tableList = tableSvc.getTableList(GenerateDataConstants.SERVICE_NAME_SALES);
		model.put("tables",tableList);
		return "sales";
	}
	
	@PostMapping("/service/search")
	public ResponseEntity<?> getSvcTableDetail(@Valid @RequestBody String gen) {
        TableInfo table = getTableDetails(gen, GenerateDataConstants.SERVICE_NAME_SERVICE);
        return ResponseEntity.ok(table);
    }
	
	@PostMapping("/sales/search")
	public ResponseEntity<?> getSalesTableDetail(@Valid @RequestBody String gen) {
        TableInfo table = getTableDetails(gen, GenerateDataConstants.SERVICE_NAME_SALES);
        return ResponseEntity.ok(table);
    }
	
	private TableInfo getTableDetails(String gen, String serviceName) {
		JsonObject jobj = new Gson().fromJson(gen, JsonObject.class);
		String tableName = jobj.get("tableName").getAsString();
		boolean isFilterOutFields = jobj.get("filter").getAsBoolean();
		return tableSvc.getTableDetailByTableName(tableName, serviceName, isFilterOutFields);
	}
	
	@PostMapping("/service/generate")
	public ResponseEntity<?> generateSvcDataFile(@Valid @RequestBody String gen) throws Exception {
		GenerateInfo genInfo = new Gson().fromJson(gen, GenerateInfo.class);
		genSvc.GenerateData(genInfo, GenerateDataConstants.SERVICE_NAME_SERVICE);
        return ResponseEntity.ok(genInfo);
    }
	
	@PostMapping("/sales/generate")
	public ResponseEntity<?> generateSalesDataFile(@Valid @RequestBody String gen) throws Exception {
		GenerateInfo genInfo = new Gson().fromJson(gen, GenerateInfo.class);
		genSvc.GenerateData(genInfo, GenerateDataConstants.SERVICE_NAME_SALES);
        return ResponseEntity.ok(genInfo);
    }
	
	@GetMapping("/download")
	public ResponseEntity<Resource> downloadFile(@RequestParam String fileName){
        Resource resource = maintenanceSvc.loadFileAsResource(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
	}
	
}
