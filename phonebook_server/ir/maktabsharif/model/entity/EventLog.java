package ir.maktabsharif.model.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EventLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private String name;
	@Column
	private String level;
	@Column
	private String description;
	@Column
	private Timestamp time;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public EventLog() {
	}

	public EventLog(long id, String name, String level, String description, Timestamp time) {
		this.id = id;
		this.name = name;
		this.level=level;
		this.description = description;
		this.time = time;
	}

	@Override
	public String toString() {
		return "id:" + getId() + ", name:" + getName() + ", level:" + getLevel() + ", description:" + getDescription() + ", date:" + getTime();
	}
}
