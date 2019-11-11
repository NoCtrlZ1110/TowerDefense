package TowerDefense;
public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point["+getX()+","+getY()+"]";
    }
    public double getDistance (Point p2)
    {
        return Math.sqrt((this.getX()-p2.getX())*(this.getX()-p2.getX())+(this.getY()-p2.getY())*(this.getY()-p2.getY()));
    }
}

