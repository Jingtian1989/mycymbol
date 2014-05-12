package org.my.cymbol.parse;

import java.util.LinkedList;
import java.util.List;

import org.my.cymbol.lex.Token;

public class CymbolAST {

	private Token token;
	private List<CymbolAST> children = new LinkedList<>();

	public CymbolAST(Token token) {
		this.token = token;
	}

	public void addChild(CymbolAST child) {
		children.add(child);
	}

}
