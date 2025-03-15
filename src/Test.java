import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Game game = Game.getInstance();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Do you want to run the demo? (y/n)");
            String answer = scanner.nextLine().toLowerCase().trim();

            if (answer.equals("y")) {
                game.runDemo();
                break;
            } else if (answer.equals("n")) {
                game.run();
                break;
            } else {
                System.out.println("Invalid option");
            }
        }

        scanner.close();
    }
}
