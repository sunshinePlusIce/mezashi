package com.arato.Mezashi.User;

import com.arato.Mezashi.Mezashi.Mezashi;
import com.arato.Mezashi.Tag.Tag;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name="user_info")

public class User {
    @Id
    @GeneratedValue
    private long id;
    @Column(length=30)
    @Size(min=2, max=30)
    private String username;
    @Column(length=20)
    @Size(min=6, max=20)
    @JsonIgnore
    private String password;
    private LocalDate creationDate;
    @OneToMany(
            mappedBy="user"
    )
    private Set<Mezashi> mezashiList;
    @OneToMany(
            mappedBy="user"
    )
    private Set<Tag> tags;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, Set<Mezashi> mezashiList, Set<Tag> tags) {
        this.username = username;
        this.mezashiList = mezashiList;
        this.tags = tags;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Mezashi> getMezashiList() {
        return mezashiList;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public long getId() {
        return id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
