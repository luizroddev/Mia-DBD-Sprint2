package br.com.fiap.MiaDBD.services;

import br.com.fiap.MiaDBD.models.User;
import br.com.fiap.MiaDBD.repositories.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public void createUser(User user) {
        user.setCreated_at(LocalDateTime.now());
        userRepository.save(user);
    }

    public void updateUser(Integer id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser != null) {
            user.setId(id);
            user.setCreated_at(existingUser.getCreated_at());
            userRepository.save(user);
        }
    }

    public void deleteUser(Integer id) {
        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser != null) {
            userRepository.delete(existingUser);
        }
    }
}
