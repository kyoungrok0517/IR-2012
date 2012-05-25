package com.joogle.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class TermRankingFunction {
	public static double getRocchioWeight(String term, String doc,
			List<String> collection) {
		Map<String, Integer> doc_vector = getDocumentVector(doc);

		int tf = 0;
		if (doc_vector.containsKey(term)) {
			tf = doc_vector.get(term);
		}

		// System.out.println(term + ":" + tf);

		int df = 0;
		for (String d : collection) {
			Map<String, Integer> dv = getDocumentVector(d);
			if (dv.containsKey(term)) {
				df++;
			}
		}

		return ((1 + Math.log(tf)) * Math.log10(collection.size() / df + 1));
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
		List<String> terms = Tokenizer.tokenize(document);

		for (String token : terms) {
			if (!term_vector.containsKey(token)) {
				term_vector.put(token, 1);
			} else {
				term_vector.put(token, term_vector.get(token) + 1);
			}
		}

		return term_vector;
	}
}
