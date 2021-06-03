package ncomegf.bpw.analytics.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class TrustedTicket {

	public static String getTrustedTicket(String scheme, String host, int port, String username, String client_ip)
			throws Exception {
				String ticketId = "";
				DefaultHttpClient httpclient = new DefaultHttpClient();
				URI uri = new URI(scheme, "", host, port, "/trusted", (String) null, (String) null);
				HttpPost httppost = new HttpPost(uri);
				List<BasicNameValuePair> nvps = new ArrayList();
				nvps.add(new BasicNameValuePair("username", username));
				nvps.add(new BasicNameValuePair("client_ip", client_ip));
				httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
				HttpResponse response = httpclient.execute(httppost);
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			
				for (String line = ""; (line = rd.readLine()) != null; ticketId = ticketId + line) {
					;
				}
			
				return ticketId;
			}
	
	public static String stackTraceToString(Throwable e) {
		String retValue = null;
		StringWriter sw = null;
		PrintWriter pw = null;

		try {
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			retValue = sw.toString();
		} finally {
			try {
				if (pw != null) {
					pw.close();
				}

				if (sw != null) {
					sw.close();
				}
			} catch (IOException var10) {
				;
			}

		}

		return retValue;
	}


}