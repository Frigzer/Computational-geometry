# Computational Geometry Project

This is a Java-based computational geometry project developed using the Swing GUI toolkit. The project consists of a series of lab assignments exploring key algorithms and concepts in computational geometry. It provides both visualizations and implementations of geometric computations.

## Technologies Used

- **Java** – Core programming language
- **Swing** – GUI framework for interactive visualizations
- **AWT** – For graphical rendering support
- **Java Collections Framework** – For data management and algorithm support

## Features

- Interactive visual interface for geometric algorithms
- Step-by-step visualizations of computations
- Modular design organized by lab assignments
- Real-time user input handling for drawing and testing

## Structure

Each lab introduces new computational geometry concepts and is implemented in a modular fashion. The project is structured to allow easy extension with new algorithms and visualizations.

---

## Lab 2 – Basic Geometric Primitives and Operations

This lab introduces foundational geometric structures and operations, with a focus on line equations, point-line relationships, and geometric transformations. All implementations are visualized using Java Swing for real-time interaction and debugging.

### Implemented Classes

- **Point**: Represents a point in 2D space with `x` and `y` coordinates. Implements a `Printable` interface for standardized output.
- **Line**: Represents a line or segment defined by two points. Stores its line equation in the form `y = ax + b` upon instantiation.

### Core Functionalities

- **Line Equation Calculation**: Given two points, the corresponding line equation is computed and displayed.
- **Point-on-Line Test**: Checks if a point lies on the infinite line defined by two points.
- **Point-on-Segment Test**: Determines if a point lies specifically on the finite segment.
- **Point Positioning**: Identifies whether a point is to the left, right, or exactly on a line.
- **Line Translation**: Applies a translation vector to move a line segment in space.
- **Point Reflection**: Computes the mirror image of a point with respect to a line.
- **Graphical Visualization**: Renders points, segments, and infinite lines with color-coded display and a coordinate grid in the `Panel` class.

### User Interface

- **Frame**: A basic Swing window initialized with a fixed size and a custom icon.
- **Panel**: Responsible for rendering all visual elements:
  - Cartesian grid and axis arrows
  - Distinction between infinite lines and finite segments
  - Colored points for input, result, and reference

### Example Use Case (from `Main.java`)

```java
Point p1 = new Point(4, 5);
Point p2 = new Point(10, 5);
Line l1 = new Line(p1, p2);
panel.drawPoint(p1, Color.YELLOW);
panel.drawPoint(p2, Color.YELLOW);
panel.drawLine(l1, Color.BLUE, true);

Point reflected = l1.reflectPoint(new Point(0, 0));
panel.drawPoint(reflected, Color.PINK);
```

---

## Lab 3 – Line Intersections, Point-to-Line Distance, and Triangle Construction

This lab expands on the previous foundational geometry structures by introducing line intersection detection, geometric distances, and triangle creation from line equations. It emphasizes both mathematical accuracy and graphical output through Swing.

### New Functionalities

#### 🔹 Line-Line Intersection
Two methods for computing the intersection point of lines were implemented:
- **From general form**: Computes the intersection using the standard line form `Ax + By + C = 0`.
- **From endpoints**: Determines the intersection by evaluating lines defined by pairs of points.

```java
Point intersection1 = line1.findIntersectionPoint(line2);
Point intersection2 = line1.findIntersectionFromGeneralForm(line3);
```

#### 🔹 Point-to-Line Distance

Implements a method to compute the shortest distance from a point to a line (orthogonal projection length):

```java
double distance = line.getPointLineDistance(point);
```

This function returns 0 if the point lies on the line.

#### 🔹 Triangle from Three Lines

Introduces a new class: `Triangle`, which constructs a triangle object from the intersection points of three non-parallel lines.

```java
Triangle triangle = new Triangle(line1, line2, line3);
panel.drawTriangle(triangle, Color.RED);
```

Validation is performed to ensure:

* All lines intersect pairwise.
* The triangle's vertices are distinct and non-colinear.

### Enhancements in Visualization

* **`Panel` class updates**:

  * Draws filled triangles using intersection points.
  * Displays coordinates and axes with dynamic numeric labels.
  * Robust error handling for degenerate inputs (e.g., parallel lines).
* **Improved Graphics**:

  * `Graphics2D` for better rendering quality.
  * Real-time drawing of intersection points, distance anchor points, and resulting triangle geometry.

### Example Scenario

```java
Point[] points = { new Point(5, 7), new Point(-5, 3), ... };
Line l1 = new Line(points[0], points[1]);
Line l2 = new Line(points[2], points[3]);

Point p = l1.findIntersectionPoint(l2);
double d = l2.getPointLineDistance(new Point(-8, -10));
Triangle triangle = new Triangle(l1, l2, l3);
```

All elements are visualized with unique color codes to help differentiate between constructs.

---

