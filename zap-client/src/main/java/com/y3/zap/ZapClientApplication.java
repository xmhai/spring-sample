package com.y3.zap;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ApiResponseSet;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

// Prerequist
// make sure ZAP server is running as daemon mode with proper session
// Configure "Options API screen"
//   Set "Addresses permitted to use the API"
//   Get API Key and put it in command line

// Usage:
//   java -jar y3-zap-client-0.0.1-SNAPSHOT.jar -s <zap-server-url> -k <apikey> -c <context> -l <login-url> -u <username> -p <password>
public class ZapClientApplication {
	public static void main(String[] args) throws Exception {
		ZapClientArg arg = new ZapClientArg(args); 
		
		System.out.println("Zap scan is triggered...");

		// get token
		String token = getToken(arg);
		
		// create API Client
		ClientApi api = new ClientApi(arg.getServerAddr(), arg.getServerPort(), arg.getApiKey()); 

		// set token
		if (token != null) {
			try {
				api.replacer.removeRule("JWT");
			} catch (Exception e) {
				// ignore
			}
			api.replacer.addRule("JWT", "true", "REQ_HEADER", "false", "Authorization", token, "");
			System.out.println("Token set.");
		}
		
		// get context id
		int contextId = getContextId(api, arg.getContext());
		
		// trigger scan on the context
		startActiveScan(api, contextId);
		
		// generate report
		getReports(api, arg);
		
		System.out.println("ZAP scan complete");
	}
	
	private static String getToken(ZapClientArg arg) {
		String token = null;
		
		// check is it passed in command line argument
		token = arg.getToken(); 
		if (token != null) {
			return token;
		}
		
		// look for env variable "ZAP_AUTH_HEADER_VALUE" first, this can be set from GitLab or command line
		token = System.getenv("ZAP_AUTH_HEADER_VALUE");
		if (token != null) {
			return token;
		}
		
		// get it from server
		String loginUrl = arg.getLoginUrl();
		if (loginUrl != null) {
			try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
				String request = "{\"username\":\""+arg.getUsername()+"\",\"password\":\""+arg.getPassword()+"\"}";
				System.out.println(request);
				StringEntity requestEntity = new StringEntity(request);
				HttpPost httpPost = new HttpPost(loginUrl);
				httpPost.setHeader("Accept", "application/json");
			    httpPost.setHeader("Content-type", "application/json");				
			    httpPost.setEntity(requestEntity);

			    HttpResponse response = client.execute(httpPost);
			    String responseBody = EntityUtils.toString(response.getEntity());
			    
			    JsonObject jsonObject = new Gson().fromJson(responseBody, JsonObject.class);
			    token = jsonObject.getAsJsonObject("data").getAsJsonPrimitive("token").getAsString();
				System.out.println("Token from server:" + token);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return token==null ? null : "Bearer "+token;
	}
	
	private static int getContextId(ClientApi api, String contextname) {
		int contextId = 1;
		try {
			if (contextname != null) {
				ApiResponseSet resp;
					resp = (ApiResponseSet)api.context.context(contextname);
				ApiResponseElement elem = (ApiResponseElement) resp.getValue("id");
				contextId = Integer.parseInt(elem.getValue());
			}
			System.out.println("Scan context:" + contextname + " [id: "+contextId + "]");
		} catch (ClientApiException e) {
			e.printStackTrace();
		}
		return contextId;
	}
	
	private static void startActiveScan(ClientApi api, int contextId) {

		System.out.println("Active scan context id " + contextId);

		try {
			api.ascan.removeAllScans();
			api.ascan.clearExcludedFromScan();
			
			// initiate the active scan - refer doc to understand the constructor parameters.
			ApiResponse resp = api.ascan.scan(null, "True", "False", null, null, null, contextId);
			int progress;

			// scan response will return the scan id to support concurrent scanning.
			String scanid = ((ApiResponseElement) resp).getValue();
			// Polling the status of scan until it completes
			while (true) {
				Thread.sleep(5000);
				progress = Integer.parseInt(((ApiResponseElement) api.ascan.status(scanid)).getValue());
				System.out.println("Active Scan progress : " + progress + "%");
				if (progress >= 100) {
					break;
				}
			}
			System.out.println("Active Scan complete");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void getReports(ClientApi api, ZapClientArg arg) {
		try {
			// calling core apis to get html report in bytes.
			byte[] bytes = api.core.htmlreport();
			// storing the bytes in to html report.
			String str = new String(bytes, StandardCharsets.UTF_8);
			String timeStamp = new SimpleDateFormat("yyyyMMdd-HHmm").format(new java.util.Date());
			File newTextFile = new File("zap-scan-report-"+arg.getContext()+"-"+timeStamp+".html");
			FileWriter fw = new FileWriter(newTextFile);
			fw.write(str);
			fw.close();
	
			// getting the alert messages and just printing those.
			//String applicationUrl = "http://localhost:8083/";
			//ApiResponse messages = api.core.messages(applicationUrl, "0", "99999999");
			//System.out.println(messages);
	
			System.out.println("Report generated in report.html");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
