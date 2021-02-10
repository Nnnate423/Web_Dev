package nate.payroll.employee;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/")
public class EmployeeController {

    private final EmployeeRepo employeeRepo;
    private final EmployeeModelAssembler employeeModelAssembler;

    @Autowired
    public EmployeeController(EmployeeRepo employeeRepo,EmployeeModelAssembler employeeModelAssembler){
        this.employeeRepo = employeeRepo;
        this.employeeModelAssembler = employeeModelAssembler;
    }

    @GetMapping("/employees")
    CollectionModel<EntityModel<Employee>> all(){
        //List<Employee> emps = employeeRepo.findAll();
        List<EntityModel<Employee>> employees = employeeRepo.findAll()
                .stream().map(employeeModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(employees,linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }

    @PostMapping("/employees")
    ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) {

        EntityModel<Employee> entityModel = employeeModelAssembler.toModel(employeeRepo.save(newEmployee));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @GetMapping("/employees/{id}")
    EntityModel<Employee> one(@PathVariable Long id){
        Employee employee = employeeRepo.findById(id).orElseThrow(()->new EmployeeNotFoundException(id));
        return employeeModelAssembler.toModel(employee);
    }

    @PutMapping("/employees/{id}")
    Employee replaceEmployee(@RequestBody Employee NewEmployee, @PathVariable Long id){
        return employeeRepo.findById(id)
                .map(employee->{
                    employee.setName(NewEmployee.getName());
                    employee.setRole(NewEmployee.getRole());
                    return employee;
                })
                .orElseGet(()->{
                    NewEmployee.setId(id);
                    return employeeRepo.save(NewEmployee);
                });
    }

    @DeleteMapping("/employees/{id}")
    ResponseEntity<?> deleteEmployee(@PathVariable Long id) {

        employeeRepo.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
