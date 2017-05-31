package skplanet.recopick.demo.mall.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skplanet.recopick.demo.mall.domain.*;
import skplanet.recopick.demo.mall.repository.OrderItemRepository;
import skplanet.recopick.demo.mall.repository.OrderRepository;

import java.util.List;

/**
 * @author homo.efficio@gmail.com
 *         created on 2017-05-23
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class OrderService {

    @NonNull private final CartService cartService;
    @NonNull private final OrderRepository orderRepository;
    @NonNull private final OrderItemRepository orderItemRepository;

    // 신규 주문
    public Order save(Member member, Cart cart) {

        Order order = new Order(member, cart);
        Order persistedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = order.getOrderItems();
        orderItems.forEach((i) -> {
            i.setOrders(persistedOrder);
            i.setOrderItemId(new OrderItemId(persistedOrder.getId(), i.getProduct().getProductCode()));
            orderItemRepository.save(i);
        });

        persistedOrder.setOrderItems(orderItems);

        cartService.emptyCartItemsOf(member);

        return persistedOrder;
    }

    // 수정 주문
    public Order save(Order order) {

        Order persistedOrder = orderRepository.save(order);

        return persistedOrder;
    }

    public Order findOne(Long orderId) {
        return orderRepository.findOne(orderId);
    }
}
