package ma.formation.springboot.controller;

import ma.formation.springboot.model.Article;
import ma.formation.springboot.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/redirectToLogin")
    public String redirectToLogin() {
        return "redirect:/login";
    }

    @GetMapping("")
    public String list(Model model, @RequestParam(required = false) String message) {
        model.addAttribute("articles", articleService.findAll());
        model.addAttribute("message", message);
        return "articles";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("article", new Article());
        return "addArticle";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Article article, Model model) {
        if (article.getQuantite() < 0) {
            article.setQuantite(Math.abs(article.getQuantite()));  // Convertir la quantité négative en valeur positive
            model.addAttribute("message", "❌ Quantité négative, nous l'avons convertie en positive : " + article.getQuantite());
        }
        articleService.save(article);
        model.addAttribute("message", "✅ Article ajouté avec succès !");
        return "redirect:/articles";  // Rester sur la page des articles après suppression
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id, Model model) {
        articleService.findById(id).ifPresent(article -> model.addAttribute("article", article));
        return "editArticle";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Article article, Model model) {
        // Vérification obligatoire de l'ID
        if (article.getId() == null || article.getId() <= 0) {
            model.addAttribute("error", "ID d'article invalide");
            return "editArticle";
        }

        // Vérification de l'existence de l'article
        if (!articleService.existsById(article.getId())) {
            model.addAttribute("error", "Article non trouvé");
            return "editArticle";
        }

        // Gestion des quantités négatives
        if (article.getQuantite() < 0) {
            article.setQuantite(Math.abs(article.getQuantite()));
            model.addAttribute("warning", "Quantité convertie en positive");
        }

        articleService.updateArticle(article); // Utilisation d'une méthode dédiée
        return "redirect:/articles?success=Article modifié avec succès";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, Model model) {
        articleService.delete(id);
        model.addAttribute("message", "🗑️ Article supprimé avec succès !");
        return "redirect:/articles";  // Rester sur la page des articles après suppression
    }

    @GetMapping("/search")
    public String showSearchForm() {
        return "searchArticles"; // équivaut à searchArticles.html
    }

    @PostMapping("/search")
    public String searchArticles(@RequestParam(required = false) String description,
                                 @RequestParam(required = false) String minPrice,
                                 @RequestParam(required = false) String maxPrice,
                                 Model model) {

        Double min = parseDouble(minPrice);
        Double max = parseDouble(maxPrice);

        List<Article> articles = articleService.search(description, min != null ? min : 0.0, max != null ? max : Double.MAX_VALUE);

        model.addAttribute("articles", articles);

        if (articles.isEmpty()) {
            model.addAttribute("message", "❌ Aucun article ne correspond à votre recherche.");
        } else {
            model.addAttribute("message", "✅ Résultat(s) trouvé(s) : " + articles.size());
        }

        return "searchResults";
    }
    private Double parseDouble(String value) {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException | NullPointerException e) {
                return null;
            }
    }
}

