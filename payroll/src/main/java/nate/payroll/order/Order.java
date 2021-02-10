package nate.payroll.order;




import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Data
@Entity
@Table(name = "CUSTOMER_ORDER")
public class Order {
    private @GeneratedValue @Id Long id;
    private String description;
    private Status status;

    Order(){}

    public Order(String description, Status status) {
        this.description = description;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id.equals(order.id) &&
                description.equals(order.description) &&
                status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, status);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

    enum Status {
        IN_PROGRESS,
        COMPLETED,
        CANCELLED
    }
}
