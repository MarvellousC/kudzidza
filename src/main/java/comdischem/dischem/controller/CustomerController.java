package comdischem.dischem.controller;


import comdischem.dischem.utils.dto.CustomerDTO;
import comdischem.dischem.entity.Customer;
import comdischem.dischem.repository.CustomerRepository;
import comdischem.dischem.repository.OrderRepository;
import comdischem.dischem.utils.request.CustomerRequest;
import comdischem.dischem.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/api/stores")
public class CustomerController {

    private final CustomerService customerService;

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public CustomerController(CustomerService customerService, CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/all-customers")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping(value = "/customer/{orderId}")
    public Optional<CustomerDTO> getCustomerByOrderId(@PathVariable Integer orderId) {
        return customerService.getCustomerDTOResponseEntity(orderId);
    }

    @PostMapping("/customer/create")
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerRequest request) {
        Customer createdCustomer = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);

    }

}
