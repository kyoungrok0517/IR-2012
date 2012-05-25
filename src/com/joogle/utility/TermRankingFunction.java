package com.joogle.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joogle.model.TermWithFrequency;

public class TermRankingFunction {

	public static List<TermWithFrequency> getTf(String document) {
		// preprocess the document
		String pre_document = preprocess(document);
		
		Map<String, Integer> term_freq_map = new HashMap<String, Integer>();

		// first we count the freq. of terms
		for (String term : Tokenizer.tokenize(pre_document)) {
			if (!term_freq_map.containsKey(term)) {
				term_freq_map.put(term, 1);
			} else {
				int new_freq = (term_freq_map.get(term) + 1);
				term_freq_map.put(term, new_freq);
			}
		}

		// we save the terms with their frequency for easy handling
		List<TermWithFrequency> term_freq_list = new ArrayList<TermWithFrequency>();
		for (String term : term_freq_map.keySet()) {
			TermWithFrequency twf = new TermWithFrequency(term,
					term_freq_map.get(term));
			term_freq_list.add(twf);
		}

		// Sort the terms by their frequency
		Collections.sort(term_freq_list);

		return term_freq_list;
	}
	
	public static int getDf(List<String> documents, String term) {
		int df = 0;
		
		for (String doc : documents) {
			if (doc.contains(term)) {
				df++;
			}
		}
		
		return df;
	}

	// TODO: 각종 전처리 과정 구현 필요
	private static String preprocess(String text) {
		return text;
	}
}
