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

## Lab 4 – Triangle Area, Point Inclusion, and Polygon Representation

This lab enhances the geometry engine with support for triangle area computation, advanced point-in-shape tests, and complete polygon handling with inclusion checks and drawing support.

### Triangle Functionality

#### Area Calculation
Implements the Heron's formula to compute triangle area using its three side lengths:

```java
double area = triangle.calculateArea();
```

#### Point Inclusion (Two Methods)

1. **Line-based test**: Checks on which side of each triangle edge the test point lies. If consistent, the point is inside.
2. **Area-based test**: Sums the areas of three triangles formed between the test point and triangle edges; if it matches the total area, the point is inside.

```java
triangle.containsPointByLines(testPoint);
triangle.containsPointByArea(testPoint);
```

### Area Between Two Lines

This functionality is interpreted as computing the angle between two line segments and drawing them for visual analysis:

```java
double angle = line1.angleBetweenLines(line2);
```

It uses vector dot product and arccos to compute the angle in degrees.

---

### Polygon Support

#### Polygon Class

A new `Polygon` class supports creation from an arbitrary number of points. Edges are automatically built into `Line` objects.

#### Point Inclusion in Polygon

Uses a ray-casting approach in four directions (left, right, top, bottom). If all ray intersections yield an odd count, the point is inside.

```java
polygon.containsPoint(testPoint);
```

> Edge cases such as colinearity are handled by detecting if a point lies exactly on a polygon edge.

#### Rendering

Polygons are rendered as filled shapes with colored vertices and boundary. Points used to define the polygon are visualized distinctly for clarity.

```java
panel.drawPolygon(polygon, Color.BLUE);
```

---

### Example Usage (from `Main.java`)

```java
Triangle triangle = new Triangle(p1, p2, p3);
triangle.print();
triangle.containsPointByLines(testPoint);
triangle.containsPointByArea(testPoint);

Polygon polygon = new Polygon();
polygon.addPoint(p1); polygon.addPoint(p2); ...
polygon.containsPoint(testPoint);
```

The results are visible both via printed output and graphical rendering in the Swing GUI.

---

## Lab 5 – Convex Hull Algorithms and Collision Simulation

This lab focuses on two major computational geometry tasks:
1. Computing convex hulls using two classic algorithms.
2. Simulating and detecting collisions between a spaceship and high-velocity missiles in a 2D space.

---

### Task 1 – Convex Hull Algorithms

#### Implemented Algorithms

- **Jarvis March (Gift Wrapping)**:
  Iteratively selects the leftmost point and wraps around the set by selecting the most counter-clockwise point.

```java
convexHull.calculateJarvis();
```

* **QuickHull**:
  Recursive divide-and-conquer algorithm inspired by QuickSort. Starts with extreme points, recursively builds the hull by finding farthest points from a dividing line.

  ```java
  convexHull.calculateQuickHull();
  ```

#### Point File Format

Points are read from a `.txt` file with the following structure:

```
[number of points]
x1 y1
x2 y2
...
```

Custom parser reads these into a list of `Point` objects.

#### Visual Comparison

Both algorithms are applied to each input dataset and rendered side-by-side using distinct color-coded convex hull edges and input points for easy comparison.

```java
ConvexHull convexHull = new ConvexHull("shape.txt");
convexHull.calculateQuickHull();
panel.drawConvexHull(convexHull);
```

---

### Task 2 – Spacecraft Collision Detection

#### Scenario

The battlefield is a 1000×1000 grid. A spaceship moves through space, and missiles are launched by the enemy. The goal is to:

* Simulate movement over 2 seconds (updated every 0.1s)
* Track missiles' trajectory and detect collisions with the spaceship
* Visualize the event using the ship’s convex hull and explosion indicators

#### Input Files

* **`craft.txt`** – Static point cloud defining the ship's shape.
* **`space_craft.txt`** – Initial position and velocity vector of the ship.
* **`missiles.txt`** – List of missiles in the format:

  ```
  [spawnTime] [x0] [y0] [dx] [dy]
  ```

Each missile has its own velocity and appears at a specified simulation time.

#### Core Mechanics

