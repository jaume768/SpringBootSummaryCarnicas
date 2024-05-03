package com.example.api.Model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GCARNART")
public class Article {

    @Id
    @Column(nullable = false, name = "ARTCOD")
    private Integer articleCode;

    @Column(name = "ARTDES")
    private String articleName;
}

