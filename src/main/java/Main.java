import integral.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Program oblicza wartość całki dla zadanej funkcji");
        final double MIN = 0;
        final double MAX  =15;

        IntegralParam integralParam = new IntegralParam.Builder(MIN, MAX)
                .integralMethod(IntegralMethods.RECTANGLE)
                .numberOfThreads(10)
                .numberOfSection(1000).build();

        OriginalFunction1 originalFunction1 = new OriginalFunction1();
        double exactValue = originalFunction1.value(MAX) - originalFunction1.value(MIN);
        System.out.println(" WARTOŚĆ DOKŁADNA (kalkulator całek online): 849.0290637385763");
        System.out.println(" WARTOŚĆ DOKŁADNA (funkcja pierwotna): " + exactValue);
        //  integralParam na wzorzez class builder !!!!!!!!!!!!!!!

        testAllIntegralMethods("ns", integralParam);
    }


    private static void testAllIntegralMethods(String timeUnit, IntegralParam integralParam) throws InterruptedException {
        long timeStart, timeStop;
        double result;
        for (IntegralMethods method : IntegralMethods.values()) {

//            integralParam.integralMethod = method;
            integralParam.setIntegralMethod(method);

            if(timeUnit.equals("ns")){
                timeStart = System.nanoTime();
            }else{
                timeStart = System.currentTimeMillis();
            }

            result = Integral.calculate(integralParam, new Function1());

            if(timeUnit.equals("ns")){
                timeStop = System.nanoTime();
            }else{
                timeStop = System.currentTimeMillis();
            }

            System.out.println(method.toString() + " : " + result + " time : " + (timeStop - timeStart) + " ["+timeUnit+"]");
        }

    }
    private static double roundTo(double value, int digits){
        BigDecimal bd = new BigDecimal(value).setScale(digits, RoundingMode.HALF_EVEN);
        return bd.doubleValue();
    }

}
