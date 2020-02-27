package com.bin.jmeter.maintenance.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.bin.jmeter.maintenance.MaintenanceService;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

	@Value("${outputFolder}")
	private String outputFolder;

	@Override
	public Resource loadFileAsResource(String fileName) {
		ByteArrayResource resource = null;
		try {
			Path path = Paths.get(outputFolder + fileName);
		    resource = new ByteArrayResource(Files.readAllBytes(path));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		return resource;
	}

}
