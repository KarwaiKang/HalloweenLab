public class JackOLantern {
    private String[][] faceFeatures;

    public JackOLantern(String[][] faceFeatures) {
        this.faceFeatures = faceFeatures;
    }

    public void edit(String replace, int row, int column) {
        faceFeatures[row][column] = replace;
    }

    public void fill(String str) {
        for (int i = 0; i < faceFeatures.length; i++) {
            for (int j = 0; j < faceFeatures[i].length; j++)
                edit(str, i, j);
        }
    }

    public void fillRow(String str, int row) {
        for (int j = 0; j < faceFeatures[row].length; j++)
            edit(str, row, j);
    }

    public void fillColumn(String str, int column) {
        for (int i = 0; i < faceFeatures.length; i++)
            edit(str, i, column);
    }

    public String toString() {
        String out = "";
        for (int i = 0; i < faceFeatures.length; i++) {
            for (int j = 0; j < faceFeatures[i].length; j++)
                out += faceFeatures[i][j];
            out += "\n";
        }
        return out;
    }
}
