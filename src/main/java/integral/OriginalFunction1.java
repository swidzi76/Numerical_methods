package integral;

public class OriginalFunction1 implements Function {
    @Override
    public double value(double x) {
        return (3*(-Math.cos(x))) -
               ((1.0/4.0)*0.2*Math.pow(x,4)) +
               ((1.0/3.0)*3*Math.pow(x,3));
        }

}
