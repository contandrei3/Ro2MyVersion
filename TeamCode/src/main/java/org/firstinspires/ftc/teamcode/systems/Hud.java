package org.firstinspires.ftc.teamcode.systems;

import org.firstinspires.ftc.teamcode.declarations.RobotMap;
import org.firstinspires.ftc.teamcode.maths_and_systems.functions;

import com.bylazar.configurables.annotations.Configurable;

@Configurable
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
                double dist=functions.getdistance(r.Odo.getPose().getX(),r.Odo.getPose().getY());
                if (dist>2.5)
                {
                    CS=hudStatus.FAR;
                    break;
                }
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
