package se.fivefactorial.voting.data;

import java.util.ArrayList;

public class BallotList {
	private ArrayList<Ballot> ballots;

	public BallotList() {
		ballots = new ArrayList<>();
	}

	public Ballot add(Ballot ballot) {
		if (ballots.contains(ballot)) {
			return ballots.get(ballots.indexOf(ballot));
		}
		ballots.add(ballot);
		return ballot;
	}

	public int size() {
		return ballots.size();
	}

	public void addBlanks(int blank) {
		for (int i = 1; i <= blank; i++) {
			String name = String.format("Blank vote #%d", i);
			Ballot ballot = new Ballot(name);
			ballots.add(ballot);
		}
	}

	public void reset() {
		for (Ballot ballot : ballots)
			ballot.reset();
	}

	public void assignAll() {
		for (Ballot ballot : ballots) {
			ballot.assign();
		}
	}
}
