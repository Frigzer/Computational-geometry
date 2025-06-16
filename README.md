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
