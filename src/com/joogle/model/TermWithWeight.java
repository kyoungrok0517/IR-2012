package com.joogle.model;

public class TermWithWeight implements Comparable<TermWithWeight> {
	public String term;
	public Integer freq;

	public TermWithWeight() {

	}

	public TermWithWeight(String term, Integer freq) {
		this.term = term;
		this.freq = freq;
	}

	@Override
	public int compareTo(TermWithWeight t) {
		return (t.freq - this.freq);
	}

	public String toString() {
		return term + ":" + freq;
	}
}
