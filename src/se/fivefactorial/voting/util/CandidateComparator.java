package se.fivefactorial.voting.util;

import java.util.Comparator;

import se.fivefactorial.voting.data.Candidate;

public class CandidateComparator implements Comparator<Candidate> {

	@Override
	public int compare(Candidate c1, Candidate c2) {
		double d = c2.getVotes() - c1.getVotes();
		if (d < 0)
			return -1;
		if (d > 0)
			return 1;
		return 0;
	}

}
