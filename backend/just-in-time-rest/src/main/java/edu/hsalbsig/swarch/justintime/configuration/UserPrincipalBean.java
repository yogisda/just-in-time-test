package edu.hsalbsig.swarch.justintime.configuration;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;

@RequestScoped
@Stateful
public class UserPrincipalBean {
    private int id;
    private int role;
    
    public UserPrincipalBean() {
    }

    public UserPrincipalBean(int id, int role) {
        this.id = id;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
