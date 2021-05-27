import java.util.ArrayList;
import java.util.List;

public class Lost {

    char[][] island;
    int[][] wheels;
    int johnPosX;
    int johnPosY;
    int katePosX;
    int katePosY;

    int nLines;
    int nColumns;

    public Lost(char[][] island, int[][] wheels, int johnPosX, int johnPosY, int katePosX, int katePosY) {
        this.island = island;
        this.wheels = wheels;
        this.johnPosX = johnPosX;
        this.johnPosY = johnPosY;
        this.katePosX = katePosX;
        this.katePosY = katePosY;
        this.nLines = island.length;
        this.nColumns = island[0].length;
    }

    private boolean isOnTopBorder(int vert) {
        return (0<=vert && vert<=(nColumns-1));
    }

    private boolean isOnBottomBorder(int vert) {
        return ((nLines*(nColumns-1))<=vert && vert<=((nLines*nColumns)-1));
    }

    private boolean isOnLeftBorder(int vert) {
        return vert%nColumns == 0;
    }

    private boolean isOnRightBorder(int vert) {
        return (vert-nColumns-1)%nColumns == 0;
    }

    private Edge getEdge(int destination, int direction, int weight) {
        switch (direction) {
            case 0: { //North
                int destLine = destination / nColumns;
                int destColumn = destination - destLine * nColumns;
                if (destLine == 0) return null;
                int src = (destColumn - 1) + destLine * nColumns;
                return new Edge(src, destination, weight);
            }
            case 1: { //South
                int destLine = destination / nColumns;
                int destColumn = destination - destLine * nColumns;
                if (destLine == nLines-1) return null;
                int src = (destColumn + 1) + destLine * nColumns;
                return new Edge(src, destination, weight);
            }
            case 2: { //West
                int destLine = destination / nColumns;
                int destColumn = destination - destLine * nColumns;
                if (destColumn == 0) return null;
                int src = destColumn + (destLine - 1) * nColumns;
                return new Edge(src, destination, weight);
            }
            case 3: { //East
                int destLine = destination/nColumns;
                int destColumn = destination - destLine*nColumns;
                if (destColumn == nColumns-1) return null;
                int src = destColumn + (destLine+1)*nColumns;
                return new Edge(src, destination, weight);
            }
            default: throw new RuntimeException("Invalid Direction: " + direction);
        }
    }

    private Edge[] computeJohnMoves() {
        List<Edge> m = new ArrayList<>();
        for(int l=0; l<nLines; l++) {
            for(int c=0; c<nColumns; c++) {
                switch(island[l][c]) {
                    case 'W':
                    case 'O':
                        continue;
                    case 'X':
                    case 'G':
                    default:
                        for(int i=0; i<4; i++){
                            Edge e = getEdge(nColumns*l + c, i, 1);
                            if (e!=null) m.add(e);
                        }

                        Integer wheelIndex = null;
                        try {
                            wheelIndex = Integer.parseInt(String.valueOf(island[l][c]));
                        }
                        catch (Exception ignored) {
                            wheelIndex = null;
                        }

                        if(wheelIndex!=null) {
                            int[] wheel = wheels[wheelIndex];
                            m.add(new Edge(nColumns*l + c, nColumns*wheel[0] + wheel[1], wheel[3]));
                        }

                        break;
                }
            }
        }
        return (Edge[]) m.toArray();
    }

    public int[] solve() {

    }
}
