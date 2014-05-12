package org.my.cymbol.lex;

public class Token {

	private int type;
	private String text;

	public Token(int type, String text) {
		this.type = type;
		this.text = text;
	}
	
	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setValue(String value) {
		this.text = value;
	}

	public String getValue() {
		return text;
	}

	public String toString() {
		return text;
	}

}
