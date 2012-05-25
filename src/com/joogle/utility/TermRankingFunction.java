package com.joogle.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class TermRankingFunction {
	private static PorterStemmer stemmer = new PorterStemmer();

	public static double getRocchioWeight(String term, String doc,
			List<String> collection) {
		Map<String, Integer> doc_vector = getDocumentVector(doc);

		int tf = 0;
		if (doc_vector.containsKey(term)) {
			tf = doc_vector.get(term);
		}

		int df = 0;
		for (String d : collection) {
			Map<String, Integer> dv = getDocumentVector(d);
			if (dv.containsKey(term)) {
				df++;
			}
		}

		return ((1 + Math.log(tf)) * (1 / df));
	}

	public static List<String> getMergedDocumentVector(String document) {
		Map<String, Integer> vector = getDocumentVector(document);
		List<String> merged_vector = new ArrayList<String>();

		for (String term : vector.keySet()) {
			merged_vector.add(term);
		}

		return merged_vector;
	}

	public static Map<String, Integer> getDocumentVector(String document) {
		Map<String, Integer> term_vector = new HashMap<String, Integer>();
		List<String> terms = tokenize(document);

		for (String token : terms) {
			if (!term_vector.containsKey(token)) {
				term_vector.put(token, 1);
			} else {
				term_vector.put(token, term_vector.get(token) + 1);
			}
		}

		return term_vector;
	}

	private static List<String> tokenize(String text) {
		// TODO: 여기서 stopword 제거, 소문자화, stemming 등을 모두 처리
		StringTokenizer tokenizer = new StringTokenizer(text,
				" \"'()<>{}[]~`!@#$%^&*_-=+/|,.;:\t\n\r");

		List<String> tokens = new ArrayList<String>();
		while (tokenizer.hasMoreTokens()) {
			String t = tokenizer.nextToken().toLowerCase();
			stemmer.add(t.toCharArray(), t.length());
			stemmer.stem();
			String stemmed = stemmer.toString();
			
			tokens.add(stemmed);
		}

		return tokens;
	}
}
