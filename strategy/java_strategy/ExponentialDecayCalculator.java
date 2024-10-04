package java_strategy;

public class ExponentialDecayCalculator {
    private double a;
    private double W;
    
    public ExponentialDecayCalculator(double a, double W){
        this.a = a;
        this.W = W;
    }

    public ExponentialDecayCalculator(double a){
        this.a = a;
        this.W = 0;
    }

    public double calculate(double lastValue, double lastTime){
        double timeDiff = (System.currentTimeMillis() - lastTime) / (1000*60*60);
        // double timeDiff = (System.currentTimeMillis() - lastTime) / (1000);
        return lastValue * Math.exp(-a * timeDiff) + W;
    }
}
