package com.bin.jmeter.process;

import com.bin.jmeter.domain.ScriptInformation;

public interface ProcessService {
	
	//	RETURN JMETER FILE NAME FOR FURTHER PROCESSING
	public ScriptInformation enrichJMeterScriptWithInput (ScriptInformation info) throws Exception;
	
	public ScriptInformation enrichJMeterFragmentWithInput (ScriptInformation info) throws Exception;
	
}
