package com.joogle.termselector;

import java.util.List;

public interface TermSelector {
	public List<String> selectTerm(String query, String corpus);
}
