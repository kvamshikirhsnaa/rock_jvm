package java_new;

import java.util.Map.Entry;
import java.util.Set;

public class ClientTest {
    public static void main(String[] args) {
        Employee e1 = new Employee(1001, "Saikrishna", 1000.00, "sai@gmail.com");
        Employee e2 = new Employee(1002, "Aravind", 900.00, "arvnd@gmail.com");
        Employee e3 = new Employee(1003, "Prakash", 600.00, "prakash@gmail.com");
        Employee e4 = new Employee(1004, "Narahari", 700.00, "narahari@gmail.com");
        Employee e5 = new Employee(1005, "Vamshikrishna", 950.00, "vk@gmail.com");

        Department d1 = new Department(11, "IT", "Bangalore");
        Department d2 = new Department(12, "Finance", "Hyderabad");

        LRUCache<Employee, Department> cache = LRUCache.newInstance(2);

        cache.put(e1,d1);
        cache.put(e2,d1);
        cache.put(e3,d2);
        cache.put(e4,d2);
        cache.put(e5,d1);

        Set<Entry<Employee, Department>> entrySet = cache.entrySet();
        for (Entry<Employee, Department> entry : entrySet) {

            Employee employee = entry.getKey();
            Department department = entry.getValue();
            System.out.println(employee);
            System.out.println(department);
        }

    }
}
