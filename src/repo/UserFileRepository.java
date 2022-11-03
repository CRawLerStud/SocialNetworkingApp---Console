package repo;

import models.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class UserFileRepository extends InMemoryRepository<Long, User> {
    private final String filename;

    public UserFileRepository(String filename) {
        this.filename = filename;
        loadData();
    }

    private void loadData() {
        Path path = Paths.get(filename);
        try{
            List<String> lines = Files.readAllLines(path);
            for(String line : lines){
                String[] words = line.split(";");
                User user = new User(words[1], words[2], LocalDate.parse(words[3]));
                user.setId(Long.parseLong(words[0]));
                super.save(user);
            }
        }
        catch(IOException|RepositoryException e) {
            System.err.println("Error while reading from file!");
            e.printStackTrace();
        }
    }
}
