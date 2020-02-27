package com.bin.generate.data.post.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.bin.generate.data.post.DataMaintenanceService;

@Service
public class DataMaintenanceServiceImpl implements DataMaintenanceService {
	
	@Value("${outputFile}")
	private String outputFile;

	@Override
	public Resource loadFileAsResource(String fileName) {
		ByteArrayResource resource = null;
		try {
			Path path = Paths.get(outputFile + fileName);
		    resource = new ByteArrayResource(Files.readAllBytes(path));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		return resource;
	}

}
