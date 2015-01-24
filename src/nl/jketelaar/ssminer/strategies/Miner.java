package nl.jketelaar.ssminer.strategies;

import nl.jketelaar.ssminer.main.Main;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.soulsplit.api.methods.Inventory;
import org.soulsplit.api.methods.Players;
import org.soulsplit.api.methods.SceneObjects;
import org.soulsplit.api.wrappers.SceneObject;

/**
 * @author JKetelaar
 */
public class Miner implements Strategy {

    private SceneObject currentObject;
    public Main core;

    public Miner(Main main){
        this.core = main;
    }


    @Override
    public boolean activate() {
        if (Inventory.getCount() < 28 && Players.getMyPlayer().getAnimation() < 1) {
            for (int i : core.getRockIDs()) {
                SceneObject[] rocks;
                if ((rocks = SceneObjects.getNearest(i)) != null) {
                    for (SceneObject so : rocks) {
                        if (so != null && so.distanceTo() <= core.getDistance()) {
                            this.currentObject = so;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void execute() {
        if (this.currentObject != null){
            final int previous = Inventory.getCount();
            currentObject.interact(1);
            Time.sleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return Inventory.getCount() != previous;
                }
            }, 7500);
        }
    }
}
