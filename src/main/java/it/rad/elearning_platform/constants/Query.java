package it.rad.elearning_platform.constants;

public class Query
{

    private Query(){}

    public static final String INSERT_USER_QUERY =
            "INSERT INTO user(username, user_password) values (?,?)";
    public static final String FIND_USER_BY_USERNAME=
            "SELECT * FROM user WHERE username = ?";
    public static final String UPDATE_USER_WITH_CONTACT_ID=
            "UPDATE user SET contact_id = ? WHERE id = ?";

    public static final String ADD_CONTACT =
            "INSERT INTO contact(first_name, last_name) values (?,?)";
    public static final String ADD_CONTACT_EMAIL =
            "INSERT INTO contact_email(contact_id, email) values (?,?)";
    public static final String SELECT_ALL_CONTACTS =
            "SELECT c.id, c.first_name, c.last_name, " +
                    "GROUP_CONCAT(ce.email SEPARATOR ', ') AS emails " +
                    "FROM contact c " +
                    "LEFT JOIN contact_email ce ON c.id = ce.contact_id " +
                    "GROUP BY c.id, c.first_name, c.last_name";
    public static final String ADD_CONTACT_EMAIL_BY_CONTACT_ID=
            "INSERT INTO contact_email (contact_id, email) values (?,?)";
    public static final String DELETE_CONTACT_EMAIL_BY_CONTACT_ID=
            "DELETE * FROM contact_email WHERE contact_id = ? AND email = ?";

    public static final String INSERT_CUSTOMER =
            "INSERT INTO customer (first_name, last_name, phone_number, vat_number, company) " +
                    "VALUES (?,?,?,?,?)";
    public static final String UPDATE_CUSTOMER=
            "UPDATE customer SET " +
                    "first_name = ?, " +
                    "last_name = ?, " +
                    "phone_number = ?, " +
                    "vat_number = ?, " +
                    "company = ? " +
                    "WHERE id = ?;";
    public static final String INSERT_CUSTOMER_EMAIL =
            "INSERT INTO customer_email(customer_id, email) values (?,?)";
    public static final String ADD_CUSTOMER_EMAIL_BY_CUSTOMER_ID=
            "INSERT INTO customer_email (customer_id, email) values (?,?)";
    public static final String DELETE_CUSTOMER_EMAIL_BY_CUSTOMER_ID=
            "DELETE * FROM customer_email WHERE customer_id = ? AND email = ?";
    public static final String INSERT_CUSTOMER_TO_CONTACT=
            "INSERT INTO customer_to_contact(customer_id, contact_id) values (?,?)";
    public static final String FIND_CUSTOMER_TO_CONTACT_ID=
            "SELECT id FROM customer_to_contact " +
                    "WHERE customer_id = ? " +
                    "AND contact_id = ?;";

    public static final String SELECT_ALL_CUSTOMERS_BY_CONTACT_ID =
            "SELECT c.id, c.first_name, c.last_name, c.phone_number, c.vat_number, c.company, " +
                    "GROUP_CONCAT(ce.email SEPARATOR ', ') AS emails " +
                    "FROM customer c " +
                    "JOIN customer_to_contact cc ON c.id = cc.customer_id " +
                    "JOIN contact ct ON cc.contact_id = ct.id " +
                    "LEFT JOIN customer_email ce ON c.id = ce.customer_id " +
                    "WHERE ct.id = ? " +
                    "GROUP BY c.id, c.first_name, c.last_name, c.phone_number, c.vat_number, c.company;";

    public static final String INSERT_APPOINTMENT =
            "INSERT INTO appointment(customer_to_contact_id, appointment_date, reminder_date, notes)" +
                    "VALUES (?,?,?,?)";
    public static final String UPDATE_APPOINTMENT =
            "UPDATE appointment SET " +
                    "customer_to_contact_id = ?, " +
                    "appointment_date = ?, " +
                    "reminder_date = ?, " +
                    "notes = ? " +
                    "WHERE id = ?;";

    public static final String SELECT_ALL_APPOINTMENT_BY_CUSTOMER_ID =
            "SELECT a.id AS appointment_id, " +
                    "a.customer_to_contact_id, " +
                    "a.appointment_date, " +
                    "a.reminder_date, " +
                    "a.notes " +
                    "FROM appointment a " +
                    "JOIN customer_to_contact cc ON a.customer_to_contact_id = cc.id " +
                    "WHERE cc.customer_id = ?;";
    public static final String DELETE_APPOINTMENT_BY_ID=
            "DELETE FROM appointment WHERE id=?";
    public static final String GET_ALL_APPOINTMENT_DATE_BY_CONTACT_ID =
            "SELECT a.id AS appointment_id, " +
                    "DATE_FORMAT(a.appointment_date, \"%Y-%m-%d\") as appointment_date, " +
                    "DATE_FORMAT(a.reminder_date, \"%Y-%m-%d\") as reminder_date, " +
                    "a.notes AS notes, " +
                    "c.id AS customer_id, " +
                    "c.company " +
                    "FROM appointment a " +
                    "JOIN customer_to_contact cc ON a.customer_to_contact_id = cc.id " +
                    "JOIN customer c ON cc.customer_id = c.id " +
                    "WHERE cc.contact_id = ?;";
    public static final String SAVE_APPOINTMENT_NOTE =
            "UPDATE appointment a " +
                    "SET a.notes = ? " +
                    "WHERE a.id = ?;";

    public static final String GET_ALL_EMAIL_DATA=
            "SELECT " +
                    "a.appointment_date, " +
                    "a.reminder_date, " +
                    "c.first_name AS customer_first_name, " +
                    "c.last_name AS customer_last_name, " +
                    "c.company AS customer_company, " +
                    "ct.first_name AS contact_first_name, " +
                    "ct.last_name AS contact_last_name, " +
                    "GROUP_CONCAT(ce.email SEPARATOR ', ') AS contact_emails " +
                    "FROM appointment a " +
                    "JOIN customer_to_contact cc ON a.customer_to_contact_id = cc.id " +
                    "JOIN customer c ON cc.customer_id = c.id " +
                    "JOIN contact ct ON cc.contact_id = ct.id " +
                    "LEFT JOIN contact_email ce ON ct.id = ce.contact_id " +
                    "WHERE DATE(a.reminder_date) = CURDATE() " +
                    "OR DATE(a.appointment_date) = DATE_ADD(CURDATE(), INTERVAL 1 DAY) " +
                    "GROUP BY " +
                    "a.appointment_date, " +
                    "a.reminder_date, " +
                    "c.first_name, " +
                    "c.last_name, " +
                    "c.company, " +
                    "ct.first_name, " +
                    "ct.last_name;";
}
