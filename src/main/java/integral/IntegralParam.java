package integral;

public class IntegralParam{
    private final double a;
    private final double b;
    private final int numberOfSection;
    private IntegralMethods integralMethod;
    private final int numberOfThreads;

    public static class Builder{
        private final double a;
        private final double b;

        private int numberOfSection = 100;
        private IntegralMethods integralMethod = IntegralMethods.RECTANGLE;
        private int numberOfThreads = 8;

        public Builder(double a, double b) {
            this.a = a;
            this.b = b;
        }
        public Builder numberOfSection(int value){
            numberOfSection = value;
            return this;
        }
        public Builder integralMethod(IntegralMethods value){
            integralMethod = value;
            return this;
        }
        public Builder numberOfThreads(int value){
            numberOfThreads = value;
            return this;
        }
        public IntegralParam build(){
            return new IntegralParam(this);
        }
    }
    private IntegralParam(Builder builder){
        a = builder.a;
        b = builder.b;
        numberOfSection = builder.numberOfSection;
        integralMethod = builder.integralMethod;
        numberOfThreads = builder.numberOfThreads;
    }
    public void setIntegralMethod(IntegralMethods value){
        integralMethod = value;
    }
    public IntegralMethods getIntegralMethod(){
        return integralMethod;
    }
    public double getA(){
        return a;
    }
    public double getB(){
        return b;
    }
    public int getNumberOfSection(){
        return numberOfSection;
    }
    public int getNumberOfThreads(){
        return numberOfThreads;
    }

}
