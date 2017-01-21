package fractrace;

public final class Vector {
	public double x;
	public double y;
	public double z;
	
	private static double mod(double a, double b) {
		double c = Math.ceil((a / b));
		double d = c * b;
		return a - d;
	}
	
	public Vector()
	{
		x = y = z = 0.0;
	}
	public Vector(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static Vector add(Vector a, Vector o)
	{
		return new Vector(
				a.x + o.x,
				a.y + o.y,
				a.z + o.z);
	}
	public static Vector sub(Vector a, Vector o)
	{
		return new Vector(
				a.x - o.x,
				a.y - o.y,
				a.z - o.z);
	}
	public static Vector mul(Vector a, Vector o)
	{
		return new Vector(
				a.x * o.x,
				a.y * o.y,
				a.z * o.z);
	}
	public static Vector div(Vector a, Vector o)
	{
		return new Vector(
				a.x / o.x,
				a.y / o.y,
				a.z / o.z);
	}

	public static Vector add(Vector a, double o) {
		return new Vector(
				a.x + o,
				a.y + o,
				a.z + o);
	}
	public static Vector sub(Vector a, double o) {
		return new Vector(
				a.x - o,
				a.y - o,
				a.z - o);
	}
	public static Vector mul(Vector a, double o) {
		return new Vector(
				a.x * o,
				a.y * o,
				a.z * o);
	}
	public static Vector div(Vector a, double o) {
		return new Vector(
				a.x / o,
				a.y / o,
				a.z / o);
	}

	public static Vector add(double a, Vector b){
		return new Vector(
			a + b.x,
			a + b.y,
			a + b.z
		);
	}
	public static Vector sub(double a, Vector b){
		return new Vector(
			a - b.x,
			a - b.y,
			a - b.z
		);
	}
	public static Vector mul(double a, Vector b){
		return new Vector(
			a * b.x,
			a * b.y,
			a * b.z
		);
	}
	public static Vector div(double a, Vector b){
		return new Vector(
			a / b.x,
			a / b.y,
			a / b.z
		);
	}
	public static Vector mod(Vector a, Vector b) {
		return new Vector(
				mod(a.x, b.x),
				mod(a.y, b.y),
				mod(a.z, b.z));
	}
	public static Vector mod(Vector a, double b) {
		return new Vector(
				mod(a.x, b),
				mod(a.y, b),
				mod(a.z, b));
	}
	public static Vector mod(double a, Vector b) {
		return new Vector(
				mod(a, b.x),
				mod(a, b.y),
				mod(a, b.z));
	}
	
	public static double dot(Vector a, Vector b) {
		return a.x * b.x + a.y * b.y + a.z * b.z;
	}
	
	public double magnitude() {
		return Math.sqrt(x * x + y * y + z * z);
	}
	
	public static Vector min(Vector a, Vector b) {
		return new Vector(
				Math.min(a.x, b.x),
				Math.min(a.y, b.y),
				Math.min(a.z, b.z));
	}
	public static Vector max(Vector a, Vector b) {
		return new Vector(
				Math.max(a.x, b.x),
				Math.max(a.y, b.y),
				Math.max(a.z, b.z));
	}
	public static Vector clamp(Vector a, double min, double max) {
		return max(min(a, new Vector(max, max, max)), new Vector(min, min, min));
	}
	
	public Vector negate() {
		return mul(this, -1);
	}
	public static Vector normalize(Vector v) {
		return mul(v, 1 / v.magnitude());
	}
	public static Vector cross(Vector a, Vector b) {
		return new Vector(
			a.y * b.z - a.z * b.y,
			a.z * b.x - a.x * b.z,
			a.x * b.y - a.y * b.x);
	}
	
	public static Vector rotate(Vector v, Vector axis, double angle) {
		final double costheta = Math.cos(angle);
		final double sintheta = Math.sin(angle);
		
		final Vector v1 = mul(v, costheta);
		final Vector v2 = mul(cross(axis, v), sintheta);
		final Vector v3 = mul(axis, dot(axis, v) * (1 - costheta));
		
		return add(v1, add(v2, v3));
	}
	
	public int toRGB() {
		final int xval = (int)(x * 255) & 255;
		final int yval = (int)(y * 255) & 255;
		final int zval = (int)(z * 255) & 255;
		
		return (zval) | (yval << 8) | (xval << 16);
	}
	
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
