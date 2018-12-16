package se.fivefactorial.voting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.fivefactorial.voting.data.BallotList;
import se.fivefactorial.voting.data.Candidate;
import se.fivefactorial.voting.data.CandidateList;
import se.fivefactorial.voting.data.Guild;
import se.fivefactorial.voting.data.Result;
import se.fivefactorial.voting.util.CandidateComparator;

public class Voting {

	private Settings settings;
	private CandidateList candidateList;
	private BallotList ballotList;

	public Voting(Settings settings, CandidateList candidateList, BallotList ballotList) {
		this.settings = settings;
		this.candidateList = candidateList;
		this.ballotList = ballotList;
	}

	public List<Candidate> calculateGuaranteed() {
		List<Candidate> guaranteed = new ArrayList<>();
		for (Guild guild : Guild.values()) {
			List<Candidate> candidates = candidateList.getList(guild);
			Result result = vote(1, candidates);
			guaranteed.addAll(result.getElected());
		}
		return guaranteed;
	}

	public Result calculateRegular(List<Candidate> guaranteed) {
		List<Candidate> candidates = candidateList.getList();
		candidates.removeAll(guaranteed);
		int seats = settings.getSeats() - guaranteed.size();
		Result result = vote(seats, candidates);
		result.addElected(guaranteed);
		return result;
	}

	private double quota(int seats) {
		return (ballotList.size()) / (seats + 1.0) + 1.0;
	}

	private Result vote(int seats, List<Candidate> candidates) {
		double quota = quota(seats);

		ballotList.reset();
		candidateList.reset(candidates);
		ballotList.assignAll();

		List<Candidate> elected = new ArrayList<>();
		List<Candidate> removed = new ArrayList<>();

		while (elected.size() < seats && candidates.size() > 0) {
			Collections.sort(candidates, new CandidateComparator());

			if (elected.size() + candidates.size() == seats) {
				elected.addAll(candidates);
				break;
			}

			Candidate first = candidates.get(0);
			if (first.getVotes() >= quota) {
				elected.add(first);
				candidates.remove(first);
				first.elected(quota);
			} else {
				Candidate last = candidates.get(candidates.size() - 1);
				removed.add(last);
				candidates.remove(last);
				last.removed();
			}
		}
		Collections.sort(elected);
		Collections.reverse(removed);
		return new Result(elected, removed);
	}
}
