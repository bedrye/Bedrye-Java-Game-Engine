package com.bedrye.bjge.GameEngine.Scripts;

import com.bedrye.bjge.GameEngine.EngineWindowManager;
import com.bedrye.bjge.GameEngine.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.Util.Annotation.InspectorVisible;
import org.joml.Vector3f;

public class Box3DCollider extends MainBehaviour{



        @InspectorVisible
        private Vector3f size = new Vector3f(1, 1, 1);
        @InspectorVisible
        private boolean isTrigger = false;
        public Box3DCollider() {}
        @Override
        public void update() {
            Object3DAbstract self = getGameObject();
            if (self == null) return;
            for (Object3DAbstract other : EngineWindowManager.getInstance().getActiveScene().getGameObjects()) {
                if (other == self) continue;

                Box3DCollider otherCollider = other.getScript(Box3DCollider.class);
                if (otherCollider == null) continue;

                if (isColliding(self, other, this, otherCollider)) {
                    onCollision(other);
                    if (!isTrigger && !otherCollider.isTrigger) {
                        resolveCollision(self, other);
                    }
                }
            }
        }

        private boolean isColliding(Object3DAbstract a, Object3DAbstract b,
                                    Box3DCollider colA, Box3DCollider colB) {
            Vector3f aPos = a.getPosition();
            Vector3f bPos = b.getPosition();

            Vector3f aSize = colA.size;
            Vector3f bSize = colB.size;

            return Math.abs(aPos.x - bPos.x) < (aSize.x + bSize.x) / 2 &&
                    Math.abs(aPos.y - bPos.y) < (aSize.y + bSize.y) / 2 &&
                    Math.abs(aPos.z - bPos.z) < (aSize.z + bSize.z) / 2;
        }

        private void resolveCollision(Object3DAbstract self, Object3DAbstract other) {
            Vector3f direction = new Vector3f(self.getPosition()).sub(other.getPosition());
            direction.normalize();

            self.setLocalPos(self.getPosition().add(direction.mul(0.05f)));
        }

        public void onCollision(Object3DAbstract other) {
            //System.out.println(getGameObject().getName() + " collided with " + other.getName());
        }

        public Vector3f getSize() {
            return size;
        }

        public void setSize(Vector3f size) {
            this.size = size;
        }

        public boolean isTrigger() {
            return isTrigger;
        }

        public void setTrigger(boolean trigger) {
            isTrigger = trigger;
        }

}
