package com.api.cv.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Reward.
 */
@Entity
@Table(name = "reward")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Reward implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "issuer_entity", nullable = false)
    private String issuerEntity;

    @NotNull
    @Column(name = "short_description", nullable = false)
    private String shortDescription;

    @ManyToOne
    @JoinColumn(name = "person_id")
    @JsonIgnoreProperties({"languages", 
    	"rewards", "locations", "academicExperiences", "contacts", "professionalExperiences",
    	"picturePath", "birthDate", "birthTown", "shortDescription", "longDescription"})
    private Person person;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Reward title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public Reward date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getIssuerEntity() {
        return issuerEntity;
    }

    public Reward issuerEntity(String issuerEntity) {
        this.issuerEntity = issuerEntity;
        return this;
    }

    public void setIssuerEntity(String issuerEntity) {
        this.issuerEntity = issuerEntity;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public Reward shortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Person getPerson() {
        return person;
    }
    
    public Reward person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reward)) {
            return false;
        }
        return id != null && id.equals(((Reward) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Reward{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", date='" + getDate() + "'" +
            ", issuerEntity='" + getIssuerEntity() + "'" +
            ", shortDescription='" + getShortDescription() + "'" +
            "}";
    }
}
