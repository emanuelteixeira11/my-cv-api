package com.api.cv.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "picture_path")
    private String picturePath;

    @NotNull
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "birth_town")
    private String birthTown;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "long_description")
    private String longDescription;

    @OneToMany(mappedBy = "person", fetch =  FetchType.EAGER)
    private Set<ProfessionalExperience> professionalExperiences = new HashSet<>();

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER)
    private Set<AcademicExperience> academicExperiences = new HashSet<>();

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER)
    private Set<Contact> contacts = new HashSet<>();

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER)
    private Set<Location> locations = new HashSet<>();

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER)
    private Set<Reward> rewards = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "person")
    private Set<TokenMap> tokenMaps = new HashSet<>();

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER)
    private Set<Language> languages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Person firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Person lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public Person picturePath(String picturePath) {
        this.picturePath = picturePath;
        return this;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Person birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthTown() {
        return birthTown;
    }

    public Person birthTown(String birthTown) {
        this.birthTown = birthTown;
        return this;
    }

    public void setBirthTown(String birthTown) {
        this.birthTown = birthTown;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public Person shortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public Person longDescription(String longDescription) {
        this.longDescription = longDescription;
        return this;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Set<ProfessionalExperience> getProfessionalExperiences() {
        return professionalExperiences;
    }

    public Person professionalExperiences(Set<ProfessionalExperience> professionalExperiences) {
        this.professionalExperiences = professionalExperiences;
        return this;
    }

    public Person addProfessionalExperience(ProfessionalExperience professionalExperience) {
        this.professionalExperiences.add(professionalExperience);
        professionalExperience.setPerson(this);
        return this;
    }

    public Person removeProfessionalExperience(ProfessionalExperience professionalExperience) {
        this.professionalExperiences.remove(professionalExperience);
        professionalExperience.setPerson(null);
        return this;
    }

    public void setProfessionalExperiences(Set<ProfessionalExperience> professionalExperiences) {
        this.professionalExperiences = professionalExperiences;
    }

    public Set<AcademicExperience> getAcademicExperiences() {
        return academicExperiences;
    }

    public Person academicExperiences(Set<AcademicExperience> academicExperiences) {
        this.academicExperiences = academicExperiences;
        return this;
    }

    public Person addAcademicExperience(AcademicExperience academicExperience) {
        this.academicExperiences.add(academicExperience);
        academicExperience.setPerson(this);
        return this;
    }

    public Person removeAcademicExperience(AcademicExperience academicExperience) {
        this.academicExperiences.remove(academicExperience);
        academicExperience.setPerson(null);
        return this;
    }

    public void setAcademicExperiences(Set<AcademicExperience> academicExperiences) {
        this.academicExperiences = academicExperiences;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public Person contacts(Set<Contact> contacts) {
        this.contacts = contacts;
        return this;
    }

    public Person addContact(Contact contact) {
        this.contacts.add(contact);
        contact.setPerson(this);
        return this;
    }

    public Person removeContact(Contact contact) {
        this.contacts.remove(contact);
        contact.setPerson(null);
        return this;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public Person locations(Set<Location> locations) {
        this.locations = locations;
        return this;
    }

    public Person addLocation(Location location) {
        this.locations.add(location);
        location.setPerson(this);
        return this;
    }

    public Person removeLocation(Location location) {
        this.locations.remove(location);
        location.setPerson(null);
        return this;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

    public Set<Reward> getRewards() {
        return rewards;
    }

    public Person rewards(Set<Reward> rewards) {
        this.rewards = rewards;
        return this;
    }

    public Person addReward(Reward reward) {
        this.rewards.add(reward);
        reward.setPerson(this);
        return this;
    }

    public Person removeReward(Reward reward) {
        this.rewards.remove(reward);
        reward.setPerson(null);
        return this;
    }

    public void setRewards(Set<Reward> rewards) {
        this.rewards = rewards;
    }

    public Set<TokenMap> getTokenMaps() {
        return tokenMaps;
    }

    public Person tokenMaps(Set<TokenMap> tokenMaps) {
        this.tokenMaps = tokenMaps;
        return this;
    }

    public Person addTokenMap(TokenMap tokenMap) {
        this.tokenMaps.add(tokenMap);
        tokenMap.setPerson(this);
        return this;
    }

    public Person removeTokenMap(TokenMap tokenMap) {
        this.tokenMaps.remove(tokenMap);
        tokenMap.setPerson(null);
        return this;
    }

    public void setTokenMaps(Set<TokenMap> tokenMaps) {
        this.tokenMaps = tokenMaps;
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public Person languages(Set<Language> languages) {
        this.languages = languages;
        return this;
    }

    public Person addLanguage(Language language) {
        this.languages.add(language);
        language.setPerson(this);
        return this;
    }

    public Person removeLanguage(Language language) {
        this.languages.remove(language);
        language.setPerson(null);
        return this;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        return id != null && id.equals(((Person) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", picturePath='" + getPicturePath() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", birthTown='" + getBirthTown() + "'" +
            ", shortDescription='" + getShortDescription() + "'" +
            ", longDescription='" + getLongDescription() + "'" +
            "}";
    }
}
