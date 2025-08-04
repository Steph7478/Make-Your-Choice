package com.make_your_choice.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;

@Entity
public class ChoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "choice")
    private String choice;

    @ManyToOne
    @JoinColumn(name = "dialog_id")
    private DialogEntity dialog;

    @ManyToOne
    @JoinColumn(name = "next_dialog_id")
    private DialogEntity nextDialog;

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

    public DialogEntity getDialog() {
        return dialog;
    }

    public DialogEntity getNextDialog() {
        return nextDialog;
    }

}
