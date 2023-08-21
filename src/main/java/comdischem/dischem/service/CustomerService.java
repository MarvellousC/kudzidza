package comdischem.dischem.service;

import comdischem.dischem.utils.dto.CustomerDTO;
import comdischem.dischem.entity.Customer;
import comdischem.dischem.repository.CustomerRepository;
import comdischem.dischem.repository.OrderRepository;
import comdischem.dischem.utils.request.CustomerRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record CustomerService(CustomerRepository customerRepository,OrderRepository orderRepository) {

    //Split orders and Customers in different services

    public Optional<CustomerDTO> getCustomerDTOResponseEntity(Integer orderId) {
        /*  Consider defining custome exception  so that you can handle them in Controller Advice and give actual
         response
          Do we really need to throw an exception when record is not found??
         */
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

    /*you need to check if customer exists firts before creating a record
    * */
    public Customer createCustomer(CustomerRequest customerRequest) {
        /*
        check comparison between save and saveAndFlush just to see if this is the real method you need
         */
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
