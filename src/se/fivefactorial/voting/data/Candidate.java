package se.fivefactorial.voting.data;

import java.util.ArrayList;
import java.util.List;

public class Candidate implements Comparable<Candidate> {
	private String firstname;
	private String lastname;
	private Guild guild;

	private ArrayList<Ballot> ballots;
	private boolean eliminated;

	public Candidate(String firstname, String lastname, Guild guild) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.guild = guild;

		ballots = new ArrayList<>();
		eliminated = false;
	}

	public String getName() {
		return String.format("%s %s", firstname, lastname);
	}

	@Override
	public String toString() {
		return String.format("%s %s (%s)", firstname, lastname, guild);
	}

	@Override
	public int compareTo(Candidate c) {
		if (c.guild != guild) {
			return guild.compareTo(c.guild);
		}
		if (lastname.equals(c.lastname)) {
			return firstname.compareTo(c.firstname);
		}
		return lastname.compareTo(c.lastname);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Candidate)
			return ((Candidate) obj).compareTo(this) == 0;
		return false;
	}

	public boolean isGuild(Guild guild) {
		return this.guild.equals(guild);
	}

	public void reset(List<Candidate> candidates) {
		ballots = new ArrayList<>();
		eliminated = !candidates.contains(this);
	}

	public boolean isEliminated() {
		return eliminated;
	}

	public void addBallot(Ballot ballot) {
		ballots.add(ballot);
	}

	public double getVotes() {
		double votes = 0;
		for (Ballot ballot : ballots) {
			votes += ballot.getWeight();
		}
		return votes;
	}

	public void removed() {
		eliminated = true;
		for (Ballot ballot : ballots) {
			ballot.next(1);
		}
	}

	public void elected(double quota) {
		eliminated = true;
		double votes = getVotes();
		double weight = (votes - quota) / votes;
		for (Ballot ballot : ballots) {
			ballot.next(weight);
		}
	}

	public Guild getGuild() {
		return guild;
	}
}
