package ma.formation.springboot.model;

import jakarta.persistence.*;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 45)
    private String description;

    @Column(nullable = false)
    private int quantite;

    @Column(nullable = false)
    private double price;

    // Getters and setters
    public Integer getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
