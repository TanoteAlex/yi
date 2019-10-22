package com.yi.core.utils;

public class ReplacementScheme {

	private int start;
	private int end;
	private char replacement;

	public ReplacementScheme() {

	}

	public ReplacementScheme(int start, int end, char replacement) {
		this.start = start;
		this.end = end;
		this.replacement = replacement;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public char getReplacement() {
		return replacement;
	}

	public void setReplacement(char replacement) {
		this.replacement = replacement;
	}

}
