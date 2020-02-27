package com.bin.generate.data.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.ResourceUtils;

public class GenerateDataUtil {

	public static List<String> getAllFilesByFolderPath(String path) throws IOException{
		return Files.list(Paths.get(path)).filter(Files::isRegularFile).map(x -> x.toString()).filter(p -> p.endsWith(".xml")).collect(Collectors.toList());
	}
	
	public static List<String> readFile(String path) throws IOException {
		return Files.lines(ResourceUtils.getFile(path).toPath()).collect(Collectors.toList());
	}
	
	public static String getCurrentDateAsString() {
		LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return now.format(formatter);
	}
	
}
