package com.joogle.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TermRankingFunction {
	

	public static List<String> tokenize(String text) {
		// TODO: 여기서 stopword 제거, 소문자화, stemming 등을 모두 처리
		StringTokenizer tokenizer = new StringTokenizer(text,
				" <>()_-.?[]{}\"\'~!@#$%^&*,/");

		List<String> tokens = new ArrayList<String>();
		while (tokenizer.hasMoreTokens()) {
			tokens.add(tokenizer.nextToken());
		}

		return tokens;
	}
}
