import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game {
    private ArrayList<Account> accounts;
    private Grid map;
    private Account userAccount;
    private Character userCharacter;
    private int level;
    private int kills;

    private static Game instance;

    // constructor that uses the input file to load the accounts
    private Game() {
        this.accounts = JsonInput.loadAccounts("C:\\Users\\alexo\\IdeaProjects\\Tema_POO_part2\\src\\accounts.json");
        this.map = null;
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    // method to read the email address and the password of the user until the authentication is correct
//    private void authentication() {
//        Scanner readInput = new Scanner(System.in);
//        boolean okAutentication = false;
//
//        while (!okAutentication) {
//            System.out.println("Please enter your email: ");
//            String email = readInput.nextLine();
//
//            System.out.println("Please enter your password: ");
//            String password = readInput.nextLine();
//
//            // after reading the input the method checks if the credentials are correct using
//            // the ArrayList of accounts and a method that checks if email and password match
//            for (Account account : accounts) {
//                if (account.info.getCredentials().authentication(email, password)) {
//                    userAccount = account;
//                    okAutentication = true;
//                    break;
//                }
//            }
//
//            if (!okAutentication) {
//                System.out.println("Email or Password is incorrect");
//            } else {
//                System.out.println("Welcome, " + userAccount.getInfo().getName());
//                System.out.println(userAccount.getCharacters());
//            }
//        }
//    }

    public void authenticationGUI() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(500, 350);
        loginFrame.setLayout(new GridLayout(3, 1));

        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");

        JTextField emailField = new JTextField(37);
        JPasswordField passwordField = new JPasswordField(15);

        loginFrame.add(emailLabel);
        loginFrame.add(emailField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginFrame.add(loginButton);

        loginButton.addActionListener( e -> {
            String email = new String(emailField.getText());
            String password = new String(passwordField.getPassword());

            boolean okAuthentication = false;
            while (!okAuthentication) {
                for (Account account : accounts) {
                    if (account.info.getCredentials().authentication(email, password)) {
                        userAccount = account;
                        okAuthentication = true;
                        break;
                    }
                }

                if (!okAuthentication) {
                    JOptionPane.showMessageDialog(loginFrame, "Email or Password is incorrect");
                    break;
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Welcome, " + userAccount.getInfo().getName());
                    loginFrame.dispose();
                }
            }
        });
        loginFrame.setVisible(true);

        while (loginFrame.isVisible()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

    // user's characters are loaded in an ArrayList and displayed so that the user
    // can pick one of them for the game
    private void selectCharacter() {

        ArrayList<Character> characters = userAccount.getCharacters();
        Scanner readInput = new Scanner(System.in);
        int characterChoice = 0;
        boolean validChoice = false;

        while (!validChoice) {
            System.out.print("Enter the number of your character(or q to quit): ");

            String characterInput = readInput.nextLine().trim();
            if (characterInput.equalsIgnoreCase("q")) {
                System.out.println("Leaving game...");
                System.exit(0);
            }

            try {
                characterChoice = Integer.parseInt(characterInput);

                if (characterChoice < 1 || characterChoice > characters.size()) {
                    System.out.println("Invalid choice");
                } else {
                    validChoice = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Select a valid number");
            }
        }

        userCharacter = characters.get(characterChoice - 1);
        System.out.println(userCharacter.getCharacter());
    }

    // generates a grid of random size, making sure there is room for enough cells of each type
    private void setGrid() {
        System.out.println("Initializing the grid of the game:");
        Random rand = new Random();
        map = Grid.generateGrid(rand.nextInt((10 - 3) + 1) + 3, rand.nextInt((10 - 3) + 1) + 3);
    }

    // generates the test grid
    private void setDemoGrid() {
        System.out.println("Initializing the grid of the game:");
        Random rand = new Random();
        map = Grid.generateDemoGrid(5, 5);
    }

    // method that is used by the enemy for finding the spell with the most damage
    private Spell findStrongestSpell(Entity entity) {
        Spell strongestSpell = null;
        int maxDamage = 0;

        for (Spell spell : entity.abilities) {
            if (spell.getDamage() > maxDamage) {
                maxDamage = spell.getDamage();
                strongestSpell = spell;
            }
        }
        return strongestSpell;
    }

    // fight method handles a battle between the user and an enemy wher the user can choose
    // to attack with a default move or use a spell. if a spell is chosen, it is removed
    // from the list(it's also removed when the enemy is imune). the battle continues
    // until either the user or the enemy loses all health.
    private void fight(Character userCharacter) {
        Scanner scanner = new Scanner(System.in);

        Enemy enemy = new Enemy();

        userCharacter.regeneateSpells();

        int initialMana = userCharacter.getCurrentMana();

        System.out.println("Your spells for this epic fight are:");
        System.out.println(userCharacter.abilities);

        // loops until one of the entities is dead
        // The fight loop continues until either the user or the enemy is dead
        while (userCharacter.currentHP > 0 && enemy.currentHP > 0) {

            System.out.println("-------- Start this round ---------");
            System.out.println("Your life is at: " + userCharacter.getCurrentHP() + " and your left mana is " + userCharacter.getCurrentMana());
            System.out.println("--------------- VS ---------------");
            System.out.println("Your enemy's life is at: " + enemy.getCurrentHP() + " and his left mana is " + enemy.getCurrentMana());

            int choice = -1;

            // getting user's choice of attack
            while (choice == -1) {
                try {
                    System.out.println("Choose your attack: ");
                    System.out.println("1. Default Attack");
                    System.out.println("2. Use a Spell");

                    if (!scanner.hasNextInt()) {
                        scanner.next();
                        throw new InvalidCommandException("Invalid input! Please enter a number.");
                    }

                    choice = scanner.nextInt();

                    if (choice < 1 || choice > 2) {
                        throw new InvalidCommandException("Invalid choice! Please select 1 or 2.");
                    }
                } catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                    choice = -1;
                }
            }

            // player choose to use a spell
            if (choice == 2 && !userCharacter.abilities.isEmpty()) {
                int spellIndex = -1;

                while (spellIndex == -1) {
                    try {
                        System.out.println("Choose a spell to use (enter the index):");

                        for (int i = 0; i < userCharacter.abilities.size(); i++) {
                            System.out.println(i + ": " + userCharacter.abilities.get(i));
                        }

                        if (!scanner.hasNextInt()) {
                            scanner.next();
                            throw new InvalidCommandException("Invalid input! Please enter a number.");
                        }

                        spellIndex = scanner.nextInt();

                        if (spellIndex < 0 || spellIndex >= userCharacter.abilities.size()) {
                            throw new InvalidCommandException("Invalid spell index! Please select a valid spell.");
                        }
                    } catch (InvalidCommandException e) {
                        System.out.println(e.getMessage());
                        spellIndex = -1;
                    }
                }

                // use the chosen spell
                Spell chosenSpell = userCharacter.abilities.get(spellIndex);

                if (userCharacter.useAbility(chosenSpell, enemy)) {
                    System.out.println("You used " + chosenSpell.toString() + " against the enemy!");

                    enemy.accept(chosenSpell);
                    userCharacter.currentMana -= chosenSpell.getManaCost();
                    if (userCharacter.currentMana < 0) {
                        userCharacter.currentMana = 0;
                        System.out.println("You lost all your mana!");
                    }

                } else {
                    System.out.println("The spell had no effect or you didn't have enough mana!");
                }

                userCharacter.abilities.remove(chosenSpell);

            } else {
                // user choose to use a basic attack
                int basicDamage = userCharacter.getDamage();
                System.out.println("You attack the enemy with a basic attack for " + basicDamage + " damage!");
                enemy.receiveDamage(basicDamage);
            }

            // checks if the enemy is dead
            if (enemy.currentHP <= 0) {
                System.out.println("You defeated the enemy!");
                kills++;
                break;
            }

            // enemy attacks
            Spell enemyStrongestSpell = findStrongestSpell(enemy);

            if (enemyStrongestSpell != null && enemy.useAbility(enemyStrongestSpell, userCharacter)) {
                System.out.println("The enemy used " + enemyStrongestSpell.toString() + " against you!");

                userCharacter.accept(enemyStrongestSpell);
                enemy.currentMana -= enemyStrongestSpell.getManaCost();

                if (enemy.currentMana < 0)
                {
                    enemy.currentMana = 0;
                    System.out.println("This enemy used all of its mana!");
                }

                enemy.abilities.remove(enemyStrongestSpell);

            } else {
                // enemy has no spells, uses a basic attack
                int enemyBasicDamage = enemy.getDamage();
                System.out.println("The enemy attacks you with a basic attack for " + enemyBasicDamage + " damage!");
                userCharacter.receiveDamage(enemyBasicDamage);
            }

            // check if the player is defeated
            if (userCharacter.currentHP <= 0) {
                System.out.println("You were defeated by the enemy!");

                // after the win the character's hp doubles, mana regenrates
                // also his stats charisma, strength, dexterity increase
                userCharacter.regenerateHP(userCharacter.getCurrentHP());
                userCharacter.currentMana = initialMana;
                userCharacter.increaseStats();

                // adding xp for the win
                Random rand = new Random();
                int xpGained = (rand.nextInt(5) + 1) * level;
                userCharacter.addExperience(xpGained);

                break;
            }
        }
    }

    // logic for the game
    private void gameLoop() {
        Scanner readInput = new Scanner(System.in);
        boolean running = true;
        kills = 0;

        while (running) {
            // print the map before the user's move
            System.out.println(map.toString());
            System.out.println("What's going to be your next move? (n/s/e/w to move, q to quit)");

            String move = readInput.nextLine().trim();
            if (move.isEmpty()) {
                System.out.println("Please enter a valid move");
                continue;
            }

            char command = move.toLowerCase().charAt(0);

            // based on the first letter of the input(n, s, e, w or q to quit) the move is made
            try {
                switch (command) {
                    case 's':
                        map.goSouth();
                        break;
                    case 'w':
                        map.goWest();
                        break;
                    case 'e':
                        map.goEast();
                        break;
                    case 'n':
                        map.goNorth();
                        break;
                    case 'q':
                        running = false;
                        System.out.println("See you again!");
                        continue;
                    default:
                        System.out.println("Invalid command! Try again!");
                }
            } catch (ImpossibleMoveException e) {
                System.out.println("Could not move in that direction...\n" + e.getMessage());
            }

            if (map.currentCell.getType() == CellEntityType.PORTAL) {
                System.out.println("You conquered this level!");

                level++;
                int xpGained = level * 5;
                userCharacter.addExperience(xpGained);
                userAccount.incrementPlayedGames();

                System.out.println("This level you killed " + kills + " enemies!");
                System.out.println("You earned: " + xpGained + " XP!");

                setGrid();
                System.out.println("Level " + (level + 1) + " loading...");

            } else if (map.currentCell.getType() == CellEntityType.SANCTUARY) {
                //sanctuary logic adding up to 45% of the current HP
                Random rand = new Random();

                int increaseHP = rand.nextInt(45) * userCharacter.currentHP / 100;
                if ((userCharacter.currentHP + increaseHP) > userCharacter.maxHP) {
                    userCharacter.currentHP = userCharacter.maxHP;
                } else {
                    userCharacter.currentHP = userCharacter.currentHP + increaseHP;
                }

                //sanctuary logic adding up to 45% of the current Mana
                int increaseMana = rand.nextInt(45) * userCharacter.currentMana / 100;
                if ((userCharacter.currentMana + increaseMana) > userCharacter.maxMana) {
                    userCharacter.currentMana = userCharacter.maxMana;
                } else {
                    userCharacter.currentMana = userCharacter.currentMana + increaseMana;
                }

                System.out.println("Take a break from the fight!");
                map.currentCell.setType(CellEntityType.PLAYER);
            } else if (map.currentCell.getType() == CellEntityType.ENEMY) {
                System.out.println("It's your time to shine!!!");
                fight(userCharacter);

                map.currentCell.setType(CellEntityType.PLAYER);
            } else if (map.currentCell.getType() == CellEntityType.VOID) {
                map.currentCell.setType(CellEntityType.PLAYER);
            }
            // check if the player is still alive
            if (userCharacter.getCurrentHP() <= 0) {
                System.out.println("GAME OVER... \n" +
                        "You are dead!\n");
                running = false;
            }
        }

        selectCharacter();
        setGrid();
        gameLoop();
    }

    public void run() {
        authenticationGUI();

        if (userAccount == null)
        {
            System.out.println("Failed authentincation");
            return;
        }

        selectCharacter();
        setGrid();
        gameLoop();
    }

    public void runDemo() {
        authenticationGUI();

        if (userAccount == null)
        {
            System.out.println("Failed authentincation");
            return;
        }

        selectCharacter();
        setDemoGrid();
        gameLoop();
    }


    public static void main(String[] args) {
        Game game = Game.getInstance();
        //game.run();
        //game.runDemo();
    }
}
