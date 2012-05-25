package com.joogle.model;

public class TermWithFrequency implements Comparable<TermWithFrequency> {
	public String term;
	public Integer freq;

	public TermWithFrequency() {

	}

	public TermWithFrequency(String term, Integer freq) {
		this.term = term;
		this.freq = freq;
	}

	@Override
	public int compareTo(TermWithFrequency t) {
		return (t.freq - this.freq);
	}

	public String toString() {
		return term + ":" + freq;
	}
}
