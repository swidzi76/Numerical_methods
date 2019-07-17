package integral;

public class Function1 implements Function {
    @Override
    public double value(double x) {
        return (3*Math.sin(x)) -
                (0.2*Math.pow(x,3)) +
                (3*Math.pow(x,2));
    }
}
