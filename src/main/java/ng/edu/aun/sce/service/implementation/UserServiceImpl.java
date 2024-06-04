package ng.edu.aun.sce.service.implementation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import ng.edu.aun.sce.model.User;
import ng.edu.aun.sce.repository.UserRepository;
import ng.edu.aun.sce.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void save(User user) {
        // Encode the password
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);

        userRepository.save(user);

        // sendVerificationEmail(user.getEmail(), token);
    }

    @Override
    public void saveAfterVerification(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String un) {
        return userRepository.findByEmail(un);
    }

    @Override
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(username);
    }

    @Override
    public List<User> get() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public long countUsers() {
        return userRepository.count();
    }

    private void sendVerificationEmail(String userEmail, String token) {
        // Construct verification URL
        String verificationUrl = "http://localhost:8080/verify?token=" + token;

        // Create email message
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Email Verification");
        message.setText("Please click the link below to verify your email address:\n" + verificationUrl);

        // Send email
        javaMailSender.send(message);
    }

    @Override
    public User findByVerificationToken(String token) {
        return userRepository.findByVerificationToken(token);
    }

    @Override
    public List<User> findFacultyWithoutRoles() {
        return userRepository.findByRolesEmpty();
    }

    @Override
    public List<User> findFacultyWithRoles() {
        return userRepository.findByRolesNotEmpty();
    }
}
