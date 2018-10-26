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
            for (int j = 0; j < faceFeatures[i].length; j++) {
                faceFeatures[i][j] = str;
            }
        }
    }

    public void fillRow(String str, int row) {
        for (int j = 0; j < faceFeatures[row].length; j++) {
            faceFeatures[row][j] = str;
        }
    }

    public void fillColumn(String str, int column) {
        for (int i = 0; i < faceFeatures.length; i++) {
            faceFeatures[i][column] = str;
        }
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
