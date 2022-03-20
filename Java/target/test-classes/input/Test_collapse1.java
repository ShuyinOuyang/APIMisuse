package org.csu.input;

class Test_collapse1 {
	
	String s;
	
	public void before() {
		StringBuffer sb = new StringBuffer();
		sb.append("");
		sb.append(s);
		sb.append("");
		sb.toString();
	}

	public void after() {
		StringBuffer sb = new StringBuffer();
		sb.append("");
		sb.toString();
	}
}