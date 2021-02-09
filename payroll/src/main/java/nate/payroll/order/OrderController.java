package nate.payroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class OrderController {
    private final OrderRepo orderRepo;
    private final OrderModelAssembler orderModelAssembler;

    @Autowired
    public OrderController(OrderRepo orderRepo, OrderModelAssembler orderModelAssembler) {
        this.orderRepo = orderRepo;
        this.orderModelAssembler = orderModelAssembler;
    }

    @GetMapping("/orders")
    CollectionModel<EntityModel<Order>> all() {

        List<EntityModel<Order>> orders = orderRepo.findAll().stream() //
                .map(orderModelAssembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(orders, //
                linkTo(methodOn(OrderController.class).all()).withSelfRel());
    }

    @GetMapping("/orders/{id}")
    EntityModel<Order> one(@PathVariable Long id) {

        Order order = orderRepo.findById(id) //
                .orElseThrow(() -> new OrderNotFoundException(id));

        return orderModelAssembler.toModel(order);
    }

    @PostMapping("/orders")
    ResponseEntity<EntityModel<Order>> newOrder(@RequestBody Order order) {

        order.setStatus(Order.Status.IN_PROGRESS);
        Order newOrder = orderRepo.save(order);

        return ResponseEntity //
                .created(linkTo(methodOn(OrderController.class).one(newOrder.getId())).toUri()) //
                .body(orderModelAssembler.toModel(newOrder));
    }

    @DeleteMapping("/orders/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Long id) {
        Order order = orderRepo.findById(id).orElseThrow(()->new OrderNotFoundException(id));
        if (order.getStatus() == Order.Status.IN_PROGRESS){
            order.setStatus(Order.Status.CANCELLED);
            orderRepo.save(order);
            return ResponseEntity.ok(orderModelAssembler.toModel(order));
        }
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create() //
                .withTitle("Method not allowed") //
                .withDetail("You can't cancel an order that is in the " + order.getStatus() + " status"));
    }

    @PutMapping("/orders/{id}/complete")
    ResponseEntity<?> complete(@PathVariable Long id) {

        Order order = orderRepo.findById(id) //
                .orElseThrow(() -> new OrderNotFoundException(id));

        if (order.getStatus() == Order.Status.IN_PROGRESS) {
            order.setStatus(Order.Status.COMPLETED);
            return ResponseEntity.ok(orderModelAssembler.toModel(orderRepo.save(order)));
        }

        return ResponseEntity //
                .status(HttpStatus.METHOD_NOT_ALLOWED) //
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                .body(Problem.create() //
                        .withTitle("Method not allowed") //
                        .withDetail("You can't complete an order that is in the " + order.getStatus() + " status"));
    }
}
