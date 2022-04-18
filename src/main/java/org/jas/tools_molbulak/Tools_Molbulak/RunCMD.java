package org.jas.tools_molbulak.Tools_Molbulak;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunCMD {

	public static void run(String command, String msgOK, String msgNO, String msgRES) {
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
		builder.redirectErrorStream(true);
		Process process;
		try {
			process = builder.start();
			BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			String log = null;
			while (true) {
				line = r.readLine();
				if (line == null) {
					break;
				}
				System.out.println(line);
				log = line;
			}
			if (log.equals(msgRES)) {
				System.out.println(msgOK);
			} else {
				System.out.println(msgNO);
			}
		} catch (IOException e) {
			System.out.println(msgNO);
			e.printStackTrace();
		}
	}

}
