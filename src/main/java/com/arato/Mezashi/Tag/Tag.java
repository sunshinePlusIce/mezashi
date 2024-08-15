package com.arato.Mezashi.Tag;


import com.arato.Mezashi.Mezashi.Mezashi;
import com.arato.Mezashi.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.List;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property="id"
)
public class Tag {
    @Id
    @GeneratedValue
    private long id;
    @Column(length=20, unique=true)
    private String name;
    @ManyToOne
    @JsonIgnore
    private User user;
    @ManyToMany(
            mappedBy="tags",
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private List<Mezashi> mezashiList;
    private TagColor tagColor;

    public Tag() {
    }

    public Tag(String name, TagColor tagColor) {
        this.name = name;
        this.tagColor = tagColor;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Mezashi> getMezashiList() {
        return mezashiList;
    }

    public void setMezashiList(List<Mezashi> mezashiList) {
        this.mezashiList = mezashiList;
    }

    public TagColor getTagColor() {
        return tagColor;
    }

    public void setTagColor(TagColor tagColor) {
        this.tagColor = tagColor;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", user=" + user +
                ", mezashiList=" + mezashiList +
                ", tagColor=" + tagColor +
                '}';
    }


}
