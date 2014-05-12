package org.my.cymbol.lex;

import java.util.HashMap;
import java.util.Map;

import org.my.cymbol.exception.CymbolEOFException;
import org.my.cymbol.exception.CymbolMissmatchedException;

public class CymbolLexer {

	private String text;
	private int size;
	private int cursor = -1;
	private char peek = ' ';
	private Map<String, Token> words = new HashMap<String, Token>();

	public static int line = 0;

	public static final Token ADD = new Token(Tag.ADD, "+");
	public static final Token SUB = new Token(Tag.SUB, "-");
	public static final Token MEMBER = new Token(Tag.MEMBER, "->|.");
	public static final Token MUL = new Token(Tag.MUL, "*");
	public static final Token DIV = new Token(Tag.DIV, "/");
	public static final Token ASSIGN = new Token(Tag.ASSIGN, "=");
	public static final Token EQ = new Token(Tag.EQ, "==");
	public static final Token GT = new Token(Tag.GT, ">");
	public static final Token GE = new Token(Tag.GE, ">=");
	public static final Token LT = new Token(Tag.LT, "<");
	public static final Token LE = new Token(Tag.LE, "<=");
	public static final Token EOF = new Token(Tag.EOF, "eof");

	public static final Token IF = new Token(Tag.IF, "if");
	public static final Token ELSE = new Token(Tag.ELSE, "else");
	public static final Token WHILE = new Token(Tag.WHILE, "while");
	public static final Token CLASS = new Token(Tag.CALSS, "class");
	public static final Token TRUE = new Token(Tag.TRUE, "true");
	public static final Token FALSE = new Token(Tag.FALSE, "false");
	public static final Token RETURN = new Token(Tag.RETURN, "return");

	public CymbolLexer(String text) {
		this.text = text;
		this.size = text.length();
	}

	private void init() {
		words.put("if", CymbolLexer.IF);
		words.put("else", CymbolLexer.ELSE);
		words.put("while", CymbolLexer.WHILE);
		words.put("class", CymbolLexer.CLASS);
		words.put("true", CymbolLexer.TRUE);
		words.put("false", CymbolLexer.FALSE);
		words.put("return", CymbolLexer.RETURN);
	}

	private void consumeChar() throws CymbolEOFException {
		cursor++;
		if (cursor > size) {
			peek = ' ';
			throw new CymbolEOFException();
		}
		peek = text.charAt(cursor);
	}

	private void match(char c) throws CymbolMissmatchedException,
			CymbolEOFException {
		if (peek != c)
			throw new CymbolMissmatchedException("lex error at line "
					+ CymbolLexer.line + ", expected '" + String.valueOf(c)
					+ "'" + ", but encountered '" + String.valueOf(peek) + "'");
		consumeChar();
	}

	private boolean isDigit(char c) {
		if (c >= '0' && c <= '9') {
			return true;
		}
		return false;
	}

	private boolean isLetter(char c) {
		if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
			return true;
		}
		return false;
	}

	private int toInt(char c) {
		return c - '0';
	}

	public Token nextToken() throws CymbolMissmatchedException {
		Token ret = null;
		try {
			while (peek == ' ' || peek == '\t' || peek == '\r' || peek == '\n') {
				if (peek == '\n') {
					CymbolLexer.line++;
				}
				consumeChar();
			}

			if (peek == '+') {
				ret = CymbolLexer.ADD;
				consumeChar();
				return ret;
			} else if (peek == '-') {
				ret = CymbolLexer.SUB;
				consumeChar();
				if (peek == '>') {
					ret = CymbolLexer.MEMBER;
					consumeChar();
				}
				return ret;
			} else if (peek == '*') {
				ret = CymbolLexer.MUL;
				consumeChar();
				return ret;
			} else if (peek == '/') {
				ret = CymbolLexer.DIV;
				consumeChar();
				return ret;
			} else if (peek == '\'') {
				consumeChar();
				ret = new Token(Tag.CHAR, String.valueOf(peek));
				consumeChar();
				match('\'');
				return ret;
			} else if (peek == '"') {
				consumeChar();
				StringBuilder sb = new StringBuilder();
				while (peek != '"') {
					sb.append(peek);
					consumeChar();
				}
				ret = new Token(Tag.STRING, sb.toString());
				match('"');
				return ret;
			} else if (peek == '=') {
				ret = CymbolLexer.ASSIGN;
				consumeChar();
				if (peek == '=') {
					ret = CymbolLexer.EQ;
					consumeChar();
				}
				return ret;
			} else if (peek == '>') {
				ret = CymbolLexer.GT;
				consumeChar();
				if (peek == '=') {
					ret = CymbolLexer.GE;
					consumeChar();
				}
				return ret;
			} else if (peek == '<') {
				ret = CymbolLexer.LT;
				consumeChar();
				if (peek == '=') {
					ret = CymbolLexer.LE;
					consumeChar();
				}
				return ret;
			} else if (peek == '.') {
				ret = CymbolLexer.MEMBER;
				consumeChar();
				return ret;
			} else if (isDigit(peek)) {
				int h = 0, l = 0;
				float base = 1.0f;
				while (isDigit(peek)) {
					h = 10 * h + toInt(peek);
					consumeChar();
				}
				ret = new Token(Tag.INT, String.valueOf(h));
				if (peek == '.') {
					consumeChar();
					while (isDigit(peek)) {
						l = 10 * l + toInt(peek);
						base = 10 * base;
						consumeChar();
					}
					ret = new Token(Tag.FLOAT, String.valueOf((h + l) / base));
				}
				return ret;
			} else if (isLetter(peek)) {
				StringBuffer sb = new StringBuffer();
				while (isLetter(peek) || isDigit(peek)) {
					sb.append(peek);
					consumeChar();
				}
				String s = sb.toString();
				if ((ret = words.get(s)) == null) {
					ret = new Token(Tag.ID, s);
					words.put(s, ret);
				}
				return ret;
			}
			ret = new Token(peek, String.valueOf(peek));
			consumeChar();
			return ret;

		} catch (CymbolEOFException e) {
			if (ret == null)
				return CymbolLexer.EOF;
			return ret;
		}
	}
}
