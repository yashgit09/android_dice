package com.example.dice;

public class Dice {

    private int dice_sides;
    private int current_side_up;

    public Dice(int number_of_sides) {
        this.dice_sides = number_of_sides;

    }

    public void setNoOfSides(int number_of_sides) {
        this.dice_sides = number_of_sides;
    }

    public int getCurrentSideUp() {
        return current_side_up;
    }

    public void roll() {
        this.current_side_up = (int)Math.floor(Math.random()*(this.dice_sides)+1);
    }


}
