package org.firstinspires.ftc.teamcode;

public class Hud {

    public enum hudStatus {
        INITIALIZE,
        CLOSE,
        FAR,
        MIDDLE,
    }

    public hudStatus CS = hudStatus.INITIALIZE;
    double close=0.8;
    double far=0.8;
    double middle=0.8;
    public void update(RobotMap r) {
        if (CS == hudStatus.INITIALIZE) {
            r.hud.setPosition(0.5);
        } else if (CS == hudStatus.CLOSE) {
            r.hud.setPosition(close);
        } else if (CS == hudStatus.FAR) {
            r.hud.setPosition(far);
        } else if (CS==hudStatus.MIDDLE) {
            r.hud.setPosition(middle);
        }
    }
}
