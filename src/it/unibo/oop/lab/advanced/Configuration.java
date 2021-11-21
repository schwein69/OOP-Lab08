package it.unibo.oop.lab.advanced;

public class Configuration {
    private int min;
    private int max;
    private int chance;

    public Configuration(final int min, final int max, final int chance) {
        super();
        this.min = min;
        this.max = max;
        this.chance = chance;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getChance() {
        return chance;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

}
