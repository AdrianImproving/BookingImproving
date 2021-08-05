package com.example.demo.Models;

import java.time.LocalDateTime;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Reservation")
@Getter
@Setter

public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column
    private LocalDateTime time;

    public Reservation() {
    }

    public Reservation(Integer id, String name, LocalDateTime time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }

    public Reservation(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Reservation(String name, LocalDateTime time) {
        this.name = name;
        this.time = time;
    }

    public Reservation(String name) {
        this.name = name;
    }

}
