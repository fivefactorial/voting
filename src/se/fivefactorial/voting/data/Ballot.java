package se.fivefactorial.voting.data;

import java.util.ArrayList;
import java.util.Collections;

public class Ballot {

	private String id;
	private ArrayList<Vote> votes;
	private int i;
	private double weight;

	public Ballot(String id) {
		this.id = id;
		votes = new ArrayList<>();
		reset();
	}

	public void add(Vote vote) {
		votes.add(vote);
		Collections.sort(votes);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Ballot)
			return ((Ballot) obj).id.equals(id);
		return false;
	}

	public void reset() {
		i = 0;
		weight = 1;
	}

	public void assign() {
		while (i < votes.size() && votes.get(i).getCandidate().isEliminated()) {
			i++;
		}
		if (i >= votes.size())
			return;
		votes.get(i).addBallot(this);
	}

	public double getWeight() {
		return weight;
	}

	public void next(double weight) {
		this.weight *= weight;
		assign();
	}
}
