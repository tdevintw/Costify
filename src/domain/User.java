package domain;

import domain.enums.Role;

import java.util.List;

public class User {
    private int id;
    private String name;
    private String password;
    private String address;
    private String phone;
    private boolean isProfessional;
    private Role role;
    private List<Project> projects ;

    //this constructor is for creating user when we fetch users data from the database since we have the id now

    public User(int id, String name, String password, String address, String phone, boolean isProfessional, Role role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.isProfessional = isProfessional;
        this.role = role;
    }

    //this constructor is for creating user and register it in the database since we don't know the id

    public User(String name, String password, String address, String phone, boolean isProfessional, Role role) {
        this.name = name;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.isProfessional = isProfessional;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isProfessional() {
        return isProfessional;
    }

    public void setProfessional(boolean professional) {
        isProfessional = professional;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
