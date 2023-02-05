package eliascregard.util;

public class Vector2 {

    public double x;
    public double y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2() {
        this(0, 0);
    }

    public void set(Vector2 vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 makeCopy() {
        return new Vector2(this.x, this.y);
    }

    public void scale(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }

    public Vector2 scaled(double scalar) {
        return new Vector2(this.x * scalar, this.y * scalar);
    }

    public void negate() {
        this.scale(-1);
    }
    public Vector2 negated() {
        return this.scaled(-1);
    }

    public void add(Vector2 otherVector, double scalar) {
        this.x += otherVector.x * scalar;
        this.y += otherVector.y * scalar;
    }

    public void add(Vector2 otherVector) {
        this.add(otherVector, 1);
    }

    public Vector2 sum(Vector2 otherVector) {
        Vector2 newVector = this.makeCopy();
        newVector.add(otherVector, 1);
        return newVector;
    }

    public void subtract(Vector2 otherVector, double scalar) {
        this.x -= otherVector.x * scalar;
        this.y -= otherVector.y * scalar;
    }

    public void subtract(Vector2 otherVector) {
        this.subtract(otherVector, 1);
    }

    public static Vector2 difference(Vector2 vector1, Vector2 vector2) {
        Vector2 newVector = new Vector2(vector1.x, vector1.y);
        newVector.subtract(vector2);
        return newVector;
    }

    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public Vector2 normalized() {
        if (this.x == 0 && this.y == 0) {
            return this;
        }
        double length = this.length();
        return new Vector2(this.x / length, this.y / length);
    }
    public void normalize() {
        this.set(this.normalized());
    }

    public void setDirection(double angle) {
        this.set(angleToVector(angle, this.length()));
    }
    public double getDirection() {
        return vectorToAngle(this);
    }
    public void rotate(double angle) {
        double newAngle = angle + this.getDirection();
        this.setDirection(newAngle);
    }

    public double dotProduct(Vector2 otherVector) {
        return this.x * otherVector.x + this.y * otherVector.y;
    }

    public double crossProduct(Vector2 otherVector) {
        return this.x * otherVector.y - this.y * otherVector.x;
    }

    public static double vectorToAngle(Vector2 vector) {
        return Math.atan2(vector.y, vector.x);
    }

    public static Vector2 angleToVector(double angle, double length) {
        Vector2 vector = new Vector2(Math.cos(angle), Math.sin(angle));
        return vector.scaled(length);
    }
    public static Vector2 angleToVector(double angle) {
        return angleToVector(angle, 1);
    }

    public static double distance(Vector2 vector1, Vector2 vector2) {
        return difference(vector1, vector2).length();
    }
    public double distance(Vector2 vector2) {
        return distance(this, vector2);
    }

    public boolean equals(Vector2 otherVector) {
        System.out.println("HI");
        return this.x == otherVector.x && this.y == otherVector.y;
    }
    public String toString() {
        return "x: " + this.x + ", y: " + this.y;
    }
}
