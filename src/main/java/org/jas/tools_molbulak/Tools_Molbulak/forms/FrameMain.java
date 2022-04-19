package org.jas.tools_molbulak.Tools_Molbulak.forms;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JScrollPane;

import java.awt.Toolkit;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jas.tools_molbulak.Tools_Molbulak.JsonUrl;
import org.jas.tools_molbulak.Tools_Molbulak.RunCMD;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class FrameMain extends JFrame {

	/**
	 * 
	 */
	private JPanel contentPane;
	private JTable table;
	private JProgressBar progressBar;
	private JButton btnInstall;
	static String msg = "";

	private JSONArray ja = null;
	JSONObject jo = null;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameMain frame = new FrameMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrameMain() {
		String jsontxt = JsonUrl.getUrlAsString("https://fs.molbulak.com/progs.txt").replace("\\", "\\\\");
		// System.out.println(jsontxt);

		try {
			ja = new JSONArray(jsontxt);

			Object tdata[][] = new Object[ja.length()][3];

			// System.out.println(sizeJson);

			for (int i = 0; i < ja.length(); i++) {
				try {
					JSONObject jo = new JSONObject(ja.get(i).toString());

					tdata[i][0] = new Boolean(false);
					tdata[i][1] = new String(jo.get("soft").toString());
					tdata[i][2] = new String(jo.get("version").toString());

				} catch (Exception e) {
					break;
				}
			}

			setResizable(false);
			setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\jsaliev\\Desktop\\tools-molbulak-icon.png"));
			setTitle("Tools-Molbulak   v0.1");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 400, 500);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);

			btnInstall = new JButton("Установить");
			btnInstall.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					progressBar.show();
					progressBar.enable();
					table.disable();
					btnInstall.hide();
					new Thread(new Runnable() {
						public void run() {

							JSONObject jo = new JSONObject();
							ArrayList<String> val1 = new ArrayList<String>();
							ArrayList<String> val2 = new ArrayList<String>();

							for (int i = 0; i < ja.length(); i++) {
								if (table.getValueAt(i, 0).equals(true)) {

									for (int j = 0; j < ja.length(); j++) {
										jo = new JSONObject(ja.get(j).toString());
										if (table.getValueAt(i, 1).equals(jo.get("soft").toString())) {
											val1.add(jo.get("url").toString());
											val2.add(jo.get("command").toString());
										}
									}

								}

							}

							if (val1.size() != 0) {
								System.out.println("		Start Download...");
								System.out.println();
								for (int k = 0; k < val1.size(); k++) {

									String url = val1.get(k).toString();
									String folder = "C:\\SoftsMolbulak\\";
									String file = "" + url.subSequence((url.lastIndexOf('/') + 1), url.length());
									String command = "mkdir C:\\SoftsMolbulak | bitsadmin /transfer softsMolbulak "
											+ url + " " + folder + file;
									System.out.println("url - " + url);
									System.out.println("folder - " + folder);
									RunCMD.run(command, "========= Downloaded " + file + "!",
											"=== Error Download " + file + "!", "Transfer complete.");
									System.out.println();
								}

								System.out.println();
								System.out.println();
								System.out.println("		Start Install...");
								System.out.println();
								for (int k = 0; k < val1.size(); k++) {
									String url = val1.get(k).toString();
									String folder = "C:\\SoftsMolbulak\\";
									String file = "" + url.subSequence((url.lastIndexOf('/') + 1), url.length());
									String command = val2.get(k).toString();
									System.out.println("command - " + command);
									RunCMD.run(command, "========= Installed " + file + " OK!",
											"=== Error Install " + file + "!", "");
								}

							} else {
								Component frame = null;
								JOptionPane.showMessageDialog(frame, "Выберите программу для установки!");
							}
							table.enable();
							progressBar.disable();
							progressBar.hide();
							btnInstall.show();
						}
					}).start();
				}
			});
			btnInstall.setBounds(5, 11, 384, 37);
			btnInstall.setFont(new Font("Tahoma", Font.BOLD, 14));
			contentPane.add(btnInstall);

			JScrollPane scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(5, 59, 382, 196);
			scrollPane_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			contentPane.add(scrollPane_1);

			JScrollPane scrollPane_2 = new JScrollPane();
			scrollPane_2.setBounds(5, 266, 384, 194);
			contentPane.add(scrollPane_2);

			String tcname[] = new String[] { "*"/* "Column 1" */, "Проаграммы"/* "Column 1" */,
					"Версия"/* "Column 1" */ };

			DefaultTableModel defaultTableModel = new DefaultTableModel(tdata, tcname) {
				public Class<?> getColumnClass(int col) {
					switch (col) {
					case 0:
						return Boolean.class;
					case 1:
						return String.class;
					case 2:
						return String.class;
					default:
						return String.class;
					}
				}
			};

			table = new JTable();
			table.setModel(defaultTableModel);
			table.getColumnModel().getColumn(0).setPreferredWidth(40);
			table.getColumnModel().getColumn(0).setMinWidth(30);
			table.getColumnModel().getColumn(0).setMaxWidth(50);
			table.getColumnModel().getColumn(1).setPreferredWidth(300);
			table.getColumnModel().getColumn(1).setMinWidth(200);
			table.getColumnModel().getColumn(1).setMaxWidth(400);
			table.getColumnModel().getColumn(2).setPreferredWidth(70);
			table.getColumnModel().getColumn(2).setMinWidth(60);
			table.getColumnModel().getColumn(2).setMaxWidth(80);
			table.setRowHeight(22);
			scrollPane_1.setViewportView(table);

			progressBar = new JProgressBar();
			progressBar.setBounds(5, 256, 384, 8);
			contentPane.add(progressBar);
			progressBar.hide();

			progressBar.setIndeterminate(true);

			textArea = new JTextArea();
			textArea.setBounds(-27, 266, 379, 94);

			textArea.setBackground(Color.BLACK);
			textArea.setForeground(Color.LIGHT_GRAY);
			textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
			System.setOut(new PrintStream(new OutputStream() {
				@Override
				public void write(int b) throws IOException {
					textArea.append(String.valueOf((char) b));
				}
			}));
			scrollPane_2.setViewportView(textArea);
		} catch (Exception e) {
			Component frame = null;
			JOptionPane.showMessageDialog(frame, "Проверьте корректность JSON!");
			System.exit(0);
		}
	}
}
