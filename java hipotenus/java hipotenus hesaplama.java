import java.util.Scanner;

public class HypotenuseCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("İlk kenarı girin: ");
        double side1 = scanner.nextDouble();

        System.out.print("İkinci kenarı girin: ");
        double side2 = scanner.nextDouble();

        double hypotenuse = calculateHypotenuse(side1, side2);

        System.out.println("Hipotenüs: " + hypotenuse);

        scanner.close();
    }

    public static double calculateHypotenuse(double side1, double side2) {
        return Math.sqrt(Math.pow(side1, 2) + Math.pow(side2, 2));
    }
}
