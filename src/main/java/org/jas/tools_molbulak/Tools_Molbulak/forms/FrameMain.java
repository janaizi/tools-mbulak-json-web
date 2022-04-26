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
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

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
	private JButton btnFirewallOn;
	private JButton btnFirewallOff;
	private JTextField tfNamePC;
	private JTextField tfUser;
	private JPasswordField pfPass;

	private Component frame = null;
	private JTextField tfDomain;

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
		// String jsontxt =
		// JsonUrl.getUrlAsString("file:///C:/Users/jsaliev/Desktop/JDEV/softs.json.txt").replace("\\","\\\\");
		// System.out.println(jsontxt);

		Object tdata[][] = null;
		int jsonstat = 1;
		try {
			ja = new JSONArray(jsontxt);
			tdata = new Object[ja.length()][3];

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

		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, "Проверьте корректность JSON!");
			jsonstat = 0;
		}

		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\jsaliev\\Desktop\\tools-molbulak-icon.png"));
		setTitle("Tools-Molbulak   v0.2");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 532);
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
								String folder = "%systemdrive%\\SoftsMolbulak\\";
								String file = "" + url.subSequence((url.lastIndexOf('/') + 1), url.length());
								String command = "mkdir %systemdrive%\\SoftsMolbulak | bitsadmin /transfer softsMolbulak "
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
								String folder = "%systemdrive%\\SoftsMolbulak\\";
								String file = "" + url.subSequence((url.lastIndexOf('/') + 1), url.length());
								String command = val2.get(k).toString();
								System.out.println("command - " + command);
								RunCMD.run(command, "========= Installed " + file + " OK!",
										"=== Error Install " + file + "!", "");
							}

						} else {
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
		btnInstall.setBounds(5, 11, 384, 30);
		btnInstall.setFont(new Font("Tahoma", Font.BOLD, 14));
		if (jsonstat == 0) {
			btnInstall.hide();
		}
		contentPane.add(btnInstall);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(5, 52, 382, 160);
		scrollPane_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(scrollPane_1);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(5, 223, 384, 202);
		contentPane.add(scrollPane_2);

		String tcname[] = new String[] { "*"/* "Column 1" */, "Проаграммы"/* "Column 1" */, "Версия"/* "Column 1" */ };

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
		progressBar.setBounds(5, 214, 384, 8);
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

		JButton btnUsbOn = new JButton("ON");
		btnUsbOn.setFont(new Font("Dialog", Font.BOLD, 9));
		btnUsbOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RunCMD.run(
						"reg add HKLM\\SOFTWARE\\Policies\\Microsoft\\windows\\RemovableStorageDevices /v Deny_All /t REG_DWORD /d \"0\" /f",
						" Successful - ON USB Storeg!", " Error - ON USB Storeg!", "");
			}
		});
		btnUsbOn.setBounds(5, 451, 66, 23);
		contentPane.add(btnUsbOn);

		JButton btnUsbOff = new JButton("OFF");
		btnUsbOff.setFont(new Font("Dialog", Font.BOLD, 9));
		btnUsbOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RunCMD.run(
						"reg add HKLM\\SOFTWARE\\Policies\\Microsoft\\windows\\RemovableStorageDevices /v Deny_All /t REG_DWORD /d \"1\" /f",
						" Successful - OFF USB Storeg!", " Error - OFF USB Storeg!", "");
			}
		});
		btnUsbOff.setBounds(5, 474, 66, 23);
		contentPane.add(btnUsbOff);

		btnFirewallOn = new JButton("ON");
		btnFirewallOn.setFont(new Font("Dialog", Font.BOLD, 9));
		btnFirewallOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RunCMD.run("netsh advfirewall set allprofiles state on", " Successful - ON firewall!",
						" Error - ON firewall!", "");
			}
		});
		btnFirewallOn.setBounds(81, 451, 66, 23);
		contentPane.add(btnFirewallOn);

		btnFirewallOff = new JButton("OFF");
		btnFirewallOff.setFont(new Font("Dialog", Font.BOLD, 9));
		btnFirewallOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RunCMD.run("netsh advfirewall set allprofiles state off", " Successful - OFF firewall!",
						" Error - OFF firewall!", "");
			}
		});

		btnFirewallOff.setBounds(81, 474, 66, 23);
		contentPane.add(btnFirewallOff);

		tfNamePC = new JTextField();
		tfNamePC.setHorizontalAlignment(SwingConstants.CENTER);
		tfNamePC.setBounds(157, 451, 75, 20);
		contentPane.add(tfNamePC);
		tfNamePC.setColumns(10);

		JButton btnRenamePC = new JButton("Rename");
		btnRenamePC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (tfNamePC.getText().equals("") || tfNamePC.getText() == null || tfNamePC.getText() == "") {
					JOptionPane.showMessageDialog(frame, "Введите новое имя компьютера!");
				} else {
					RunCMD.run("wmic computersystem where name=\"%computername%\" call rename name=\"" + tfNamePC.getText()
							+ "\"", " Successful - rename PC!", " Error - rename PC!", "");
				}

			}
		});

		btnRenamePC.setBounds(157, 473, 75, 23);
		contentPane.add(btnRenamePC);

		JLabel lblNewLabel = new JLabel("USB Storeg");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(5, 436, 66, 14);
		contentPane.add(lblNewLabel);

		JLabel lblFirewall = new JLabel("Firewall");
		lblFirewall.setHorizontalAlignment(SwingConstants.CENTER);
		lblFirewall.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFirewall.setBounds(81, 436, 66, 14);
		contentPane.add(lblFirewall);

		JLabel lblPcName = new JLabel("PC Name");
		lblPcName.setHorizontalAlignment(SwingConstants.CENTER);
		lblPcName.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPcName.setBounds(157, 436, 75, 14);
		contentPane.add(lblPcName);

		JLabel lblDmain = new JLabel("Domain :");
		lblDmain.setHorizontalAlignment(SwingConstants.CENTER);
		lblDmain.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDmain.setBounds(242, 433, 56, 14);
		contentPane.add(lblDmain);

		tfUser = new JTextField();
		tfUser.setHorizontalAlignment(SwingConstants.CENTER);
		tfUser.setText("user");
		tfUser.setToolTipText("");
		tfUser.setColumns(10);
		tfUser.setBounds(242, 451, 73, 20);
		contentPane.add(tfUser);

		pfPass = new JPasswordField();
		pfPass.setHorizontalAlignment(SwingConstants.CENTER);
		pfPass.setBounds(316, 451, 73, 20);
		contentPane.add(pfPass);

		JButton btnAddDomain = new JButton("Add to domain");
		btnAddDomain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (tfDomain.getText().equals("") || tfDomain.getText() == null || tfDomain.getText() == "") {
					JOptionPane.showMessageDialog(frame, "Пожалуйста введите домен!");
				} else if (tfUser.getText().equals("") || tfUser.getText() == null || tfUser.getText() == "") {
					JOptionPane.showMessageDialog(frame, "Пожалуйста введите имя учетной записи для домен!");
				} else if (pfPass.getText().equals("") || pfPass.getText() == null || pfPass.getText() == "") {
					JOptionPane.showMessageDialog(frame,
							"Пожалуйста введите пароль для учетной записи " + tfUser.getText() + "!");
				} else {
					RunCMD.run(
							"wmic computersystem where name=\"%computername%\" call joindomainorworkgroup fjoinoptions=3 name=\""
									+ tfDomain.getText() 
									+ "\" username=\"" + tfUser.getText() 
									+ "\" Password=\""+ pfPass.getText() + "\"",
							" Successful - add to domain!", " Error - add to domain!", "");
				}
			}
		});
		btnAddDomain.setBounds(242, 473, 147, 23);
		contentPane.add(btnAddDomain);

		tfDomain = new JTextField();
		tfDomain.setHorizontalAlignment(SwingConstants.CENTER);
		tfDomain.setText("molbulak.ru");
		tfDomain.setBounds(297, 430, 92, 20);
		contentPane.add(tfDomain);
		tfDomain.setColumns(10);

	}
}
