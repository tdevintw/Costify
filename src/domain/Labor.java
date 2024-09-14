package domain;

public class Labor extends Component {
    private double costPerHour;
    private double hoursOfWork;

    public Labor(int id, String name, String componentType, double TVA, double qualityCoefficient, Project project, double costPerHour, double hoursOfWork) {
        super(id, name, componentType, TVA, qualityCoefficient, project);
        this.costPerHour = costPerHour;
        this.hoursOfWork = hoursOfWork;
    }

    public Labor(String name, String componentType, double TVA, double qualityCoefficient, Project project, double costPerHour, double hoursOfWork) {
        super(name, componentType, TVA, qualityCoefficient, project);
        this.costPerHour = costPerHour;
        this.hoursOfWork = hoursOfWork;
    }

    public double getCostPerHour() {
        return costPerHour;
    }

    public void setCostPerHour(double costPerHour) {
        this.costPerHour = costPerHour;
    }

    public double getHoursOfWork() {
        return hoursOfWork;
    }

    public void setHoursOfWork(double hoursOfWork) {
        this.hoursOfWork = hoursOfWork;
    }
}
