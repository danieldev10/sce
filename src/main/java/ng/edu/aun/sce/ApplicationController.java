package ng.edu.aun.sce;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
// import org.springframework.web.servlet.view.RedirectView;

import ng.edu.aun.sce.model.User;
import ng.edu.aun.sce.service.UserService;

@Controller
public class ApplicationController {
    @Autowired
    private UserService userService;

    @GetMapping("/landing")
    public String landingPage() {
        return "landing";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/logout")
    public String logout() {
        return "login";
    }

    @PostMapping("/register")
    public String addNew(User user, RedirectAttributes redir, BindingResult bindingResult) {
        // Check if the email already exists
        User existingUser = userService.findByUsername(user.getEmail());
        if (existingUser != null) {
            redir.addFlashAttribute("error", "Email is already in use.");
            return "redirect:/register";
        }

        // If email is unique, proceed with registration
        userService.save(user);
        return "verify-email";
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam("token") String token, Model model) {
        User user = userService.findByVerificationToken(token);
        if (user != null) {
            user.setVerified(true);
            userService.saveAfterVerification(user);
            return "verification-success";
        } else {
            return "verification-failure";
        }
    }

    @GetMapping
    public String verifyEmail() {
        return "verify-email";
    }
}
