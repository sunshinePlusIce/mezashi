package com.arato.Mezashi.Mezashi;

import jakarta.persistence.*;
import com.arato.Mezashi.User.User;

import java.time.LocalDate;
import java.util.*;
import com.arato.Mezashi.constant.Constant;
import jakarta.validation.constraints.Future;

@Entity
@Table(name="mezashi")
public class Mezashi {
    @Id
    @GeneratedValue
    private long id;
    @Column(length=Constant.MEZASHI_NAME_MAX_LENGTH)
    private String name;

    @ManyToMany
    @JoinTable(
            name="mezashi_tags",
            joinColumns = @JoinColumn(name="mezashi_id"),
            inverseJoinColumns = @JoinColumn(name="tag_id")
    )
    private Set<Tag> tags;
    private CompleteCondition completeCondition;
    @Column(length=Constant.MEZASHI_DESCRIPTION_MAX_LENGTH)
    private String description;
    @Future
    private LocalDate targetDate;
    @ManyToOne(fetch=FetchType.LAZY)
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    private Mezashi parent;
    @OneToMany(mappedBy="parent", fetch=FetchType.LAZY)
    private List<Mezashi> children;

    public Mezashi() {
    }

    public Mezashi(
            String name,
            Set<Tag> tags,
            CompleteCondition completeCondition,
            String description,
            User user,
            Mezashi parent
    ) {
        this.name = name;
        this.tags = tags;
        this.completeCondition = completeCondition;
        this.description = description;
        this.user = user;
        this.parent = parent;
    }

    public List<Mezashi> getChildren() {
        return children;
    }

    public void setChildren(List<Mezashi> children) {
        this.children = children;
    }

    /*
            add a child mezashi to a parent mezashi.
         */
    public boolean addChild(Mezashi child) {
        return this.children.add(child);
    }

    public boolean removeChild(Mezashi child) {
        child.setParent(null);
        return this.children.remove(child);
    }

    public boolean AddTag(Tag tag) {
        return this.tags.add(tag);
    }

    public boolean removeTag(Tag tag) {
        return this.tags.remove(tag);
    }

    public Mezashi getParent() {
        return parent;
    }

    public void setParent(Mezashi parent) {
        this.parent = parent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public CompleteCondition getCompleteCondition() {
        return completeCondition;
    }

    public void setCompleteCondition(CompleteCondition completeCondition) {
        this.completeCondition = completeCondition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mezashi mezashi = (Mezashi) o;
        return id == mezashi.id && Objects.equals(name, mezashi.name) && Objects.equals(tags, mezashi.tags) && completeCondition == mezashi.completeCondition && Objects.equals(description, mezashi.description) && Objects.equals(targetDate, mezashi.targetDate) && Objects.equals(user, mezashi.user) && Objects.equals(parent, mezashi.parent) && Objects.equals(children, mezashi.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, tags, completeCondition, description, targetDate, user, parent, children);
    }

    @Override
    public String toString() {
        return "Mezashi{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tags=" + tags +
                ", completeCondition=" + completeCondition +
                ", description='" + description + '\'' +
                ", targetDate=" + targetDate +
                ", user=" + user +
                ", parent=" + parent +
                ", children=" + children +
                '}';
    }
}
