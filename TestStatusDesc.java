package com.ntrs.automationDriver;

public class TestStatusDesc {
	private String testID;
	private String runID;
	private String sequenceID;
	private String status;
	private String htmlContent;
	private String errorStatusDesc;

	public String getTestID() {
		return testID;
	}

	public void setTestID(String testID) {
		this.testID = testID;
	}

	public String getRunID() {
		return runID;
	}

	public void setRunID(String runID) {
		this.runID = runID;
	}

	public String getSequenceID() {
		return sequenceID;
	}

	public void setSequenceID(String testScenarioID) {
		this.sequenceID = testScenarioID;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public String getErrorStatusDesc() {
		return errorStatusDesc;
	}

	public void setErrorStatusDesc(String errorStatusDesc) {
		this.errorStatusDesc = errorStatusDesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
