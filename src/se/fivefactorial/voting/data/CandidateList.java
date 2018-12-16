package se.fivefactorial.voting.data;

import java.util.ArrayList;
import java.util.List;

public class CandidateList {
	private ArrayList<Candidate> candidates;

	public CandidateList() {
		candidates = new ArrayList<>();
	}

	public Candidate add(Candidate candidate) {
		if (candidates.contains(candidate)) {
			return candidates.get(candidates.indexOf(candidate));
		}
		candidates.add(candidate);
		return candidate;
	}

	public int size() {
		return candidates.size();
	}

	public List<Candidate> getList(Guild guild) {
		ArrayList<Candidate> list = new ArrayList<>();
		for (Candidate candidate : candidates) {
			if (candidate.isGuild(guild))
				list.add(candidate);
		}
		return list;
	}

	public List<Candidate> getList() {
		ArrayList<Candidate> list = new ArrayList<>();
		list.addAll(candidates);
		return list;
	}

	public void reset(List<Candidate> candidates) {
		for (Candidate candidate : this.candidates) {
			candidate.reset(candidates);
		}
	}
}
