package com.vendingmachine.vendingmachine.domains.product.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vendingmachine.vendingmachine.domains.user.entities.User;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(
        name = "product",
        schema = "public"
)
public class Product {
    @Id
    @SequenceGenerator(
            name="product_id_seq",
            sequenceName = "product_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    private Integer id;

    @Column(
            nullable = false
    )
    private String name;

    @Column(
            nullable = false
    )
    private Integer price;

    @Column(
            nullable = false
    )
    private Integer qty;

    @ManyToOne
    @JoinColumn(name="user_id")
    User user;

    public Product(Integer id, String name, Integer price, Integer qty, User user) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.user = user;
    }

    public Product(String name, Integer price, Integer qty, User user) {
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.user = user;
    }

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(qty, product.qty) && Objects.equals(user, product.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, qty, user);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", qty=" + qty +
                ", user=" + user +
                '}';
    }
}
