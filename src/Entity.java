import java.util.List;

public abstract class Entity implements Battle, Element<Entity> {
    protected List<Spell> abilities;
    protected int currentHP;
    protected int maxHP = 2000;
    protected int currentMana;
    protected int maxMana = 500;
    protected boolean fireProtection;
    protected boolean iceProtection;
    protected boolean earthProtection;

    public Entity(int currentHP, int maxHP, int currentMana, int maxMana, boolean fireProtection, boolean iceProtection, boolean earthProtection) {
        this.currentHP = currentHP;
        this.maxHP = maxHP;
        this.currentMana = currentMana;
        this.maxMana = maxMana;
        this.fireProtection = fireProtection;
        this.iceProtection = iceProtection;
        this.earthProtection = earthProtection;
    }

    public Entity() {
    }

    public void regenerateHP(int value) {
        int updatedHP = currentHP + value;
        if (updatedHP > maxHP) {
            this.currentHP = maxHP;
        } else {
            this.currentHP = updatedHP;
        }
    }

    public void regenrateMana(int value) {
        int updatedMana = currentMana + value;
        if (updatedMana > maxMana) {
            this.currentMana = maxMana;
        } else {
            this.currentMana = updatedMana;
        }
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    private boolean testImunity(Spell spell) {
        if (spell instanceof Fire && this.fireProtection) return true;
        if (spell instanceof Ice && this.iceProtection) return true;
        if (spell instanceof Earth && this.earthProtection) return true;
        return false;
    }

    public boolean useAbility(Spell spell, Entity enemy) {
        boolean okToUse = true;
        if (currentMana > spell.getManaCost()) {
            if (testImunity(spell)) {
                //System.out.println("This spell had no effect...");
                okToUse = false;
            }
        } else {
            //System.out.println("Can't use this spell...");
            okToUse = false;
        }
        return okToUse;
    }

    @Override
    public void receiveDamage(int damage) {
        currentHP -= damage;
    }

    @Override
    public abstract int getDamage();

    @Override
    public void accept(Visitor<Entity> visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Health: " + currentHP + "/" + maxHP + "\n" +
                "Mana: " + currentMana + "/" + maxMana + "\n" +
                "Immunities: Fire: " + fireProtection + ", Ice: " + iceProtection + ", Earth: " + earthProtection + "\n" +
                "Abilities: " + (abilities != null && !abilities.isEmpty() ? abilities.toString() : "None");
    }
}
