package com.bin.jmeter.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.bin.jmeter.domain.ScriptInformation;
import com.bin.jmeter.maintenance.MaintenanceService;
import com.bin.jmeter.process.ProcessService;
import com.bin.jmeter.util.JMeterConstants;
import com.bin.jmeter.util.JMeterUtils;
import com.google.gson.Gson;

@Controller
public class JMeterController {
	
	@Autowired
	ProcessService processSvc;
	
	@Autowired
	MaintenanceService maintenanceSvc;
	
	@PostMapping("/jmeter/generate")
	public ResponseEntity<?> generateJMeterFile(@Valid @RequestBody String gen) throws Exception {
		ScriptInformation info = new Gson().fromJson(gen, ScriptInformation.class);
		info.setSoapBody(JMeterUtils.formatSignInString(info.getSoapBody()));
		if (JMeterConstants.EXPORT_TYPE_FRAGEMENT.equals(info.getExportType())) {
			info = processSvc.enrichJMeterFragmentWithInput(info);
		} else if (JMeterConstants.EXPORT_TYPE_RUNABLE_SCRIPT.equals(info.getExportType())) {
			info = processSvc.enrichJMeterScriptWithInput(info);
		}
        return ResponseEntity.ok(info);
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
