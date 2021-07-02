package com.ntrs.auto;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.awt.event.MouseEvent;

import java.io.IOException;

import java.io.OutputStream;
import java.io.PrintStream;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import java.util.Properties;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.text.BadLocationException;

import org.apache.commons.lang.StringUtils;
import com.ntrs.utils.JDatePickerImpl;

import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.RoundedBalloonStyle;
import net.java.balloontip.utils.FadingUtils;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;

import org.jdatepicker.impl.UtilDateModel;

import com.ntrs.automationDriver.AutomationDriver;

import com.ntrs.automationDriver.UserDetails;

@SuppressWarnings("serial")
public class InputTestAutomation {

	static JFrame j=new JFrame();

	public static int pbVal=0;

	JTextArea textArea;

	JButton buttonRun = new JButton("Run");
	JButton buttonExit = new JButton("Exit");
	JButton buttonOutputValidation = new JButton("Run Output Validation");
	JButton buttonClear = new JButton("Clear");
	JLabel scenariolabel = new JLabel("Scenario : ");
	JLabel categorylabel = new JLabel("Select Category : ");

	public static JProgressBar pb=new JProgressBar(0, 500);

	JLabel pbLabel = new JLabel("Execution Percentage : ");
	JPanel checkboxPanel = new JPanel(new GridBagLayout());
	JPanel comboboxPanel = new JPanel(new GridBagLayout());
	JPanel buttonPanel = new JPanel(new GridBagLayout());
	JPanel cashFlowPanel = new JPanel(new GridBagLayout());

	@SuppressWarnings("rawtypes")
	JComboBox scenarioList = new JComboBox();
	@SuppressWarnings("rawtypes")
	JComboBox envList = new JComboBox();

	@SuppressWarnings("rawtypes")
	JComboBox userList = new JComboBox();
	@SuppressWarnings("rawtypes")
	JComboBox categoryList = new JComboBox();


	JLabel envLabel = new JLabel("Environment : ");
	JLabel rttIDLabel = new JLabel("RTT ID : ");
	JCheckBox chkSimulator = new JCheckBox();
	JCheckBox chkrunOutputValidation = new JCheckBox("", true);
	JCheckBox chkRTTIDAutoGen = new JCheckBox();
	JLabel runOutputValidationLabel = new JLabel("Run Output Validations : ");
	//JLabel simulatorLabel = new JLabel("ByPass Simulator : ");
	JLabel msgIDlabel = new JLabel("RTT ID for Volume Testing : ");
	JLabel msgIDslabel = new JLabel("RTT ID : ");
	JLabel amtlabel = new JLabel("Amount for CashFlow : ");
	JLabel qtylabel= new JLabel("Quantity for CashFlow : ");
	JLabel secIDlabel = new JLabel("Security ID for CashFlow : ");
	JLabel acctlabel= new JLabel("Account for CashFlow : ");
	JLabel rttIDsAutoGenLabel = new JLabel("Auto RTT ID");
	JLabel tradeDateLabel = new JLabel("Trade Date :");
	JLabel executionTime = new JLabel("Execution Time :");
	long MINUTES = 0;
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("mm : ss");
	JLabel clock = new JLabel(sdf.format(new Date(MINUTES)),JLabel.CENTER);

	String msgID = null;
	TextField msgIDText = new TextField(20);
	TextField amount = new TextField(10);
	TextField quantity = new TextField(10);
	TextField tradeDate = new TextField(10);
	TextField secID = new TextField(10);
	TextField acct = new TextField(10);

	JLabel logoLabel = new JLabel(new ImageIcon(getClass().getResource("logo.jpg")));
	//JButton logoButton = new JButton(new ImageIcon("logo.jpg"));
	JButton changeButton = new JButton(new ImageIcon(getClass().getResource("calender.JPG")));
	JButton clearFields = new JButton("Clear Fields");
	TextArea rttIDList = new TextArea();
	Color colorLabels = new Color(0, 102, 0); 
	Color colorLabelsBG = new Color(128,0,0);
	Color colorPb = new Color(0,100,0);

	GridBagConstraints constraints2 = new GridBagConstraints();
	GridBagConstraints constraints11 = new GridBagConstraints();
	//TextArea rttIDList = new TextArea();
	JLabel emaillabel = new JLabel("Email Automation Report : ");
	String runId="";
	//JCheckBox regressionCheckBox = new JCheckBox("", false);
	//JLabel regressionLabel = new JLabel("Regression : ");


	PrintStream standardOut;

	Object runIds = null;
	//int runId = 0;
	String runID = null;
	int scenarioListcount = 0;
	int categoryListcount = 0;
	int envCount = 0;
	int emailCount = 0;

	String[] scenarioDetails = new String[100];
	String[] regressionDetails = new String[100];

	String ScenarioAndScenarioID = null;

	String scenarioIds[] = new String[100];
	String scenarioId = null;
	String scenarioName = null;
	String ScenarioDet[] = new String[100];
	String TradeDt="";

	String scenarioFirstItem = "----------------- Select Scenario to Run ---------------------------------------------";
	String userListFirstItem = "----------------- Select Email ID ----------------------------------------------------";
	String environmentFirstItem = "----------------- UAT -------------------------";
	String rttIDFirstItem = "----------------- Select RTT ID ----------------------------------";
	String categoryListFirstItem = "-- Select Category --";
	String envDets[] = new String[100];
	String env = "DEV";
	ArrayList<UserDetails> userDetails = new ArrayList<UserDetails>();
	String email = null;
	String runSimulator = null;
	String categoryID = null;

	String amt="";
	String qty="";
	String securityID="";
	String account="";


	@SuppressWarnings({ "unchecked", "unused" })
	public InputTestAutomation() {

		j.setTitle("Northern Trust - Test Automation");
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("logo1.jpg"));    
		j.setIconImage(icon);    
		//j.setLayout(null);  





		scenariolabel.setOpaque(true);
		scenariolabel.setForeground(colorLabels);
		envLabel.setOpaque(true);
		envLabel.setForeground(colorLabels);
		rttIDsAutoGenLabel.setOpaque(true);
		rttIDsAutoGenLabel.setForeground(colorLabels);
		rttIDLabel.setOpaque(true);
		rttIDLabel.setForeground(colorLabels);
		pb.setForeground(colorPb);
		pbLabel.setOpaque(true);
		pbLabel.setForeground(colorLabels);
		executionTime.setOpaque(true);
		executionTime.setForeground(colorLabels);
		/*simulatorLabel.setOpaque(true);
		simulatorLabel.setForeground(Color.BLUE);*/
		runOutputValidationLabel.setOpaque(true);
		runOutputValidationLabel.setForeground(colorLabels);
		msgIDlabel.setOpaque(true);
		msgIDlabel.setForeground(colorLabels);
		msgIDslabel.setOpaque(true);
		msgIDslabel.setForeground(colorLabels);
		amtlabel.setOpaque(true);
		amtlabel.setForeground(colorLabels);
		qtylabel.setOpaque(true);
		qtylabel.setForeground(colorLabels);
		secIDlabel.setOpaque(true);
		secIDlabel.setForeground(colorLabels);
		acctlabel.setOpaque(true);
		acctlabel.setForeground(colorLabels);

