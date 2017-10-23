package ru.mera.sergeynazin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mera.sergeynazin.model.Order;
import ru.mera.sergeynazin.model.Shaurma;
import ru.mera.sergeynazin.service.OrderService;
import ru.mera.sergeynazin.service.ShaurmaService;

import java.util.HashSet;
import java.util.List;
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

    @RequestMapping(value = "/{orderNumber}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<?> getOrderInfoByIdInJSON(@PathVariable("orderNumber") final String orderNumber) {
        return getOrderInfo(orderNumber);
    }

    // TODO: 10/20/17 XML
    @RequestMapping(value = "/{orderNumber}", method = RequestMethod.GET, headers = "Accept=application/xml")
    public ResponseEntity<?> getOrderInfoByIdInXML(@PathVariable("orderNumber") final String orderNumber) {
        return getOrderInfo(orderNumber);
    }

    private ResponseEntity<?> getOrderInfo(final String orderNumber) {
        checkOrThrowOrderByName(orderNumber);
        return orderService.optionalIsExist(orderNumber)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // TODO: Do I need value = "/" ???
    // TODO: Aspect
    @RequestMapping(value = "/all", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Order> getAllOrdersInJSON() {
        return orderService.getAll();
    }

    // TODO: 10/20/17 XML
    // TODO: Do I need value = "/" ???
    // TODO: Aspect
    @RequestMapping(value = "/all", method = RequestMethod.GET, headers = "Accept=application/xml")
    public List<Order> getAllOrdersInXML() {
        return orderService.getAll();
    }

    // TODO: Is that value = "/" we need here?
    // TODO: 10/20/17 Aspect
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody final Order order) {
        orderService.save(order);
        return ResponseEntity.ok(order);
    }

    // TODO: 10/20/17 Aspect
    @RequestMapping(value = "/order/{orderid}/add/{shaurmaid}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<?> updateOrderInJson(@PathVariable(value = "orderid") Long orderId,
                                               @PathVariable(value = "shaurmaid") Long shaurmaId) {
        return updateOrder(orderId, shaurmaId);
    }
    // TODO: 10/20/17 Aspect
    @RequestMapping(value = "/order/{orderid}/add/{shaurmaid}", method = RequestMethod.PUT, headers = "Accept=application/xml")
    public ResponseEntity<?> updateOrderInXML(@PathVariable(value = "orderid") Long orderId,
                                              @PathVariable(value = "shaurmaid") Long shaurmaId) {
        return updateOrder(orderId, shaurmaId);
    }

    private ResponseEntity<?> updateOrder(Long orderId, Long shaurmaId) {
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
                return ResponseEntity.ok(order);
            });
    }

    // TODO: 10/20/17 Aspect
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE, headers = "Accept=application/json")
    public void deleteOrderInJson(@PathVariable("id") final Long id) {
        orderService.tryDelete(id);
    }
    // TODO: 10/20/17 Aspect
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE, headers = "Accept=application/xml")
    public void deleteOrderInXML(@PathVariable("id") final Long id) {
        orderService.tryDelete(id);
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
