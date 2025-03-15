import java.util.ArrayList;
import java.util.Random;

public class Character extends Entity {
    public String name;
    public int currentExperience;
    public int level;
    public int Strength;
    public int Dexterity;
    public int Charisma;

    public Character(String name, int currentExperience, int level, int Strength, int Dexterity, int Charisma) {
        this.name = name;
        this.currentExperience = currentExperience;
        this.level = level;
        this.Strength = Strength;
        this.Dexterity = Dexterity;
        this.Charisma = Charisma;
    }

    public Character(String name, int currentExperience, int level) {
        this(name, currentExperience, level, 0, 0, 0); // Default HP is 100
    }

    public Character() {
        this("", 0, 0, 0, 0, 0);
    }

    public void addExperience(int experience) {
        this.currentExperience += experience;
    }

    protected ArrayList<Spell> generateSpells() {
        ArrayList<Spell> spells = new ArrayList<>();
        Random random = new Random();

        // generates the abilities list, at least one from each type with 50% chance to generate the second one
        int randomHP = random.nextInt(100) + 50;
        int randomMana = random.nextInt(20) + 40;
        Spell newSpell = new Fire("Fire", randomHP, randomMana);
        spells.add(newSpell);

        if (random.nextInt(100) < 50) {
            randomHP = random.nextInt(100) + 50;
            randomMana = random.nextInt(20) + 40;
            newSpell = new Fire("Fire", randomHP, randomMana);
            spells.add(newSpell);
        }

        randomHP = random.nextInt(100) + 50;
        randomMana = random.nextInt(20) + 40;
        newSpell = new Ice("Ice", randomHP, randomMana);
        spells.add(newSpell);

        if (random.nextInt(100) < 50) {
            randomHP = random.nextInt(100) + 50;
            randomMana = random.nextInt(20) + 40;
            newSpell = new Ice("Ice", randomHP, randomMana);
            spells.add(newSpell);
        }

        randomHP = random.nextInt(100) + 50;
        randomMana = random.nextInt(20) + 40;
        newSpell = new Earth("Earth", randomHP, randomMana);
        spells.add(newSpell);

        if (random.nextInt(100) < 50) {
            randomHP = random.nextInt(100) + 50;
            randomMana = random.nextInt(20) + 40;
            newSpell = new Earth("Earth", randomHP, randomMana);
            spells.add(newSpell);
        }

        return spells;
    }

    public void regeneateSpells()
    {
        this.abilities = generateSpells();
    }

    @Override
    public void receiveDamage(int damage) {
        Random random = new Random();

        if (random.nextInt(100) > (Dexterity + (Charisma % 10) +(Strength % 5))) {
            this.currentHP -= damage;
            System.out.println("This character received " + damage + " damage");
        }
        else {
            System.out.println("This character avoided the damage");
        }
        // character is dead case
        if (this.currentHP <= 0) {
            this.currentHP = 0;
        }
    }

    @Override
    public int getDamage() {
        Random random = new Random();

        int damage = random.nextInt(100)+100;
        if (random.nextInt(100) < 50) {
            damage *= 2;
        }
        return damage;
    }

    public Character getCharacter() {
        return this;
    }

    public void increaseStats()
    {
        this.Strength += (this.Strength / 5);
        this.Dexterity += (this.Dexterity / 5);
        this.Charisma += (this.Charisma / 5);
    }


    @Override
    public String toString() {
        return "Character: " + " Name - " + name + " Current Experience - " + currentExperience + " Level - " + level +
                " Strength - " + Strength + " Dexterity - " + Dexterity + " Charisma - " + Charisma + '\n';
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class Warrior extends Character {

    public Warrior(String name, int currentExperience, int level) {
        super(name, currentExperience, level, 35, 10, 5);
        this.fireProtection = true;
        this.earthProtection = false;
        this.iceProtection = false;
        this.currentHP = 400;
        this.currentMana = 100;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}

class Rogue extends Character {

    public Rogue(String name, int currentExperience, int level) {
        super(name, currentExperience, level, 10, 25, 15);
        this.fireProtection = false;
        this.earthProtection = true;
        this.iceProtection = false;
        this.currentHP = 400;
        this.currentMana = 100;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class Mage extends Character {

    public Mage(String name, int currentExperience, int level) {
        super(name, currentExperience, level, 10, 10, 30);
        this.fireProtection = false;
        this.earthProtection = false;
        this.iceProtection = true;
        this.currentHP = 400;
        this.currentMana = 100;

    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
