package vo;

import java.util.ArrayList;
import java.util.List;

public class Matchdetails {

	private Integer p;
	private List<Goal> g;

	public List<Score> getScorer() {
		List<Score> result = new ArrayList<>();

		for (int i = 0; i < g.size(); i++) {

			Goal goal = g.get(i);
			Score score = new Score();
			score.setId(p + "-" + i+"-"+goal.getN());
			score.setName(goal.getN());
			score.setEvent("Goal");

			if (goal.getP() > 0) {
				score.setEvent("Penalty");
			}
			if (goal.getO() > 0) {
				score.setEvent("Own");
			}
			result.add(score);
		}

		return result;
	}

	public Integer getP() {
		return p;
	}

	public void setP(Integer p) {
		this.p = p;
	}

	public List<Goal> getG() {
		return g;
	}

	public void setG(List<Goal> g) {
		this.g = g;
	}

}
