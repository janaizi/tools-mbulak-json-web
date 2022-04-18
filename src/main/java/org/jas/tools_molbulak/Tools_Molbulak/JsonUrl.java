package org.jas.tools_molbulak.Tools_Molbulak;

import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class JsonUrl {

	public static String getUrlAsString(String url) {
		try {
			URL urlObj = new URL(url);
			URLConnection con = urlObj.openConnection();

			con.setDoOutput(true); // we want the response
			con.setRequestProperty("Cookie", "myCookie=test123");
			con.connect();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			StringBuilder response = new StringBuilder();
			String inputLine;

			String newLine = System.getProperty("line.separator");
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine + newLine);
			}

			in.close();

			return response.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
