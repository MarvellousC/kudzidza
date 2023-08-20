package comdischem.dischem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;




@Getter
@Setter
@Entity
@Builder
/* you have conflicting stuff here
 * NoArgsConstructor vs AllArgsConstructor -- Round 1 Fight
 *
 * */
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "address", nullable = false, length = 200)
    private String address;

    @Column(name = "phone", length = 20)
    private String phone;

    /*
    * There you are fighting with the Framework
    * Framework is returning everything and you are saying please ignore..No need this
    * Read about the N+1 Issue on Data Jpa
    *
    *  */
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("customer")
    private List<Order> orders = new ArrayList<>();

}