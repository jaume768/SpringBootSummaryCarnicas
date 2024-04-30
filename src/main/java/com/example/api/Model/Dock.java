package com.example.api.Model;


import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "GCARNMOLL")
public class Dock {

    @Id
    @Column(nullable = false, name = "MOLLCOD")
    private String dokcId;

    @Column(nullable = false, name = "MOLLDES")
    private String dockName;

}
