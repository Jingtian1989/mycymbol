package org.my.cymbol.lex;

public class Tag {

	public static final int EOF = -1;
	
	public static final int METHOD_DECL = 0;
	public static final int ARG_DECL = 1;
	public static final int BLOCK = 2;
	public static final int VAR_DECL = 3;
	public static final int FIELD_DECL = 4;
	public static final int CALL = 5;
	public static final int EXPR_LIST = 6;
	public static final int EXPR = 7;
	public static final int UNARY_MINUS = 8;
	public static final int UNARY_NOT = 9;
	public static final int ASSIGN = 10;
	public static final int ADDR = 11;
	public static final int DEREF = 12;
	public static final int ADD = 13;
	public static final int SUB = 14;
	public static final int MUL = 15;
	public static final int DIV = 16;
	public static final int MEMBER = 17;
	
	public static final int NE = 18;
	public static final int EQ = 19;
	public static final int LT = 20;
	public static final int GT = 21;
	public static final int LE = 22;
	public static final int GE = 23;
	
	
	public static final int ID = 24;
	public static final int INT = 25;
	public static final int FLOAT = 26;
	public static final int CHAR  = 27;
	public static final int TRUE = 28;
	public static final int FALSE = 29;
	public static final int STRING = 30;
	
	public static final int IF = 31;
	public static final int ELSE = 32;
	public static final int CALSS = 33;
	public static final int WHILE = 34;
	public static final int RETURN = 35;
	
}
