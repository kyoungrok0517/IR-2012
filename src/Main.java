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
import com.joogle.utility.TermRankingHelper;
import com.joogle.utility.YahooAnswerHelper;

public class Main {
	public static List<String> stopwords;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		stopwords = populateStopWords("./resource/english_stopword_v2.txt");

		String tokenized_query = getNormalizedQuery("are sun beds safe");

		System.out.println("Query: " + tokenized_query);

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
		TermRankingHelper rank_helper = new TermRankingHelper(prf_docs,
				collection, stopwords);

		for (String doc : prf_docs) {
			List<String> vector = TermRankingHelper.getUniqueTermVector(doc);

			for (String term : vector) {
				if (!stopwords.contains(term)) {
					double weight = rank_helper.getRocchioWeight(term);
					weight_vector.put(term, weight);
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
	}

	private static String getNormalizedQuery(String query) {
		StringTokenizer tokenizer = new StringTokenizer(query,
				" \"()<>{}[]~`!@#$%^?&*_-=+/|,.;:\t\n\r1234567890");

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
