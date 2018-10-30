public class Runner {
    public static void main(String[] args) {
        String[][] faceFeatures = new String[30][30];

        JackOLantern jackOLantern = new JackOLantern(faceFeatures);
        jackOLantern.fill("_");
        //jackOLantern.fillRow("A", 12);
        //jackOLantern.fillColumn("B", 20);
        //jackOLantern.graphFunction("C", "y = x");
        //jackOLantern.graphFunction("D", "y = 2*x");
        jackOLantern.graphFunction("E", "y = x^2");
        jackOLantern.graphFunction("F", "y = x^(1/2)");
        jackOLantern.graphFunction("G", "y = (x - 5)^(1/2)");
        //jackOLantern.graphFunction("H", "y = (13^2 - ((x - 13)^2))^(1/2) + 13");
        //jackOLantern.graphFunction("I", "y = -1*(13^2 - ((x - 13)^2))^(1/2) + 13");
        //jackOLantern.graphFunction("J", "y = x^2");
        System.out.print(jackOLantern);
    }
}
