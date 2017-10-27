package ru.mera.sergeynazin.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mera.sergeynazin.model.Order;
import ru.mera.sergeynazin.model.Shaurma;
import ru.mera.sergeynazin.service.OrderService;
import ru.mera.sergeynazin.service.ShaurmaService;

import java.net.URI;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    private ShaurmaService shaurmaService;

    private Order currentOrder;

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void setShaurmaService(ShaurmaService shaurmaService) {
        this.shaurmaService = shaurmaService;
    }


    @GetMapping(value = "/{orderNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOrderInfoByOrderNumberInJSON(@PathVariable("orderNumber") final String orderNumber) {
        return get(orderNumber);
    }

    @GetMapping(value = "/{orderNumber}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> getOrderInfoByOrderNumberInXML(@PathVariable("orderNumber") final String orderNumber) {
        return get(orderNumber);
    }

    /**
     * Just getting the order by
     * @see Order#orderNumber
     * @param orderNumber orderNumber from session
     * @return 200 or 404 (don't know how to send 410)
     */
    private ResponseEntity<?> get(final String orderNumber) {
        return orderService.optionalIsExist(orderNumber)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // TODO: Aspect
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Order>> getAllOrdersInJSON() {
        return ResponseEntity.ok(orderService.getAll());
    }

    // TODO: 10/20/17 XML
    // TODO: Aspect
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Collection<Order>> getAllOrdersInXML() {
        return ResponseEntity.ok(orderService.getAll());
    }

    // TODO: Is that value = "/" we need here?
    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> createNewOrder(@RequestBody final Order order) {
        orderService.save(order);
        return ResponseEntity.created(URI.create("/" + order.getOrderNumber())).body(order);
    }

    // TODO: Produces!!
    // TODO: 10/20/17 Aspect
    @PutMapping(value = "/order/{orderid}/add/{shaurmaid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateOrderInJson(@PathVariable(value = "orderid") Long orderId,
                                               @PathVariable(value = "shaurmaid") Long shaurmaId) {
        return updateOrCreateOrder(orderId, shaurmaId);
    }
    // TODO: 10/20/17 Aspect
    @PutMapping(value = "/order/{orderid}/add/{shaurmaid}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> updateOrderInXML(@PathVariable(value = "orderid") Long orderId,
                                              @PathVariable(value = "shaurmaid") Long shaurmaId) {
        return updateOrCreateOrder(orderId, shaurmaId);
    }

    /**
     * updates or creates the Order with the given ID.
     * @param orderId {@link Order#getOrderNumber()}
     * @param shaurmaId {@link Shaurma#getId()}
     * @return 200 or 201
     */
    //TODO Migrate to session bean instead of new
    private ResponseEntity<?> updateOrCreateOrder(Long orderId, Long shaurmaId) {
        return shaurmaService.optionalIsExist(shaurmaId)
            .map(shaurma -> {
                 final Order newOrder = orderService.optionalIsExist(orderId)
                    .map(order -> {
                        order.getShaurmaSet().add(shaurma);
                        return order;
                    }).orElseGet(() -> {
                         if (currentOrder.getShaurmaSet() != null) {
                             currentOrder.getShaurmaSet().add(shaurma);
                         } else {
                             final Set<Shaurma> shaurmaSet = new HashSet<>(1);
                             shaurmaSet.add(shaurma);
                             currentOrder.setShaurmaSet(shaurmaSet);
                             orderService.save(currentOrder);
                         }
                        return currentOrder;
                    });
                return ResponseEntity.created(URI.create(newOrder.getOrderNumber())).body(newOrder);
            }).orElse(ResponseEntity.notFound().build());
    }


    /**
     * NOT NESSESARY METHOD As the <code>bean</code> is session scooped
     * @param id order id from db
     * @return 200 or 404
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteOrderInJson(@PathVariable("id") final Long id) {
        return delete(id);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> deleteOrderInXML(@PathVariable("id") final Long id) {
        return delete(id);
    }

    // TODO: 10/20/17 Aspect
    private ResponseEntity<?> delete(Long id) {
        return orderService.optionalIsExist(id)
            .map(order -> {
                orderService.delete(order);
                return ResponseEntity.ok(order);
            }).orElse(ResponseEntity.notFound().build());
    }


    /**
     * Helper methods
     * @param id/orderNumber identifier
     */
    // TODO: 10/23/17 WHY IGNORED ??? (...- No Handler ?? )witch to security with (also there is Principal)
    private void checkOrThrowShaurma(final Long id) {
        shaurmaService.optionalIsExist(id)
            .orElseThrow(() -> new NotFoundExeption(String.valueOf(id)));
    }

    // TODO: 10/23/17 WHY IGNORED ??? (...- No Handler ?? )witch to security with (also there is Principal)
    private void checkOrThrowOrderById(final Long id) {
        orderService.optionalIsExist(id)
            .orElseThrow(() -> new NotFoundExeption(String.valueOf(id)));
    }

    // TODO: 10/23/17 WHY IGNORED ??? (...- No Handler ?? )witch to security with (also there is Principal)
    private void checkOrThrowOrderByName(final String orderNumber) {
        orderService.optionalIsExist(orderNumber)
            .orElseThrow(() -> new NotFoundExeption(orderNumber));
    }
}
