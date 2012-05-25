import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.joogle.model.TermWithWeight;
import com.joogle.model.YahooQuestion;
import com.joogle.utility.TermRankingFunction;
import com.joogle.utility.YahooAnswerHelper;

public class Main {
	private static List<String> stopwords;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		stopwords = populateStopWords("./resource/english_stopword_v2.txt");

		String tokenized_query = getQuery("how was the black plague stopped?");
		
		System.out.println("Query:" + tokenized_query);
		
		List<YahooQuestion> questions = YahooAnswerHelper
				.searchQuestions(tokenized_query);

		
		// build the collection & PRF documents
		// Collection: retrieved
		List<String> collection = new ArrayList<String>();
		List<String> prf_docs = new ArrayList<String>();
		for (YahooQuestion q : questions) {
			String question = q.Content;
			String answer = q.ChosenAnswer;
			collection.add(question);
			collection.add(answer);
			prf_docs.add(answer);
		}

		Map<String, Double> weight_vector = new HashMap<String, Double>();

		for (String doc : prf_docs) {
			List<String> vector = TermRankingFunction
					.getMergedDocumentVector(doc);

			// System.out.println(doc);
			// System.out.println("---------------------------------------");

			for (String term : vector) {
				if (stopwords.contains(term)) {
					continue;
				}
				
				double weight = TermRankingFunction.getRocchioWeight(term, doc,
						collection);

				if (!weight_vector.containsKey(term)) {
					weight_vector.put(term, weight);
				} else {
					double new_weight = weight_vector.get(term) + weight;
					weight_vector.put(term, new_weight);
				}
			}
		}

		List<TermWithWeight> tww = new ArrayList<TermWithWeight>();
		for (String term : weight_vector.keySet()) {
			tww.add(new TermWithWeight(term, weight_vector.get(term)));
		}

		Collections.sort(tww);

		for (TermWithWeight t : tww) {
			System.out.println(t);
		}

		// for (String term : weight_vector.keySet()) {
		// System.out.println(term + ":" + weight_vector.get(term));
		// }
	}
	
	private static String getQuery(String query) {
		StringTokenizer tokenizer = new StringTokenizer(query, " \"()<>{}[]~`!@#$%^?&*_-=+/|,.;:\t\n\r1234567890");
		
		String query_modified = "";
		
		while (tokenizer.hasMoreTokens()) {
			String term = tokenizer.nextToken();
			
			if (!stopwords.contains(term)) {
				query_modified += " " + term;
			}
		}
		return query_modified;
	}

	private static List<String> populateStopWords(String filename) {
		BufferedReader reader = null;
		List<String> result = new ArrayList<String>();
		
		try {
			reader = new BufferedReader(new FileReader(filename));
			
			while (true) {
				String stopword = reader.readLine();
				
				if (stopword != null) {
					result.add(stopword);
				} else {
					break;
				}
			}
		} catch (IOException e) {
			
		}
		
		return result;
	}

}
