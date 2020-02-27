package com.bin.jmeter.maintenance;

import org.springframework.core.io.Resource;

public interface MaintenanceService {
	
	public Resource loadFileAsResource(String fileName);

}
