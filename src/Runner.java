public class Runner {
    public static void main(String[] args) {
        String[][] faceFeatures = new String[6][8];

        JackOLantern jackOLantern = new JackOLantern(faceFeatures);
        jackOLantern.fill("█");
        jackOLantern.edit("Ｘ", 5, 4);
        jackOLantern.fillRow("Ｏ", 2);
        jackOLantern.fillColumn("Ｒ", 3);
        System.out.print(jackOLantern);
    }
}