* **Dynamic positioning**: The ship is rotated and translated based on its velocity vector.
* **Bounding detection**: Collision is checked using the ship's convex hull and geometric distance between a missile and the hull sides.
* **Explosions**: Upon collision, a graphical explosion is drawn, and the missile is removed.

```java
spaceship.setPosition("space_craft.txt");
panel.drawConvexHull(spaceship);

List<Point> missiles = loadMissile("missiles.txt");

Timer timer = new Timer(10, e -> {
    spaceship.setInMotion("space_craft.txt");
    for (Point missile : missiles) {
        if (missile.spawnTime == time) {
            panel.drawPoint(missile, Color.RED);
            missile.spawned = true;
        }
        spaceship.detectCollision(missile, panel, time);
    }
    setInMotion(missiles, panel);
});
timer.start();
```

#### Visual Output

* **Convex hull**: Updated every frame to follow ship motion
* **Missiles**: Tracked in real-time
* **Explosions**: Shown at the exact moment and location of collision

---

### File Structure Summary

* `ConvexHull.java`: Implements both convex hull algorithms and collision logic
* `Panel.java`: Handles all rendering, including hulls, points, explosions
* `Main.java`: Coordinates simulation logic and I/O
* `Point`, `Line`, `Triangle`, `Polygon`: Core geometry types
* `Frame.java`: Sets up the main application window using Swing

---

## Lab 6 – Range Tree (1D & 2D)

This lab introduces the implementation of **Range Trees**, a data structure used for efficient multi-dimensional range queries. Two variants are implemented: 1D and 2D.

---

### 1D Range Tree

#### Features

- Builds a balanced binary search tree on a set of 1D integer points
- Allows fast querying for points within a specified interval

#### Building the Tree

Points are first sorted, then inserted recursively using the median split:

```java
int[] points = {49, 37, 3, 10, 19, ...};
RangeTree1D tree = new RangeTree1D();
tree.build(points);
```

#### Range Query

```java
List<Integer> result = tree.queryRange(25, 90);
```

The query visits only necessary subtrees using BST logic.

#### Complexity

* **Time (worst-case)**: `O(n)`
* **Time (average)**: `O(log n + k)` where `k` is the number of points in range
* **Space**: `O(n)` for nodes, assuming balanced structure

#### Visualization

Tree is visualized using **GraphStream**, with:

* Nodes positioned by depth
* Leaves marked visually
* Optional styles applied via external `style.css`

```java
Main1D.visualize(tree);
```

---

### 2D Range Tree

#### Features

* Tree is built by first sorting points by **x-coordinate**
* Each node contains a full **YTree**, sorted by **y-coordinate**
* Allows efficient querying in a 2D rectangular region

#### Building the Tree

```java
int[][] points = {
  {3, 8}, {4, 2}, {1, 5}, {6, 7}, ...
};
RangeTree2D tree = new RangeTree2D();
tree.build(points);
```

Each `Node` contains a 1D Y-tree for fast vertical queries.

#### Range Query

```java
List<RangeTree2D.Node> result = tree.queryRange(xStart, xEnd, yStart, yEnd);
```

YTree is queried only for subtrees where x-coordinates fall within bounds.

#### Complexity

* **Time (worst-case)**: `O(n)`
* **Time (average)**: `O(log² n + k)`
* **Space**: `O(n log n)` due to auxiliary Y-trees at each node

#### Visualization

Each 2D tree node opens a separate Y-tree window:

* X-tree is displayed with coordinates labeled `(x, y)`
* Y-tree is dynamically rendered per node using GraphStream

```java
Main.visualizeXTree(tree);
```

#### Memory Usage Insight

Using datasets of increasing size (20 to 100 points), memory consumption rises due to:

* Extra Y-trees built per X-node
* Graph rendering for each subtree
  Ideal for demonstrating space-time tradeoffs in geometric data structures.

---

### Key Classes

* `RangeTree1D`, `RangeTree2D`: Data structures with recursive construction
* `YTree`: Subtree for vertical range filtering
* `Main1D.java`, `Main.java`: Execute and visualize 1D/2D cases
* `GraphStream`: For interactive UI visualization of structure and queries

---