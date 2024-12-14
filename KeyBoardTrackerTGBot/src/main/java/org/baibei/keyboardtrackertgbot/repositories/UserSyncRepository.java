package org.baibei.keyboardtrackertgbot.repositories;

import org.baibei.keyboardtrackertgbot.pojo.user.UserSync;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSyncRepository extends MongoRepository<UserSync, String> {

    public UserSync findByUsername(String userName);

    public UserSync findByTgId(String tgId);

    void deleteByTgId(String tgId);
}
