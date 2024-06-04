package ng.edu.aun.sce.service;

import java.util.List;
import java.util.Optional;
import ng.edu.aun.sce.model.User;

public interface UserService {
    public void save(User user);

    public void saveAfterVerification(User user);

    public User findByUsername(String un);

    public User getCurrentUser();

    List<User> get();

    void deleteById(Long id);

    Optional<User> findById(Long id);

    public long countUsers();

    public User findByVerificationToken(String token);

    List<User> findFacultyWithoutRoles();

    List<User> findFacultyWithRoles();
}