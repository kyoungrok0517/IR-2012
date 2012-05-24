import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;


public class YahooAnswerHelper {
	private static String API_SECRET = "e23c1b9928c9eaab910e58159db865c8a257201b";
	private static String QUESTION_SEARCH_URL = "http://answers.yahooapis.com/AnswersService/V1/questionSearch";
	private static String QUESTION_DETAIL_URL = "http://answers.yahooapis.com/AnswersService/V1/getQuestion";
	
	public static void test() {
		String url = buildQuestionSearchURL("beaver");
	}
	
	
	private static String buildQuestionSearchURL(String query) {
		// build HTTP query string that will be attached to URL
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("app_id", API_SECRET));
		params.add(new BasicNameValuePair("query", query));
		String query_string = URLEncodedUtils.format(params, "UTF-8");
		
		// build URL for question search
		String url = QUESTION_SEARCH_URL + "?" + query_string;
		
		// return the url
		return url;
	}
	
	private static String buildQuestionDetailURL(String question_id) {
		// build HTTP query string that will be attached to URL
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("app_id", API_SECRET));
		params.add(new BasicNameValuePair("question_id", question_id));
		String query_string = URLEncodedUtils.format(params, "UTF-8");
		
		// build URL for question search
		String url = QUESTION_DETAIL_URL + "?" + query_string;
		
		// return the url
		return url;
	}
}
