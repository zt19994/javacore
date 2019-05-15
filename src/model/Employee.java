package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 雇员
 *
 * @author zt1994 2019/5/13 21:39
 */
public class Employee implements Serializable {

    public static final int NAME_SIZE = 40;
    public static final int RECORD_SIZE = 2 * NAME_SIZE + 8 + 4 + 4 + 4;

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

    public Employee() {
    }

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

    /**
     * 按比例涨工资
     *
     * @param byPercent 涨幅比例
     */
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
