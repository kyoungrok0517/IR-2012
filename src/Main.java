import java.util.List;

import com.joogle.model.YahooAnswer;
import com.joogle.model.YahooQuestion;
import com.joogle.utility.URLExtractor;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<YahooQuestion> questions = YahooAnswerHelper
				.searchQuestions("prime factor");

		String corpus = "";

		for (YahooQuestion q : questions) {
			corpus += (" " + q.Subject);
			corpus += (" " + q.Content);
			corpus += (" " + q.ChosenAnswer);
		}
		
//		URLExtractor.extractURL(corpus);

		System.out.println(corpus);
	}

}
