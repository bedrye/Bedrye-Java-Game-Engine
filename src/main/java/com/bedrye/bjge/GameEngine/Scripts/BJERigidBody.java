package com.bedrye.bjge.GameEngine.Scripts;

import com.bedrye.bjge.GameEngine.EngineManager;
import com.bedrye.bjge.GameEngine.Util.Annotation.EditorBehaviour;
import com.bedrye.bjge.GameEngine.Util.Annotation.InspectorVisible;
import org.joml.Vector3f;
@EditorBehaviour
public class BJERigidBody extends MainBehaviour {
    @InspectorVisible
    private final Vector3f velocity = new Vector3f();
    @InspectorVisible
    private final Vector3f acceleration = new Vector3f();
    @InspectorVisible
    private final Vector3f forces = new Vector3f();
    @InspectorVisible

    private float mass = 1.0f;
    @InspectorVisible
    private boolean isStatic = false;
    @InspectorVisible
    private static float gravity = 10;

    public void addForce(Vector3f force) {
        forces.add(force);
    }

    public void integrate(float dt) {
        if (isStatic) return;

        acceleration.set(forces).div(mass);
        acceleration.y+=gravity;
        velocity.fma(dt, acceleration);

        getGameObject().getLocalPos().fma(dt, velocity);

        forces.zero();
    }

    public void onCollision(BJERigidBody other) {
        System.out.println("Collision detected between " + this + " and " + other);
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f vel) {
        this.velocity.set(vel);
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }


    @Override
    public void fixedUpdate(){
        if (!isStatic()) {
            integrate((float) EngineManager.getInstance().getFixedTimeStamp()/1000f);
        }
    }
}
