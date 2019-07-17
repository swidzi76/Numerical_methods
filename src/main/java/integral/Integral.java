package integral;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class Integral {
    private static double a = 0;
    private static double b = 0;
    private static int numberOfSection = 0;
    private static int numberOfThreads = 0;
    private static volatile double result = 0;

    public static double calculate(IntegralParam integralParam, Function function) throws InterruptedException {
        a = integralParam.a;
        b = integralParam.b;
        numberOfThreads = integralParam.numberOfThreads;
        numberOfSection = integralParam.numberOfSection;
        switch (integralParam.integralMethod){
            case RECTANGLE: return rectangleMethod(function);
            case RECTANGLE2: return rectangleMethod2(function);
            case RECTANGLE_WITH_THREADS: return rectangleMethodWithThreads(function);
        }
        return 0;
    };
    private static double rectangleMethodWithThreads(Function function) throws InterruptedException {
        if(numberOfThreads == 0) return 0;
        result = 0;
        ExecutorService threadPool = Executors.newFixedThreadPool(numberOfThreads);
        double dx = (b - a) / numberOfSection;
        for (int section = 0; section < numberOfSection; section++) {
            int finalSection = section;
            threadPool.submit(() -> addToResult(dx * function.value(a + finalSection * dx + dx/2)));
        }
        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
        return result;
    }
    private static synchronized void addToResult(double value){
        result+=value;
    }
    private static double rectangleMethod(Function function){
        double dx = (b - a) / numberOfSection;
        double sum = 0;
        for (int section = 0; section < numberOfSection; section++) {
            sum += dx * function.value(a + section * dx + dx/2);
        }
        return sum;
    };
    private static double rectangleMethod2(Function function){
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
            sum += dx * function.value(x+dx/2.0);
            x+=dx;
        }
        return sum;

    }
}

