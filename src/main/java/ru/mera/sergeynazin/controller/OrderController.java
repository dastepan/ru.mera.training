package ru.mera.sergeynazin.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.mera.sergeynazin.controller.advice.Admin;
import ru.mera.sergeynazin.controller.advice.NotFoundException;
import ru.mera.sergeynazin.model.Order;
import ru.mera.sergeynazin.model.Shaurma;
import ru.mera.sergeynazin.service.IngredientService;
import ru.mera.sergeynazin.service.OrderService;
import ru.mera.sergeynazin.service.ShaurmaService;

import java.net.URI;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@EnableAsync
@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    private ShaurmaService shaurmaService;

    private IngredientService ingredientService;

    private Order currentOrder;

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }
    public void setIngredientService(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
    public void setShaurmaService(ShaurmaService shaurmaService) {
        this.shaurmaService = shaurmaService;
    }
    // BEGIN_INCLUDE(IngredientController.@Admin)
    @Admin
    @Async
    @GetMapping(value = "/{order_number}", produces = { MediaType.APPLICATION_JSON_VALUE } )
    public CompletableFuture<ResponseEntity<?>> getOrderInfoByOrderNumberInJSON(@PathVariable("order_number") final String orderNumber) {
        return CompletableFuture.completedFuture(get(orderNumber));
    }

    @Admin
    @Async
    @GetMapping(value = "/{order_number}", produces = { MediaType.APPLICATION_XML_VALUE } )
    public CompletableFuture<ResponseEntity<?>> getOrderInfoByOrderNumberInXML(@PathVariable("order_number") final String orderNumber) {
        return CompletableFuture.completedFuture(get(orderNumber));
    }

    /**
     * Just getting the order by
     * @see Order#orderNumber
     * @param orderNumber orderNumber from session
     * @return 200 or 404 (don't know how to send 410)
     */
    private ResponseEntity<?> get(final String orderNumber) throws NotFoundException {
        return orderService.optionalIsExist(orderNumber)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> NotFoundException.throwNew(orderNumber));
    }

    @Admin
    @Async
    @GetMapping(value = "/all", produces = { MediaType.APPLICATION_JSON_VALUE } )
    public CompletableFuture<ResponseEntity<Collection<Order>>> getAllOrdersInJSON() {
        return CompletableFuture.completedFuture(ResponseEntity.ok(orderService.getAll()));
    }

    @Admin
    @Async
    @GetMapping(value = "/all", produces = { MediaType.APPLICATION_XML_VALUE } )
    public CompletableFuture<ResponseEntity<Collection<Order>>> getAllOrdersInXML() {
        return CompletableFuture.completedFuture(ResponseEntity.ok(orderService.getAll()));
    }

    @Admin
    @Async
    @DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE } )
    public CompletableFuture<ResponseEntity<?>> deleteOrderInJSON(@PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(delete(id));
    }

    @Admin
    @Async
    @DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE } )
    public CompletableFuture<ResponseEntity<?>> deleteOrderInXML(@PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(delete(id));
    }

    /**
     * NOT NECESSARY METHOD As if the order is not supposed to be deleted???
     * @param id order id from db
     * @return 200 or 404
     */
    private ResponseEntity<?> delete(final Long id) throws NotFoundException {
        return orderService.optionalIsExist(id)
            .map(order -> {
                orderService.delete(order);
                return ResponseEntity.ok(order);
            }).orElseThrow(() -> NotFoundException.throwNew(id));
    }
    // END_INCLUDE(IngredientController.@Admin)


    /**
     * Create empty order
     * @param order body
     * @return 201 with URI
     */
    @Async
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE } )
    public CompletableFuture<ResponseEntity<?>> createNewOrder(@RequestBody final Order order) {
        orderService.save(order);
        final URI created = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{order_number}")
            .buildAndExpand(order.getOrderNumber()).toUri();
        return CompletableFuture.completedFuture(ResponseEntity.created(created).body(order));
    }

    @Async
    @PutMapping(value = "/{order_id}/add/{shaurma_id}", produces = { MediaType.APPLICATION_JSON_VALUE } )
    public CompletableFuture<ResponseEntity<?>> updateOrderInJSON(@PathVariable("order_id") final Long orderId,
                                                                  @PathVariable("shaurma_id") final Long shaurmaId) {
        return CompletableFuture.completedFuture(updateOrCreateOrderFromMenu(orderId, shaurmaId));
    }

    @Async
    @PutMapping(value = "/{order_id}/add/{shaurma_id}", produces = { MediaType.APPLICATION_XML_VALUE } )
    public CompletableFuture<ResponseEntity<?>> updateOrderInXML(@PathVariable("order_id") final Long orderId,
                                                                 @PathVariable("shaurma_id") final Long shaurmaId) {
        return CompletableFuture.completedFuture(updateOrCreateOrderFromMenu(orderId, shaurmaId));
    }

    /**
     * Updates the Order with predefined shaurma from the menu.
     * Method is suitable for adding the Order with predefined shaurma.
     * or for adding MORE to the amount of currently present in the order shaurma
     * @param orderId {@link Order#getOrderNumber()}
     * @param shaurmaId {@link Shaurma#getId()}
     * @return 200 or 201
     */
    private ResponseEntity<?> updateOrCreateOrderFromMenu(final Long orderId, final Long shaurmaId) throws NotFoundException {
        return shaurmaService.optionalIsExist(shaurmaId)
            .map(shaurma -> {
                final Order newOrder = orderService.optionalIsExist(orderId)
                    .map(order -> {
                        order.getShaurmaSet().add(shaurma);
                        return order;
                    }).orElseGet(() -> {
                        // @see controllers.xml -> switched to null value shaurma set
                        currentOrder.getShaurmaSet().add(shaurma);
                        orderService.save(currentOrder);
                        return currentOrder;
                    });
                final URI created = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .replacePath("/{order_number}")
                    .buildAndExpand(newOrder.getOrderNumber()).toUri();
                return ResponseEntity.created(created).body(newOrder);
            }).orElseThrow(() -> NotFoundException.throwNew(shaurmaId));
    }

    @Async
    @PutMapping(value = "/{order_id}/add", produces = { MediaType.APPLICATION_JSON_VALUE } )
    public CompletableFuture<ResponseEntity<?>> updateOrderInJSON(@PathVariable("order_id") final Long orderId,
                                                                  @RequestBody final Shaurma shaurma) {
        return CompletableFuture.completedFuture(updateOrCreateOrderFromConstructor(orderId, shaurma));
    }

    @Async
    @PutMapping(value = "/{order_id}/add", produces = { MediaType.APPLICATION_XML_VALUE } )
    public CompletableFuture<ResponseEntity<?>> updateOrderInXML(@PathVariable("order_id") final Long orderId,
                                                                 @RequestBody final Shaurma shaurma) {
        return CompletableFuture.completedFuture(updateOrCreateOrderFromConstructor(orderId, shaurma));
    }

    /**
     * Method is suitable for adding the Order with custom shaurma,
     * or for adding MORE to the amount of currently present in the order shaurma
     * @param orderId {@link Order#getOrderNumber()}
     * @param shaurma {@link Shaurma} body
     * @return 200 or 201
     */
    private ResponseEntity<?> updateOrCreateOrderFromConstructor(final Long orderId, final Shaurma shaurma) throws NotFoundException {
        return orderService.optionalIsExist(orderId)
            .map(order -> {
                if (shaurma.getIngredientSet()
                    .parallelStream()
                    .allMatch(ingredient -> ingredientService.optionalIsExist(ingredient.getId()).isPresent())) {
                    return ResponseEntity.unprocessableEntity().body(shaurma);
                }
                order.getShaurmaSet().add(shaurma);
                return ResponseEntity.ok(order);
            }).orElseGet(() -> {
                // @see controllers.xml -> switched to null value shaurma set
                currentOrder.getShaurmaSet().add(shaurma);
                orderService.save(currentOrder);
                final URI created = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .replacePath("/{order_number}")
                    .buildAndExpand(currentOrder.getOrderNumber()).toUri();
                return ResponseEntity.created(created).body(currentOrder);
            });
    }

    /**
     * Helper methods
     * @param id/orderNumber identifier
     */
    private void checkOrThrowShaurma(final Long id) {
            shaurmaService.optionalIsExist(id)
                .orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }

    private void checkOrThrowOrderById(final Long id) {
            orderService.optionalIsExist(id)
                .orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }

    private void checkOrThrowOrderByName(final String orderNumber) {
            orderService.optionalIsExist(orderNumber)
                .orElseThrow(() -> new NotFoundException(orderNumber));
    }
}
