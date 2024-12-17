package org.baibei.keyboardtrackerdesktop.services;

import org.baibei.keyboardtrackerdesktop.repositories.PropertiesRepository;
import org.springframework.stereotype.Service;

@Service
public class AppProperties {

    private final PropertiesRepository propertiesRepository;

    private Long timeToSend;
    private Boolean printUpload;

    public AppProperties(PropertiesRepository propertiesRepository) {
        this.propertiesRepository = propertiesRepository;
    }

    public void update() {
        timeToSend = propertiesRepository.getTimeToSend();
        if (timeToSend == null) {
            timeToSend = 10000L;
        }

        printUpload = propertiesRepository.getPrintUpdate();
        if (printUpload == null) {
            printUpload = true;
        }
    }

    public Long getTimeToSend() {
        return timeToSend;
    }

    public String toString() {
        return "time_to_send = " + timeToSend + "\n" +
                "print_upload = " + printUpload;
    }

    public Boolean getPrintUpload() {
        return printUpload;
    }
}
