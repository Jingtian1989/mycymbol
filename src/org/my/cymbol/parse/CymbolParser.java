package org.my.cymbol.parse;

import java.util.LinkedList;
import java.util.List;

import org.my.cymbol.exception.CymbolMissmatchedException;
import org.my.cymbol.lex.CymbolLexer;
import org.my.cymbol.lex.Token;

public class CymbolParser {

	private int p = 0;
	private List<Token> lookahead = new LinkedList<Token>();
	private List<Integer> markers = new LinkedList<Integer>();
	private CymbolLexer lexer;

	public CymbolParser(CymbolLexer lexer) {
		this.lexer = lexer;
	}

	private void seek(int marker) {
		p = marker;
	}

	private int mark() {
		markers.add(Integer.valueOf(p));
		return p;
	}

	private void release() {
		int marker = markers.get(markers.size() - 1);
		markers.remove(markers.size() - 1);
		seek(marker);
	}

	private boolean isSpeculating() {
		return markers.size() > 0;
	}

	private void consumeToken() throws CymbolMissmatchedException {
		p++;
		if (p == lookahead.size() && !isSpeculating()) {
			p = 0;
			lookahead.clear();
		}
		syncTokens(1);
	}

	private void fillTokens(int n) throws CymbolMissmatchedException {
		for (int i = 0; i < n; i++) {
			lookahead.add(lexer.nextToken());
		}
	}

	private void syncTokens(int i) throws CymbolMissmatchedException {
		if (p + i - 1 > (lookahead.size() - 1)) {
			int n = (p + i - 1) - (lookahead.size() - 1);
			fillTokens(n);
		}
	}

	private Token lookToken(int i) throws CymbolMissmatchedException {
		syncTokens(i);
		return lookahead.get(p + i - 1);
	}

	private int lookAhead(int i) throws CymbolMissmatchedException {
		return lookToken(i).getType();
	}

	private void match(int type) throws CymbolMissmatchedException {
		if (lookAhead(1) == type) {
			consumeToken();
		} else {
			throw new CymbolMissmatchedException("parse error at line "
					+ CymbolLexer.line + ", expected '" + String.valueOf(type)
					+ "'" + ", but encountered '"
					+ String.valueOf(lookAhead(1)) + "'");
		}
	}
	
	
	
}
