package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new AuthenticationException("User not found");
        }

        User user = optionalUser.get();

        if (!user.getPassword().equals(password)) {
            throw new AuthenticationException("Invalid password");
        }

        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent()) {
            throw new RuntimeException("User with email " + email + " already exists.");
        }
        User newUser = new User();
        newUser.setEmail(email);
        byte[] salt = HashUtil.getSalt();
        newUser.setSalt(salt);
        String hashedPassword = HashUtil.hashPassword(password, salt);
        newUser.setPassword(hashedPassword);

        return userService.add(newUser);
    }
}
