package comdischem.dischem;

import comdischem.dischem.controller.CustomerController;
import comdischem.dischem.dto.CustomerDTO;
import comdischem.dischem.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerUTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    /*This method should have pagination*/
    @Test
    @DisplayName("Should return all customers")
    void shouldReturnAllCustomers() throws Exception {
        //These customers should have a status otherwise we will retrieve the whole database..-for what reason and why?
        given(customerService.retrieveAllCustomers()).willReturn(Arrays.asList(customers()));
       mockMvc.perform(get("/v1/api/stores/all-customers"))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("firstName").isString())
               .andExpect(jsonPath("firstName").value("joe"))
               .andExpect(jsonPath("lastName").value("Green"))
               .andExpect(jsonPath("lastName").isString())
               .andExpect(jsonPath("email").isString())
               .andExpect(jsonPath("email").value("joe.green@kudzidza.com"));
        verify(customerService,times(1)).retrieveAllCustomers();
    }
/*
  A lot of discussion has been going around on what to return when the records does not exist
  some suggested 404 but this might be a vague to the use..its up to you and your business use
  case ..
  I would suggest returning a 200 with a status and a message explaining what happened to the use
  This implies that you need to change how you return all customers to the use
  i woould suggest returning an object like this
  customersResponse{
    status: success,
    message: available customers,
    customers:[
      {},
      {}
    ]
  }

  Front end users will have to check status and message before mapping the received customers object, don't forget
  Http Responses
 */
    @Test
    @DisplayName("Return 404 when customer does not exist")
    void shouldReturn404_whenCustomers_doNotExists() {
        given(customerService.retrieveAllCustomers()).willReturn(Collections.emptyList());
        mockMvc.perform(get("/v1/api/stores/all-customers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").isString())
                .andExpect(jsonPath("message").value("customer does not exists"));
        verify(customerService,times(1)).retrieveAllCustomers();
    }


    private List<CustomerDTO> customers(){
        //see how the record is forcing us to include all the fields
        //i guess some of the fields should be optional
        var customerDto =  new CustomerDTO("joe","green","joe.green@kudzidza.com");
        return Arrays.asList(customerDto);
    }
}
