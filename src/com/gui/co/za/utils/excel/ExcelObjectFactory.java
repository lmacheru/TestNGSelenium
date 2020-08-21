package com.gui.co.za.utils.excel;

public class ExcelObjectFactory  {
	private String page;
	private String selectorType;
	private String selectorContent;
	private String objectType;
	private String maxLen;
	private String valueToUse;
	private String expectedResult;
	private String ritTestItem;
	
	
	public String getPage() {

		return page;
	}
	public void setPage(String page) {

		this.page = page;
	}
	public String getSelectorType() {
		return selectorType;
	}
	public void setSelectorType(String selectorType) {
		this.selectorType = selectorType;
	}
	public String getSelectorContent() {
		return selectorContent;
	}
	public void setSelectorContent(String selectorContent) {
		this.selectorContent = selectorContent;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public String getMaxLen() {
		return maxLen;
	}
	public void setMaxLen(String maxLen) {
		this.maxLen = maxLen;
	}
	public String getValueToUse() {
		return valueToUse;
	}
	public void setValueToUse(String valueToUse) {
		this.valueToUse = valueToUse;
	}
	public String getExpectedResult() {
		return expectedResult;
	}
	public void setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
	}
	
	public String getRitTestItem() {
		return ritTestItem;
	}
	public void setRitTestItem(String ritTestItem) {
		this.ritTestItem = ritTestItem;
	}
	
}
