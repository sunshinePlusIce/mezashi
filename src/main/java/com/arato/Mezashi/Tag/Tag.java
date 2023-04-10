package com.arato.Mezashi.Tag;


import com.arato.Mezashi.Mezashi.Mezashi;
import com.arato.Mezashi.User.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Tag {
    @Id
    @GeneratedValue
    private long id;
    @Column(length=20)
    private String name;
    @ManyToOne
    private User user;
    @ManyToMany(
            mappedBy="tags",
            cascade = CascadeType.ALL
    )
    private List<Mezashi> mezashiList;
    private TagColor tagColor;

    public Tag() {
    }

    public Tag(String name, TagColor tagColor) {
        this.name = name;
        this.tagColor = tagColor;
    }
}
