package comdischem.dischem.service;

import comdischem.dischem.dto.CustomerDTO;
import comdischem.dischem.entity.Customer;
import comdischem.dischem.repository.CustomerRepository;
import comdischem.dischem.repository.OrderRepository;
import comdischem.dischem.request.CustomerRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public CustomerService(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    public Optional<CustomerDTO> getCustomerDTOResponseEntity(Integer orderId) {
        return Optional.of(orderRepository.findById(orderId)
                .map(order -> {
                    Customer customer = order.getCustomer();
                    return CustomerDTO.builder()
                            .firstName(customer.getFirstName())
                            .lastName(customer.getLastName())
                            .address(customer.getAddress())
                            .phone(customer.getPhone())
                            .email(customer.getEmail())
                            .orderDate(order.getOrderDate())
                            .totalAmount(order.getTotalAmount())
                            .orderId(order.getId())
                            .build();
                })
                .orElseThrow(IllegalStateException::new));
    }

    public Customer createCustomer(CustomerRequest customerRequest) {
        Customer savedCustomer = Customer.builder()
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .email(customerRequest.getEmail())
                .address(customerRequest.getAddress())
                .phone(customerRequest.getPhone())
                .build();
        return customerRepository.saveAndFlush(savedCustomer);
    }
}
