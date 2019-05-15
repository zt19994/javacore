package io;

import model.Employee;
import model.Manager;

import java.io.*;

/**
 * 对象流
 *
 * @author zt1994 2019/5/15 22:13
 */
public class ObjectStreamTest {

    /**
     * 主方法
     *
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Employee harry = new Employee("Harry hacker", 5000, 1989, 10, 1);
        Manager carl = new Manager("Carl Cracker", 80000, 1987, 12, 15);
        carl.setSecretary(harry);
        Manager tony = new Manager("Tony Tester", 40000, 1990, 3, 15);
        tony.setSecretary(harry);

        Employee[] staff = new Employee[3];

        staff[0] = carl;
        staff[1] = harry;
        staff[2] = tony;

        //保存所有雇员信息到employee1.dat
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("employee1.dat"))) {
            out.writeObject(staff);
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("employee1.dat"))) {
            //获取所有数据存入Array中
            Employee[] newStaff = (Employee[]) in.readObject();

            //涨秘书的工资
            newStaff[1].raiseSalary(10);

            for (Employee employee : newStaff) {
                System.out.println(employee);
            }
        }
    }
}
