package it.rad.elearning_platform.service;

import it.rad.elearning_platform.model.EmailData;
import it.rad.elearning_platform.repository.EmailRepo;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import java.time.LocalDate;
import java.util.List;
import java.util.Arrays;

import static it.rad.elearning_platform.constants.Query.*;

public class EmailService implements EmailRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void checkAndSendReminderEmails() {
        List<EmailData> appointments = getAllEmailData();

        for (EmailData appointment : appointments) {
            sendEmail(appointment);
        }
    }

    private void sendEmail(EmailData appointment) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            List<String> contactEmail = appointment.getContactEmail();

            if (appointment.getReminderDate().equals(LocalDate.now())) {
                helper.setSubject("🔔 Reminder: Appuntamento con " + appointment.getCustomerFullName());
                helper.setText("Oggi è il giorno del promemoria per il tuo appuntamento con " +
                        appointment.getCustomerFullName() + " dell'Azienda " + appointment.getCustomerCompany() + " il " +
                        appointment.getAppointmentDate());
            }
            else if (appointment.getAppointmentDate().equals(LocalDate.now().plusDays(1))) {
                helper.setSubject("⏳ Domani hai un appuntamento con " + appointment.getCustomerFullName());
                helper.setText("Un promemoria che domani hai un appuntamento con " +
                        appointment.getCustomerFullName() + " dell'Azienda " + appointment.getCustomerCompany() + " il " +
                        appointment.getAppointmentDate());
            }

            for(String emails : contactEmail)
            {
                helper.setTo(emails);
                mailSender.send(message);
            }
            System.out.println("Email inviata");
        } catch (Exception e) {
            System.err.println("Errore nell'invio dell'email: " + e.getMessage());
        }
    }

    @Override
    public List<EmailData> getAllEmailData() {

        return jdbcTemplate.query(GET_ALL_EMAIL_DATA, (rs, rowNum) -> new EmailData(
                rs.getDate("appointment_date").toLocalDate(),
                rs.getDate("reminder_date").toLocalDate(),
                rs.getString("customer_first_name") + " " +
                        rs.getString("customer_last_name"),
                rs.getString("customer_company"),
                rs.getString("contact_first_name") + " " +
                        rs.getString("contact_last_name"),
                Arrays.asList(rs.getString("emails").split(", "))
        ));
    }
}
