package io;

import java.io.*;
import java.time.LocalDate;

/**
 * 序列化克隆
 *
 * @author zt1994 2019/5/16 21:45
 */
public class SerialCloneTest {

    /**
     * 主方法
     *
     * @param args
     */
    public static void main(String[] args) throws CloneNotSupportedException {
        Employee harry = new Employee("Harry Hacker", 35000, 1989, 10, 5);

        Employee harry2 = (Employee) harry.clone();

        harry.raiseSalary(10);

        System.out.println(harry);
        System.out.println(harry2);
    }


    /**
     * 一个克隆方法为序列化的类
     */
    static class SerialCloneable implements Cloneable, Serializable {
        public Object clone() throws CloneNotSupportedException {
            try {
                //保存对象到byte array中
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                try (ObjectOutputStream out = new ObjectOutputStream(bout)) {
                    out.writeObject(this);
                }

                //从byte array中读取对象
                try (ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray())) {
                    ObjectInputStream in = new ObjectInputStream(bin);
                    return in.readObject();
                }
            } catch (IOException | ClassNotFoundException e) {
                CloneNotSupportedException e2 = new CloneNotSupportedException();
                e2.initCause(e);
                throw e2;
            }
        }
    }


    static class Employee extends SerialCloneable {
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
}
