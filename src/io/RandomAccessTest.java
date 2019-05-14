package io;

import model.Employee;

import java.io.*;
import java.time.LocalDate;

/**
 * 随机访问测试
 *
 * @author zt1994 2019/5/14 21:52
 */
public class RandomAccessTest {

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

        try (DataOutputStream out = new DataOutputStream(new FileOutputStream("employee.dat"))) {
            for (Employee employee : staff) {
                writeData(out, employee);
            }
        }

        try (RandomAccessFile in = new RandomAccessFile("employee.dat", "r")) {
            int n = (int) (in.length() / Employee.RECORD_SIZE);
            Employee[] newStaff = new Employee[n];

            for (int i = n - 1; i >= 0; i--) {
                newStaff[i] = new Employee();
                in.seek(i * Employee.RECORD_SIZE);
                newStaff[i] = readData(in);
            }

            for (Employee employee : newStaff) {
                System.out.println(employee);
            }
        }
    }


    /**
     * 将employee数据写入data
     *
     * @param out
     * @param employee
     * @throws IOException
     */
    private static void writeData(DataOutput out, Employee employee) throws IOException {
        writeFixedString(employee.getName(), Employee.NAME_SIZE, out);
        out.writeDouble(employee.getSalary());

        LocalDate hireDay = employee.getHireDay();
        out.writeInt(hireDay.getYear());
        out.writeInt(hireDay.getMonthValue());
        out.writeInt(hireDay.getDayOfMonth());
    }


    /**
     * 读取数据
     *
     * @param in
     * @return
     * @throws IOException
     */
    private static Employee readData(DataInput in) throws IOException {
        String name = readFixedString(Employee.NAME_SIZE, in);
        double salary = in.readDouble();
        int year = in.readInt();
        int month = in.readInt();
        int day = in.readInt();
        return new Employee(name, salary, year, month - 1, day);
    }


    /**
     * 读取复杂的String
     *
     * @param size
     * @param in
     * @return
     * @throws IOException
     */
    public static String readFixedString(int size, DataInput in) throws IOException {
        StringBuilder b = new StringBuilder(size);
        int i = 0;
        boolean done = false;
        while (!done && i < size) {
            char ch = in.readChar();
            i++;
            if (ch == 0) done = true;
            else b.append(ch);
        }
        in.skipBytes(2 * (size - i));
        return b.toString();
    }

    /**
     * 写入复杂的String
     *
     * @param s
     * @param size
     * @param out
     * @throws IOException
     */
    public static void writeFixedString(String s, int size, DataOutput out) throws IOException {
        for (int i = 0; i < size; i++) {
            char ch = 0;
            if (i < s.length()) ch = s.charAt(i);
            out.writeChar(ch);
        }
    }
}
