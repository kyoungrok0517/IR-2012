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
				.searchQuestions("Jackie Robinson major league debut");

//		for (YahooQuestion q : questions) {
//			System.out.println("[Subject]");
//			System.out.println(q.Subject);
//			System.out.println("[Content]");
//			System.out.println(q.Content);
//			System.out.println("[Selected Answer]");
//			System.out.println(q.ChosenAnswer);
//			System.out
//					.println("----------------------------------------------------------");
//		}

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
