package org.firstinspires.ftc.teamcode.systems;

import org.firstinspires.ftc.teamcode.declarations.RobotMap;
import org.firstinspires.ftc.teamcode.declarations.globals;
import org.firstinspires.ftc.teamcode.maths_and_systems.functions;

import com.bylazar.configurables.annotations.Configurable;

@Configurable
public class Hud {

    public enum hudStatus {

        CLOSE,

    }

    public hudStatus CS = hudStatus.CLOSE;

    //double far=globals.hudangle;

    //double middle=0.8;
    public void update(RobotMap r) {
        switch (CS){

            case CLOSE:{
                double close=globals.hudangle;
                //double dist=functions.getdistance(r);
                /*if (dist>2.5)
                {
                    CS=hudStatus.FAR;
                    break;
                }*/
                r.hud.setPosition(close);
                break;
            }

        }

    }
}
