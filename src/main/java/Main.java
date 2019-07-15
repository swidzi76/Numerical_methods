import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {
    public static void main(String[] args) {
        System.out.println("Program oblicza wartość całki dla zadanej funkcji");
        final double MIN = 0;
        final double MAX  =15;
        for (int i = 10; i <= 1000; i*=10) {
            calculateIntegral(MIN, MAX, i);
        }
        // ADDITIONAL TASKS
        // 1 - threads

    }


    public static void calculateIntegral(double a, double b, int numberOfSection){
        System.out.println("Liczba przedziałów : " + numberOfSection);
        long startTime = System.nanoTime();
        double calculatedValue = integralRectangle(a,b,numberOfSection);
        long endTime = System.nanoTime();
        double preciseValue = originalFunction(b) - originalFunction(a);
        System.out.println("Wartość obliczona : " + calculatedValue);
        System.out.println("Wartość dokładna : " + preciseValue);
        System.out.println("Bład bezwzględny : " + roundTo(Math.abs(calculatedValue - preciseValue),4));
        System.out.println("Czas obliczenia : " + (endTime - startTime)+" [ns]");
    }
    public static double integralRectangle(double a, double b, int numberOfSection){
        if (a == b){
            return 0;
        }
        if (a > b ){
            double temp = a;
            a = b;
            b = temp;
        }
        double dx = (b -a) / numberOfSection;
        double x = a;
        double sum = 0;

        while(x < b){
            sum += dx * function(x+dx/2);
            x+=dx;
        }
        return sum;
    }
    private static double function(double x){
        return (3*Math.sin(x)) -
                (0.2*Math.pow(x,3)) +
                (3*Math.pow(x,2));
    }
    private static double originalFunction(double x){
        return (3*(-Math.cos(x))) -
                ((1.0/4.0)*0.2*Math.pow(x,4)) +
                ((1.0/3.0)*3*Math.pow(x,3));
    }
    private static double roundTo(double value, int digits){
        BigDecimal bd = new BigDecimal(value).setScale(digits, RoundingMode.HALF_EVEN);
        return bd.doubleValue();
    }

}
