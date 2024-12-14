package org.baibei.keyboardtrackerdesktop.repositories;

import org.baibei.keyboardtrackerdesktop.pojo.keys.Key;
import org.baibei.keyboardtrackerdesktop.pojo.other.RepositoryPath;
import org.baibei.keyboardtrackerdesktop.pojo.user.User;
import org.baibei.keyboardtrackerdesktop.pojo.user.UserConvertor;
import org.baibei.keyboardtrackerdesktop.pojo.words.Word;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Repository
public class StandardRepository {

    /*
    USER STRUCTURE:
    *   username:name
    *   key:N=amount
    *   word:N=amount
    END
    */

    private String repositoryPath;

    public StandardRepository(RepositoryPath repoPath) {
        this.repositoryPath = repoPath.getPath();
    }

    public void update(Key key) {
        User currentUser = find();
        currentUser.addKey(key.add());
        save(currentUser);
    }

    public void update(Word word) {
        User currentUser = find();
        currentUser.addWord(word.add());
        save(currentUser);
    }

    public void save(User user) {
        try(FileWriter writer = new FileWriter(repositoryPath, false))
        {
            String info = buildInfo(user);
            writer.write(info);
            writer.flush();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void delete() {
        try(FileWriter writer = new FileWriter(repositoryPath, false))
        {
            writer.write("");
            writer.flush();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public User find() {
        try {
            String info = read();
            return UserConvertor.convert(info);
        } catch (Exception e) {
            return new User();
        }
    }


    public String read() throws IOException {
        StringBuilder infoString = new StringBuilder();
        File file = new File(repositoryPath);
        if (!file.exists()) {
            file.createNewFile();
        }

        try (FileReader reader = new FileReader(repositoryPath)) {
            int character;
            while ((character = reader.read()) != -1) {
                infoString.append((char) character);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return infoString.toString();
    }

    public String buildInfo(User user) {
        StringBuilder infoString = new StringBuilder();
        infoString.append("USER STRUCTURE\n");
        infoString.append("username:")
                .append(user.getUsername())
                .append("\n");

        infoString.append("password:")
                .append(user.getPassword())
                .append("\n");

        List<Key> keys = user.getKeys();
        if (keys != null) {
            for (Key key : keys) {
                infoString.append("key:")
                        .append(key.toString())
                        .append("\n");
            }
        }

        List<Word> words = user.getWords();
        if (words != null) {
            for (Word word : words) {
                infoString.append("word:")
                        .append(word.toString())
                        .append("\n");
            }
        }

        infoString.append("END");
        return infoString.toString();
    }
}
