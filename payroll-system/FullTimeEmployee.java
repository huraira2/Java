/**
 * FullTimeEmployee class - represents full-time employees with annual salary
 */
public class FullTimeEmployee extends Employee {
    private double annualSalary;
    private double bonus;
    
    public FullTimeEmployee(String name, String email, String department, double annualSalary) {
        super(name, email, department);
        this.annualSalary = annualSalary;
        this.bonus = 0.0;
    }
    
    @Override
    public double calculateSalary() {
        return (annualSalary / 12) + bonus; // Monthly salary plus bonus
    }
    
    @Override
    public String getEmployeeType() {
        return "Full-Time";
    }
    
    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
    
    public double getAnnualSalary() { return annualSalary; }
    public void setAnnualSalary(double annualSalary) { this.annualSalary = annualSalary; }
    public double getBonus() { return bonus; }
    
    @Override
    public String toString() {
        return super.toString() + String.format(" | Annual: $%.2f | Bonus: $%.2f", annualSalary, bonus);
    }
}