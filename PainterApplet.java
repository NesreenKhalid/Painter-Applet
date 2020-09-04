import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

public class PainterApplet extends Applet {

    Shape shape = new Line();
    String shapeType = "Line";
    boolean isFilled = false;
    Color color = Color.RED;

    Vector<Line> linesVector = new Vector();
    Vector<FilledShape> rectVector = new Vector();
    Vector<FilledShape> ovalsVector = new Vector();
    
    Button lineBtn, rectBtn, ovalBtn,
           redBtn, greenBtn, blueBtn,
           filledBtn, notFilledBtn,
           clearAllBtn;

    @Override public void init() {

        lineBtn = new Button("Line");
        rectBtn = new Button("Rectangle");
        ovalBtn = new Button("Oval");
        
        redBtn = new Button("Red");
        greenBtn = new Button("Green");
        blueBtn = new Button("Blue");
        
        filledBtn = new Button("Filled");
        notFilledBtn = new Button("Not Filled");
        clearAllBtn = new Button("Clear All");
        
        lineBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                shapeType = "Line";
                shape = new Line();
            }
        });
        rectBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                shapeType = "Rect";
                shape = new FilledShape();
            }
        });
        ovalBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                shapeType = "Oval";
                shape = new FilledShape();
            }
        });
        
        redBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                color = Color.RED;
            }
        });
        greenBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                color = Color.GREEN;
            }
        });
        blueBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                color = Color.BLUE;
            }
        });
        
        filledBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                isFilled = true;
            }
        });
        notFilledBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                isFilled = false;
            }
        });
        clearAllBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                linesVector.clear();
                rectVector.clear();
                ovalsVector.clear();
                
                repaint();
            }
        });
        
        this.addMouseListener(new MouseListener() {

            @Override public void mouseClicked(MouseEvent e) { }
            @Override public void mouseEntered(MouseEvent e) { }
            @Override public void mouseExited(MouseEvent e) { }

            @Override public void mousePressed(MouseEvent e) {
                shape.setX1(e.getX());
                shape.setY1(e.getY());
            }

            @Override public void mouseReleased(MouseEvent e) {
                
                switch (shapeType) {
                    case "Rect":
                        FilledShape tempRect = (FilledShape) shape;
                        tempRect.setFilled(isFilled);
                        tempRect.setColor(color);
                        rectVector.addElement(tempRect);
                        shape = new FilledShape();
                        break;
                    case "Oval":
                        FilledShape tempOval = (FilledShape) shape;
                        tempOval.setFilled(isFilled);
                        tempOval.setColor(color);
                        ovalsVector.addElement(tempOval);
                        shape = new FilledShape();
                        break;
                    default:
                        Line tempLine = (Line) shape;
                        tempLine.setColor(color);
                        linesVector.addElement(tempLine);
                        shape = new Line();
                        break;
                }
            }
        });

        this.addMouseMotionListener(new MouseMotionListener() {

            @Override public void mouseMoved(MouseEvent e) { }
            
           @Override public void mouseDragged(MouseEvent e) {
                if(shapeType.equals("Line")){
                    shape.setX2_width(e.getX());
                    shape.setY2_height(e.getY());
                }else{
                    shape.setX2_width(e.getX() - shape.getX1());
                    shape.setY2_height(e.getY() - shape.getY1());                
                }                
                repaint();
            }
        });        
        
        add(lineBtn);
        add(rectBtn);
        add(ovalBtn);
        
        add(redBtn);
        add(greenBtn);
        add(blueBtn);
        
        add(filledBtn);
        add(notFilledBtn);
        add(clearAllBtn);
    }

    @Override public void paint(Graphics g) {
        g.setColor(color);
        switch (shapeType) {
            case "Rect":
                if(isFilled) g.fillRect(shape.getX1(),
                                shape.getY1(),
                                shape.getX2_width(),
                                shape.getY2_height()
                                );
                else g.drawRect(shape.getX1(),
                                shape.getY1(),
                                shape.getX2_width(),
                                shape.getY2_height()
                                );
                break;
            case "Oval":
                 if(isFilled) g.fillOval(shape.getX1(),
                              shape.getY1(),
                              shape.getX2_width(),
                              shape.getY2_height()
                              );
                 else g.drawOval(shape.getX1(),
                                shape.getY1(),
                                shape.getX2_width(), 
                                shape.getY2_height()
                                );
                break;
            default:
                g.drawLine(shape.getX1(), 
                        shape.getY1(), 
                        shape.getX2_width(), 
                        shape.getY2_height()
                        );
                break;
        }

        for (int index = 0; index < linesVector.size(); index++) {            
            g.setColor(linesVector.get(index).getColor());
            g.drawLine(linesVector.get(index).getX1(),
                       linesVector.get(index).getY1(),
                       linesVector.get(index).getX2_width(),
                       linesVector.get(index).getY2_height());
        }
        
        for (int index = 0; index < rectVector.size(); index++) {
            g.setColor(rectVector.get(index).getColor());
            
            if(rectVector.get(index).isFilled()){
                g.fillRect(rectVector.get(index).getX1(),
                           rectVector.get(index).getY1(),
                           rectVector.get(index).getX2_width(),
                           rectVector.get(index).getY2_height());
            }else{
                g.drawRect(rectVector.get(index).getX1(),
                           rectVector.get(index).getY1(),
                           rectVector.get(index).getX2_width(),
                           rectVector.get(index).getY2_height());
            }
        }
        
        for (int index = 0; index < ovalsVector.size(); index++) {
            g.setColor(ovalsVector.get(index).getColor());
            
            if(ovalsVector.get(index).isFilled()){
                g.fillOval(ovalsVector.get(index).getX1(),
                       ovalsVector.get(index).getY1(),
                       ovalsVector.get(index).getX2_width(),
                       ovalsVector.get(index).getY2_height());
            }else{
                g.drawOval(ovalsVector.get(index).getX1(),
                           ovalsVector.get(index).getY1(),
                           ovalsVector.get(index).getX2_width(),
                           ovalsVector.get(index).getY2_height());
            }
        }
    }

}

abstract class Shape {

        int x1;
        int x2_width;
        int y1;
        int y2_height;
        Color color;

        public Shape() {
            this.color = Color.RED;
        }

        public Shape(int x1, int x2_width, int y1, int y2_height, Color color) {
            this.x1 = x1;
            this.x2_width = x2_width;
            this.y1 = y1;
            this.y2_height = y2_height;
            this.color = color;
        }

        public int getX1() {
            return x1;
        }

        public void setX1(int x1) {
            this.x1 = x1;
        }

        public int getX2_width() {
            return x2_width;
        }

        public void setX2_width(int x2_width) {
            this.x2_width = x2_width;
        }

        public int getY1() {
            return y1;
        }

        public void setY1(int y1) {
            this.y1 = y1;
        }

        public int getY2_height() {
            return y2_height;
        }

        public void setY2_height(int y2_height) {
            this.y2_height = y2_height;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }
        
        public void draw(){}

    }

class Line extends Shape {

    public Line(int x1, int x2_width, int y1, int y2_height, Color color) {
        super(x1, x2_width, y1, y2_height, color);
    }

    public Line() {        }

}

class FilledShape extends Shape {
    boolean filled;
    
    public FilledShape() {}

    public FilledShape(int x1, int x2_width, int y1, int y2_height,
            Color color, boolean filled) {
        super(x1, x2_width, y1, y2_height, color);
        this.filled = filled;
    }
    
    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

}
