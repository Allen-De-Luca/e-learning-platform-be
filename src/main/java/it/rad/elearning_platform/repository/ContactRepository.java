package it.rad.elearning_platform.repository;

import it.rad.elearning_platform.model.Contact;
import java.util.List;

public interface ContactRepository {

    int saveContact(Contact contact);

    List<Contact> getAllContacts();
}