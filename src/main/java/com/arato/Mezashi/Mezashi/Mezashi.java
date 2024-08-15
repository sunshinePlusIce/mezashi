package com.arato.Mezashi.Mezashi;

import com.arato.Mezashi.Tag.Tag;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import com.arato.Mezashi.User.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import com.arato.Mezashi.constant.Constant;
import jakarta.validation.constraints.Future;
import lombok.ToString;

@Entity
@Table(name="mezashi")
public class Mezashi implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Column(length=Constant.MEZASHI_NAME_MAX_LENGTH, unique = true)
    private String name;

    @ManyToMany(
            cascade = CascadeType.ALL
    )
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
    @ManyToOne
    @JsonIgnore
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="parentId", nullable = true)
    @JsonIgnore
    @ToString.Exclude
    private Mezashi parent;
    @OneToMany(mappedBy="parent")
    private List<Mezashi> children;

    private boolean isDone;
    private LocalDate completeDate;
    private LocalDate creationDate;

    public Mezashi() {
    }

    public Mezashi(
            String name,
            Set<Tag> tags,
            CompleteCondition completeCondition,
            String description,
            User user,
            Mezashi parent,
            boolean isDone
    ) {
        this.name = name;
        this.tags = tags;
        this.completeCondition = completeCondition;
        this.description = description;
        this.user = user;
        this.parent = parent;
        this.isDone = isDone;
        this.creationDate = LocalDate.now();
    }

    public Mezashi(Mezashi toClone) {
        this.id = toClone.getId();
        this.name = new String(toClone.getName());
        this.parent = toClone.getParent();
        var newChildren = new ArrayList<Mezashi>(toClone.getChildren() != null ? toClone.getChildren().size() : 0);
        newChildren.addAll(toClone.getChildren());
        this.children = newChildren;
        this.isDone = toClone.isDone();
        this.completeCondition = toClone.getCompleteCondition();
        this.creationDate = toClone.getCreationDate();
    }

    public List<Mezashi> getChildren() {
        return children;
    }

    public void setChildren(List<Mezashi> children) {
        this.children = children;
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

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public LocalDate getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(LocalDate completeDate) {
        this.completeDate = completeDate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
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

//    @Override
//    public String toString() {
//        return "Mezashi{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", tags=" + tags +
//                ", completeCondition=" + completeCondition +
//                ", description='" + description + '\'' +
//                ", targetDate=" + targetDate +
//                ", user=" + user +
//                ", parent=" + parent +
//                ", children=" + children +
//                '}';
//    }
}
