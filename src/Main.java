import java.util.ArrayList;
import java.util.List;

import com.joogle.model.TermWithFrequency;
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
		
		// the collection is mega-document
		// composed of (retrieved questions + chosen answers)  
		String collection = "";
		
		for (YahooQuestion q : questions) {
			collection += " " + q.Content;
			collection += " " + q.ChosenAnswer;			
		}
		
		// the relevant documents are chosen answers (treated as individual document)
		List<String> all_documents = new ArrayList<String>();
		List<String> prf_documents = new ArrayList<String>(); 
		for (YahooQuestion q : questions) {
			String question = q.Content;
			String answer = q.ChosenAnswer;
			all_documents.add(question);
			all_documents.add(answer);
			prf_documents.add(answer);
		}
		
//		for (TermWithFrequency twf : TermRankingFunction.getTf(collection)) {
//			System.out.println(twf);
//		}
		
		for (TermWithFrequency twf : TermRankingFunction.getTf(collection)) {
			int df = TermRankingFunction.getDf(prf_documents, twf.term);
			
			System.out.println("The df of " + twf.term.trim() + " is: " + df);
		}
	}


}
