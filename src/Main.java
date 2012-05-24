import java.util.List;

import com.joogle.model.YahooAnswer;
import com.joogle.model.YahooQuestion;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<YahooQuestion> questions = YahooAnswerHelper.searchQuestions("beaver");
		YahooQuestion q = questions.get(2);
		
		List<YahooAnswer> answers = YahooAnswerHelper.getAnswers(q);
		
		for (YahooAnswer answer : answers) {
			System.out.println(answer);
		}
	}

}
