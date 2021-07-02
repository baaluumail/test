package com.ntrs.automationDriver;

public class InputPrepDetails {
	private int runId;
	private String scenarioId;
	private String sequenceId;
	private String environment;
	private String mainClass;
	private String routingClass;
	private String spName;
	private String query;
	private String msgId;
	private String simulator;
	private String email;
	private String inputType;
	private boolean batch;

	public int getRunId() {
		return runId;
	}

	public void setRunId(int runId) {
		this.runId = runId;
	}

	public String getScenarioId() {
		return scenarioId;
	}

	public void setScenarioId(String testId) {
		this.scenarioId = testId;
	}

	public String getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getMainClass() {
		return mainClass;
	}

	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}

	public String getRoutingClass() {
		return routingClass;
	}

	public void setRoutingClass(String routingClass) {
		this.routingClass = routingClass;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getSimulator() {
		return simulator;
	}

	public void setSimulator(String simulator) {
		this.simulator = simulator;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public boolean getBatch() {
		return batch;
	}

	public void setBatch(boolean batch) {
		this.batch = batch;
	}

}
