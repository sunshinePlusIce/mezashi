package com.arato.Mezashi.User;

import com.arato.Mezashi.Mezashi.Mezashi;
import com.arato.Mezashi.Tag.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

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
    private String password;
    @OneToMany(mappedBy="user", fetch=FetchType.LAZY)
    private Set<Mezashi> mezashiList;
    @OneToMany(mappedBy="user", fetch=FetchType.LAZY)
    private Set<Tag> tags;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean addMezashi(Mezashi mezashi) {
        return this.mezashiList.add(mezashi);
    }

    public boolean removeMezashi(Mezashi mezashi) {
        return this.mezashiList.remove(mezashi);
    }

    public boolean addTag(Tag tag) {
        return this.tags.add(tag);
    }

    public boolean removeTag(Tag tag)  {
        return this.tags.remove(tag);
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

    public long getId() {
        return id;
    }
}
