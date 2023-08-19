package comdischem.dischem.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

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
