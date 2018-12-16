package se.fivefactorial.voting;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import se.fivefactorial.voting.data.Ballot;
import se.fivefactorial.voting.data.BallotList;
import se.fivefactorial.voting.data.Candidate;
import se.fivefactorial.voting.data.CandidateList;
import se.fivefactorial.voting.data.Guild;
import se.fivefactorial.voting.data.Vote;

public class Parser {

	public static void parse(Settings settings, CandidateList candidateList, BallotList ballotList) throws IOException {
		File file = new File(settings.getFilename());
		Scanner scan = new Scanner(file);

		if (settings.hasCSVHeader()) {
			scan.nextLine();
		}
		
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			String[] data = line.split(";");

			String firstname = data[settings.getColFirstName()].trim();
			String lastname = data[settings.getColLastName()].trim();
			String guild = data[settings.getColGuild()].trim();

			Candidate candidate = new Candidate(firstname, lastname, Guild.valueOf(guild));
			candidate = candidateList.add(candidate);

			String ballotID = data[settings.getColBallotID()].trim();
			Ballot ballot = new Ballot(ballotID);
			ballot = ballotList.add(ballot);

			int rating = Integer.parseInt(data[settings.getColRating()]);
			Vote vote = new Vote(candidate, rating, settings.isRatingDESC());
			ballot.add(vote);
		}
		scan.close();

	}
}
