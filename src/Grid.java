import java.util.ArrayList;
import java.util.Random;

public class Grid extends ArrayList<ArrayList<Cell>> {
    public int length;
    public int width;
    public Character currentCharacter;
    public Cell currentCell;

    private Grid(int length, int width, Character currentCharacter, Cell currenrCell) {
        this.length = length;
        this.width = width;
        this.currentCharacter = currentCharacter;
        this.currentCell = currenrCell;
    }

    // generates a grid of given size, initializing every cell as a void one
    private Grid(int length, int width) {
        this.length = length;
        this.width = width;

        for (int i = 0; i < length; i++) {
            this.add(new ArrayList<>());
            for (int j = 0; j < width; j++) {
                this.get(i).add(new Cell(i, j, CellEntityType.VOID));
            }
        }
    }

    // method that add a minimum of 2 sanctuaries but using Random
    // the number might increase proportionally to the size of the grid
    private static void addSanctuaries(Grid grid) {
        Random rand = new Random();
        int length = grid.length;
        int width = grid.width;

        int sanctuaries = 2 + rand.nextInt((width * length) / 20 + 1);

        while (sanctuaries > 0) {
            int x = rand.nextInt(length);
            int y = rand.nextInt(width);

            if (grid.get(x).get(y).getType() == CellEntityType.VOID) {
                grid.get(x).get(y).setType(CellEntityType.SANCTUARY);
                sanctuaries--;
            }
        }
    }

    // method that add a minimum of 4 enemies but using Random
    // the number might increase proportionally to the size of the grid
    private static void addEnemies(Grid grid) {
        Random rand = new Random();
        int length = grid.length;
        int width = grid.width;

        int enemies = 4 + rand.nextInt((width * length) / 15 + 1);

        while (enemies > 0) {
            int x = rand.nextInt(length);
            int y = rand.nextInt(width);

            if (grid.get(x).get(y).getType() == CellEntityType.VOID) {
                grid.get(x).get(y).setType(CellEntityType.ENEMY);
                enemies--;
            }
        }
    }

    // builds the grid as requested
    public static Grid generateGrid(int length, int width) {
        Grid currentGrid = new Grid(length, width);

        Random rand = new Random();

        // adding minimum 2 sanctuaries
        addSanctuaries(currentGrid);

        // adding a minimum of 4 enemies
        addEnemies(currentGrid);

        //adding 1 portal to the grid
        int neededCells = 1;
        while (neededCells > 0) {
            int x = rand.nextInt(length);
            int y = rand.nextInt(width);

            if (currentGrid.get(x).get(y).getType() == CellEntityType.VOID) {
                neededCells--;
                currentGrid.get(x).get(y).setType(CellEntityType.PORTAL);
            }
        }

        //adding the player to the grid and setting the currentCell as the one with the player
        neededCells = 1;
        while (neededCells > 0) {
            int x = rand.nextInt(length);
            int y = rand.nextInt(width);

            if (currentGrid.get(x).get(y).getType() == CellEntityType.VOID) {
                neededCells--;
                currentGrid.get(x).get(y).setType(CellEntityType.PLAYER);
                currentGrid.get(x).get(y).isVisited(true);
                currentGrid.currentCell = currentGrid.get(x).get(y);
            }
        }
        return currentGrid;
    }

    // generating the test grid with its specific cells
    public static Grid generateDemoGrid(int length, int width) {
        Grid currentGrid = new Grid(length, width);

        currentGrid.get(0).get(0).setType(CellEntityType.PLAYER);
        currentGrid.get(0).get(0).isVisited(true);
        currentGrid.currentCell = currentGrid.get(0).get(0);
        currentGrid.get(0).get(3).setType(CellEntityType.SANCTUARY);
        currentGrid.get(1).get(3).setType(CellEntityType.SANCTUARY);
        currentGrid.get(2).get(0).setType(CellEntityType.SANCTUARY);
        currentGrid.get(4).get(3).setType(CellEntityType.SANCTUARY);
        currentGrid.get(3).get(4).setType(CellEntityType.ENEMY);
        currentGrid.get(4).get(4).setType(CellEntityType.PORTAL);

        return currentGrid;
    }

    // method for moving north that checks if the move is possible and updates the cells after the move
    public void goNorth() throws ImpossibleMoveException {

        if ((currentCell.getOx() - 1) < 0) {
            throw new ImpossibleMoveException("Take care not to fall of the northen edge of the map!\n");
        }
        else {
            currentCell.setType(CellEntityType.VOID);
            currentCell = this.get(currentCell.getOx() - 1).get(currentCell.getOy());
            currentCell.isVisited(true);
            System.out.println("Going north \n");
        }
    }

    // method for moving south that checks if the move is possible and updates the cells after the move
    public void goSouth() throws ImpossibleMoveException {
        if ((currentCell.getOx() + 1) >= length) {
            throw new ImpossibleMoveException("Take care not to fall of the southern edge of the map!\n");
        }
        else{
            currentCell.setType(CellEntityType.VOID);
            currentCell = this.get(currentCell.getOx() + 1).get(currentCell.getOy());
            currentCell.isVisited(true);
            System.out.println("Going south \n");
        }
    }

    // method for moving west that checks if the move is possible and updates the cells after the move
    public void goWest() throws ImpossibleMoveException {
        if ((currentCell.getOy() - 1) < 0) {
            throw new ImpossibleMoveException("Take care not to fall of the western edge of the map!\n");
        }
        else {
            currentCell.setType(CellEntityType.VOID);
            currentCell = this.get(currentCell.getOx()).get(currentCell.getOy() - 1);
            currentCell.isVisited(true);
            System.out.println("Going west \n");
        }
    }

    // method for moving east that checks if the move is possible and updates the cells after the move
    public void goEast() throws ImpossibleMoveException {
        if ((currentCell.getOy() + 1) >= width) {
            throw new ImpossibleMoveException("Take care not to fall of the eastern edge of the map!\n");
        }
        else {
            currentCell.setType(CellEntityType.VOID);
            currentCell = this.get(currentCell.getOx()).get(currentCell.getOy() + 1);
            currentCell.isVisited(true);
            System.out.println("Going east \n");
        }
    }

    // method for printing the grid
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (ArrayList<Cell> row : this) {
            for (Cell cell : row) {
                result.append(cell.cellType()).append(" ");
            }
            result.append("\n");
        }

        return result.toString();
    }
}
