package com.bin.dictionary.task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;

public final class GetFieldDescriptionTask implements Callable<String> {
	
	@Value("${StringRepositoryURL}")
	private String strRepURL;
	
	private static final String STRING_TEXT_REGEX = "(?<=\"StringText\":\")(.*?)(?=\",)";
	private String labelCode;
	
	@SuppressWarnings("unused")
	private GetFieldDescriptionTask() {};
	
	public GetFieldDescriptionTask(String labelCode) {
		this.labelCode = labelCode;
	}

	@Override
	public String call() throws Exception {
		if (strRepURL == null || strRepURL.isEmpty()) {
			strRepURL = "https://stringrepository.qad.com/QRARepository/rest/QRARepositoryService/";
		}
		String url = strRepURL + "Lookup?strtxt=&strcode="+ this.labelCode +"&ticket=&moduser=&commitstatus=all&stringtype=";
        HttpURLConnection con;
        String res = "";
		try {
			con = (HttpURLConnection) new URL(url).openConnection();
			con.setRequestMethod("GET");
	        con.setRequestProperty("User-Agent", "Mozilla/5.0");
	        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        
	        String inputLine;

	        while ((inputLine = in.readLine()) != null) {
	        	Pattern r = Pattern.compile(STRING_TEXT_REGEX);
	    		Matcher m = r.matcher(inputLine);
	    		while(m.find()){
	    			res=m.group(0).trim();
	    		}
	        }
	        in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

}
