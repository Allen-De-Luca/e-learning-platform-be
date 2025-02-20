package it.rad.elearning_platform.repository;

import it.rad.elearning_platform.model.EmailData;

import java.util.List;

public interface EmailRepo {

    List<EmailData> getAllEmailData();
}
