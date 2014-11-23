package Shadow.Util.Math;

/*
 * Think of something clever to do with this later
 */
public class Line {
    private Point ep0, ep1; //endpoints
    private int yoffs;
    
    public Line(Point ep0, Point ep1) {
        this.ep0 = ep0;
        this.ep1 = ep1;
    }
    
    public Line(double x0, double y0, double x1, double y1) {
        this(new Point(x0, y0), new Point(x1, y1));
    }
    
    /*
     * Finds the point where these two lines have sex
     */
    public static Point solveSystem(Line l0, Line l1) {
        return null;
    }
}
