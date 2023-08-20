package comdischem.dischem.request;

import lombok.*;


/*
Round 2 let AllArgsConstructor and NoArgsConstructor fight..one Must Win MK12
 */

/*
Did you test this on postman..did it accept the builder pattern
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;
}
