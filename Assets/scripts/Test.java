import com.bedrye.bjge.GameEngine.Scripts.MainBehaviour;
public class Test extends MainBehaviour {

    private float speed = 5111.0f;

    @Override
    public void update() {
        System.out.println("Player moving at speed: " + speed);
    }

}