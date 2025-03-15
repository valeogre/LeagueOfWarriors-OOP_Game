public abstract class Spell implements Visitor<Entity> {
    String type;
    protected int damage;
    protected int manaCost;

    public Spell(String type,int damage, int manaCost) {
        this.type = type;
        this.damage = damage;
        this.manaCost = manaCost;
    }

    public String toString()
    {
        return "Type: " + type + " Damage: " + this.damage + " Mana Cost: " + this.manaCost + "\n";
    }

    private boolean testImunity(Entity entity) {
        if (this instanceof Fire && entity.fireProtection) return true;
        if (this instanceof Ice && entity.iceProtection) return true;
        if (this instanceof Earth && entity.earthProtection) return true;
        return false;
    }

    public void visit(Entity entity) {
        if (!testImunity(entity))
        {
            entity.receiveDamage(damage);
        }
        else
        {
            System.out.println(entity.getClass().getName() + " immmune to " + type);
        }
    }

    public String getType() {
        return type;
    }

    public int getDamage() {
        return damage;
    }

    public int getManaCost() {
        return manaCost;
    }
}

class Ice extends Spell {
    public Ice(String type, int damage, int manaCost) {
        super("Ice", damage, manaCost);
    }

    @Override
    public void visit(Entity entity) {
        super.visit(entity);
        //System.out.println("Ice spell used on " + entity.getClass().getName() + " for " + damage + " damage.");
    }

    @Override
    public String toString()
    {
        return "Type: "+ type + " Damage: " + damage + " Mana Cost: " + manaCost + "\n";
    }
}

class Fire extends Spell {
    public Fire(String type, int damage, int manaCost) {
        super("Fire", damage, manaCost);
    }

    @Override
    public void visit(Entity entity) {
        super.visit(entity);
        //System.out.println("Fire spell used on " + entity.getClass().getName() + " for " + damage + " damage.");
    }

    @Override
    public String toString()
    {
        return "Type: "+ type + " Damage: " + damage + " Mana Cost: " + manaCost + "\n";
    }
}

class Earth extends Spell {
    public Earth(String type, int damage, int manaCost) {
        super("Earth", damage, manaCost);
    }

    @Override
    public void visit(Entity entity) {
        super.visit(entity);
        //System.out.println("Earth spell used on " + entity.getClass().getName() + " for " + damage + " damage.");
    }

    @Override
    public String toString()
    {
        return "Type: "+ type + " Damage: " + damage + " Mana Cost: " + manaCost + "\n";
    }
}
