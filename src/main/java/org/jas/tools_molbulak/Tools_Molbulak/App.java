package org.jas.tools_molbulak.Tools_Molbulak;

import java.io.IOException;
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
		
		String jsontxt = JsonUrl.getUrlAsString("https://fs.molbulak.com/progs.txt").replace("\\","\\\\");
		System.out.println(jsontxt);
		
		FrameMain fm = new FrameMain();
		fm.show();
		/*
		String jsontxt = JsonUrl.getUrlAsString("https://fs.molbulak.com/progs.txt").replace("\\","\\\\");
		System.out.println(jsontxt);
		
		
		

		JSONArray ja = new JSONArray(jsontxt);
		JSONObject jo = new JSONObject(ja.get(2));
		
		//System.out.println(jo.get("soft").toString());
		
		*/
	}
}
