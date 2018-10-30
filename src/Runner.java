public class Runner {
    public static void main(String[] args) {
        String[][] faceFeatures = new String[30][100];

        JackOLantern jackOLantern = new JackOLantern(faceFeatures);
        jackOLantern.fill("　");
        //jackOLantern.graphFunction("A", "y = x^(1/2)");
        //jackOLantern.graphFunction("B", "y = 2*x");
        //jackOLantern.graphFunction("C", "y = x^2");
        jackOLantern.graphFunction("ロ", "y = (13^2 - (x - 13)^2)^(1/2) + 13");
        jackOLantern.graphFunction("ロ", "y = -1*(13^2 - (x - 13)^2)^(1/2) + 13");
        //jackOLantern.graphFunction("E", "y = x^2");
        System.out.print(jackOLantern);
    }
}
