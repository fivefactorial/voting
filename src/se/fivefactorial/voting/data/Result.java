package se.fivefactorial.voting.data;

import java.util.List;

public class Result {
	private List<Candidate> elected;
	private List<Candidate> removed;

	public Result(List<Candidate> elected, List<Candidate> removed) {
		this.elected = elected;
		this.removed = removed;
	}

	public List<Candidate> getElected() {
		return elected;
	}

	public List<Candidate> getRemoved() {
		return removed;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Elected:");
		for (Candidate candidate : elected)
			sb.append(String.format("\n- %s", candidate));
		sb.append("\nRunner ups:");
		int i = 1;
		for (Candidate candidate : removed)
			sb.append(String.format("\n(%d) %s", i++, candidate));
		return sb.toString();
	}

	public void addElected(List<Candidate> guaranteed) {
		elected.addAll(guaranteed);
	}
}
