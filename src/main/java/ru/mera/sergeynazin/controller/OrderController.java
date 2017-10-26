package ru.mera.sergeynazin.controller;

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
import java.util.function.Function;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    private ShaurmaService shaurmaService;

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void setShaurmaService(ShaurmaService shaurmaService) {
        this.shaurmaService = shaurmaService;
    }

    /**
     * Just getting the order by
     * @see Order#orderNumber
     * @param orderNumber orderNumber from session
     * @return 200 or 404 (don't know how to send 410)
     */
    @GetMapping(value = "/{orderNumber}", produces = "application/json")
    public ResponseEntity<?> getOrderInfoByOrderNumberInJSON(@PathVariable("orderNumber") final String orderNumber) {
        return getOrderInfoByOrderNumber(orderNumber);
    }

    @GetMapping(value = "/{orderNumber}", produces = "application/xml")
    public ResponseEntity<?> getOrderInfoByOrderNumberInXML(@PathVariable("orderNumber") final String orderNumber) {
        return getOrderInfoByOrderNumber(orderNumber);
    }

    private ResponseEntity<?> getOrderInfoByOrderNumber(final String orderNumber) {
        checkOrThrowOrderByName(orderNumber);
        return orderService.optionalIsExist(orderNumber)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // TODO: Do I need value = "/" ???
    // TODO: Aspect
    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<Collection<Order>> getAllOrdersInJSON() {
        return ResponseEntity.ok(orderService.getAll());
    }

    // TODO: 10/20/17 XML
    // TODO: Do I need value = "/" ???
    // TODO: Aspect
    @GetMapping(value = "/all", produces = "application/xml")
    public ResponseEntity<Collection<Order>> getAllOrdersInXML() {
        return ResponseEntity.ok(orderService.getAll());
    }

    // TODO: Is that value = "/" we need here?
    // TODO: 10/20/17 Aspect
    @PostMapping(value = "/", consumes = {"application/json" , "application/xml"})
    public ResponseEntity<?> add(@RequestBody final Order order) {
        orderService.save(order);
        return ResponseEntity.ok(order);
    }

    // TODO: Produces!!
    // TODO: 10/20/17 Aspect
    @PutMapping(value = "/order/{orderid}/add/{shaurmaid}", produces = "application/json")
    public ResponseEntity<?> updateOrderInJson(@PathVariable(value = "orderid") Long orderId,
                                               @PathVariable(value = "shaurmaid") Long shaurmaId) {
        return updateOrCreateOrder(orderId, shaurmaId);
    }
    // TODO: 10/20/17 Aspect
    @PutMapping(value = "/order/{orderid}/add/{shaurmaid}", produces = "application/xml")
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
    private ResponseEntity<?> updateOrCreateOrder(Long orderId, Long shaurmaId) {
        checkOrThrowOrderById(orderId);
        return orderService.optionalIsExist(orderId)
            .map((Function<Order, ResponseEntity<?>>) order -> {
                order.getShaurmaSet().add(shaurmaService.loadAsPersistent(shaurmaId));
                return ResponseEntity.ok(order);
            }).orElseGet(() -> {
                //if no such Order then invoke new one
                final Order order = new Order();
                checkOrThrowShaurma(shaurmaId);
                shaurmaService.optionalIsExist(shaurmaId)
                    .ifPresent(shaurma -> {
                        // if such shaurma present then add it to newly invoked Order
                        Set<Shaurma> shaurmaSet = new HashSet<>();
                        shaurmaSet.add(shaurma);
                        order.setShaurmaSet(shaurmaSet);
                    });
                orderService.save(order);
                return ResponseEntity.created(URI.create(order.getOrderNumber())).body(order);
            });
    }


    /**
     * NOT NESSESARY METHOD As the <code>bean</code> is session scooped
     * @param id order id from db
     * @return 200 or 404
     */
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> deleteOrderInJson(@PathVariable("id") final Long id) {
        return delete(id);
    }

    @DeleteMapping(value = "/{id}", produces = "application/xml")
    public ResponseEntity<?> deleteOrderInXML(@PathVariable("id") final Long id) {
        return delete(id);
    }

    // TODO: 10/20/17 Aspect
    private ResponseEntity<?> delete(Long id) {

        checkOrThrowOrderById(id);

        return orderService.optionalIsExist(id)
            .map(order -> {
                orderService.tryDelete(id);
                return ResponseEntity.ok(order);
            }).orElse(ResponseEntity.notFound().build());
    }



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
