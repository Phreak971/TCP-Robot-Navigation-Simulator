import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;

public class RobotsArea extends Frame {


    private int collision_safety_margin;

    private Label tag_status;
    private Label status;
    private TextField txt_safety_margin;
    private LinkedList<Button> robotsList;
    private Label no_of_connected;
    private Label inDanger;

    public void addToRobotsList(Button robo) {
        robotsList.add(robo);
        update_robots();
    }

    public void removeFromRobotsList(Button robo) {
        robotsList.remove(robo);
        update_robots();
    }

    void update_robots() {
        no_of_connected.setText(String.valueOf(robotsList.size()));
    }

    RobotsArea() {
        robotsList = new LinkedList<Button>();
        collision_safety_margin = 15;
        serverDisplay();
    }

    void checkColliding(Button rec) {

        boolean danger = false;
        int skip = robotsList.indexOf(rec);
        int x = robotsList.get(skip).getBounds().x;
        int w = robotsList.get(skip).getBounds().width;
        int y = robotsList.get(skip).getBounds().y;
        int h = robotsList.get(skip).getBounds().height;
        for (int i = 0; i < robotsList.size(); i++) {
            if (i == skip) {
                continue;
            }
            int x1 = robotsList.get(i).getBounds().x;
            int w1 = robotsList.get(i).getBounds().width;
            int y1 = robotsList.get(i).getBounds().y;
            int h1 = robotsList.get(i).getBounds().height;
            System.out.println("Checking");
            System.out.println(robotsList.get(skip));
            System.out.println(robotsList.get(i));
            if (between(y1, y - h - collision_safety_margin, y + h + collision_safety_margin)) {
                if (Math.abs((x + w) - x1) <= collision_safety_margin || Math.abs((x1 + w1) - x) <= collision_safety_margin) {
                    inDanger.setText(String.format("%s, %s", robotsList.get(skip).getLabel(), robotsList.get(i).getLabel()));
                    danger = true;
                }
            }
            if (between(x1, x - w - collision_safety_margin, x + w + collision_safety_margin)) {
                if (Math.abs((y + h) - y1) <= collision_safety_margin || Math.abs((y1 + h1) - y) <= collision_safety_margin) {
                    inDanger.setText(String.format("%s, %s", robotsList.get(skip).getLabel(), robotsList.get(i).getLabel()));
                    danger = true;
                }
            }
        }
        if (!danger) {
            inDanger.setText("None");
        }
    }

    public boolean between(int i, int minValueInclusive, int maxValueInclusive) {
        return (i >= minValueInclusive && i <= maxValueInclusive);
    }


    Button create_button(String text, Rectangle cord) {    //creates a new button
        Button btn = new Button(text);  //sets the buttons text
        btn.setBounds(cord);   //sets the coordinates of button on frame
        btn.setBackground(new Color(85, 173, 122)); //sets background color of button
        btn.setForeground(Color.WHITE);  //sets foreground color of button
        return btn; //return reference to button to use later
    }

    Label create_label(String text, Rectangle cord) { //creates a new label
        Label label = new Label(text);  //sets the label text
        label.setBounds(cord); //sets the coordinates of label on frame
        label.setForeground(Color.BLACK); //sets text color of label
        label.setBackground(Color.WHITE);
        return label;   //return reference to label to later use
    }

    public Button create_robot(String name, int x, int y) {
        Button robo = new Button(name);
        robo.setBounds(x, y, 100, 30);
        robo.setBackground(Color.RED);
        robo.setForeground(Color.CYAN);
        robo.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getModifiers() == 16) {    //left click
                    status.setText(String.format("%s (%d, %d), (%d x %d)", robo.getLabel(), robo.getBounds().x, robo.getBounds().y, robo.getBounds().height, robo.getBounds().width));
                    tag_status.setVisible(true);
                    status.setVisible(true);
                } else if (e.getModifiers() == 4) {    //right click
                    //change color of robot randomly
                    Color[] arr = {Color.WHITE, Color.BLUE, Color.CYAN, Color.GRAY, Color.BLACK, Color.DARK_GRAY, Color.GREEN, Color.ORANGE, Color.PINK, Color.YELLOW};
                    int rnd = new Random().nextInt(arr.length);
                    robo.setBackground(arr[rnd]);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        add(robo);
        return robo;
    }

    private void serverDisplay() {

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        add(create_label("N", new Rectangle(430,30,25, 20)));
        setTitle("Server");
        setSize(900, 750);
        setBackground(new Color(30, 93, 136));  //sets background color of frame

        Button modify = create_button("Modify Safety Margin", new Rectangle(675, 710, 150, 30));

        txt_safety_margin = new TextField(String.valueOf(collision_safety_margin));
        txt_safety_margin.setBounds(830, 710, 40, 30);
        txt_safety_margin.setBackground(Color.GREEN);
        txt_safety_margin.setForeground(Color.BLACK);
        add(txt_safety_margin);

        modify.addActionListener(e -> {
            int a = 15;
            try {
                a = Integer.parseInt(txt_safety_margin.getText());
            } catch (Exception ee) {
                txt_safety_margin.setText(String.valueOf(collision_safety_margin));
            }
            if (a < 100) {
                collision_safety_margin = a;
            } else {
                txt_safety_margin.setText("Max=100");
            }
        });
        add(modify);

        add(create_label("Robots Connected", new Rectangle(20, 710, 120, 30)));
        no_of_connected = create_label(String.valueOf(robotsList.size()), new Rectangle(150, 710, 50, 30));
        add(no_of_connected);

        add(create_label("Danger", new Rectangle(230, 710, 80, 30)));
        inDanger = create_label("None", new Rectangle(315, 710, 120, 30));
        add(inDanger);
        add(tag_status = create_label("Status", new Rectangle(485, 675, 100, 30)));
        status = create_label("", new Rectangle(460, 710, 180, 30));
        add(status);
        tag_status.setVisible(false);
        tag_status.setVisible(false);
        setLayout(null);
        setVisible(true);

    }

}
