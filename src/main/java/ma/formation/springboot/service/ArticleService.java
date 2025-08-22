package ma.formation.springboot.service;

import ma.formation.springboot.model.Article;
import ma.formation.springboot.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public void save(Article article) {
        articleRepository.save(article);
    }

    public Optional<Article> findById(int id) {
        return articleRepository.findById(id);
    }

    public void delete(int id) {
        articleRepository.deleteById(id);
    }

    public List<Article> search(String description, Double minPrice, Double maxPrice) {
        if (minPrice == null) minPrice = 0.0;
        if (maxPrice == null) maxPrice = Double.MAX_VALUE;
        return articleRepository.findByDescriptionContainingIgnoreCaseAndPriceBetweenAndQuantiteGreaterThan(
                description, minPrice, maxPrice, 0);
    }

    public Article updateArticle(Article article) {
        // Vérification supplémentaire
        if (article.getId() == null || article.getId() <= 0) {
            throw new IllegalArgumentException("ID d'article invalide");
        }

        Article existing = articleRepository.findById(article.getId())
                .orElseThrow(() -> new RuntimeException("Article non trouvé"));

        // Mise à jour des champs modifiables
        existing.setDescription(article.getDescription());
        existing.setQuantite(article.getQuantite());
        existing.setPrice(article.getPrice());

        return articleRepository.save(existing);
    }

    public boolean existsById(Integer id) {
        return id != null && articleRepository.existsById(id);
    }

    public long getTotalArticlesCount() {
        return articleRepository.countAllArticles();
    }

    public int getTotalQuantity() {
        return articleRepository.sumAllQuantities();
    }

    public double getTotalStockValue() {
        return articleRepository.sumAllValues();
    }

}