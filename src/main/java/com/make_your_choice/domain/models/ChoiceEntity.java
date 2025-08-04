package com.make_your_choice.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;

@Entity
public class ChoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String choice;

    @ManyToOne
    private DialogEntity dialog;

    public ChoiceEntity() {
    }

    public String getCode() {
        if (this.id == null) {
            return null;
        }
        return "C" + this.id;
    }

    public String getChoice() {
        return choice;
    }

}
