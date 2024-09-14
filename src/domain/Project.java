package domain;

import domain.enums.Status;

import java.util.List;

public class Project {
    private int id;
    private String name;
    private double profitMargin;
    private double costTotal;
    private Status status;
    private List<Labor> labors;
    private List<Material> materials;
    private User user;
    private List<Estimate> estimates;

    public Project(int id, String name, double profitMargin, double costTotal, Status status, List<Labor> labors, List<Material> materials, User user, List<Estimate> estimates) {
        this.id = id;
        this.name = name;
        this.profitMargin = profitMargin;
        this.costTotal = costTotal;
        this.status = status;
        this.labors = labors;
        this.materials = materials;
        this.user = user;
        this.estimates = estimates;
    }

    public Project(String name, double profitMargin, double costTotal, Status status, List<Labor> labors, List<Material> materials, User user, List<Estimate> estimates) {
        this.name = name;
        this.profitMargin = profitMargin;
        this.costTotal = costTotal;
        this.status = status;
        this.labors = labors;
        this.materials = materials;
        this.user = user;
        this.estimates = estimates;
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

    public double getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(double profitMargin) {
        this.profitMargin = profitMargin;
    }

    public double getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(double costTotal) {
        this.costTotal = costTotal;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Labor> getLabors() {
        return labors;
    }

    public void setLabors(List<Labor> labors) {
        this.labors = labors;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Estimate> getEstimates() {
        return estimates;
    }

    public void setEstimates(List<Estimate> estimates) {
        this.estimates = estimates;
    }
}
