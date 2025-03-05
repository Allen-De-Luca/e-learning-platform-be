package it.rad.elearning_platform.service;

import it.rad.elearning_platform.model.EmailData;
import it.rad.elearning_platform.repository.EmailRepo;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Arrays;

import static it.rad.elearning_platform.constants.Query.*;

@Service
public class EmailService implements EmailRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Scheduled(cron = "0 0 8 * * ?")
    public void checkAndSendReminderEmails() {
        List<EmailData> appointments =  getAllEmailData();

        for (EmailData appointment : appointments) {
            sendEmail(appointment);
        }
    }

    private void sendEmail(EmailData appointment) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            List<String> contactEmail = appointment.getContactEmail();

            String subject = "";
            String htmlContent = "";

            if (appointment.getAppointmentDate().toLocalDate().equals(LocalDate.now().plusDays(1))) {
                subject = "⏳ Domani hai un appuntamento con " + appointment.getCustomerFullName();
                htmlContent = "<html><body>"
                        + "<h2>Promemoria appuntamento</h2>"
                        + "<p>Un promemoria che domani hai un appuntamento con <strong>"
                        + appointment.getCustomerFullName() + "</strong> dell'azienda <strong>"
                        + appointment.getCustomerCompany() + "</strong> il <strong>"
                        + appointment.getAppointmentDate().toLocalDate() + "</strong> alle ore <strong>"
                        + appointment.getAppointmentDate().toLocalTime() + "</strong></p>"
                        + "</body></html>";
            } else if (appointment.getReminderDate().equals(LocalDate.now())) {
                subject = "🔔 Reminder: Appuntamento con " + appointment.getCustomerFullName();
                htmlContent = "<html><body>"
                        + "<h2>Reminder Appuntamento</h2>"
                        + "<p>Oggi è il giorno del promemoria per il tuo appuntamento con <strong>"
                        + appointment.getCustomerFullName() + "</strong> dell'azienda <strong>"
                        + appointment.getCustomerCompany() + "</strong> il <strong>"
                        + appointment.getAppointmentDate().toLocalDate() + "</strong> alle ore <strong>"
                        + appointment.getAppointmentDate().toLocalTime() + "</strong></p>"
                        + "</body></html>";
            }

            helper.setSubject(subject);
            helper.setText(htmlContent, true); // Il secondo parametro true indica che è HTML

            for (String email : contactEmail) {
                helper.setTo(email);
                mailSender.send(message);
            }

            System.out.println("Email inviata con successo!");
        } catch (Exception e) {
            System.err.println("Errore nell'invio dell'email: " + e.getMessage());
        }
    }


    @Override
    public List<EmailData> getAllEmailData() {

        return jdbcTemplate.query(GET_ALL_EMAIL_DATA, (rs, rowNum) -> new EmailData(
                rs.getTimestamp("appointment_date").toLocalDateTime(),
                rs.getDate("reminder_date").toLocalDate(),
                rs.getString("customer_first_name") + " " +
                        rs.getString("customer_last_name"),
                rs.getString("customer_company"),
                rs.getString("contact_first_name") + " " +
                        rs.getString("contact_last_name"),
//                Arrays.asList(rs.getString("contact_emails").split(", "))
                Arrays.asList(rs.getString("contact_emails"))
        ));
    }
}
