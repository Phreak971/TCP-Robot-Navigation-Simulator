public class Messages {
    String robot_name;
    Position position;

    public String getRobot_name() {
        return robot_name;
    }

    public Position getPosition() {
        return position;
    }

    public String getDate() {
        return Date;
    }

    String Date;

    Messages(String name, Position position, String date) {
        this.Date = date;
        this.position = position;
        this.robot_name = name;
    }

}
