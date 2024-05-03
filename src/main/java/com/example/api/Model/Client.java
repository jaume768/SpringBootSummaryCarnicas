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
@Table(name = "GCARNCLI")
public class Client {

    @Id
    @Column(nullable = false, name = "CLICOD")
    private Integer clientId;

    @Column(name = "CLINCO")
    private String comercialName;
}

