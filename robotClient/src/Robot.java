import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class Robot extends Frame {

    private String robot_name;  //Sofia, Davy etc.
    private String server_address;  //localhost, 127.0.0.1 etc.
    private boolean connected;
    private int speed;  //7 for normal 15 for fast/high

    private Label robot_status;  //connected to server, disconnected etc.
    private Button toggle_mov_speed;    //normal, fast etc
    private Label last_message; // Latest Coordinates and Time
    private Label last_position;


    public Position getMyPosition() {
        return myPosition;
    }

    private Position myPosition;

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
        if (connected) {
            this.robot_status.setText(String.format("Connected (%s)", server_address));
            robot_status.setForeground(Color.GREEN);
        } else {
            this.robot_status.setText("Disconnected");
            robot_status.setForeground(Color.RED);
        }
    }

    public void setRobot_status(Label robot_status) { //sets robot connection status
        this.robot_status = robot_status;
    }

    public void setLast_message(Label last_message) {
        this.last_message = last_message;
    }

    Robot(String robot_name, String server_address) {   //Accepts name of robot and address of server
        this.connected = true;
        this.myPosition = new Position(10, 20);
        this.speed = 7; //speed is normal by default
        this.robot_name = robot_name;
        this.server_address = server_address;
        robot_display();
    }

    Robot(String robot_name, String server_address, boolean failure) {   //Accepts name of robot and address of server
        if (!failure)
            return;
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        setTitle(String.format("Client (%s)", robot_name));
        setSize(200, 200);
        create_label(String.format("%s could not", robot_name), new Rectangle(0, 75, 150, 30)).setForeground(Color.RED);
        create_label(String.format("connect %s", server_address), new Rectangle(0, 0, 150, 30)).setForeground(Color.RED);
        setVisible(true);
    }

    Button create_button(String text, Rectangle cord) {    //creates a new button
        Button btn = new Button(text);  //sets the buttons text
        btn.setBounds(cord);   //sets the coordinates of button on frame
        btn.setBackground(new Color(85, 173, 122)); //sets background color of button
        btn.setForeground(Color.WHITE);  //sets foreground color of button
        add(btn);   //add button to frame
        return btn; //return reference to button to use later
    }

    private void update_position() {
        last_position.setText(String.format("(%d, %d)", myPosition.getX(), myPosition.getY()));
    }

    public void update_last_messgae() {
        last_message.setText(String.format("Coordinates (%d,%d) : %s", myPosition.getX(), myPosition.getY(), DateTimeService.getDateAndTime()));
    }

    Label create_label(String text, Rectangle cord) { //creates a new label
        Label label = new Label(text);  //sets the label text
        label.setBounds(cord); //sets the coordinates of label on frame
        label.setForeground(Color.WHITE); //sets text color of label
        add(label); //adds label to the frame
        return label;   //return reference to label to later use
    }

    void robot_display() {

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        setTitle(String.format("Client (%s)", robot_name));
        setSize(450, 475);  //sets frame size
        setBackground(new Color(30, 93, 136));  //sets background color of frame

        //create label for showing robot name
        create_label("Robot Name: ", new Rectangle(80, 50, 100, 30));
        create_label(this.robot_name, new Rectangle(180, 50, 150, 30));

        //create label for showing robot's connection status with server
        create_label("Status: ", new Rectangle(80, 80, 100, 30));
        robot_status = create_label(String.format("Connected (%s)", this.server_address), new Rectangle(180, 80, 150, 30));
        robot_status.setForeground(Color.GREEN); //setting text color

        //create label for toggle speed button
        create_label("Toggle Speed", new Rectangle(80, 120, 100, 30));

        //create button to toggle robot movement speed
        toggle_mov_speed = create_button("Normal", new Rectangle(180, 120, 80, 30));
        toggle_mov_speed.addActionListener(new ActionListener() {   //create listener on click of button
            public void actionPerformed(ActionEvent e) {
                if (toggle_mov_speed.getLabel().equals("Normal")) {
                    toggle_mov_speed.setLabel("High");
                    speed = 15;
                } else {
                    toggle_mov_speed.setLabel("Normal");
                    speed = 7;
                }
                System.out.println(toggle_mov_speed.getLabel());
            }
        });
        //create buttons for movement
        //moves up
        create_button("Up", new Rectangle(180, 200, 80, 30))
                .addActionListener(e -> {
                    myPosition.setPosition(myPosition.getX(), myPosition.getY() - speed);
                    update_position();
                });
        //moves left
        create_button("Left", new Rectangle(80, 250, 80, 30))
                .addActionListener(e -> {
                    myPosition.setPosition(myPosition.getX() - speed, myPosition.getY());
                    update_position();
                });
        //moves right
        create_button("Right", new Rectangle(280, 250, 80, 30))
                .addActionListener(e -> {
                    myPosition.setPosition(myPosition.getX() + speed, myPosition.getY());
                    update_position();
                });

        //moves down
        create_button("Down", new Rectangle(180, 300, 80, 30))
                .addActionListener(e -> {
                    myPosition.setPosition(myPosition.getX(), myPosition.getY() + speed);
                    update_position();
                });

        //create label to show the last message sent to server and Time
        create_label("Last Message: ", new Rectangle(40, 350, 100, 30));
        last_message = create_label(String.format("Coordinates (%d,%d) : %s", myPosition.getX(), myPosition.getY(), DateTimeService.getDateAndTime()), new Rectangle(60, 375, 350, 30));

        create_label("Live Position", new Rectangle(40, 400, 100, 30));
        last_position = create_label(String.format("(%d, %d)", myPosition.getX(), myPosition.getY()), new Rectangle(60, 425, 100, 30));

        setLayout(null);
        setVisible(true);
    }
}