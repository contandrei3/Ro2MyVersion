package org.firstinspires.ftc.teamcode.systems;

import org.firstinspires.ftc.teamcode.declarations.RobotMap;

public class Hud {

    public enum hudStatus {
        INITIALIZE,
        CLOSE,
        FAR,
    }

    public hudStatus CS = hudStatus.INITIALIZE;
    double close=0.95;
    double far=0.75;

    //double middle=0.8;
    public void update(RobotMap r) {
        switch (CS){
            case INITIALIZE: {
                r.hud.setPosition(0.5);
                break;
            }
            case CLOSE:{
                r.hud.setPosition(close);
                break;
            }
            case FAR:{
                r.hud.setPosition(far);
                break;
            }
        }

    }
}
