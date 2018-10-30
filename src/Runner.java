public class Runner {
    public static void main(String[] args) {
        String[][] faceFeatures = new String[27][27];

        JackOLantern jackOLantern = new JackOLantern(faceFeatures);
        jackOLantern.fill("□");
        //jackOLantern.fillRow("A", 12);
        jackOLantern.fillColumn("▣", 13);
        //jackOLantern.graphFunction("C", "y = x");
        //jackOLantern.graphFunction("D", "y = 2*x");
        //jackOLantern.graphFunction("E", "y = x^(1/2)");
        //jackOLantern.graphFunction("F", "x = x^(1/2)");
        jackOLantern.graphFunction("▣", "x = 0.025 * (x - 13)^2 + 6");
        jackOLantern.graphFunction("▣", "x = -0.025 * (x - 13)^2 + 20");
        jackOLantern.graphFunction("■", "y = (13^2 - ((x - 13)^2))^.5 + 13");
        jackOLantern.graphFunction("■", "y = -1 * (13^2 - ((x - 13)^2))^.5 + 13");
        jackOLantern.graphFunction("■", "y = -1 * (13^2 - ((x - 13)^2))^.5 + 20", 6, 20);

        System.out.print(jackOLantern);
    }
}
