package com.vendingmachine.vendingmachine.domains.order.entities;

import com.vendingmachine.vendingmachine.domains.product.entities.Product;
import com.vendingmachine.vendingmachine.domains.user.entities.User;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(
        name = "order",
        schema = "public"
)
public class Order {
    @Id
    @SequenceGenerator(name="order_id_seq", sequenceName = "order_id_seq",  allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="order_id_seq")
    private Integer id;

    @Column(
            nullable = false
    )
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Basic(optional = false)
    @Column(insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    public Order(Integer id, Integer amount, User user, Product product) {
        this.id = id;
        this.amount = amount;
        this.user = user;
        this.product = product;
    }

    public Order(Integer amount, User user, Product product) {
        this.amount = amount;
        this.user = user;
        this.product = product;
    }

    public Order() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(amount, order.amount) && Objects.equals(user, order.user) && Objects.equals(product, order.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, user, product);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", amount=" + amount +
                ", user=" + user +
                ", product=" + product +
                '}';
    }
}
