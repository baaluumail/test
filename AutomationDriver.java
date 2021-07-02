package com.ntrs.automationDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.ntrs.automationDriver.InputDataDetails;
import com.ntrs.BRS.BusinessFunctions;
import com.ntrs.auto.InputTestAutomation;
import com.ntrs.utils.Constants;
import com.ntrs.utils.Library;
import com.ntrs.utils.OR;
import com.ntrs.utils.lib;

public class AutomationDriver extends BusinessFunctions {

	private static String wsURL;
	
	// For Running as Java Application and JAR execution
	public static void main(String args[]) throws Exception {
		////System.out.println("Test");
		AutomationDriver AutomationTesting = new AutomationDriver();
		
		// GPSAutomationTesting.processGPSRqst(args[0], args[1], args[2],
		// args[3], args[4], args[5]);
		//AutomationTesting.processScenarioRqst(args[0], args[1], args[2], args[3], args[4], args[5]);
		//AutomationTesting.processScenarioRqst("12345", "101", "UAT", null , null,"bg132@ntrs.com");
		AutomationTesting.processScenarioRqst(args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8], args[9], args[10]);
		
		
	}



	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String[] processScenarioRqst(String runId, String testId, String env, String simulator, String msgID, String amount, String quantity, String tradeDate, String secID, String acct, String email) throws Exception {
		Connection conn = null;
		Statement inputDefStmt = null;
		ResultSet inputDefRs = null;
		OutputExecDetails inpPrepData = new OutputExecDetails();
		TestStatusDesc statusDetail = new TestStatusDesc();
		String testStatus = null;
		int passFailcount=0;
		int r=1;
		int totalTestCase=0;
		String[] result=new String[3];
		try {


			try {
				conn = BusinessFunctions.getConnection(env);

				//String qryScenariosList = "SELECT * FROM AUTOMATION_SCENARIOS WHERE ROUTING_CLASS='"+testId+"'";

				String qryScenariosList = "SELECT * FROM PWP_AUTOMATION_SCENARIOS WHERE ACTION='"+testId+"'";
				
				Statement stmtScenariosList = conn.createStatement();
				ResultSet rsScenariosList = stmtScenariosList.executeQuery(qryScenariosList);
				String mainClass = "";
				String url ="";
				String testID="";
				String inputType="";
				String routingMethod="";
				String query ="";
				String sp ="";
				while (rsScenariosList.next()) {
					routingMethod = rsScenariosList.getString("ROUTING_CLASS");
					mainClass = rsScenariosList.getString("MAIN_CLASS");
					testID=rsScenariosList.getString("ACTION");
					/*inputType = rsScenariosList.getString("INPUT_TYPE");
					url = rsScenariosList.getString("URL");
					query = rsScenariosList.getString("QUERY");
					sp = rsScenariosList.getString("SPNAME");*/

				}

				Class automationCls = getAutomationClass(mainClass);
				inpPrepData = new OutputExecDetails();
				inpPrepData.setEnvironment(env);
				inpPrepData.setRoutingClass(routingMethod);
				inpPrepData.setMainClass(mainClass);
				inpPrepData.setEmail(email);
				inpPrepData.setMsgId(msgID);
				inpPrepData.setRunId(runId);
				inpPrepData.setUrl(url);
				inpPrepData.setTestId(testID);
				inpPrepData.setScenarioId(mainClass);
				inpPrepData.setSimulator(simulator);
				inpPrepData.setInputType(inputType);
				inpPrepData.setQuery(query);
				inpPrepData.setSpName(sp);
				inpPrepData.setAmount(amount);
				inpPrepData.setQuantity(quantity);
				inpPrepData.setTradeDate(tradeDate);
				inpPrepData.setSecurityID(secID);
				inpPrepData.setAccount(acct);

				//BusinessFunctions.startReportGeneration(inpPrepData);
				
				
				BusinessFunctions.startReportGeneration(inpPrepData);
				if(routingMethod.contains("~")) {
					String routes[]=routingMethod.split("~");
					totalTestCase=routes.length-1;
					
					
					for(r=1;r<=routes.length-1;r++) {
					Method method = automationCls.getMethod(routes[0], OutputExecDetails.class);
					BusinessFunctions.reportGenerationRegression(inpPrepData,routes[r]);
					Library.messages.clear();
					inpPrepData.setRoutingClass(routes[r]);
					inpPrepData.setPbPercentage(totalTestCase);
					//InputTestAutomation.pb.setValue(pbVal);
					
					method.invoke(automationCls.newInstance(),inpPrepData);
					testStatus="PASS";
					
					//InputTestAutomation.processLine();
					BusinessFunctions.completeReportGenerationRegression(inpPrepData, statusDetail, testStatus, r-1);
					passFailcount=r;
					
					
					
					}
					
				}
				else {
					//BusinessFunctions.startReportGeneration(inpPrepData);
				totalTestCase=1;
				Method method = automationCls.getMethod(routingMethod, OutputExecDetails.class);
				
				BusinessFunctions.reportGeneration(inpPrepData);
				Library.messages.clear();
				method.invoke(automationCls.newInstance(),
						inpPrepData);
				testStatus="PASS";
				int failedCount=BusinessFunctions.completeReportGeneration(inpPrepData, statusDetail, testStatus);
				if(failedCount>=1) {
					passFailcount=0;
				}
				else
				passFailcount=totalTestCase;
				
				}
				


			} catch (SQLException e) {
				int ret_code = e.getErrorCode();
				System.err.println(ret_code + e.getMessage());
			} catch (Exception e) {
				
				testStatus="FAIL";
				Library.logMessages(Constants.FAIL, "Exception occured as "+e.toString(),"Exception occured as "+e.toString());
				InputTestAutomation.processLine(5);
				BusinessFunctions.completeReportGeneration(inpPrepData, statusDetail, testStatus);
				//System.out.println("HTML Automation Report path  - " + htmlreportpath);
				/*System.out.println("Total number of test Cases is - "+ totalTestCase);
				System.out.println("Total number of test Cases Failed is - "+ (totalTestCase-passFailcount));
				System.out.println("Total number of test Cases Passed is - "+ (passFailcount));
				*/
				
				try {

					e.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} finally {
				
				InputTestAutomation.pb.setValue(500);
				System.out.println("Total number of test Cases is - "+ totalTestCase);
				System.out.println("Total number of test Cases Passed is - "+ passFailcount);
				System.out.println("Total number of test Cases Failed is - "+ (totalTestCase-passFailcount));
	
				System.out.println("HTML Automation Report path  - " + htmlreportpath);
				if (inpPrepData.getEmail() != null && inpPrepData.getEmail().contains("@") ) {
					String emailDet[] = inpPrepData.getEmail().split("<");
					String emailId = emailDet[1].replace(">", "");

					Library.sendMailFromOutlook(emailId, "", "", "Automation Testing Report for the Run ID " + inpPrepData.getRunId(),
							"Automation Testing Report for " + inpPrepData.getTestId() + " with the Run ID " + inpPrepData.getRunId(),
							htmlreportpath);
				}
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException sqle) {
						System.out.println("updateOutputExecDataStartTime - Error occured while closing the Connection");
					}
				}
			}

			


		} catch (Exception e) {
			try {
				e.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			if (inputDefRs != null) {
				inputDefRs.close();
			}
			if (inputDefStmt != null) {
				inputDefStmt.close();
			}
			if (conn != null) {
				conn.close();
			}

		}
		String totalCases="Total number of Test Case Executed - "+ totalTestCase;
		String totalPassCases="Total number of Test Cases Passed - "+ passFailcount;
		String totalFailedCases="Total number of Test Cases Failed -  "+ (totalTestCase-passFailcount);

		
			result[0]=totalCases;
			result[1]=totalPassCases;
			result[2]=totalFailedCases;
		
		
		return result;
	}




	public static Class getAutomationClass(String className) throws ClassNotFoundException {
		ClassLoader automation = AutomationDriver.class.getClassLoader();

		// String clsPackage="com.ntrs.ITS.";

		className = className.contains(".") ? className : "com.ntrs.amsf." + className;

		Class automationCls = automation.loadClass(className);
		return automationCls;
	}



	@SuppressWarnings({ "rawtypes", "deprecation", "resource", "unused" })
	public String[][] getTestExecutionDetails() throws Exception {
		// Location of the source file
		String sourceFilePath = Constants.TestDataCommonPath;

		FileInputStream fileInputStream = null;

		// Array List to store the excel sheet data
		List<List<XSSFCell>> excelDataScenario = new ArrayList<List<XSSFCell>>();
		List<List<XSSFCell>> excelDataEnvironments = new ArrayList<List<XSSFCell>>();
		List<List<XSSFCell>> excelDataUsers = new ArrayList<List<XSSFCell>>();

		// FileInputStream to read the excel file
		fileInputStream = new FileInputStream(sourceFilePath);

		// Create an excel workbook
		XSSFWorkbook excelWorkBook = new XSSFWorkbook(fileInputStream);

		// Retrieve the first sheet of the workbook.
		XSSFSheet excelSheetScenario = excelWorkBook.getSheetAt(0);

		// Iterate through the sheet rows and cells. 
		// Store the retrieved data in an arrayList
		Iterator rowsScenarios = excelSheetScenario.rowIterator();
		int rowCountScenarios=0;
		while (rowsScenarios.hasNext()) {
			XSSFRow row = (XSSFRow) rowsScenarios.next();
			Iterator cells = row.cellIterator();

			List<XSSFCell> cellData = new ArrayList<XSSFCell>();
			while (cells.hasNext()) {
				XSSFCell cell = (XSSFCell) cells.next();
				cellData.add(cell);
			}
			rowCountScenarios=rowCountScenarios+1;
			excelDataScenario.add(cellData);

		}

		XSSFSheet excelSheetEnvironments = excelWorkBook.getSheetAt(1);

		// Iterate through the sheet rows and cells. 
		// Store the retrieved data in an arrayList
		Iterator rowsEnvironments = excelSheetEnvironments.rowIterator();
		int rowCountEnvironments=0;
		while (rowsEnvironments.hasNext()) {
			XSSFRow row1 = (XSSFRow) rowsEnvironments.next();
			Iterator cells= row1.cellIterator();

			List<XSSFCell> cellData = new ArrayList<XSSFCell>();
			while (cells.hasNext()) {
				XSSFCell cell = (XSSFCell) cells.next();
				cellData.add(cell);
			}
			rowCountEnvironments=rowCountEnvironments+1;
			excelDataEnvironments.add(cellData);

		}
		XSSFSheet excelSheetUsers = excelWorkBook.getSheetAt(2);

		// Iterate through the sheet rows and cells. 
		// Store the retrieved data in an arrayList
		Iterator rowsUsers = excelSheetUsers.rowIterator();
		int rowCountUsers=0;
		while (rowsUsers.hasNext()) {
			XSSFRow rowUsers = (XSSFRow) rowsUsers.next();
			Iterator cells= rowUsers.cellIterator();

			List<XSSFCell> cellData = new ArrayList<XSSFCell>();
			while (cells.hasNext()) {
				XSSFCell cell = (XSSFCell) cells.next();
				cellData.add(cell);
			}
			rowCountUsers=rowCountUsers+1;
			excelDataUsers.add(cellData);

		}
		String scenarioList[]=new String[rowCountScenarios-1];
		String scenarioDescList[]=new String[rowCountScenarios-1];
		String environmentsList[]=new String[rowCountEnvironments-1];
		String usersList[]=new String[rowCountUsers-1];
		int actionColumn=3;
		int descColumn=4;
		outer:
			for (int rowNumScenarios = 0; rowNumScenarios < rowCountScenarios; rowNumScenarios++) {

				List listScenarios = excelDataScenario.get(rowNumScenarios);
				inner:
					for(int i=0; i<=listScenarios.size()-1; i++) {
						XSSFCell cell = (XSSFCell) listScenarios.get(i);
						if(cell.getRichStringCellValue().getString().trim().equalsIgnoreCase("ACTION")) {
							actionColumn=i;
								
						}
						if(cell.getRichStringCellValue().getString().trim().equalsIgnoreCase("DESCRIPTION")) {
							descColumn=i;
							break inner;	
						}
						

					}
				break outer;
			}
		// Print retrieved data to the console
		for (int rowNumScenarios = 1; rowNumScenarios < rowCountScenarios; rowNumScenarios++) {

			List listScenarios = excelDataScenario.get(rowNumScenarios);

			XSSFCell cell = (XSSFCell) listScenarios.get(actionColumn);
			
				scenarioList[rowNumScenarios-1]=cell.getRichStringCellValue().getString();
				System.out.print(scenarioList[rowNumScenarios-1] + " ");
			
			List listScenarioDesc = excelDataScenario.get(rowNumScenarios);
			XSSFCell cellDesc = (XSSFCell) listScenarioDesc.get(descColumn);
			
			scenarioDescList[rowNumScenarios-1]=cellDesc.getRichStringCellValue().getString();
				System.out.print(scenarioDescList[rowNumScenarios-1] + " ");
			 
			

		}

		// Print retrieved data to the console
		for (int rowNumEnvironments = 1; rowNumEnvironments < rowCountEnvironments; rowNumEnvironments++) {

			List listEnvironments = excelDataEnvironments.get(rowNumEnvironments);

			XSSFCell cell = (XSSFCell) listEnvironments.get(0);
			if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
				environmentsList[rowNumEnvironments-1]=cell.getRichStringCellValue().getString();
				System.out.print(environmentsList[rowNumEnvironments-1] + " ");
			} else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				environmentsList[rowNumEnvironments-1]=String.valueOf(cell.getNumericCellValue());
				System.out.print(environmentsList[rowNumEnvironments-1] + " ");
			} else if(cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
				environmentsList[rowNumEnvironments-1]=String.valueOf(cell.getBooleanCellValue());
				System.out.println(environmentsList[rowNumEnvironments-1] + " ");
			}


		}

		// Print retrieved data to the console
		for (int rowNumUsers = 1; rowNumUsers < rowCountUsers; rowNumUsers++) {

			List listUsers = excelDataUsers.get(rowNumUsers);

			XSSFCell cell = (XSSFCell) listUsers.get(0);
			if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
				usersList[rowNumUsers-1]=cell.getRichStringCellValue().getString();
				System.out.print(usersList[rowNumUsers-1] + " ");
			} else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				usersList[rowNumUsers-1]=String.valueOf(cell.getNumericCellValue());
				System.out.print(usersList[rowNumUsers-1] + " ");
			} else if(cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
				usersList[rowNumUsers-1]=String.valueOf(cell.getBooleanCellValue());
				System.out.println(usersList[rowNumUsers-1] + " ");
			}


		}

	
		String[][] object={scenarioList,environmentsList,usersList, scenarioDescList};
		//Object[] object= {scenarioList,environmentsList,usersList, scenarioDescList};
		
		return object;
	}





	public Object[] getTestExecutionDetailsFromDB() throws Exception {


		List<String> userList = new ArrayList<String>();
		List<String> envList = new ArrayList<String>();
		List<String> scenariosList = new ArrayList<String>();
		
		String env="DEV";

		Connection conn = null;



		try {
			conn = BusinessFunctions.getConnection(env);

			String qryUserList = "SELECT * FROM PWP_AUTOMATION_DETAILS";

			Statement stmtUserList = conn.createStatement();
			ResultSet rsUserList = stmtUserList.executeQuery(qryUserList);
			while (rsUserList.next()) {
				if(rsUserList.getString("USER_NAME")!=null && rsUserList.getString("USER_EMAIL_ID")!=null)
				userList.add(rsUserList.getString("USER_NAME")+rsUserList.getString("USER_EMAIL_ID"));
				

			}
			String qryEnvList = "SELECT ENVIRONMENTS FROM PWP_AUTOMATION_DETAILS";

			Statement stmtEnvList = conn.createStatement();
			ResultSet rsEnvList = stmtEnvList.executeQuery(qryEnvList);
			while (rsEnvList.next()) {
				if(rsEnvList.getString("ENVIRONMENTS")!=null)
				envList.add(rsEnvList.getString("ENVIRONMENTS"));

			}

			/*String qryScenariosList = "SELECT ACTION FROM AUTOMATION_SCENARIOS";

			Statement stmtScenariosList  = conn.createStatement();
			ResultSet rsScenariosList  = stmtScenariosList .executeQuery(qryScenariosList);
			while (rsScenariosList.next()) {
				if(rsScenariosList.getString("ACTION")!=null)
					if(rsScenariosList.getString("ACTION").contains(","))
						scenariosList.add(rsScenariosList.getString("ACTION").replaceAll(",", "_"));
					else
						scenariosList.add(rsScenariosList.getString("ACTION"));
				
				

			}
			*/
			String qryScenariosList = "SELECT ACTION,DESCRIPTION FROM PWP_AUTOMATION_SCENARIOS ORDER BY SERIAL ASC";

			Statement stmtScenariosList  = conn.createStatement();
			ResultSet rsScenariosList  = stmtScenariosList .executeQuery(qryScenariosList);
			while (rsScenariosList.next()) {
				if(rsScenariosList.getString("ACTION")!=null)
					if(rsScenariosList.getString("ACTION").contains(","))
						scenariosList.add(rsScenariosList.getString("ACTION").replaceAll(",", "_")+"#"+rsScenariosList.getString("DESCRIPTION"));
					else {
						scenariosList.add(rsScenariosList.getString("ACTION")+"#"+rsScenariosList.getString("DESCRIPTION"));
						
					}
				
				

			}

		} catch (SQLException e) {
			int ret_code = e.getErrorCode();
			System.err.println(ret_code + e.getMessage());
		} catch (Exception e) {
			try {

				e.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqle) {
					System.out.println("getTestExecutionDetailsFromDB - Error occured while closing the Connection");
				}
			}
		}

		Object[] object= {scenariosList,envList,userList};

		return object;
	}



	public Object[] getDescriptionForScenarios(String scenario) throws Exception {


		
		List<String> scenariosDesc = new ArrayList<String>();
		String env="DEV";

		Connection conn = null;



		try {
			conn = BusinessFunctions.getConnection(env);

			
			
			String qryScenariosDesc = "SELECT DESCRIPTION FROM AUTOMATION_SCENARIOS WHERE ACTION = '"+scenario+"'";

			Statement stmtScenariosDesc  = conn.createStatement();
			ResultSet rsScenariosDesc  = stmtScenariosDesc .executeQuery(qryScenariosDesc);
			while (rsScenariosDesc.next()) {
				if(rsScenariosDesc.getString("DESCRIPTION").contains(","))
					scenariosDesc.add(rsScenariosDesc.getString("DESCRIPTION").replaceAll(",", "_"));
				else
					scenariosDesc.add(rsScenariosDesc.getString("DESCRIPTION"));
				
				

			}


		} catch (SQLException e) {
			int ret_code = e.getErrorCode();
			System.err.println(ret_code + e.getMessage());
		} catch (Exception e) {
			try {

				e.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqle) {
					System.out.println("getTestExecutionDetailsFromDB - Error occured while closing the Connection");
				}
			}
		}

		Object[] object= {scenariosDesc};

		return object;
	}



	public String getRoutingClassFromDB(String env, String action) throws Exception {


		
		String scenario = "";

		env="DEV";

		Connection conn = null;



		try {
			conn = BusinessFunctions.getConnection(env);

		

			String qryScenariosList = "SELECT ROUTING_CLASS FROM AUTOMATION_SCENARIOS WHERE ACTION='"+action+"'";

			Statement stmtScenariosList  = conn.createStatement();
			ResultSet rsScenariosList  = stmtScenariosList .executeQuery(qryScenariosList);
			while (rsScenariosList.next()) {
				
					scenario= rsScenariosList.getString("ROUTING_CLASS");

			}


		} catch (SQLException e) {
			int ret_code = e.getErrorCode();
			System.err.println(ret_code + e.getMessage());
		} catch (Exception e) {
			try {

				e.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqle) {
					System.out.println("getTestExecutionDetailsFromDB - Error occured while closing the Connection");
				}
			}
		}

		

		return scenario;
	}

}
