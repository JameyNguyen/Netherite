package com.cs309.tutorial.post;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.NaturalId;

import lombok.Data;

@Entity
@Table(name = "tags")
@Data
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NaturalId
    private String name;

    //Specifies a lazy loading strategy, meaning that tags for a Post will only be loaded
    //from the database when explicitly accessed in code. This conserves memory and
    //improves performance by not loading associated Tag entities until they’re actually needed.
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            },
            mappedBy = "tags")

    //cascade = { CascadeType.PERSIST, CascadeType.MERGE }:
    //
    //Sets up cascading operations so that any PERSIST (save) or MERGE (update) operation on a Post entity will also apply to its associated Tag entities.
    //CascadeType.PERSIST: When a new Post is saved, any new Tags in its tags set are also saved automatically.
    //CascadeType.MERGE: When an existing Post is updated, any modified Tags are also updated.

    private Set<Post> posts = new HashSet<>();

    public Tag() {

    }

    public Tag(String name) {
        this.name = name;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tag other = (Tag) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    // Getters and Setters (Omitted for brevity)
    public Set<Post> getPosts() {
        return posts;
    }
}