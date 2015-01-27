package org.drklingmann.assignment.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "application")
public class ApplicationEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=31, nullable=false)
	private String name;
	
	@Column(length=1023, nullable=false)
	private String content;
	
	@Column(name="rejection_reason", length=255)
	private String rejectionReason;
	
	@Column(nullable=false)
	private State state;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "application", targetEntity = History.class)
	@OrderBy("date DESC")
	private List<History> history = new ArrayList<History>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<History> getHistory() {
		return history;
	}

	public void setHistory(List<History> history) {
		this.history = history;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

}
