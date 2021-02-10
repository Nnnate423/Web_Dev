package nate.payroll;

import nate.payroll.employee.Employee;
import nate.payroll.employee.EmployeeRepo;
import nate.payroll.order.Order;
import nate.payroll.order.OrderRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Autowired
    @Bean
    CommandLineRunner initDatabase(EmployeeRepo employeeRepo, OrderRepo orderRepo){
        return args -> {
            employeeRepo.save(new Employee("Bilbo", "Baggins", "burglar"));
            employeeRepo.save(new Employee("Frodo", "Baggins", "thief"));

            employeeRepo.findAll().forEach(employee -> log.info("Preloaded " + employee));


            orderRepo.save(new Order("MacBook Pro", Order.Status.COMPLETED));
            orderRepo.save(new Order("iPhone", Order.Status.IN_PROGRESS));

            orderRepo.findAll().forEach(order -> {
                log.info("Preloaded " + order);
            });
        };
    };
}
