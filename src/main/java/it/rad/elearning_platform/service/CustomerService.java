package it.rad.elearning_platform.service;

import it.rad.elearning_platform.model.Contact;
import it.rad.elearning_platform.model.Customer;
import it.rad.elearning_platform.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CustomerService implements CustomerRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    private final KeyHolder id = new GeneratedKeyHolder();

    private static final String INSERT_CUSTOMER_QUERY=
            "INSERT INTO customer (first_name, last_name, phone_number, vat_number, company) " +
                    "VALUES (?,?,?,?,?)";
    private static final String INSERT_CUSTOMER_EMAIL_QUERY=
            "INSERT INTO customer_email(customer_id, email) values (?,?)";

    private static final String SELECT_ALL_CUSTOMERS=
            "SELECT c.id, c.first_name, c.last_name, c.phone_number, c.vat_number, c.company" +
                    "GROUP_CONCAT(ce.email SEPARATOR ', ') AS emails " +
                    "FROM customer c " +
                    "LEFT JOIN customer_email ce ON c.id = ce.customer_id " +
                    "GROUP BY c.id, c.first_name, c.last_name, c.phone_number, c.vat_number, c.company";

    @Override
    public Customer saveCustomer(Customer customer) {
        jdbcTemplate.update(INSERT_CUSTOMER_QUERY,
                customer.getFirstName(), customer.getLastName(), customer.getPhoneNumber(),
                customer.getVatNumber(), customer.getCompany());
        customer.setId(Objects.requireNonNull(id.getKey()).intValue());

        jdbcTemplate.batchUpdate(INSERT_CUSTOMER_EMAIL_QUERY,
                customer.getEmails(), customer.getEmails().size(), (ps, email) -> {
                    ps.setInt(1, customer.getId());
                    ps.setString(2, email);

                });
        return customer;
    }

    @Override
    public List<Customer> getAllCustomer() {
        return jdbcTemplate.query(SELECT_ALL_CUSTOMERS, (rs, rowNum) -> new Customer(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("phone_number"),
                rs.getString("vat_number"),
                rs.getString("company"),
                Arrays.asList(rs.getString("emails").split(", "))
        ));
    }
}
