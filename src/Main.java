import java.util.List;

import com.joogle.model.YahooQuestion;
import com.joogle.termselector.TfIdfSelector;
import com.joogle.utility.PorterStemmer;
import com.joogle.utility.YahooAnswerHelper;

public class Main {
	private static PorterStemmer stemmer = new PorterStemmer();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<YahooQuestion> questions = YahooAnswerHelper
				.searchQuestions("prime factor");
		
		String corpus = "";
		
		for (YahooQuestion q : questions) {
			corpus += " " + q.Content;
			corpus += " " + q.ChosenAnswer;
		}
		
		TfIdfSelector selector = new TfIdfSelector();
		selector.selectTerm(null, corpus);
	}

}
