package org.baibei.keyboardtrackermain.repositories;

import org.baibei.keyboardtrackermain.pojo.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<User, String> {

    User findByUsername(String username);

    void deleteByUsername(String username);
}
