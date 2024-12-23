package org.baibei.keyboardtrackerdesktop.services;

import org.springframework.stereotype.Service;

@Service
public class CommandsInfoService {

    public String getInfo() {
        return """
        Commands:
         commands -> info about all commands

         repodir:
          info / ? -> path to file with all info about user

         properties:
          info / ? -> all properties of app
          path -> path to property file
          update + property_name -> change property value

         sync -> gets user info from server, unsaved data will be lost

         send -> updates info at the server

         user:
          info / ? -> all user info
          change username / password -> changes username / password
          update addkey / addword -> adds key / word from console
          reg / register -> register new user / also can log in
          del / delete -> clears a repo file
        """;
    }
}
