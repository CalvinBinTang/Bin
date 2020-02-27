package com.bin.generate.data.prepare.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.bin.generate.data.domain.FieldInfo;
import com.bin.generate.data.domain.SubTableInfo;
import com.bin.generate.data.domain.TableInfo;
import com.bin.generate.data.prepare.BasicInfoService;
import com.bin.generate.data.util.GenerateDataUtil;

@Service
public class BasicInfoServiceImpl implements BasicInfoService {

	@Override
	public Map<String, TableInfo> getInfoFromFiles(List<String> files) {
		Map<String, TableInfo> res = new HashMap<String, TableInfo>();
		for (String file : files) {
			TableInfo tInfo = new TableInfo();
			File xmlFile = new File(file);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			try {
				builder = factory.newDocumentBuilder();
				Document doc = builder.parse(xmlFile);
				String name = doc.getElementsByTagName("EntityCode").item(0).getTextContent();
				tInfo.setTableName(name);
				tInfo.setTableDescription(doc.getElementsByTagName("EntityDescription").item(0).getTextContent());
				// BEGIN TO GET SUB TABLE INFO
				NodeList subTables = doc.getElementsByTagName("EntityTable");
				for(int i = 0 ; i < subTables.getLength(); i++) {
					Node subTable = subTables.item(i);
					if (subTable.getNodeType() == Node.ELEMENT_NODE) {
						Element e = (Element) subTable;
						SubTableInfo sub = new SubTableInfo();
						sub.setSubTableName(getFirstStringValueIfExist(e.getElementsByTagName("JsonName")));
						sub.setSbuTableDescription(getFirstStringValueIfExist(e.getElementsByTagName("TableDescription")));
						// BEGIN TO GET FIELD INFO
						NodeList fields = e.getElementsByTagName("EntityField");
						for (int j = 0; j < fields.getLength(); j++) {
							Node field = fields.item(j);
							if (field.getNodeType() == Node.ELEMENT_NODE) {
								Element f = (Element) field;
								FieldInfo fInfo = new FieldInfo();
								fInfo.setName(getFirstStringValueIfExist(f.getElementsByTagName("JsonName")));
								fInfo.setDescription(getFirstStringValueIfExist(f.getElementsByTagName("FieldDescription")));
								fInfo.setType(getFirstStringValueIfExist(f.getElementsByTagName("DataType")));
								fInfo.setIsRequired(getFirstBoolValueIfExist(f.getElementsByTagName("IsRequired")));
								String maxLength = getFirstStringValueIfExist(f.getElementsByTagName("MaxLength"));
								fInfo.setMaxLength("".equals(maxLength) ? Integer.MAX_VALUE : Integer.parseInt(maxLength));
								sub.setFields(fInfo);
							}
						}
						tInfo.setSubTables(sub);
					}
				}
				res.put(name, tInfo);
			} catch (ParserConfigurationException | SAXException | IOException e) {
				continue;
			}
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

	@Override
	public Set<String> getFilterFields(String file) throws IOException {
		List<String> res = GenerateDataUtil.readFile(file);
		if (res == null || res.size()==0) {
			return new HashSet<>();
		}
		return Stream.of(res.get(0).split(",", -1)).collect(Collectors.toSet());
	}

}
