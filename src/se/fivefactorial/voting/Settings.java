package se.fivefactorial.voting;

public class Settings {
	private int seats = 27;
	private int blank = 0;
	private String filename = "data.csv";
	private boolean hasCSVHeader = true;
	private int colFirstName = 2;
	private int colLastName = 3;
	private int colBallotID = 0;
	private String separator = ",";
	private int colGuild = 4;
	private int colRating = 1;
	private boolean ratingDESC = true;

	public int getColBallotID() {
		return colBallotID;
	}

	public void setColBallotID(int colBallotID) {
		this.colBallotID = colBallotID;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public int getBlank() {
		return blank;
	}

	public void setBlank(int blank) {
		this.blank = blank;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public boolean hasCSVHeader() {
		return hasCSVHeader;
	}

	public void setHasCSVHeader(boolean ignoreCSVHeader) {
		this.hasCSVHeader = ignoreCSVHeader;
	}

	public int getColFirstName() {
		return colFirstName;
	}

	public void setColFirstName(int colFirstName) {
		this.colFirstName = colFirstName;
	}

	public int getColLastName() {
		return colLastName;
	}

	public void setColLastName(int colLastName) {
		this.colLastName = colLastName;
	}

	public int getColGuild() {
		return colGuild;
	}

	public void setColGuild(int colGuild) {
		this.colGuild = colGuild;
	}

	public int getColRating() {
		return colRating;
	}

	public void setColRating(int colRating) {
		this.colRating = colRating;
	}

	public boolean isRatingDESC() {
		return ratingDESC;
	}

	public void setRatingDESC(boolean ratingDESC) {
		this.ratingDESC = ratingDESC;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Filename: ").append(filename).append(System.lineSeparator());
		sb.append("Seats: ").append(seats).append(System.lineSeparator());
		sb.append("Blank votes: ").append(blank).append(System.lineSeparator());
		sb.append("Rating order: ").append(ratingDESC ? "DESC" : "ASC").append(System.lineSeparator());
		sb.append("CSV file settings:").append(System.lineSeparator());
		sb.append(" Have header: ").append(hasCSVHeader ? "yes" : "no").append(System.lineSeparator());
		sb.append(" First name column: ").append(colFirstName).append(System.lineSeparator());
		sb.append(" Last name column: ").append(colLastName).append(System.lineSeparator());
		sb.append(" Guild column: ").append(colGuild).append(System.lineSeparator());
		sb.append(" Rating column: ").append(colRating).append(System.lineSeparator());
		sb.append(" BallotID column: ").append(colBallotID).append(System.lineSeparator());
		sb.append(" Separator: ").append(separator);

		return sb.toString();
	}

}
