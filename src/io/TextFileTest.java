package io;

import model.Employee;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * 文本文件测试
 *
 * @author zt1994 2019/5/13 21:34
 */
public class TextFileTest {

    /**
     * 主方法
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Employee[] staff = new Employee[3];

        staff[0] = new Employee("Carl Cracker", 75000, 1987, 12, 15);
        staff[1] = new Employee("Peter", 60000, 1999, 8, 27);
        staff[2] = new Employee("Mark", 40000, 2002, 5, 12);

        // 保存所有Employee数据到文件employee.dat中
        try (PrintWriter out = new PrintWriter("employee.dat", "UTF-8")) {
            writeDate(staff, out);
        }

        // 获取数据存储到array中
        try (Scanner in = new Scanner(new FileInputStream("employee.dat"), "UTF-8")) {
            Employee[] newStaff = readDate(in);

            for (Employee employee : newStaff) {
                System.out.println(employee);
            }
        }
    }


    /**
     * 写入数据
     *
     * @param employees
     * @param out
     * @throws IOException
     */
    private static void writeDate(Employee[] employees, PrintWriter out) throws IOException {
        // 写入员工数量
        out.println(employees.length);

        for (Employee employee : employees) {
            writeEmployee(out, employee);
        }
    }


    /**
     * 读取数据到array中
     *
     * @param in
     */
    private static Employee[] readDate(Scanner in) {
        // 获取array的size
        int n = in.nextInt();
        in.nextLine();

        Employee[] employees = new Employee[n];
        for (int i = 0; i < n; i++) {
            employees[i] = readEmployee(in);
        }
        return employees;
    }


    /**
     * 写入Employee
     *
     * @param out
     * @param employee
     */
    private static void writeEmployee(PrintWriter out, Employee employee) {
        out.println(employee.getName() + "|" + employee.getSalary() + "|" + employee.getHireDay());
    }


    /**
     * 从缓存中获取Employee
     *
     * @param in
     * @return
     */
    private static Employee readEmployee(Scanner in) {
        String line = in.nextLine();
        String[] tokens = line.split("\\|");
        String name = tokens[0];
        double salary = Double.parseDouble(tokens[1]);
        LocalDate hireDate = LocalDate.parse(tokens[2]);
        int year = hireDate.getYear();
        int month = hireDate.getMonthValue();
        int day = hireDate.getDayOfMonth();
        return new Employee(name, salary, year, month, day);
    }
}
