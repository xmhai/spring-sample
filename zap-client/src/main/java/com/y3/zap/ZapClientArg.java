package com.y3.zap;

import java.net.URI;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class ZapClientArg {
	private CommandLine commandLine;
    private URI uri;

    ZapClientArg (String[] args) {
        Options options = new Options();
	    options.addOption(Option.builder("s")
	            .required(true)
	            .desc("ZAP Server Url")
	            .longOpt("serverUrl")
	            .hasArg()
	            .build());
	    options.addOption(Option.builder("k")
	        .required(true)
	        .desc("ZAP API Key")
	        .longOpt("apiKey")
            .hasArg()
	        .build());
	    options.addOption(Option.builder("c")
	        .required(false) // default to 1 "Default Context"
	        .desc("Context Name")
	        .longOpt("context")
            .hasArg()
	        .build());
	    options.addOption(Option.builder("t")
		        .required(false)
		        .desc("Authorization Token")
		        .longOpt("token")
	            .hasArg()
		        .build());
	    options.addOption(Option.builder("l")
	        .required(false)
	        .desc("Login Url")
	        .longOpt("loginUrl")
            .hasArg()
	        .build());
	    options.addOption(Option.builder("u")
	        .required(false)
	        .desc("Username")
	        .longOpt("username")
            .hasArg()
	        .build());
	    options.addOption(Option.builder("p")
	        .required(false)
	        .desc("Password")
	        .longOpt("password")
            .hasArg()
	        .build());
	
        CommandLineParser parser = new DefaultParser();
        try {
        	commandLine = parser.parse(options, args);
        	String s = commandLine.getOptionValue("s");
			uri = new URI (commandLine.getOptionValue("s"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public String getServerAddr() {
    	return uri.getHost(); 
    }

    public int getServerPort() {
    	return uri.getPort(); 
    }

    public String getApiKey() {
    	String key = commandLine.getOptionValue("k");
    	return key==null ? "f2svraokj6n3caqthe1ai5nff7" : key; 
    }

    public String getContext() {
    	return commandLine.getOptionValue("c"); 
    }

    public String getToken() {
    	return commandLine.getOptionValue("t"); 
    }

    public String getLoginUrl() {
    	return commandLine.getOptionValue("l"); 
    }

    public String getUsername() {
    	return commandLine.getOptionValue("u"); 
    }

    public String getPassword() {
    	return commandLine.getOptionValue("p"); 
    }
}
