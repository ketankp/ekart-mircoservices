package com.example.orderservice.service;

import com.example.orderservice.config.WebClientConfig;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.dto.ProductResponse;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public OrderService(OrderRepository orderRepository,WebClient.Builder webClientBuilder) {
        this.orderRepository = orderRepository;
        this.webClientBuilder = webClientBuilder;
    }

    public OrderResponse createOrder(OrderRequest orderRequest){

        ProductResponse productResponse = webClientBuilder.build()
                .get()
                .uri("http://product-service/api/product/" + orderRequest.getProductId())
                .retrieve()
                .bodyToMono(ProductResponse.class).block();

        Order order = Order.builder()
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity())
                .orderPrice(orderRequest.getQuantity() * productResponse.getPrice())
                .build();

        orderRepository.save(order);

        return  OrderResponse.builder()
                .orderId(order.getOrderId())
                .productId(order.getProductId())
                .quantity(order.quantity)
                .orderPrice(order.getOrderPrice())
                .build();
    }
}
