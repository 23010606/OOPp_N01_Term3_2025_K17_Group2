package test;

import java.util.Set;
import java.util.TreeSet;
import java.util.Iterator;
import review.Student;

public class TestStudent {
    public static void main(String[] args) {
        Student s1 = new Student("Fred", 3.0F);
        Student s2 = new Student("Sam", 3.5F);
        Student s3 = new Student("Steve", 2.1F);

        Set<Student> studentSet = new TreeSet<>();
        studentSet.add(s1);
        studentSet.add(s2);
        studentSet.add(s3);

        Iterator<Student> i = studentSet.iterator();
        while(i.hasNext()) {
            System.out.println(i.next().getName());
        }
    }
}
