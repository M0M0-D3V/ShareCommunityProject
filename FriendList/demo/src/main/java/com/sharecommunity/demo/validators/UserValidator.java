package com.sharecommunity.demo.validators;

import com.sharecommunity.demo.models.User;
import com.sharecommunity.demo.services.UserService;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    // 1
    @Override
    public boolean supports(Class<?> c) {
        return User.class.equals(c);
    }

    // 2
    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (!user.getPasswordConfirmation().equals(user.getPassword())) {
            // 3
            errors.rejectValue("passwordConfirmation", "Match");
        }
        User foundUser = userService.findByEmail(user.getEmail());
        if (foundUser != null) {
            errors.rejectValue("email", "Match");
        }
    }

    // 1. supports(Class<?>): Specifies that a instance of the User Domain Model can
    // be validated with this custom validator
    // 2. validate(Object, Errors): Creating our custom validation. We can add
    // errors via .rejectValue(String, String).
    // 3..rejectValue(String, String): the first argument is the member variable of
    // our Domain model that we are validating. The second argument is a code for us
    // to use to set an error message.
    // src/main/resources/messages.properties
    // The format of our error messages is CODE.ModelAttribute.MemberVariable=YOUR
    // CUSTOM ERROR MESSAGE
}