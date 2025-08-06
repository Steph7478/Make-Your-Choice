package com.make_your_choice.domain.entities;

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

    public String getDialogCode() {
        if (this.dialog == null) {
            return null;
        }
        return "D" + this.dialog;
    }

    public String getNextDialogCode() {
        if (this.nextDialog == null) {
            return null;
        }
        return "D" + this.nextDialog;
    }

    public String getChoice() {
        return choice;
    }

}
