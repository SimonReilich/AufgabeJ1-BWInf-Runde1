public class SurpriseBag {

    private int[] games;

    public SurpriseBag(int size) {
        games = new int[size];
    }

    public void addGame(int game) {
        games[game]++;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < games.length; i++) {
            sb.append("    Game ").append(i).append(": ").append(games[i]).append(" times \n");
        }
        return sb.toString();
    }
}
