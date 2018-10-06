package hello;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {

    //For lack of a @Table annotation, it is assumed that this entity will be mapped to a table named Customer.

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String firstName;                       //they’ll be mapped to columns that share the same name as the properties themselves.
    private String lastName;

    //todo: Frage: wozu ist dieser Konstruktor
    protected Customer() {}

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }


}
