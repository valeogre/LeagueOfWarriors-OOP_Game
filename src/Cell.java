public class Cell {
    private int Ox;
    private int Oy;
    private CellEntityType type;
    private boolean visited;

    public Cell(int x, int y) {
        this.Ox = x;
        this.Oy = y;
        this.type = CellEntityType.VOID;
        this.visited = false;
    }

    public Cell(int x, int y, CellEntityType type) {
        this.Ox = x;
        this.Oy = y;
        this.type = type;
        this.visited = false;
    }

    public Cell(int x, int y, CellEntityType type, boolean visited) {
        this.Ox = x;
        this.Oy = y;
        this.type = type;
        this.visited = visited;
    }

    public int getOx() {
        return Ox;
    }

    public int getOy() {
        return Oy;
    }

    public CellEntityType getType() {
        return type;
    }

    public void setType(CellEntityType type) {
        this.type = type;
    }

    public boolean isVisited() {
        return visited;
    }

    public void isVisited(boolean visited) {
        this.visited = visited;
    }

    public void cellAction(Character character) {
        switch (type) {
            case PLAYER:
                System.out.println("You are here!");
                break;

            case VOID:
                System.out.println("Empty cell");
                break;

            case ENEMY:
                System.out.println("It's time for a fight!");
                //fight stuff
                break;

            case SANCTUARY:
                System.out.println("Time to rest!");
                //regen hp
                break;

            case PORTAL:
                System.out.println("Time to take it to the next level");
                //new map and stuff
                break;
        }
    }

    public char cellType() {
        if (isVisited()) {
            return type.toString().charAt(0);
        } else {
            return 'N';
        }
    }
//    public char cellType()
//    {
//        return type.toString().charAt(0);
//    }

}
