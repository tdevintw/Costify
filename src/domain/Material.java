package domain;

public class Material extends Component{
    private double costPerUnit;
    private String quantity;
    private double costOfTransport;

    public Material(int id, String name, String componentType, double TVA, double qualityCoefficient, Project project, double costPerUnit, String quantity, double costOfTransport) {
        super(id, name, componentType, TVA, qualityCoefficient, project);
        this.costPerUnit = costPerUnit;
        this.quantity = quantity;
        this.costOfTransport = costOfTransport;
    }

    public Material(String name, String componentType, double TVA, double qualityCoefficient, Project project, double costPerUnit, String quantity, double costOfTransport) {
        super(name, componentType, TVA, qualityCoefficient, project);
        this.costPerUnit = costPerUnit;
        this.quantity = quantity;
        this.costOfTransport = costOfTransport;
    }

    public double getCostPerUnit() {
        return costPerUnit;
    }

    public void setCostPerUnit(double costPerUnit) {
        this.costPerUnit = costPerUnit;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public double costOfTransport() {
        return costOfTransport;
    }

    public void costOfTransport(double costOfTransport) {
        this.costOfTransport = costOfTransport;
    }
}
