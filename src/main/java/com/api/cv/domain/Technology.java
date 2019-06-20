package com.api.cv.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Technology.
 */
@Entity
@Table(name = "technology")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Technology implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "tech_name", nullable = false)
    private String techName;

    @NotNull
    @Column(name = "is_framework", nullable = false)
    private Boolean isFramework;

    @NotNull
    @Column(name = "experience_rate", nullable = false)
    private Double experienceRate;

    @ManyToMany(mappedBy = "technologies")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Project> projects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTechName() {
        return techName;
    }

    public Technology techName(String techName) {
        this.techName = techName;
        return this;
    }

    public void setTechName(String techName) {
        this.techName = techName;
    }

    public Boolean isIsFramework() {
        return isFramework;
    }

    public Technology isFramework(Boolean isFramework) {
        this.isFramework = isFramework;
        return this;
    }

    public void setIsFramework(Boolean isFramework) {
        this.isFramework = isFramework;
    }

    public Double getExperienceRate() {
        return experienceRate;
    }

    public Technology experienceRate(Double experienceRate) {
        this.experienceRate = experienceRate;
        return this;
    }

    public void setExperienceRate(Double experienceRate) {
        this.experienceRate = experienceRate;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public Technology projects(Set<Project> projects) {
        this.projects = projects;
        return this;
    }

    public Technology addProject(Project project) {
        this.projects.add(project);
        project.getTechnologies().add(this);
        return this;
    }

    public Technology removeProject(Project project) {
        this.projects.remove(project);
        project.getTechnologies().remove(this);
        return this;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Technology)) {
            return false;
        }
        return id != null && id.equals(((Technology) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Technology{" +
            "id=" + getId() +
            ", techName='" + getTechName() + "'" +
            ", isFramework='" + isIsFramework() + "'" +
            ", experienceRate=" + getExperienceRate() +
            "}";
    }
}
