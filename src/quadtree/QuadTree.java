package quadtree;

import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;

public class QuadTree {
    Rectangle boundry;
    int capacity; // max no. of points
    ArrayList<Point> points;
    boolean divide = false;
    QuadTree northeast;
    QuadTree northwest;
    QuadTree southeast;
    QuadTree southwest;

    QuadTree parent;

    public QuadTree(Rectangle rect, int cap, QuadTree parent) {
        this.boundry = rect;
        this.capacity = cap;
        points = new ArrayList<>();
        this.parent = parent;
    }

    public QuadTree(Rectangle rect, int cap) {
        this(rect, cap, null);
    }

    public boolean insert(Point p) {
        if (!this.boundry.contains(p)) return false;
        if (this.points.size() < this.capacity) {
            this.points.add(p);
            return true;
        } else {
            if (!this.divide) {
                subDivide();
            }
            return (northeast.insert(p) ||
                    northwest.insert(p) ||
                    southeast.insert(p) ||
                    southwest.insert(p));
//            if (this.northeast.insert(p)) {
//                return true;
//            } else if (this.northwest.insert(p)) {
//                return true;
//            } else if (this.southeast.insert(p)) {
//                return true;
//            } else if (this.southwest.insert(p)) {
//                return true;
//            } else return false;
        }
    }

    public void subDivide() {
        double x = this.boundry.x;
        double y = this.boundry.y;
        double w = this.boundry.width;
        double h = this.boundry.height;
        this.northeast = new QuadTree(new Rectangle(x + w / 2f, y - h / 2, w / 2, h / 2), capacity, this);
        this.northwest = new QuadTree(new Rectangle(x - w / 2f, y - h / 2, w / 2, h / 2), capacity, this);
        this.southeast = new QuadTree(new Rectangle(x + w / 2f, y + h / 2, w / 2, h / 2), capacity, this);
        this.southwest = new QuadTree(new Rectangle(x - w / 2f, y + h / 2, w / 2, h / 2), capacity, this);
        this.divide = true;
    }
//
//    private boolean tryMove(Point p) {
//        if (boundry.contains(p)) {
//            insert(p);
//            return true;
//        }
//        if (parent != null) {
//            return parent.tryMove(p);
//        }
//        return false;
//    }
//
//    public void move(Point p) {
//        if (divide) {
//            northeast.move(p);
//            northwest.move(p);
//            southeast.move(p);
//            southwest.move(p);
//        }
//
//        if (boundry.contains(p) && points.contains(p)) return;
//
//        if (points.contains(p)) {
//            if (parent != null) {
//                if (parent.tryMove(p)) {
//                    points.remove(p);
//                    if (divide) {
//                        if (northeast.points.size() == 0 &&
//                                northwest.points.size() == 0 &&
//                                southwest.points.size() == 0 &&
//                                southeast.points.size() == 0) {
//                            divide = false;
//                        }
//                    }
//                }
//            }
//        }
//    }

    public void query(Rectangle range, ArrayList<Point> points) {
        if (range.intersects(this.boundry)) {
            for (Point point : this.points) {
                if (range.contains(point))
                    points.add(point);
            }
            if (this.divide) {
                this.northeast.query(range, points);
                this.northwest.query(range, points);
                this.southeast.query(range, points);
                this.southwest.query(range, points);
            }
        }

    }

    public void show(PApplet sketch) {
        sketch.stroke(255);
        sketch.strokeWeight(1);
        sketch.noFill();
        sketch.rectMode(PConstants.CENTER);
        sketch.rect((float) this.boundry.x, (float) this.boundry.y, (float) this.boundry.width * 2, (float) this.boundry.height * 2);
        if (this.divide) {
            this.northeast.show(sketch);
            this.northwest.show(sketch);
            this.southeast.show(sketch);
            this.southwest.show(sketch);
        }

        for (Point p : points) {
            sketch.stroke(255, 0, 0);
            sketch.point(p.x, p.y);
        }
    }
}
