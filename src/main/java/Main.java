import integral.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Program oblicza wartość całki dla zadanej funkcji");
        final double MIN = 0;
        final double MAX  =15;
        IntegralParam integralParam = new IntegralParam();
        integralParam.a = MIN;
        integralParam.b = MAX;
        integralParam.integralMethod = IntegralMethods.RECTANGLE;
        integralParam.numberOfThreads = 10;
        integralParam.numberOfSection = 1000;
        integralParam.integralMethod = IntegralMethods.RECTANGLE;
        System.out.println(integralParam.integralMethod.toString()+" : " + Integral.calculate(integralParam, new Function1()));

        integralParam.integralMethod = IntegralMethods.RECTANGLE2;
        System.out.println(integralParam.integralMethod.toString()+" : " + Integral.calculate(integralParam, new Function1()));

        integralParam.integralMethod = IntegralMethods.RECTANGLE_WITH_THREADS;
        System.out.println(integralParam.integralMethod.toString()+" : " + Integral.calculate(integralParam, new Function1()));
//        Function function1 = new Function1();
//        for (int i = 10; i <= 10000; i*=10) {
//            integralParam.numberOfSection = i;
//            integralParam.integralMethod = IntegralMethods.RECTANGLE;
//            System.out.println(Integral.calculate(integralParam, new Function1()));
//            integralParam.integralMethod = IntegralMethods.RECTANGLE2;
//            System.out.println(Integral.calculate(integralParam, new Function1()));
//            calculateIntegral(MIN, MAX, i);
//        }
        // ADDITIONAL TASKS
        // 1 - threads
//        final int NUMER_OF_SECTION = 1000;
//        System.out.println(" obliczanie całki dla " + NUMER_OF_SECTION + " przedziałów");
//        System.out.println("    1 wątek - główny");
//        long startTime = System.nanoTime();
//        double calculatedValue = integralRectangle(MIN, MAX, NUMER_OF_SECTION);
//        long endTime = System.nanoTime();
//        System.out.println(calculatedValue);
//        System.out.println(" Czas : " + (endTime - startTime) + " [ns]");
//
//        System.out.println("    10 wątków");
//        startTime = System.nanoTime();
//        calculatedValue = integralRectangleWithThreads(MIN, MAX, NUMER_OF_SECTION, 10);
//        endTime = System.nanoTime();
//        System.out.println(calculatedValue);
//        System.out.println(" Czas : " + (endTime - startTime) + " [ns]");
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
            sum += dx * function(x+dx/2.0);
            x+=dx;
        }
        return sum;
    }

    public static double integralRectangleWithThreads(double a, double b, int numberOfSection, int threads) throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(threads);
        final AtomicReference<Double> sum = new AtomicReference<Double>(0.0);
        final double dx = (b - a) / numberOfSection;
        double x = a;
        while(x < b){
            final double finalX = x;
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    synchronized(Main.class){
                        sum.set(sum.get() + function(finalX + dx / 2) * dx);
                    };
                }
            });
            x+=dx;
        }
        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.MINUTES);
        return sum.get();
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
