package com.wineshop.entities;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Size;

@Entity
public class Vineyard extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	

    @Basic
    @Size(min=5, max=100, message="The name must be between {min} and {max} characters")
    private String name;
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy="vineyard", orphanRemoval=true)
    @Valid
    private Set<Wine> wines;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Wine> getWines() {
		return wines;
	}

	public void setWines(Set<Wine> wines) {
		this.wines = wines;
	}
}