package se.fivefactorial.voting;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import se.fivefactorial.voting.data.BallotList;
import se.fivefactorial.voting.data.Candidate;
import se.fivefactorial.voting.data.CandidateList;
import se.fivefactorial.voting.data.Result;
import se.fivefactorial.voting.util.CandidateComparator;

public class TLTHVoting {

	public static void main(String[] args) {
		Settings settings = new Settings();
		for (int i = 0; i < args.length; i++) {
			String command = args[i].toLowerCase();
			switch (command) {
			case "-?":
			case "--help":
				System.out.println("-?\t\t--help\t\t\tThis message");
				System.out.println("-s [%d]\t\t--seats [%d]\t\tSets the number of seats to elect.");
				System.out.println("-b [%d]\t\t--blank [%d]\t\tNumber of blank votes to add.");
				System.out.println("-f [FILENAME]\t --filename [FILENAME]\tThe CSV file for data.");
				System.out.println("-hh\t\t--has-header\t\tJumps over first row in CSV file.");
				System.out.println("-lh\t\t--lacks-header\t\tReads first row in CSV file.");
				System.out.println("-fn [%d]\t--firstname [%d]\tCol index for firstname data");
				System.out.println("-ln [%d]\t--lastname [%d]\t\tCol index for lastname data");
				System.out.println("-ba [%d]\t\t--ballot [%d]\t\tCol index for ballot id data");
				System.out.println("-g [%d]\t\t--guild [%d]\t\tCol index for guild data");
				System.out.println("-r [%d]\t\t--rating [%d]\t\tCol index for rating data");
				System.out.println("-ds [S]\t\t--data-separator [S]\tstring to separate csv data");
				System.out.println("-desc\t\t\t\t\tOrder rating descending");
				System.out.println("-asc\t\t\t\t\tOrder rating ascending");
				System.exit(0);
				break;
			case "-s":
			case "--seats":
				int seats = Integer.parseInt(args[++i]);
				settings.setSeats(seats);
				break;
			case "-b":
			case "--blank":
				int blank = Integer.parseInt(args[++i]);
				settings.setBlank(blank);
				break;
			case "-f":
			case "--filename":
				settings.setFilename(args[++i]);
				break;
			case "-hh":
			case "--has-header":
				settings.setHasCSVHeader(true);
				break;
			case "-lh":
			case "--lacks-header":
				settings.setHasCSVHeader(false);
				break;
			case "-fn":
			case "--firstname":
				int firstname = Integer.parseInt(args[++i]);
				settings.setColFirstName(firstname);
				break;
			case "-ln":
			case "--lastname":
				int lastname = Integer.parseInt(args[++i]);
				settings.setColLastName(lastname);
				break;
			case "-ba":
			case "--ballot":
				int ballot = Integer.parseInt(args[++i]);
				settings.setColBallotID(ballot);
				break;
			case "-g":
			case "--guild":
				int guild = Integer.parseInt(args[++i]);
				settings.setColGuild(guild);
				break;
			case "-r":
			case "--rating":
				int rating = Integer.parseInt(args[++i]);
				settings.setColRating(rating);
				break;
			case "-ds":
			case "--data-separator":
				settings.setSeparator(args[++i]);
				break;
			case "-desc":
				settings.setRatingDESC(true);
				break;
			case "-asc":
				settings.setRatingDESC(false);
				break;
			}

		}
		new TLTHVoting(settings);
	}

	private Settings settings;
	private CandidateList candidateList;
	private BallotList ballotList;

	public TLTHVoting(Settings settings) {
		this.settings = settings;
		System.out.println("TLTH Voting system started with following settings.");
		System.out.println(settings);
		System.out.println();

		candidateList = new CandidateList();
		ballotList = new BallotList();

		parsing();
		preprocessing();
		Result result = voting();
		result(result);
	}

	private void parsing() {
		System.out.println("### STEP 1: Parsing ##############");
		try {
			Parser.parse(settings, candidateList, ballotList);
		} catch (IOException e) {
			System.out.println("Could not parse, see error:\n" + e.getMessage());
		}
		System.out.println(" Parsing done.");
		System.out.printf(" %d candidates\n", candidateList.size());
		System.out.printf(" %d ballots\n", ballotList.size());
	}

	private void preprocessing() {
		System.out.println("### STEP 2: Preprocessing ########");
		if (settings.getBlank() == 0) {
			System.out.println(" No blank votes added");
		} else {
			ballotList.addBlanks(settings.getBlank());
			System.out.printf(" Added %d blank votes\n", settings.getBlank());
		}

		System.out.println(" Preprocessing done.");
		System.out.printf(" %d candidates\n", candidateList.size());
		System.out.printf(" %d ballots\n", ballotList.size());

		ballotList.assignAll();
		List<Candidate> candidates = candidateList.getList();
		Collections.sort(candidates, new CandidateComparator());
		System.out.println(" Listing all first hand votes:");
		for (Candidate candidate : candidates) {
			System.out.printf("  %.0f - %s \n", candidate.getVotes(), candidate.toString());
		}
	}

	private Result voting() {
		System.out.println("### STEP 3: Voting ###############");
		Voting voting = new Voting(settings, candidateList, ballotList);

		System.out.println(" Calculating guaranteed mandates for the guilds...");
		List<Candidate> guaranteed = voting.calculateGuaranteed();
		for (Candidate candidate : guaranteed) {
			System.out.printf(" %s - %s\n", candidate.getGuild(), candidate.getName());
		}
		System.out.println(" Calculating remaining seats...");
		Result result = voting.calculateRegular(guaranteed);
		System.out.println(" Done!");
		return result;
	}

	private void result(Result result) {
		System.out.println("### STEP 4: Result ###############");
		System.out.println(result);
	}
}
