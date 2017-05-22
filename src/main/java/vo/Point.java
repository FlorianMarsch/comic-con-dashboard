package vo;

import java.util.Set;

public class Point {

	private Integer position;
	private String trainer;
	private Integer trainerId;
	private Integer points;
	private Integer value;
	private Set<String> archivments;
	
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public String getTrainer() {
		return trainer;
	}
	public void setTrainer(String trainer) {
		this.trainer = trainer;
	}
	public Integer getTrainerId() {
		return trainerId;
	}
	public void setTrainerId(Integer trainerId) {
		this.trainerId = trainerId;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public Set<String> getArchivments() {
		return archivments;
	}
	public void setArchivments(Set<String> archivments) {
		this.archivments = archivments;
	}
	
	
}
