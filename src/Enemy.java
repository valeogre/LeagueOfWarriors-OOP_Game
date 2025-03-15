import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Entity {
    public String name;
    public int Strength;
    public int Dexterity;
    public int Charisma;

    public Enemy() {
        Random random = new Random();

        this.currentHP = random.nextInt(350) + 100;
        this.currentMana = random.nextInt(50) + 100;

        this.Strength = random.nextInt(20) + 1;
        this.Dexterity = random.nextInt(20) + 1;
        this.Charisma = random.nextInt(20) + 1;

        this.fireProtection = random.nextBoolean();
        this.iceProtection = random.nextBoolean();
        this.earthProtection = random.nextBoolean();

        this.abilities = new ArrayList<>();

        // generates the abilities list, at least one from each type with 50% chance to generate the second one
        int randomHP = random.nextInt(85) + 50;
        int randomMana = random.nextInt(20) + 40;
        Spell newSpell = new Fire("Fire",randomHP, randomMana);
        this.abilities.add(newSpell);

        if (random.nextInt(100) < 50) {
            randomHP = random.nextInt(85) + 50;
            randomMana = random.nextInt(20) + 40;
            newSpell = new Fire("Fire",randomHP, randomMana);
            this.abilities.add(newSpell);
        }

        randomHP = random.nextInt(85) + 50;
        randomMana = random.nextInt(20) + 40;
        newSpell = new Ice("Ice", randomHP, randomMana);
        this.abilities.add(newSpell);

        if (random.nextInt(100) < 50) {
            randomHP = random.nextInt(85) + 50;
            randomMana = random.nextInt(20) + 40;
            newSpell = new Ice("Ice", randomHP, randomMana);
            this.abilities.add(newSpell);
        }

        randomHP = random.nextInt(85) + 50;
        randomMana = random.nextInt(20) + 40;
        newSpell = new Earth("Ice", randomHP, randomMana);
        this.abilities.add(newSpell);

        if (random.nextInt(100) < 50) {
            randomHP = random.nextInt(85) + 50;
            randomMana = random.nextInt(20) + 40;
            newSpell = new Earth("Ice", randomHP, randomMana);
            this.abilities.add(newSpell);
        }
    }

    @Override
    public void receiveDamage(int damage) {
        Random random = new Random();

        if (random.nextInt(100) < 50) {
            this.currentHP -= damage;
            System.out.println("This enemy received " + damage + " damage");
        }
        else {
            System.out.println("This enemy avoided the damage");
        }
        // enemy is dead case
        if (this.currentHP <= 0) {
            this.currentHP = 0;
        }
    }

    @Override
    public int getDamage() {
        Random random = new Random();

        int damage = random.nextInt(100)+50;
        if (random.nextInt(100) < 50) {
            damage *= 2;
        }
        return damage;
    }
}
