package pl.bet123.model;

import java.sql.Timestamp;
import java.sql.Date;

public class Match {
	private long id;
    private String teamA;
    private String teamB;
    private Date timestamp;
    private int goalsA;
    private int goalsB;
    
    public Match(){}
    
    public Match(Match match){
    	this.id = match.id;
    	this.teamA = match.teamA;
    	this.teamB = match.teamB;
    	this.timestamp = match.timestamp;
    	this.goalsA = match.goalsA;
    	this.goalsB = match.goalsB;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTeamA() {
		return teamA;
	}

	public void setTeamA(String teamA) {
		this.teamA = teamA;
	}

	public String getTeamB() {
		return teamB;
	}

	public void setTeamB(String teamB) {
		this.teamB = teamB;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getGoalsA() {
		return goalsA;
	}

	public void setGoalsA(int goalsA) {
		this.goalsA = goalsA;
	}

	public int getGoalsB() {
		return goalsB;
	}

	public void setGoalsB(int goalsB) {
		this.goalsB = goalsB;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + goalsA;
		result = prime * result + goalsB;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((teamA == null) ? 0 : teamA.hashCode());
		result = prime * result + ((teamB == null) ? 0 : teamB.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Match other = (Match) obj;
		if (goalsA != other.goalsA)
			return false;
		if (goalsB != other.goalsB)
			return false;
		if (id != other.id)
			return false;
		if (teamA == null) {
			if (other.teamA != null)
				return false;
		} else if (!teamA.equals(other.teamA))
			return false;
		if (teamB == null) {
			if (other.teamB != null)
				return false;
		} else if (!teamB.equals(other.teamB))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Match [id=" + id + ", teamA=" + teamA + ", teamB=" + teamB + ", timestamp=" + timestamp + ", goalsA="
				+ goalsA + ", goalsB=" + goalsB + "]";
	}

}
