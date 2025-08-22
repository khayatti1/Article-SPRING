package ma.formation.springboot.controller;

import jakarta.servlet.http.HttpSession;
import ma.formation.springboot.model.User;
import ma.formation.springboot.service.ArticleService;
import ma.formation.springboot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final UserService userService;
    private final ArticleService articleService;

    public AuthController(UserService userService, ArticleService articleService) {
        this.userService = userService;
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("error", "Nom d'utilisateur ou mot de passe incorrect");
        }
        if (logout != null) {
            model.addAttribute("message", "Vous avez été déconnecté avec succès");
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session) {
        User user = userService.authenticate(username, password);
        if (user != null) {
            session.setAttribute("loggedInUser", user);
            return "redirect:/home";
        }
        return "redirect:/login?error=true";
    }

    @GetMapping("/register")
    public String registerForm(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("user", new User());
        if (error != null) {
            model.addAttribute("error", "Ce nom d'utilisateur existe déjà");
        }
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        if (userService.userExists(user.getUsername())) {
            return "redirect:/register?error=true";
        }
        userService.register(user);
        return "redirect:/login?registered=true";
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        // Statistiques du stock
        model.addAttribute("totalArticles", articleService.getTotalArticlesCount());
        model.addAttribute("totalQuantity", articleService.getTotalQuantity());
        model.addAttribute("totalValue", String.format("%.2f", articleService.getTotalStockValue()));

        model.addAttribute("username", user.getUsername());
        return "home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout=true";
    }
}