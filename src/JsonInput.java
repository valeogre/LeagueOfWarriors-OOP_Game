import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;


public class JsonInput {

    // Method to load accounts from a JSON file
    public static ArrayList<Account> loadAccounts(String filePath) {
        ArrayList<Account> accounts = new ArrayList<>();

        try (FileReader reader = new FileReader(filePath)) {
            JSONParser parser = new JSONParser();
            JSONObject root = (JSONObject) parser.parse(reader);
            JSONArray accountsArray = (JSONArray) root.get("accounts");

            for (Object obj : accountsArray) {
                JSONObject accountJson = (JSONObject) obj;
                Account account = parseAccount(accountJson);
                if (account != null) {
                    accounts.add(account);
                }
            }

        } catch (IOException | ParseException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        }

        return accounts;
    }

    // Parses a single Account object
    private static Account parseAccount(JSONObject accountJson) {
        if (accountJson == null) return null;

        // Parse credentials
        Credentials credentials = parseCredentials((JSONObject) accountJson.get("credentials"));

        // Parse favorite games
        SortedSet<String> favoriteGames = parseFavoriteGames((JSONArray) accountJson.get("favorite_games"));

        // Parse characters
        ArrayList<Character> characters = parseCharacters((JSONArray) accountJson.get("characters"));

        // Parse basic account details
        String name = (String) accountJson.get("name");
        String country = (String) accountJson.get("country");
        int mapsCompleted = Integer.parseInt((String) accountJson.get("maps_completed"));

        // Create Information object using the builder pattern
        Account.Information information = new Account.Information.Builder(credentials)
                .setName(name)
                .setCountry(country)
                .setFavGames(favoriteGames)
                .build();

        // Return the complete Account object
        return new Account(information, mapsCompleted, characters);
    }

    // Parses credentials
    private static Credentials parseCredentials(JSONObject credentialsJson) {
        if (credentialsJson == null) return null;

        String email = (String) credentialsJson.get("email");
        String password = (String) credentialsJson.get("password");
        return new Credentials(email, password);
    }

    // Parses favorite games
    private static SortedSet<String> parseFavoriteGames(JSONArray gamesArray) {
        SortedSet<String> favoriteGames = new TreeSet<>();
        if (gamesArray != null) {
            for (Object game : gamesArray) {
                favoriteGames.add((String) game);
            }
        }
        return favoriteGames;
    }

    // Parses characters
    private static ArrayList<Character> parseCharacters(JSONArray charactersArray) {
        ArrayList<Character> characters = new ArrayList<>();
        if (charactersArray != null) {
            for (Object obj : charactersArray) {
                JSONObject charJson = (JSONObject) obj;
                Character character = parseCharacter(charJson);
                if (character != null) {
                    characters.add(character);
                }
            }
        }
        return characters;
    }

    // Parses a single Character object
    private static Character parseCharacter(JSONObject charJson) {
        if (charJson == null) return null;

        String name = (String) charJson.get("name");
        String profession = (String) charJson.get("profession");
        int level = Integer.parseInt((String) charJson.get("level"));
        int experience = ((Long) charJson.get("experience")).intValue();

        // Using the Factory Pattern generates the user's characters list at the begining of the game
        try {
            return CharacterFactory.createCharacter(profession, name, level, experience);
        } catch (UnknownTypeException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        JsonInput jsonInput = new JsonInput();
        String filePath = "C:\\Users\\alexo\\IdeaProjects\\Tema1\\src\\accounts.json";
        ArrayList<Account> accounts = jsonInput.loadAccounts(filePath);

        for (Account account : accounts) {
            System.out.println(account);
        }
    }
}