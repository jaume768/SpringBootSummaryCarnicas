package com.example.api.Model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "GCARNART")
public class Article {

    @Id
    @Column(nullable = false, name = "ARTCOD")
    private Float articleCode;

    @Column(name = "ARTDES")
    private String articleName;
}

