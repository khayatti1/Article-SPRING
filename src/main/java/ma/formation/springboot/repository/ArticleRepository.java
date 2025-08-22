package ma.formation.springboot.repository;

import ma.formation.springboot.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    List<Article> findByDescriptionContainingIgnoreCaseAndPriceBetweenAndQuantiteGreaterThan(
            String description, double minPrice, double maxPrice, int quantite);

    boolean existsById(Integer id);
    @Query("SELECT COUNT(a) FROM Article a")
    long countAllArticles();

    @Query("SELECT COALESCE(SUM(a.quantite), 0) FROM Article a")
    int sumAllQuantities();

    @Query("SELECT COALESCE(SUM(a.price * a.quantite), 0) FROM Article a")
    double sumAllValues();
}