import java.util.ArrayList;
import java.util.List;

import com.joogle.model.YahooQuestion;
import com.joogle.utility.TermRankingFunction;
import com.joogle.utility.YahooAnswerHelper;

public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<YahooQuestion> questions = YahooAnswerHelper
				.searchQuestions("prime factor");
		
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

		for (String doc : prf_docs) {
			List<String> vector = TermRankingFunction.getMergedDocumentVector(doc);
			
			System.out.println(doc);
			System.out.println("---------------------------------------");
			
			for (String s : vector) {
				System.out.println(s);
//				double score = TermRankingFunction.getRocchioWeight(s, doc, collection);
				
//				System.out.println(s + ":" + score);
			}
			
			System.out.println();
		}
	}

}