		pb.setValue(0);
		pb.setStringPainted(true);
		tradeDateLabel.setOpaque(true);
		tradeDateLabel.setForeground(colorLabels);

		emaillabel.setOpaque(true);
		emaillabel.setForeground(colorLabels);
		buttonOutputValidation.setToolTipText("Run Output Validation");
		/*regressionLabel.setOpaque(true);
		regressionLabel.setForeground(Color.BLUE);*/
		categorylabel.setOpaque(true);
		categorylabel.setForeground(colorLabels);
		clearFields.setForeground(colorLabels);


		textArea = new JTextArea(50, 10);

		textArea.setEditable(false);
		//textArea.setToolTipText("Output Values");
		PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
		standardOut = System.out;
		System.setOut(printStream);
		System.setErr(printStream);

		UtilDateModel model = new UtilDateModel();

		Properties pr = new Properties();
		pr.put("text.today", "Today");
		pr.put("text.month", "");
		pr.put("text.year", "");    

		JDatePanelImpl datePanel = new JDatePanelImpl(model, pr);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());

		j.setLayout(new GridBagLayout());

		checkboxPanel.setSize(750, 600);
		comboboxPanel.setSize(750, 600);
		buttonPanel.setSize(750, 600);
		cashFlowPanel.setSize(750,600);

		GridBagConstraints constraints1 = new GridBagConstraints();
		constraints1.anchor = GridBagConstraints.NORTHWEST;
		constraints1.insets = new Insets(10, 10, 10, 10);
		// add components to the panel
		constraints1.gridx = 0;
		constraints1.gridy = 0;     
		//logoButton.setBackground(Color.GREEN.darker());
		checkboxPanel.add(logoLabel, constraints1);
		constraints1.gridx = 1;
		/*checkboxPanel.add(chkrunOutputValidation, constraints1);
		constraints1.gridx = 2;*/






		/*checkboxPanel.add(regressionLabel, constraints1);
		constraints1.gridx = 3;
		checkboxPanel.add(regressionCheckBox, constraints1);*/
		/*checkboxPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Check the required fields"));*/


		//GridBagConstraints constraints2 = new GridBagConstraints();
		constraints2.anchor = GridBagConstraints.WEST;
		constraints2.insets = new Insets(10, 10, 10, 10);
		// add components to the panel
		/*constraints2.gridx = 0;
		constraints2.gridy = 0;     
		comboboxPanel.add(msgIDlabel, constraints2);
		constraints2.gridx = 1;
		constraints2.gridy = 0; 
		comboboxPanel.add(msgIDText, constraints2);


		constraints2.gridx = 2;
		constraints2.gridy = 0; 
		comboboxPanel.add(chkRTTIDAutoGen, constraints2);

		constraints2.gridx = 3;
		constraints2.gridy = 0; 
		comboboxPanel.add(rttIDsAutoGenLabel, constraints2);*/


		constraints2.gridx = 0;
		constraints2.gridy = 1;  
		comboboxPanel.add(envLabel, constraints2);
		constraints2.gridx = 1;
		constraints2.gridy = 1;
		envList.setEditable(false);
		comboboxPanel.add(envList, constraints2);


		constraints2.gridx = 0;
		constraints2.gridy = 2;     
		comboboxPanel.add(scenariolabel, constraints2);
		constraints2.gridx = 1;
		constraints2.gridy = 2;
		comboboxPanel.add(scenarioList, new GridBagConstraints(1, 2, 0, 1, 1, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 1, 1));


		constraints2.gridx = 0;
		constraints2.gridy = 3;     
		comboboxPanel.add(categorylabel, constraints2);
		constraints2.gridx = 1;
		constraints2.gridy = 3;
		comboboxPanel.add(categoryList, constraints2);
		constraints2.gridx = 0;
		constraints2.gridy = 4;     
		comboboxPanel.add(emaillabel, constraints2);
		constraints2.gridx = 1;
		constraints2.gridy = 4;
		comboboxPanel.add(userList, constraints2);
		/*constraints2.gridx = 0;
		constraints2.gridy = 5;
		comboboxPanel.add(pb, constraints2);*/


		comboboxPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Select Values from dropdown to Run"));



		GridBagConstraints constraints3 = new GridBagConstraints();
		constraints3.anchor = GridBagConstraints.WEST;
		constraints3.insets = new Insets(10, 10, 10, 10);
		// add components to the panel
		constraints3.gridx = 0;
		constraints3.gridy = 0;     
		buttonClear.setForeground(colorLabels);
		buttonRun.setForeground(colorLabels);
		buttonExit.setForeground(colorLabels);
		
		/*	buttonClear.setBackground(colorLabelsBG);
		buttonRun.setBackground(colorLabelsBG);
		buttonExit.setBackground(colorLabelsBG);*/
		buttonPanel.add(buttonClear, constraints3);
		constraints3.gridx = 3;
		buttonPanel.add(buttonRun, constraints3);
		constraints3.gridx = 6;
		buttonPanel.add(buttonExit, constraints3);

		GridBagConstraints constraintsLogo = new GridBagConstraints();
		constraintsLogo.anchor = GridBagConstraints.CENTER;
		constraintsLogo.weightx = 1;
		constraintsLogo.gridx = 0;
		constraintsLogo.gridy = 0;
		constraintsLogo.insets = new Insets(10, 10, 10, 10);
		j.add(checkboxPanel, constraintsLogo);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 0;
		constraints.fill = GridBagConstraints.CENTER;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.insets = new Insets(10, 10, 10, 10);
		j.add(comboboxPanel, constraints);
		constraints.weightx = 0;
		constraints.fill = GridBagConstraints.CENTER;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.insets = new Insets(10, 10, 10, 10);
		j.add(cashFlowPanel, constraints);
		constraints.weightx = 0;
		constraints.fill = GridBagConstraints.CENTER;
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.insets = new Insets(10, 10, 10, 10);
		j.add(buttonPanel, constraints);




		constraints11.anchor = GridBagConstraints.WEST;
		constraints11.insets = new Insets(10, 10, 10, 10);



		try {

			/*String ObjectLists[][]=new AutomationDriver().getTestExecutionDetails();
			String scenariosLists[]= ObjectLists[0];
			 for (String sL : ObjectLists[0]) {
			       // System.out.print(sL + ", ");
			        scenariosLists[0]= sL;
			    }
			 
			 
			 
			
			String scenariosToolTipLists[]= ObjectLists[3];
			
			 for (String sTTL : ObjectLists[3]) {
			        //System.out.print(sTTL + ", ");
			        scenariosToolTipLists[3]= sTTL;
			    }
		
			String envrLists[]= ObjectLists[1];
			 for (String envL : ObjectLists[1]) {
			        //System.out.print(envL + ", ");
			        envrLists[0]= envL;
			    }
			
			String usersLists[]= ObjectLists[2];
			 for (String uL : ObjectLists[2]) {
			        //System.out.print(uL + ", ");
			        usersLists[0]= uL;
			    }
			*/
			
			Object ObjectLists[]=new AutomationDriver().getTestExecutionDetailsFromDB();
			String scenariosLists[]= ObjectLists[0].toString().split(",");
			String scenariosToolTipLists[]= ObjectLists[0].toString().split(",");
			System.out.println(scenariosLists.length);
			String envrLists[]= ObjectLists[1].toString().split(",");
			System.out.println(envrLists.length);
			String usersLists[]= ObjectLists[2].toString().split(",");
			System.out.println(usersLists.length);


			userList.addItem(userListFirstItem);
			for(int i=0;i<=usersLists.length-1;i++) {



				if(usersLists[i].startsWith(" ") )
					usersLists[i]=usersLists[i].substring(1);

				if( usersLists[i].endsWith(" ") )
					usersLists[i]=usersLists[i].substring(0, usersLists[i].length()-1);

				if(usersLists[i].contains("["))
					usersLists[i]=usersLists[i].replace("[", "");
				if (usersLists[i].contains("]"))
					usersLists[i]=usersLists[i].replace("]", "");

				userList.addItem( usersLists[i] ); 
			}
			
			scenarioList.setToolTipText("Select Item");
			scenarioList.addItem(scenarioFirstItem);
			for(int i=0;i<=scenariosLists.length-1;i++) {
				if(scenariosLists[i].split("#")[0].startsWith(",")  )
					scenariosLists[i]=scenariosLists[i].split("#")[0].replaceAll("_", ",");
				if(scenariosLists[i].split("#")[0].startsWith(" ")  )
					scenariosLists[i]=scenariosLists[i].split("#")[0].substring(1);
				if(scenariosLists[i].split("#")[0].endsWith(" ") )
					scenariosLists[i]=StringUtils.substring(scenariosLists[i].split("#")[0], 0, scenariosLists[i].split("#")[0].length() - 1);

				if(scenariosLists[i].split("#")[0].contains("["))
					scenariosLists[i]=scenariosLists[i].split("#")[0].replace("[", "");
				if (scenariosLists[i].split("#")[0].contains("]"))
					scenariosLists[i]=scenariosLists[i].split("#")[0].replace("]", "");
				if(scenariosLists[i].split("#")[0].length()>scenarioFirstItem.length()-3)
					scenariosLists[i]=scenariosLists[i].split("#")[0].substring(0, scenarioFirstItem.length()-3)+"...";


				//scenarioList.setToolTipText("Select Item " + i);
				scenarioList.addItem( scenariosLists[i] ); 
			}


			scenarioList.setRenderer(new BasicComboBoxRenderer() {
				@SuppressWarnings("rawtypes")
				public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
					if (isSelected) {
						setBackground(list.getSelectionBackground());
						setForeground(list.getSelectionForeground());
						if (index > -1) {
							if(index == 0) {
								list.setToolTipText("Select Scenario to Run");
							}else {
								list.setToolTipText(scenariosToolTipLists[index-1].split("#")[1]);
							}
						}
					}
					else {
						UIManager.put("ToolTip.background", Color.YELLOW);
						UIManager.put("ToolTip.border",new LineBorder(Color.BLACK,1));
						setBackground(list.getBackground());
						setForeground(list.getForeground());
					}
					UIManager.put("ToolTip.background", Color.YELLOW);
					UIManager.put("ToolTip.border",new LineBorder(Color.BLACK,1));
					setFont(list.getFont());
					setText((value == null) ? "" : value.toString());

					return this;
				}
			});


			envDets=new String[envrLists.length+1];
			//envDets[0]=environmentFirstItem;
			envList.addItem("-----Select Environment------");
			
			//envList.addItem(userListFirstItem);
			for(int i=1;i<=envDets.length;i++) {

				envList.addItem( envrLists[i] ); 
			}
			
			

			scenarioList.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					cashFlowPanel.removeAll();
					clearFields.removeAll();
					cashFlowPanel.setBorder(BorderFactory.createEmptyBorder());
					pb.setValue(0);
					clock.setText("00 : 00");

					comboboxPanel.remove(pbLabel);
					comboboxPanel.remove(pb);
					clearFields.removeAll();
					msgIDText.setText("");
					secID.setText("");
					acct.setText("");
					amount.setText("");
					tradeDate.setText("");
					quantity.setText("");
					secID.setEditable(true);
					acct.setEditable(true);
					amount.setEditable(true);
					tradeDate.setEditable(true);
					quantity.setEditable(true);


					String scenar=scenarioList.getSelectedItem().toString();
					if(scenar.contains("ORDER") && (scenar.contains("SELL") ||scenar.contains("BUY"))) {


						cashFlowPanel.remove(amtlabel);
						cashFlowPanel.remove(amount);
						clearFields.remove(quantity);
						cashFlowPanel.remove(tradeDateLabel);
						cashFlowPanel.remove(tradeDate);
						cashFlowPanel.remove(datePicker);

						cashFlowPanel.remove(msgIDlabel);
						cashFlowPanel.remove(msgIDText);
						cashFlowPanel.remove(chkRTTIDAutoGen);
						cashFlowPanel.remove(rttIDsAutoGenLabel);

						// add components to the panel

						constraints11.gridx = 2;
						constraints11.gridy = 3;     
						cashFlowPanel.add(acctlabel, constraints11);
						constraints11.gridx =3;
						cashFlowPanel.add(acct, constraints11);


						constraints11.gridx =2;  
						constraints11.gridy = 4;     

						cashFlowPanel.add(amtlabel, constraints11);
						constraints11.gridx =3;
						cashFlowPanel.add(amount, constraints11);
						constraints11.gridx =4;
						cashFlowPanel.add(tradeDateLabel, constraints11);
						constraints11.gridx =5;
						tradeDate.setEditable(false);
						cashFlowPanel.add(tradeDate, constraints11);

						constraints11.gridx = 6;

						Date date=null;
						if (date != null) {
							model.setDate(Integer.parseInt((new SimpleDateFormat("yyyy")).format(date)),
									Integer.parseInt((new SimpleDateFormat("MM")).format(date))-1,
									Integer.parseInt((new SimpleDateFormat("dd")).format(date)));
						}


						model.setSelected(true); 
						datePicker.setShowYearButtons(false);
						datePicker.setTextEditable(true);
						datePicker.setButtonFocusable(false);
						datePicker.setSize(600,750);

						changeButton.setPreferredSize(new Dimension(50, 50));

						datePicker.getJFormattedTextField().setPreferredSize(new Dimension(600, 750));
						datePicker.getJFormattedTextField().setBackground(new Color(0xeaeaea));

						cashFlowPanel.add(datePicker,constraints11);  

						cashFlowPanel.setBorder(BorderFactory.createTitledBorder(
								BorderFactory.createEtchedBorder(), "Provide the required fields for CashFlow"));


						j.revalidate();
					}
						
					else if(scenar.contains("Shares") || scenar.contains("LIQUIDATION") ) {


						cashFlowPanel.remove(amtlabel);
						cashFlowPanel.remove(amount);
						clearFields.remove(quantity);
						cashFlowPanel.remove(tradeDateLabel);
						cashFlowPanel.remove(tradeDate);
						cashFlowPanel.remove(datePicker);

						cashFlowPanel.remove(msgIDlabel);
						cashFlowPanel.remove(msgIDText);
						cashFlowPanel.remove(chkRTTIDAutoGen);
						cashFlowPanel.remove(rttIDsAutoGenLabel);

						// add components to the panel

						constraints11.gridx = 2;
						constraints11.gridy = 3;     
						cashFlowPanel.add(secIDlabel, constraints11);
						constraints11.gridx =3;
						cashFlowPanel.add(secID, constraints11);


						constraints11.gridx =2;  
						constraints11.gridy = 4;     

						cashFlowPanel.add(qtylabel, constraints11);
						constraints11.gridx =3;
						cashFlowPanel.add(quantity, constraints11);
						constraints11.gridx =4;
						cashFlowPanel.add(tradeDateLabel, constraints11);
						constraints11.gridx =5;
						tradeDate.setEditable(false);
						cashFlowPanel.add(tradeDate, constraints11);

						constraints11.gridx = 6;

						Date date=null;
						if (date != null) {
							model.setDate(Integer.parseInt((new SimpleDateFormat("yyyy")).format(date)),
									Integer.parseInt((new SimpleDateFormat("MM")).format(date))-1,
									Integer.parseInt((new SimpleDateFormat("dd")).format(date)));
						}


						model.setSelected(true); 
						datePicker.setShowYearButtons(false);
						datePicker.setTextEditable(true);
						datePicker.setButtonFocusable(false);
						datePicker.setSize(600,750);

						changeButton.setPreferredSize(new Dimension(50, 50));

						datePicker.getJFormattedTextField().setPreferredSize(new Dimension(600, 750));
						datePicker.getJFormattedTextField().setBackground(new Color(0xeaeaea));

						cashFlowPanel.add(datePicker,constraints11);  

						cashFlowPanel.setBorder(BorderFactory.createTitledBorder(
								BorderFactory.createEtchedBorder(), "Provide the required fields for CashFlow"));


						j.revalidate();
					}
					else if(scenar.contains("Dollars")||scenar.contains("Withdrawl") || scenar.contains("Contribution") ) {


						cashFlowPanel.remove(qtylabel);
						cashFlowPanel.remove(quantity);
						clearFields.remove(quantity);
						cashFlowPanel.remove(tradeDateLabel);
						cashFlowPanel.remove(tradeDate);
						cashFlowPanel.remove(datePicker);

						cashFlowPanel.remove(msgIDlabel);
						cashFlowPanel.remove(msgIDText);
						cashFlowPanel.remove(chkRTTIDAutoGen);
						cashFlowPanel.remove(rttIDsAutoGenLabel);



						constraints11.gridx = 2;
						constraints11.gridy = 3;     
						cashFlowPanel.add(secIDlabel, constraints11);
						constraints11.gridx =3;
						cashFlowPanel.add(secID, constraints11);


						constraints11.gridx =2;  
						constraints11.gridy = 4;     

						cashFlowPanel.add(amtlabel, constraints11);
						constraints11.gridx =3;
						cashFlowPanel.add(amount, constraints11);
						constraints11.gridx =4;
						cashFlowPanel.add(tradeDateLabel, constraints11);
						constraints11.gridx =5;
						tradeDate.setEditable(false);
						cashFlowPanel.add(tradeDate, constraints11);

						constraints11.gridx = 6;


						Date date=null;
						if (date != null) {
							model.setDate(Integer.parseInt((new SimpleDateFormat("yyyy")).format(date)),
									Integer.parseInt((new SimpleDateFormat("MM")).format(date))-1,
									Integer.parseInt((new SimpleDateFormat("dd")).format(date)));
						}


						model.setSelected(true); 
						datePicker.setShowYearButtons(false);
						datePicker.setTextEditable(true);
						datePicker.setButtonFocusable(false);
						datePicker.setSize(600,750);

						changeButton.setPreferredSize(new Dimension(50, 50));

						datePicker.getJFormattedTextField().setPreferredSize(new Dimension(600, 750));
						datePicker.getJFormattedTextField().setBackground(new Color(0xeaeaea));

						cashFlowPanel.add(datePicker,constraints11);  

						cashFlowPanel.setBorder(BorderFactory.createTitledBorder(
								BorderFactory.createEtchedBorder(), "Provide the required fields for CashFlow"));


						j.repaint();
						j.revalidate();
					}

					else if(scenar.contains("CASHFLOW") && scenar.contains("Regression") ||scenar.contains("Regression")  ) {


						
						constraints2.gridx = 0;
						constraints2.gridy = 5;
						comboboxPanel.add(pbLabel, constraints2);
						constraints2.gridx = 1;
						constraints2.gridy = 5;
						comboboxPanel.add(pb, constraints2);


						j.repaint();
						j.revalidate();
					}
					else if(scenar.contains("BULK") || scenar.contains("Volume")) {

						constraints11.gridx = 2;
						constraints11.gridy = 3;     
						cashFlowPanel.add(msgIDlabel, constraints11);
						constraints11.gridx =3;
						cashFlowPanel.add(msgIDText, constraints11);


						constraints11.gridx =4;   
						constraints11.gridy =3; 
						cashFlowPanel.add(chkRTTIDAutoGen, constraints11);
						constraints11.gridx =5;   
						constraints11.gridy =3;
						cashFlowPanel.add(rttIDsAutoGenLabel, constraints11);


						cashFlowPanel.setBorder(BorderFactory.createTitledBorder(
								BorderFactory.createEtchedBorder(), "Provide the required fields for Volume Testing"));


						j.repaint();
						j.revalidate();
					}

					else if(scenar.contains("ORDER") && scenar.contains("PROD to UAT")) {

						constraints11.gridx = 2;
						constraints11.gridy = 3;     
						cashFlowPanel.add(msgIDslabel, constraints11);
						constraints11.gridx =3;
						cashFlowPanel.add(msgIDText, constraints11);


						cashFlowPanel.setBorder(BorderFactory.createTitledBorder(
								BorderFactory.createEtchedBorder(), "Provide the required fields for Prod to UAT "));


						j.repaint();
						j.revalidate();
					}

					else if (!scenar.contains("Shares") || !scenar.contains("LIQUIDATION") || !scenar.contains("Dollars"))
					{
						clearFields.removeAll();
						cashFlowPanel.removeAll();
						cashFlowPanel.setBorder(BorderFactory.createEmptyBorder());


						j.repaint();
						j.revalidate();
					}


					final ToolTipManager ttm = ToolTipManager.sharedInstance();
					final MouseEvent event = new MouseEvent(scenarioList, 0, 0, 0,0, 0,0, false);
					final int oldDelay = ttm.getInitialDelay();
					final String oldText = scenarioList.getToolTipText(event);


					String scenarioDes[]=new String[1];
					try {
						Object ObjectLists[]=new AutomationDriver().getDescriptionForScenarios(scenar);
						scenarioDes=ObjectLists[0].toString().split(",");


					} catch (Exception e1) {

						e1.printStackTrace();
					}


					if(scenarioDes[0].contains("_ "))				    
						scenarioList.setToolTipText( scenarioDes[0].replaceAll("_ ", ","));
					if(scenarioDes[0].contains("_"))				    
						scenarioList.setToolTipText( scenarioDes[0].replaceAll("_", ","));
					else
						scenarioList.setToolTipText( scenarioDes[0]);

					UIManager.put("ToolTip.background", Color.YELLOW);
					UIManager.put("ToolTip.border",new LineBorder(Color.BLACK,1));


					ttm.setInitialDelay(0);
					ttm.setDismissDelay(10000);
					ttm.mouseMoved(event);

					new Timer().schedule(new TimerTask()
					{
						@Override
						public void run()
						{
							ttm.setInitialDelay(oldDelay);
							scenarioList.setToolTipText(oldText);
						}
					}, ttm.getDismissDelay());


					j.repaint();
					j.revalidate();


				}

			});

			buttonRun.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

				}
			});
			envList.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					secID.setText("");
					acct.setText("");
					amount.setText("");
					tradeDate.setText("");
					quantity.setText("");
					secID.setEditable(true);
					acct.setEditable(true);
					amount.setEditable(true);
					tradeDate.setEditable(true);
					quantity.setEditable(true);

					j.repaint();
					j.revalidate();
					Object envDet = null;
					envDet = envList.getSelectedItem();
					env = envDet.toString();

				}
			});

			clearFields.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					j.repaint();
					j.revalidate();
					secID.setText("");
					acct.setText("");
					amount.setText("");
					tradeDate.setText("");
					quantity.setText("");
					msgIDText.setText("");
					cashFlowPanel.remove(clearFields);
					j.repaint();
					j.revalidate();

				}
			});

			datePicker.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {



					if(!datePicker.getJFormattedTextField().getText().equals("")) {
						tradeDate.setText(datePicker.getJFormattedTextField().getText());


						String date=datePicker.getJFormattedTextField().getText().toString();


						String dt=checkTradeDateToConvertMMddyyyy(date);
						TradeDt=dt;
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");

						LocalDate localDate = LocalDate.parse(dt, formatter);

						DayOfWeek day = DayOfWeek.of(localDate.get(ChronoField.DAY_OF_WEEK));
						String dayVal="";

						String holidays[]= {"12/24/2019","12/25/2019","01/01/2020","01/20/2020","02/17/2020","04/10/2020","05/25/2020","07/03/2020","09/07/2020","11/26/2020","11/27/2020","12/24/2020","12/25/2020"};

						String holidaysDesc[]= {"Christmas Eve - Market is Closed at 1:00 p.m. (1:15 p.m. for eligible options)","Christmas Day - Market is Closed","New Year's Day - Market is Closed","Martin Luther King, Jr. Day - Market is Closed","George Washington's Birthday - Market is Closed","Good Friday - Market is Closed","Memorial Day - Market is Closed","Independence Day - Market is Closed","Labor Day - Market is Closed","Thanksgiving Day - Market is Closed","Thanksgiving Eve - Market is Closed at 1:00 p.m. (1:15 p.m. for eligible options)","Christmas Eve - Market is Closed at 1:00 p.m. (1:15 p.m. for eligible options)","Christmas Day - Market is Closed"};
						String holidayValue="";
						String holidayDes="";
						inner:
							for(int i=0; i<=holidays.length-1; i++)
								if(TradeDt.equals(holidays[i])) {
									holidayValue=holidays[i];
									holidayDes=holidaysDesc[i];
									break inner;
								}



						switchloop:
							switch (day ) {
							case SATURDAY:{


								System.out.println("You have selected Saturday - "+ datePicker.getJFormattedTextField().getText() +" as Trade Date");


								/*datePicker.setToolTipText("You have selected Weekend Saturday - "+ datePicker.getJFormattedTextField().getText() +" as Trade Date");
								//textArea.setToolTipText("You have selected Weekend Saturday - "+ datePicker.getJFormattedTextField().getText() +" as Trade Date");

								final ToolTipManager ttm = ToolTipManager.sharedInstance();
								final MouseEvent event = new MouseEvent(datePicker, 0, 0, 0,0, 0,0, false);
								final int oldDelay = ttm.getInitialDelay();
								final String oldText = datePicker.getToolTipText(event);

								UIManager.put("ToolTip.background", Color.RED);
								UIManager.put("ToolTip.border",new LineBorder(Color.BLACK,1));

								ttm.setInitialDelay(0);
								ttm.setDismissDelay(20000);
								ttm.mouseMoved(event);

								new Timer().schedule(new TimerTask()
								{
									@Override
									public void run()
									{
										ttm.setInitialDelay(oldDelay);

									}
								}, ttm.getDismissDelay());*/


								Thread firstTimeBalloonTipThread = new Thread(new Runnable()
								{
									private ActionListener finishedAction;

									@Override
									public void run()

									{


										BalloonTip balloonTip = new BalloonTip(datePicker, "You have selected Weekend Saturday - "+ datePicker.getJFormattedTextField().getText() +" as Trade Date", new RoundedBalloonStyle(3,3,Color.yellow,new Color(0, 102, 0)),true);
										FadingUtils.fadeInBalloon(balloonTip, finishedAction, 100, 10); 
										// balloonTip.setVisible(true); 
										Thread t1 = new Thread(() -> {balloonTip.setVisible(true); try {

											Thread.sleep(5000);
											balloonTip.setVisible(false);
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}});
										t1.start();
										//balloonTip.setVisible(false); 
									}
								});
								firstTimeBalloonTipThread.setName("splitterThread");
								firstTimeBalloonTipThread.setDaemon(false);
								firstTimeBalloonTipThread.setPriority(Thread.NORM_PRIORITY);
								firstTimeBalloonTipThread.start();  


								j.revalidate();
								j.repaint();




								break switchloop;
							}
							case SUNDAY:{

								System.out.println("You have selected Sunday - "+ datePicker.getJFormattedTextField().getText() +" for Trade Date");

								/*UIManager.put("ToolTip.background", Color.RED);
								UIManager.put("ToolTip.border",new LineBorder(Color.BLACK,1));

								datePicker.setToolTipText("You have selected Weekend Sunday - "+ datePicker.getJFormattedTextField().getText() +" for Trade Date");

								final ToolTipManager ttm = ToolTipManager.sharedInstance();
								final MouseEvent event = new MouseEvent(datePicker, 0, 0, 0,0, 0,0, false);
								final int oldDelay = ttm.getInitialDelay();
								final String oldText = datePicker.getToolTipText(event);


								ttm.setInitialDelay(0);
								ttm.setDismissDelay(20000);
								ttm.mouseMoved(event);

								new Timer().schedule(new TimerTask()
								{
									@Override
									public void run()
									{
										ttm.setInitialDelay(oldDelay);

									}
								}, ttm.getDismissDelay());
								 */

								Thread firstTimeBalloonTipThread = new Thread(new Runnable()
								{
									private ActionListener finishedAction;

									@Override
									public void run()

									{

										final MouseEvent event = new MouseEvent(datePicker, 0, 0, 0,0, 0,0, false);

										BalloonTip balloonTip = new BalloonTip(datePicker, "You have selected Sunday - "+ datePicker.getJFormattedTextField().getText() +" for Trade Date", new RoundedBalloonStyle(3,3,Color.yellow,new Color(0, 102, 0)),true);
										FadingUtils.fadeInBalloon(balloonTip, finishedAction, 100, 10); 
										// balloonTip.setVisible(true); 
										Thread t1 = new Thread(() -> {balloonTip.setVisible(true); try {

											Thread.sleep(5000);
											balloonTip.setVisible(false);
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}});
										t1.start();
										//balloonTip.setVisible(false); 
									}
								});
								firstTimeBalloonTipThread.setName("splitterThread");
								firstTimeBalloonTipThread.setDaemon(false);
								firstTimeBalloonTipThread.setPriority(Thread.NORM_PRIORITY);
								firstTimeBalloonTipThread.start();  

								break switchloop;



							}
							default:{

								if(!holidayValue.equals("") && !holidayValue.equals("Less Than Today")) {
									System.out.println("You have selected "+holidayValue + " - " + holidayDes);

									UIManager.put("ToolTip.background", Color.yellow);
									UIManager.put("ToolTip.border",new LineBorder(new Color(0, 102, 0),1));

									datePicker.setToolTipText("You have selected "+holidayValue + " - " + holidayDes);

									final ToolTipManager ttm = ToolTipManager.sharedInstance();
									final MouseEvent event = new MouseEvent(datePicker, 0, 0, 0,0, 0,0, false);
									final int oldDelay = ttm.getInitialDelay();
									final String oldText = datePicker.getToolTipText(event);


									ttm.setInitialDelay(0);
									ttm.setDismissDelay(20000);
									ttm.mouseMoved(event);

									new Timer().schedule(new TimerTask()
									{
										@Override
										public void run()
										{
											ttm.setInitialDelay(oldDelay);

										}
									}, ttm.getDismissDelay());



									break switchloop;
								}
								/*else if(holidayValue.equals("Less Than Today")) {
									System.out.println("You have selected Past Date for Trade - " + TradeDt);


									JOptionPane.showMessageDialog(null, "You have selected Past Date for Trade - " + TradeDt+"\nPlease select some other Future date for Trade to proceed further. ", "You have selected Past Date for Trade - " + TradeDt,
											 JOptionPane.ERROR_MESSAGE);
									j.repaint();
									j.revalidate();

									UIManager.put("ToolTip.background", Color.RED);
									UIManager.put("ToolTip.border",new LineBorder(Color.BLACK,1));

									datePicker.setToolTipText("You have selected "+holidayValue + " - " + holidayDes);

									final ToolTipManager ttm = ToolTipManager.sharedInstance();
									final MouseEvent event = new MouseEvent(datePicker, 0, 0, 0,0, 0,0, false);
									final int oldDelay = ttm.getInitialDelay();
									final String oldText = datePicker.getToolTipText(event);


									ttm.setInitialDelay(0);
									ttm.setDismissDelay(20000);
									ttm.mouseMoved(event);

									new Timer().schedule(new TimerTask()
									{
										@Override
										public void run()
										{
											ttm.setInitialDelay(oldDelay);

										}
									}, ttm.getDismissDelay());

									break switchloop;
									}*/
								else					
									break switchloop;
							}
							}


								j.repaint();
								j.revalidate();
					}
				}
			});

			userList.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					email = userList.getSelectedItem().toString();
					j.repaint();
					j.revalidate();

				}
			});

			/*secID.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					secID.setText("");
					acct.setText("");
					amount.setText("");
					tradeDate.setText("");
				}
			});*/


			chkSimulator.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {

					runSimulator = e.getStateChange() == 1 ? "byPassSimulator" : "runSimulator";
					msgIDlabel.setText(
							"" + (e.getStateChange() == 1 ? "Enter Message ID to Bypass : " : "Run through Simulator :    "));

				}
			});

			chkRTTIDAutoGen.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {

					LocalDate currentdate = LocalDate.now();
					int currentDay = currentdate.getDayOfMonth();
					String currentdy=String.valueOf(currentDay);
					Month currentMonth = currentdate.getMonth();
					String currentMnth=currentMonth.toString().substring(0, 3);
					if(currentdy.length()==1)
						currentdy="0"+currentdy;

					String rttIDLists="VOL"+currentMnth+currentdy+"TS";

					rttIDLists= e.getStateChange() == 1 ? rttIDLists : "";
					msgIDText.setText(rttIDLists);

					j.repaint();
					j.revalidate();
				}
			});


			/*regressionCheckBox.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {

					//runOutput = e.getStateChange() == 1 ? "runOutput" : "notrunOuput";
					if(e.getStateChange() == 1)
					{
						scenarioList.setVisible(false);
						scenariolabel.setVisible(false);
						categorylabel.setVisible(true);
						categoryList.setVisible(true);
					}
					else
					{
						scenarioList.setVisible(true);
						scenariolabel.setVisible(true);
						categorylabel.setVisible(false);
						categoryList.setVisible(false);

					}

				}
			});*/
			clearConsoleAtBeginning();
		} catch (Exception sqle) {
			System.out.println(sqle.getMessage());
		}


		categorylabel.setVisible(false);
		categoryList.setVisible(false);

		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridheight = 7;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		j.add(new JScrollPane(textArea), constraints);

		buttonRun.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (env == null) {
					JOptionPane.showMessageDialog(null, "Environment should be selected for Run",
							"Environment Selection error", JOptionPane.ERROR_MESSAGE);
					j.repaint();
					j.revalidate();
				}

				j.repaint();
				j.revalidate();
				if((scenarioList.getSelectedItem().toString().contains("Shares") && (secID.getText().equals("")||secID.getText().equals(null)||quantity.getText().equals("")||quantity.getText().equals(null)||tradeDate.getText().equals("")||tradeDate.getText().equals(null)))
						|| (scenarioList.getSelectedItem().toString().contains("LIQUIDATION") && (secID.getText().equals("")||secID.getText().equals(null)||quantity.getText().equals("")||quantity.getText().equals(null)||tradeDate.getText().equals("")||tradeDate.getText().equals(null))
								|| (scenarioList.getSelectedItem().toString().contains("Dollars") && (secID.getText().equals("")||secID.getText().equals(null)||amount.getText().equals("")||amount.getText().equals(null)||tradeDate.getText().equals("")||tradeDate.getText().equals(null))))
						) {
					//||acct.getText().equals("")||acct.getText().equals(null),  ||acct.getText().equals("")||acct.getText().equals(null)
					JOptionPane.showMessageDialog(null, "Please provide Required Fields to Run the Scenario - "+scenarioList.getSelectedItem().toString(),
							"Please provide Require Fields to Run the Scenario - "+scenarioList.getSelectedItem().toString(), JOptionPane.ERROR_MESSAGE);
					j.repaint();
					j.revalidate();
				}


				//printLog();
				else printLog();
			}
		});

		buttonExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				System.exit(0);
			}
		});

		buttonClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {

				clearConsole();
			}
		});

		j.setSize(780, 750);
		//setLocationRelativeTo(null);

	}



	private void printLog() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				//System.out.println("test");

				try {
					Date date = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyHHmmss");
					//System.out.println(formatter.format(date));

					runId=String.valueOf(formatter.format(date));

					email=userList.getSelectedItem().toString();
					scenarioId=scenarioList.getSelectedItem().toString();
					Object msg_ID = msgIDText.getText();
					/*Object msg_ID_List = rttIDList.getSelectedItem();

					if(msg_ID.equals(""))
					msgID = msg_ID_List.toString();
					else*/
					msgID=msg_ID.toString();


					if(scenarioId.contains("Dollars")) {
						amt=amount.getText();
						qty=amt;
					}
					else if(scenarioId.contains("Shares") || scenarioId.contains("LIQUIDATION")) {
						amt=amount.getText();
						qty=quantity.getText();
					}
					else if(scenarioId.contains("Withdrawl")|| scenarioId.contains("Contribution")) {
						amt=amount.getText();
					}
					/*else if(scenarioId.contains("Regression")) {
						amt=amount.getText();
						qty=amt;
					}*/

					
					else if(scenarioId.contains("ORDER") && (scenarioId.contains("SELL") ||scenarioId.contains("BUY"))) {
						amt=amount.getText();
						qty=amt;
					}
					else {
						amt="";
						qty="";
					}

					securityID=secID.getText();
					account=acct.getText();

					env=envList.getSelectedItem().toString();

					/*secID.setEditable(false);
					acct.setEditable(false);
					quantity.setEditable(false);
					amount.setEditable(false);
					tradeDate.setEditable(false);
					scenarioList.j.repaint();
					userList.j.repaint();*/
					j.repaint();
					j.revalidate();


					System.out.println("******************Execution is Started ******************");
					//System.out.println("Run ID is " + runId);

					if(!email.contains("-"))
						System.out.println("Execution is Started for the Run ID : " + runId +" and the Scenario: "+ scenarioId
								+ " with an Environment : " + "UAT "+"\nSet Email Execution Report to "+ email);
					else
						System.out.println("Execution is Started for the Run ID : " + runId +" and the Scenario: "+ scenarioId
								+ " with an Environment : UAT");
					j.repaint();
					j.revalidate();

					if(scenarioId.contains("Regression"))
					{
						cashFlowPanel.removeAll();
						cashFlowPanel.setBorder(BorderFactory.createEmptyBorder());
						pbVal=0;

					}
					//clock.setText("00 : 00");
					//j.setLocation(400,300);
					j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					/*final long THIRTY_MINUTES = 0;
				    final java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("mm : ss");
				    final JLabel clock = new JLabel(sdf.format(new Date(THIRTY_MINUTES)),JLabel.CENTER);*/
					clock.setForeground(Color.BLUE);
					int x = 0;
					ActionListener al = new ActionListener(){
						long x = MINUTES + 1000;
						public void actionPerformed(ActionEvent ae){
							clock.setText(sdf.format(new Date(x)));
							x += 1000;}};
							javax.swing.Timer time = new javax.swing.Timer(1000, al);
							time.start();
							/*JPanel jp = new JPanel();
				    jp.add(clock);*/
							constraints2.gridx = 0;
							constraints2.gridy = 6;
							comboboxPanel.add(executionTime, constraints2);

							constraints2.gridx = 1;
							constraints2.gridy = 6;
							comboboxPanel.add(clock, constraints2);

							/* j.getContentPane().add(comboboxPanel);
				   j.pack();*/
							j.repaint();
							j.revalidate();


							String[] passFailCount=new AutomationDriver().processScenarioRqst(runId, scenarioId, env, runSimulator, msgID, amt, qty, TradeDt, securityID, account, email);
							time.stop();

							j.repaint();
							j.revalidate();

							if (!(runSimulator == null) && !runSimulator.equals("")
									&& runSimulator.equals("byPassSimulator")) {
								System.out.println("Output validation is Completed for the Run ID : "
										+ runId + " and the Scenario ID : " + scenarioId + " with an Environment : " + env);

								j.repaint();
								j.revalidate();
							} else {
								System.out.println("Execution is Completed for the Run ID : " + runId +" and the Scenario: "+ scenarioId
										+ " with an Environment : UAT");
								System.out.println("******************Execution is Completed ******************");


								//System.out.println(passFailCount);


								/*if(scenarioId.contains("CASHFLOW")) {
							GridBagConstraints constraints11 = new GridBagConstraints();
							constraints11.anchor = GridBagConstraints.WEST;
							constraints11.insets = new Insets(10, 10, 10, 10);
							constraints11.gridx =6;   
							constraints11.gridy =3;
							cashFlowPanel.add(clearFields, constraints11);
							j.repaint();
							j.revalidate();

						}*/
								j.repaint();
								j.revalidate();

								if(scenarioId.contains("Regression"))
								{

									comboboxPanel.remove(executionTime);
									comboboxPanel.remove(clock);
									GridBagConstraints constraints11 = new GridBagConstraints();
									constraints11.anchor = GridBagConstraints.WEST;
									constraints11.insets = new Insets(10, 10, 10, 10);
									constraints11.gridx =6;   
									constraints11.gridy =3;

									JLabel resultTotalTestCasesLabel= new JLabel(passFailCount[0]);
									resultTotalTestCasesLabel.setOpaque(true);
									resultTotalTestCasesLabel.setForeground(Color.BLUE);
									cashFlowPanel.add(resultTotalTestCasesLabel, constraints11);

									constraints11.gridy =4;
									JLabel resultPassCountLabel= new JLabel(passFailCount[1]);
									Color colorPassCountLable = new Color(0, 77, 0); 
									resultPassCountLabel.setOpaque(true);
									resultPassCountLabel.setForeground(colorPassCountLable);
									cashFlowPanel.add(resultPassCountLabel, constraints11);

									constraints11.gridy =5;						
									JLabel resultFailedCountLabel= new JLabel(passFailCount[2]);
									resultFailedCountLabel.setOpaque(true);
									resultFailedCountLabel.setForeground(Color.RED);
									cashFlowPanel.add(resultFailedCountLabel, constraints11);


									cashFlowPanel.setBorder(BorderFactory.createTitledBorder(
											BorderFactory.createEtchedBorder(), "Execution Result Summary - "+clock.getText()));


									j.repaint();
									j.revalidate();

								}
								else if( scenarioId.contains("LATE_NOTE"))
									clearFields.removeAll();
								else if( scenarioId.contains("TBR"))
									clearFields.removeAll();

								else if(!scenarioId.contains("BULK") || !scenarioId.contains("Volume") || !scenarioId.contains("Cash Batch"))

								{
									GridBagConstraints constraints11 = new GridBagConstraints();
									constraints11.anchor = GridBagConstraints.WEST;
									constraints11.insets = new Insets(10, 10, 10, 10);
									constraints11.gridx =5;   
									constraints11.gridy =3;

									cashFlowPanel.add(clearFields, constraints11);



									j.repaint();
									j.revalidate();

								}

								j.repaint();
								j.revalidate();

								/*secID.setText("");
						acct.setText("");
						amount.setText("");
						tradeDate.setText("");
						quantity.setText("");*/


								//textArea.setToolTipText("******************Execution is Completed ******************");

								/*Thread firstTimeBalloonTipThread = new Thread(new Runnable()
						        {
						            private ActionListener finishedAction;

						            @Override
						            public void run()
						            {
						                BalloonTip balloonTip = new BalloonTip(textArea, "******************Execution is Completed ******************");
						                if (animated) { FadingUtils.fadeInBalloon(balloonTip, finishedAction, 1000, 10); } else { balloonTip.setVisible(true); }
						                try { Thread.sleep(60000); } catch (InterruptedException ex) {  }
						                if (animated) { FadingUtils.fadeOutBalloon(balloonTip, finishedAction, 1000, 10); } else { balloonTip.setVisible(true); }
						            }
						        });
						        firstTimeBalloonTipThread.setName("splitterThread");
						        firstTimeBalloonTipThread.setDaemon(false);
						        firstTimeBalloonTipThread.setPriority(Thread.NORM_PRIORITY);
						        firstTimeBalloonTipThread.start();  */

								/*add(buttonOutputValidation,
									new GridBagConstraints(0, 3, 1, 1, 1, 0, GridBagConstraints.NORTHEAST,
											GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

							j.revalidate();
							j.repaint();*/

								if (chkrunOutputValidation.isSelected()) {
									buttonOutputValidation.doClick();
									/*remove(buttonOutputValidation);
								j.revalidate();
								j.repaint();*/
								} else {
									JOptionPane.showMessageDialog(null,
											"Input Preparation is completed. \nPlease Click on Run Output Validation Button for Output Execution ",
											"Click on Run Output Validation Button", JOptionPane.INFORMATION_MESSAGE);

								}

								j.repaint();
								j.revalidate();

							}
				} catch (Exception e) {

					e.printStackTrace();

				}
			}
		});
		thread.start();
	}



	/*@SuppressWarnings({ "static-access" })
	private void runOutputValidation() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				loop: while (true) {
					try {

						new AutomationDriver().
							//new OutputValidateApp().processOutputValidations(runId, scenarioId, msgID, env, email);
							remove(buttonOutputValidation);
							j.revalidate();
							j.repaint();
							System.out.println("Output Validation is Completed for the Run ID : " + runID
									+ " and the Scenario ID : " + scenarioId + " with an Environment : " + env);	

						System.out.println("******************Execution is completed******************");
						System.out.println("\n");
						break loop;
					} catch (Exception e) {
						e.printStackTrace();
						break loop;
					}
				}
			}
		});
		thread.start();
	}*/

	private void resetVariables() {
		if (scenarioList.getItemCount() > 0) {
			scenarioList.removeAllItems();
		}
		if (categoryList.getItemCount() > 0) {
			categoryList.removeAllItems();
		}
		/*scenarioList.addItem(scenarioFirstItem);
		categoryList.addItem(categoryListFirstItem);*/
	}

	private void clearConsole() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				loop: while (true) {
					try {
						textArea.setText("Console values are cleared ");
						break loop;
					} catch (Exception e) {
						e.printStackTrace();
						break loop;
					}
				}
			}
		});
		thread.start();
	}

	private void clearConsoleAtBeginning() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				loop: while (true) {
					try {
						textArea.setText("");
						break loop;
					} catch (Exception e) {
						e.printStackTrace();
						break loop;
					}
				}
			}
		});
		thread.start();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new InputTestAutomation().j.setVisible(true);

			}
		});
	}

	public class CustomOutputStream extends OutputStream {
		private JTextArea textArea;

		public CustomOutputStream(JTextArea textArea) {
			this.textArea = textArea;
		}

		@Override
		public void write(int b) throws IOException {
			textArea.setText(textArea.getText() + String.valueOf((char) b));
			textArea.setCaretPosition(textArea.getDocument().getLength());
			//System.out.println(textArea.getLineCount());
			/*

			if(textArea.getText().contains("Only local connections are allowed.\r\n")) {
			int lines = textArea.getLineCount();
			int lineCount=0;
	        try{// Traverse the text in the JTextArea line by line
	            for(int i = 0; i < lines; i ++){
	                int start = textArea.getLineStartOffset(i);
	                int end = textArea.getLineEndOffset(i);
	                // Implement method processLine
	                processLine(textArea.getText(start, end-start));


	            }
	        }catch(BadLocationException e){
	            // Handle exception as you see fit
	        }

}*/
		}

		/*private void processLine() {
			// TODO Auto-generated method stub
			pb.setValue(5);
		}*/
	}

	public static int generateRunId() {
		int aNumber = 0; 
		aNumber = (int)((Math.random() * 9000000)+1000000); 
		String newAccountName = String.valueOf(aNumber);

		return Integer.parseInt(newAccountName);

	}


	public static String checkTradeDateToConvertMMddyyyy(String values) {

		String val[]=values.split(" ");
		if(val[0].contains("Jan")) {
			val[0]="01";
		}else if(val[0].contains("Feb")) {
			val[0]="02";
		}else if(val[0].contains("Mar")) {
			val[0]="03";
		}else if(val[0].contains("Apr")) {
			val[0]="04";
		}else if(val[0].contains("May")) {
			val[0]="05";
		}else if(val[0].contains("Jun")) {
			val[0]="06";
		}else if(val[0].contains("Jul")) {
			val[0]="07";
		}else if(val[0].contains("Aug")) {
			val[0]="08";
		}else if(val[0].contains("Sep")) {
			val[0]="09";
		}else if(val[0].contains("Oct")) {
			val[0]="10";
		}else if(val[0].contains("Nov")) {
			val[0]="11";
		}else if(val[0].contains("Dec")) {
			val[0]="12";
		}

		String dt=val[1].replaceAll(",", "");
		if(Integer.parseInt(dt)<=9)
			dt="0"+dt;

		String newDate=val[0].concat("/").concat(dt).concat("/").concat(val[2]);

		return newDate;

	}



	public static void processLine(int val) {
		pbVal=pbVal+val;
		//j.comboboxPanel.add(pb, constraints2);
		pb.setValue(pbVal);
		/*	j.repaint();
		j.revalidate();*/

	}


}
