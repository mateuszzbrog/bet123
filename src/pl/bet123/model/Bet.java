package pl.bet123.model;

import java.sql.Timestamp;

public class Bet {
	private long id;
    private long userId;
    private long matchId;
    private int goalsA;
    private int goalsB;
    private Timestamp date;
    private int points;
    
    public Bet(){}
    
    public Bet(Bet bet){
    	this.id = bet.id;
        this.userId = bet.userId;
        this.matchId = bet.matchId;
        this.goalsA= bet.goalsA;
        this.goalsB= bet.goalsB;
        this.date = new Timestamp(bet.date.getTime());
        this.points = bet.points;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getMatchId() {
		return matchId;
	}

	public void setMatchId(long matchId) {
		this.matchId = matchId;
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

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + goalsA;
		result = prime * result + goalsB;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (matchId ^ (matchId >>> 32));
		result = prime * result + points;
		result = prime * result + (int) (userId ^ (userId >>> 32));
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
		Bet other = (Bet) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (goalsA != other.goalsA)
			return false;
		if (goalsB != other.goalsB)
			return false;
		if (id != other.id)
			return false;
		if (matchId != other.matchId)
			return false;
		if (points != other.points)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Bet [id=" + id + ", userId=" + userId + ", matchId=" + matchId + ", goalsA=" + goalsA + ", goalsB="
				+ goalsB + ", date=" + date + ", points=" + points + "]";
	}
    
    

}
