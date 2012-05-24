import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class YahooAnswerHelper {
	private static HttpClient client = new DefaultHttpClient();

	private static String API_SECRET = "e23c1b9928c9eaab910e58159db865c8a257201b";
	private static String QUESTION_SEARCH_URL = "http://answers.yahooapis.com/AnswersService/V1/questionSearch";
	private static String QUESTION_DETAIL_URL = "http://answers.yahooapis.com/AnswersService/V1/getQuestion";
	
	

	public static void test() {
		String url = buildQuestionSearchURL("beaver");

		HttpGet method = new HttpGet(url);

		try {
			HttpResponse response = client.execute(method);
			HttpEntity entity = response.getEntity();
			
			if (entity != null) {	// we have the response
				int code = response.getStatusLine().getStatusCode();
				
				if (code >= 300) {	// the server responded error
					System.out.println("Failed");
					System.out.println(url);
				} else {
					String response_string = EntityUtils.toString(entity);
					
					System.out.println(response_string);
				}
			}
		} catch (IOException e) {

		}
	}

	private static String buildQuestionSearchURL(String query) {
		// build HTTP query string that will be attached to URL
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("appid", API_SECRET));
		params.add(new BasicNameValuePair("query", query));
		params.add(new BasicNameValuePair("output", "json"));		
		String query_string = URLEncodedUtils.format(params, "UTF-8");

		// build URL for question search
		String url = QUESTION_SEARCH_URL + "?" + query_string;

		// return the url
		return url;
	}

	private static String buildQuestionDetailURL(String question_id) {
		// build HTTP query string that will be attached to URL
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("appid", API_SECRET));
		params.add(new BasicNameValuePair("question_id", question_id));
		params.add(new BasicNameValuePair("output", "json"));
		String query_string = URLEncodedUtils.format(params, "UTF-8");

		// build URL for question search
		String url = QUESTION_DETAIL_URL + "?" + query_string;

		// return the url
		return url;
	}
}
