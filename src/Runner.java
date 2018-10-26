public class Runner {
    public static void main(String[] args) {
        String[][] faceFeatures = new String[6][8];

        JackOLantern jackOLantern = new JackOLantern(faceFeatures);
        jackOLantern.fill("ア");
        jackOLantern.edit("イ", 5, 5);
        jackOLantern.fillRow("ウ", 2);
        jackOLantern.fillColumn("オ", 3);
        System.out.print(jackOLantern);
    }
}
