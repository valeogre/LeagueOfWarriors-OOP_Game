public class CharacterFactory {
    public static Character createCharacter(String type, String name, int level, int experience) throws UnknownTypeException {
        switch (type) {
            case "Warrior":
                return new Warrior(name, level, experience);
            case "Mage":
                return new Mage(name, level, experience);
            case "Rogue":
                return new Rogue(name, level, experience);
            default:
                throw new UnknownTypeException("Unknown character type");
        }
    }
}
