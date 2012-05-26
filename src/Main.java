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
import com.joogle.model.YahooAnswer;
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
		//TODO: 몇 천개라도 collection을 모아두는게 좋을 듯. 
		List<String> collection = new ArrayList<String>();
		List<String> prf_docs = new ArrayList<String>();
		for (YahooQuestion question : questions) {
			String question_content = question.Content;
			String chosen_answer_content = question.ChosenAnswer;
			List<YahooAnswer> answers = YahooAnswerHelper.getAnswers(question);
			
			prf_docs.add(chosen_answer_content);			
			collection.add(question_content);
			collection.add(chosen_answer_content);
			for (YahooAnswer ans : answers) {
				collection.add(ans.Content);
			}
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
