

package com.mycompany.teamsgenerator;
import java.util.List;

public class Team {
    private String name;
    private List<Person> members;
    
// Constructor to initialize a Team object
    public Team(String name, List<Person> members) {
        this.name = name;
        this.members = members;
    }
  // Get the name of the team
    public String getName() {
        return name;
    }
     // Get the list of members in the team
    public List<Person> getMembers() {
        return members;
    }
}
