package org.jas.tools_molbulak.Tools_Molbulak;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Watchable;

import javax.swing.plaf.synth.SynthStyle;

import org.jas.tools_molbulak.Tools_Molbulak.forms.FrameMain;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		
		FrameMain fm = new FrameMain();
		fm.show();
		
		
		//String jsontxt = JsonUrl.getUrlAsString("https://fs.molbulak.com/progs.txt").replace("\\","\\\\");
		//System.out.println(jsontxt);
		
		/*
		String jsontxt = JsonUrl.getUrlAsString("https://fs.molbulak.com/progs.txt").replace("\\","\\\\");
		System.out.println(jsontxt);

		JSONArray ja = new JSONArray(jsontxt);
		JSONObject jo = new JSONObject(ja.get(2));
		
		//System.out.println(jo.get("soft").toString());
		
		
		
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "reg add HKLM\\SOFTWARE\\Policies\\Microsoft\\windows\\RemovableStorageDevices /v Deny_All /t REG_DWORD /d \"1\" /f");
		builder.redirectErrorStream(true);
		Process process;
	    String cmd = "reg add HKLM\\SOFTWARE\\Policies\\Microsoft\\windows\\RemovableStorageDevices /v Deny_All /t REG_DWORD /d \"1\" /f";

		try {
			Process proc = Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}
}
