package edu.tum.ase.project.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="projects")
@Getter
@Setter
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "project_id")
    private String id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name = "project_project_users", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "userId")
    private Set<String> userIds = new HashSet<>();

    public Project(String name){
        this.name = name;
    }

    public boolean isAuthenticated(String username){
        System.out.println(username);
        if(this.getUserIds().contains(username))
            return true;
        return false;
    }

}
