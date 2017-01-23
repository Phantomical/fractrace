package fractrace;

public final class TraceData {
    public final double threshold;
    public double currentDist;
    public double maxdist;
    public  Ray ray;
    public Traceable[] possible;

    public TraceData(
        double threshold,
        double currentDist,
        double maxdist,
        Ray ray,
        Traceable[] possible
    ) {
        this.threshold = threshold;
        this.currentDist = currentDist;
        this.maxdist = maxdist;
        this.ray = ray;
        this.possible = possible;
    }
}
