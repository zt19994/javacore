package model;

import java.time.LocalDate;

/**
 * 雇员
 *
 * @author zt1994 2019/5/13 21:39
 */
public class Employee {
    /**
     * 姓名
     */
    private String name;

    /**
     * 薪水
     */
    private double salary;

    /**
     * 雇用时间
     */
    private LocalDate hireDay;

    public Employee(String n, double s, int year, int month, int day) {
        name = n;
        salary = s;
        hireDay = LocalDate.of(year, month, day);
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public LocalDate getHireDay() {
        return hireDay;
    }

    public void raiseSalary(double byPercent) {
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", hireDay=" + hireDay +
                '}';
    }
}
