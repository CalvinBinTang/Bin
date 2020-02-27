package com.bin.jmeter.process.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.bin.jmeter.domain.ScriptInformation;
import com.bin.jmeter.process.ProcessService;
import com.bin.jmeter.util.JMeterConstants;
import com.bin.jmeter.util.JMeterUtils;

@Service
public class ProcessImpl implements ProcessService {

	@Value("${outputFolder}")
	private String outputFolder;

	@Override
	public ScriptInformation enrichJMeterScriptWithInput(ScriptInformation info) throws Exception {
		List<String> validFieldTemplate = Files.lines(ResourceUtils.getFile("classpath:template/ValidFieldTemplate").toPath()).collect(Collectors.toList());
		List<String> extractorTemplate = Files.lines(ResourceUtils.getFile("classpath:template/ExtractorFieldTemplate").toPath()).collect(Collectors.toList());
		List<String> specialTemplate = Files.lines(ResourceUtils.getFile("classpath:template/SpecialExtractorFieldTemplate").toPath()).collect(Collectors.toList());
		List<String> replaced = Files.lines(ResourceUtils.getFile("classpath:template/RunableScriptTemplate").toPath())
				.map(line -> line.replaceAll(JMeterConstants.INTERFACE_NAME, info.getInterfaceName()))
				.map(line -> line.replaceAll(JMeterConstants.METHOD_NAME, info.getMethodName()))
				.map(line -> line.replaceAll(JMeterConstants.SOAP_BODY, info.getSoapBody()))
				.map(line -> line.replaceAll(JMeterConstants.VALIDATE_FIELDS, enrichFieldValidation(info, validFieldTemplate)))
				.map(line -> line.replaceAll(JMeterConstants.EXTRACTOR_FIELDS, enrichFieldExtractor(info, extractorTemplate)))
				.map(line -> line.replaceAll(JMeterConstants.SPECIAL_EXTRACTOR_FIELDS, enrichSpecailFieldExtractor(info, specialTemplate)))
				.map(line -> line.replaceAll(JMeterConstants.COMPONENT_NAME, info.getDataComponentName()))
				.collect(Collectors.toList());
		String fileName = JMeterConstants.FILE_NAME_PREFIX + "_" + info.getInterfaceName() + "_" + info.getMethodName()
				+ "_" + JMeterUtils.getCurrentDateAsString() + JMeterConstants.FILE_TYPE;
		writeReplacedFile(replaced, fileName);
		info.setFile(new File(outputFolder + fileName));
		info.setFileName(fileName);
		return info;
	}

	@Override
	public ScriptInformation enrichJMeterFragmentWithInput(ScriptInformation info) throws Exception {
		List<String> validFieldTemplate = Files.lines(ResourceUtils.getFile("classpath:template/ValidFieldTemplate").toPath()).collect(Collectors.toList());
		List<String> extractorTemplate = Files.lines(ResourceUtils.getFile("classpath:template/ExtractorFieldTemplate").toPath()).collect(Collectors.toList());
		List<String> specialTemplate = Files.lines(ResourceUtils.getFile("classpath:template/SpecialExtractorFieldTemplate").toPath()).collect(Collectors.toList());
		List<String> replaced = Files.lines(ResourceUtils.getFile("classpath:template/FregmentTemplate").toPath())
				.map(line -> line.replaceAll(JMeterConstants.INTERFACE_NAME, info.getInterfaceName()))
				.map(line -> line.replaceAll(JMeterConstants.METHOD_NAME, info.getMethodName()))
				.map(line -> line.replaceAll(JMeterConstants.SOAP_BODY, info.getSoapBody()))
				.map(line -> line.replaceAll(JMeterConstants.VALIDATE_FIELDS, enrichFieldValidation(info, validFieldTemplate)))
				.map(line -> line.replaceAll(JMeterConstants.EXTRACTOR_FIELDS, enrichFieldExtractor(info, extractorTemplate)))
				.map(line -> line.replaceAll(JMeterConstants.SPECIAL_EXTRACTOR_FIELDS, enrichSpecailFieldExtractor(info, specialTemplate)))
				.map(line -> line.replaceAll(JMeterConstants.COMPONENT_NAME, info.getDataComponentName()))
				.collect(Collectors.toList());
		String fileName = JMeterConstants.FILE_NAME_FRAGMENT_PREFIX + "_" + info.getInterfaceName() + "_"
				+ info.getMethodName() + "_" + JMeterUtils.getCurrentDateAsString() + JMeterConstants.FILE_TYPE;
		writeReplacedFile(replaced, fileName);
		info.setFile(new File(outputFolder + fileName));
		info.setFileName(fileName);
		return info;
	}

	private String enrichFieldExtractor(ScriptInformation info, List<String> template) {
		if (info.getExFieldName() == null
			|| (info.getExFieldName().size() == 1
			&& "".equals(info.getExFieldName().get(0))))
			return "";
		StringBuffer sb = new StringBuffer();
		for (String field : info.getExFieldName()) {
			List<String> replaced = template.stream().map(line -> line.replaceAll(JMeterConstants.FIELD_NAME, field))
					.collect(Collectors.toList());
			sb.append(replaced);
		}
		return sb.toString();
	}

	private String enrichFieldValidation(ScriptInformation info, List<String> template) {
		if (info.getFieldName() == null 
			|| (info.getFieldName().size() == 1 
			&& "".equals(info.getFieldName().get(0))))
			return "";
		StringBuffer sb = new StringBuffer();
		for (String field : info.getFieldName()) {
			List<String> replaced = template.stream().map(line -> line.replaceAll(JMeterConstants.FIELD_NAME, field))
					.collect(Collectors.toList());
			sb.append(replaced);
		}
		return sb.toString();
	}
	
	private String enrichSpecailFieldExtractor(ScriptInformation info, List<String> template) {
		if (info.getSpFieldName() == null 
			|| (info.getSpFieldName().size() == 1 
			&& "".equals(info.getSpFieldName().get(0))))
			return "";
		StringBuffer sb = new StringBuffer();
		for (String field : info.getSpFieldName()) {
			List<String> replaced = template.stream()
					.map(line -> line.replaceAll(JMeterConstants.FIELD_NAME, field))
					.collect(Collectors.toList());
			sb.append(replaced);
		}
		return sb.toString();
	}

	private void writeReplacedFile(List<String> output, String fileName) {
		Path path = Paths.get(outputFolder + fileName);
		try {
			Files.write(path, output, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
