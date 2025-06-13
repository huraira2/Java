/**
 * ContractEmployee class - represents contract employees with project-based payment
 */
public class ContractEmployee extends Employee {
    private double contractAmount;
    private int projectsCompleted;
    private double paymentPerProject;
    
    public ContractEmployee(String name, String email, String department, double contractAmount, int totalProjects) {
        super(name, email, department);
        this.contractAmount = contractAmount;
        this.projectsCompleted = 0;
        this.paymentPerProject = contractAmount / totalProjects;
    }
    
    @Override
    public double calculateSalary() {
        return projectsCompleted * paymentPerProject;
    }
    
    @Override
    public String getEmployeeType() {
        return "Contract";
    }
    
    public void completeProject() {
        projectsCompleted++;
    }
    
    public void setProjectsCompleted(int projectsCompleted) {
        this.projectsCompleted = projectsCompleted;
    }
    
    public double getContractAmount() { return contractAmount; }
    public int getProjectsCompleted() { return projectsCompleted; }
    public double getPaymentPerProject() { return paymentPerProject; }
    
    @Override
    public String toString() {
        return super.toString() + String.format(" | Contract: $%.2f | Projects: %d | Per Project: $%.2f", 
                contractAmount, projectsCompleted, paymentPerProject);
    }
}