package com.make_your_choice.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;

@Entity
@Access(AccessType.FIELD)
public class DialogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dialog;

    @OneToMany(mappedBy = "dialog")
    private List<ChoiceEntity> choices = new ArrayList<>();

    public DialogEntity() {
    }

    public String getCode() {
        if (this.id == null) {
            return null;
        }
        return "D" + this.id;
    }

    public String getDialog() {
        return dialog;
    }

    public List<ChoiceEntity> getChoices() {
        return choices;
    }
}
