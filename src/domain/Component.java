package domain;

public abstract class Component {
    protected int id;
    protected String name;
    protected String componentType;
    protected double TVA;
    protected double qualityCoefficient;
    protected Project project;

    //this constructor is for creating component  when we fetch components data from the database since we have the id now

    public Component(int id, String name, String componentType, double TVA, double qualityCoefficient, Project project) {
        this.id = id;
        this.name = name;
        this.componentType = componentType;
        this.TVA = TVA;
        this.qualityCoefficient = qualityCoefficient;
        this.project = project;
    }
    //this constructor is for creating component and insert it in the database since we don't know the id

    public Component(String name, String componentType, double TVA, double qualityCoefficient, Project project) {
        this.name = name;
        this.componentType = componentType;
        this.TVA = TVA;
        this.qualityCoefficient = qualityCoefficient;
        this.project = project;
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

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public double getTVA() {
        return TVA;
    }

    public void setTVA(double TVA) {
        this.TVA = TVA;
    }

    public double getQualityCoefficient() {
        return qualityCoefficient;
    }

    public void setQualityCoefficient(double qualityCoefficient) {
        this.qualityCoefficient = qualityCoefficient;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
