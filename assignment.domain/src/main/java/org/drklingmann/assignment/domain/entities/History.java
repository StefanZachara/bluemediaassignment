package org.drklingmann.assignment.domain.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "history")
public class History implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private State state;
	
	@Column(nullable=false)
	private Date date;
	
	@ManyToOne(optional = false)
	private ApplicationEntity application;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ApplicationEntity getApplication() {
		return application;
	}

	public void setApplication(ApplicationEntity application) {
		this.application = application;
	}

}
