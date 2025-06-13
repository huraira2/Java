/**
 * PartTimeEmployee class - represents part-time employees paid hourly
 */
public class PartTimeEmployee extends Employee {
    private double hourlyRate;
    private int hoursWorked;
    
    public PartTimeEmployee(String name, String email, String department, double hourlyRate) {
        super(name, email, department);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = 0;
    }
    
    @Override
    public double calculateSalary() {
        double regularPay = Math.min(hoursWorked, 40) * hourlyRate;
        double overtimePay = Math.max(0, hoursWorked - 40) * hourlyRate * 1.5; // 1.5x for overtime
        return regularPay + overtimePay;
    }
    
    @Override
    public String getEmployeeType() {
        return "Part-Time";
    }
    
    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = Math.max(0, hoursWorked); // Ensure non-negative
    }
    
    public double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(double hourlyRate) { this.hourlyRate = hourlyRate; }
    public int getHoursWorked() { return hoursWorked; }
    
    @Override
    public String toString() {
        return super.toString() + String.format(" | Rate: $%.2f/hr | Hours: %d", hourlyRate, hoursWorked);
    }
}