package com.arato.Mezashi.Mezashi;


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
    @ManyToMany(mappedBy="tags")
    private List<Mezashi> mezashiList;
    private TagColor tagColor;

    public Tag() {
    }
}
