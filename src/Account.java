import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class Account {
    public Information info;
    public ArrayList<Character> characters;
    public int playedGames;

    public Account()
    {
        this.info = info;
        this.characters = new ArrayList();
        this.playedGames = 0;
    }

    public Account(Information info, int playedGames, ArrayList<Character> characters)
    {
        this.info = info;
        this.characters = characters;
        this.playedGames = playedGames;
    }

    public Account(Information info)
    {
        this.info = info;
        this.characters = new ArrayList<>();
        this.playedGames = 0;
    }

    public Information getInfo()
    {
        return info;
    }

    public void setInfo(Information info)
    {
        this.info = info;
    }

    public ArrayList getCharacters()
    {
        return characters;
    }

    public void setCharacters(ArrayList characters)
    {
        this.characters = characters;
    }

    public int getPlayedGames() {
        return playedGames;
    }

    public void incrementPlayedGames()
    {
        playedGames++;
    }

    public void setPlayedGames(int playedGames) {
        this.playedGames = playedGames;
    }

    public static class Information {
        private Credentials credentials;
        private SortedSet<String> favGames;
        private String name;
        private String country;

        public Information(Builder builder)
        {
            this.credentials = builder.credentials;
            this.favGames = builder.favoriteGames;
            this.name = builder.name;
            this.country = builder.country;
        }

        public Credentials getCredentials()
        {
            return credentials;
        }

        public SortedSet<String> getFavGames()
        {
            return favGames;
        }

        public void addFavGames(String name)
        {
            this.favGames.add(name);
        }

        public String getName()
        {
            return name;
        }

        public String getCountry()
        {
            return country;
        }

        public static class Builder {
            private Credentials credentials;
            private SortedSet<String> favoriteGames;
            private String name;
            private String country;

            public Builder(Credentials credentials)
            {
                this.credentials = credentials;
            }

            public Builder setName(String name) {
                this.name = name;
                return this;
            }

            public Builder setCountry(String country) {
                this.country = country;
                return this;
            }

            public Builder setFavGames(SortedSet<String> favoriteGames) {
                this.favoriteGames = favoriteGames;
                return this;
            }

            public Information build() {
                return new Information(this);
            }
        }
    }

    @Override
    public String toString() {
        String result = "Account Information:\n";
        result += "Name: " + info.getName() + "\n";
        result += "Country: " + info.getCountry() + "\n";
        result += "Maps Completed: " + playedGames + "\n";
        result += "Favorite Games: " + info.getFavGames() + "\n";
        result += "Email: " + info.getCredentials().getEmail() + "\n";

        result += "\nCharacters:\n";
        for (Character character : characters) {
            result += character.toString() + "\n";
        }

        return result;
    }
}
