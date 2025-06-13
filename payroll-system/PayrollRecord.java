import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * PayrollRecord class - represents a payroll record for an employee
 */
public class PayrollRecord {
    private String employeeId;
    private String employeeName;
    private double grossSalary;
    private double tax;
    private double netSalary;
    private String employeeType;
    private LocalDate payrollDate;
    
    public PayrollRecord(String employeeId, String employeeName, double grossSalary, 
                        double tax, double netSalary, String employeeType) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.grossSalary = grossSalary;
        this.tax = tax;
        this.netSalary = netSalary;
        this.employeeType = employeeType;
        this.payrollDate = LocalDate.now();
    }
    
    // Getters
    public String getEmployeeId() { return employeeId; }
    public String getEmployeeName() { return employeeName; }
    public double getGrossSalary() { return grossSalary; }
    public double getTax() { return tax; }
    public double getNetSalary() { return netSalary; }
    public String getEmployeeType() { return employeeType; }
    public LocalDate getPayrollDate() { return payrollDate; }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("Date: %s | ID: %s | Name: %s | Type: %s | Gross: $%.2f | Tax: $%.2f | Net: $%.2f",
                payrollDate.format(formatter), employeeId, employeeName, employeeType, 
                grossSalary, tax, netSalary);
    }
}