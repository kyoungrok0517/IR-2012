package com.joogle.termselector;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.joogle.utility.PorterStemmer;

public class TfIdfSelector implements TermSelector {
	private PorterStemmer stemmer = new PorterStemmer();

	@Override
	public List<String> selectTerm(String query, String corpus) {
		Map<String, Integer> term_with_count = new HashMap<String, Integer>();
		StringTokenizer tokenizer = new StringTokenizer(corpus);
		
		// TODO: 특수문자 제거 기능 구현 필요 
		
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			
			stemmer.add(token.toCharArray(), token.length());
			stemmer.stem();
			String term_stemmed = stemmer.toString();
			
			if (!term_with_count.containsKey(term_stemmed)) {
				term_with_count.put(term_stemmed, 1);
			} else {
				int term_count = term_with_count.get(term_stemmed) + 1;
				term_with_count.put(term_stemmed, term_count);
			}
		}

		for (String term : term_with_count.keySet()) {
			System.out.println(term + ": " + term_with_count.get(term));
		}
		
		return null;
	}
}
