package com.bin.dictionary.scan.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.bin.dictionary.domain.FieldInfo;
import com.bin.dictionary.scan.FileScanService;

@Service
public class FileScanServiceImpl implements FileScanService {

	@Value("${dsLocation}")
	private String dsLocation;

	@Value("${transFile}")
	private String transFile;

	@Override
	public List<FieldInfo> getFieldInfoInFile() throws Exception {
		List<FieldInfo> infos = new ArrayList<FieldInfo>();
		List<String> files = Files.list(Paths.get(dsLocation)).filter(Files::isRegularFile).map(x -> x.toString())
				.filter(p -> p.endsWith(".xml")).collect(Collectors.toList());
		for (String file : files) {
			File xmlFile = new File(file);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			try {
				builder = factory.newDocumentBuilder();
				Document doc = builder.parse(xmlFile);
				NodeList fields = doc.getElementsByTagName("EntityField");
				for (int i = 0; i < fields.getLength(); i++) {
					Node field = fields.item(i);
					if (field.getNodeType() == Node.ELEMENT_NODE) {
						Element e = (Element) field;
						FieldInfo info = new FieldInfo();
						info.setEntityName(getFirstStringValueIfExist(e.getElementsByTagName("EntityTableCode")));
						info.setStrCode(getFirstStringValueIfExist(e.getElementsByTagName("JsonName")));
						info.setStrDesc(getFirstStringValueIfExist(e.getElementsByTagName("FieldDescription")));
						info.setLabelCode(getFirstStringValueIfExist(e.getElementsByTagName("FieldLabel")));
						info.setDataType(getFirstStringValueIfExist(e.getElementsByTagName("DataType")));
						info.setDisplayFormat(getFirstStringValueIfExist(e.getElementsByTagName("DisplayFormat")));
						info.setLength(getFirstStringValueIfExist(e.getElementsByTagName("MaxLength")));
						info.setMandatory(getFirstBoolValueIfExist(e.getElementsByTagName("IsRequired")));
						info.setReadOnly(getFirstBoolValueIfExist(e.getElementsByTagName("IsReadOnly")));
						info.setSortable(getFirstBoolValueIfExist(e.getElementsByTagName("IsSortable")));
						info.setHiddenForUI(getFirstBoolValueIfExist(e.getElementsByTagName("IsHiddenForUI")));
						info.setHidden(getFirstBoolValueIfExist(e.getElementsByTagName("IsHidden")));
						infos.add(info);
					}
				}
			} catch (ParserConfigurationException | SAXException | IOException e) {
				continue;
			}
		}
		return infos;
	}

	@Override
	public Map<String, String> getTransInfo() throws Exception {
		Map<String, String> res = new HashMap<String, String>();
		File xmlFile = new File(transFile);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(xmlFile);
			NodeList fields = doc.getElementsByTagName("TranslatedString");
			for(int i = 0 ; i < fields.getLength(); i++) {
				Node field = fields.item(i);
				if (field.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) field;
					res.put(getFirstStringValueIfExist(e.getElementsByTagName("StringCode")).toLowerCase(), getFirstStringValueIfExist(e.getElementsByTagName("StringText")));
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	private String getFirstStringValueIfExist(NodeList nodes) {
		if (nodes.getLength() > 0) {
			return nodes.item(0).getTextContent();
		}
		return "";
	}

	private Boolean getFirstBoolValueIfExist(NodeList nodes) {
		if (nodes.getLength() > 0) {
			return "true".equalsIgnoreCase(nodes.item(0).getTextContent());
		}
		return false;
	}

}
