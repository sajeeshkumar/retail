package net.sajeesh.retail.order.persistence;

import net.sajeesh.retail.order.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, String> {
}
