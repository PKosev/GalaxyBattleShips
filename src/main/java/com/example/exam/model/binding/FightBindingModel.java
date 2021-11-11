package com.example.exam.model.binding;


import javax.validation.constraints.Positive;

public class FightBindingModel {
    private Long attacker;
    private Long defender;

    public FightBindingModel() {
    }

    @Positive
    public Long getAttacker() {
        return attacker;
    }

    public void setAttacker(Long attacker) {
        this.attacker = attacker;
    }
    @Positive
    public Long getDefender() {
        return defender;
    }

    public void setDefender(Long defender) {
        this.defender = defender;
    }
}
