package com.bin.jmeter.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JMeterUtils {

	public static String formatSignInString(String str) {
		String replacedstr = str.replace("<", "&lt;").replace(">", "&gt;").replace("$", "\\$").replace("{", "\\{")
				.replace("}", "\\}").replace("\"", "&quot;").replace("\n", "&#xd;\r");
		return replacedstr;
	}
	
	public static String getCurrentDateAsString() {
		LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return now.format(formatter);
	}
	
}
