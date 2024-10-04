package KMeans.MyMethod;


public class Point implements Comparable<Point> {
    private double temp;

    public Point(double temp) {
        this.temp = temp;
    }


    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    @Override
    public int compareTo(Point o) {
        int compare = Double.compare(this.temp, o.temp);
        return compare;
    }
}
