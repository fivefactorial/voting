package se.fivefactorial.voting.data;

public class Vote implements Comparable<Vote> {
	private Candidate candidate;
	private int prio;
	private boolean sortDESC;

	public Vote(Candidate candidate, int prio, boolean sortDESC) {
		this.candidate = candidate;
		this.prio = prio;
		this.sortDESC = sortDESC;
	}

	@Override
	public int compareTo(Vote v) {
		if (sortDESC)
			return v.prio - prio;
		else
			return prio - v.prio;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vote)
			return ((Vote) obj).compareTo(this) == 0;
		return false;
	}

	@Override
	public String toString() {
		return String.format("%s (%d)", candidate.getName(), prio);
	}

	public void addBallot(Ballot ballot) {
		candidate.addBallot(ballot);
	}

	public Candidate getCandidate() {
		return candidate;
	}

}
