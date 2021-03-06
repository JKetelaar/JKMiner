package nl.jketelaar.ssminer.strategies;

import nl.jketelaar.ssminer.main.Main;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.soulsplit.api.methods.Inventory;
import org.soulsplit.api.wrappers.Item;

/**
 * @author JKetelaar
 */
public class Dropper implements Strategy {

    public Main core;

    public Dropper(Main main){
        this.core = main;
    }

    @Override
    public boolean activate() {
        return Inventory.getCount() >= 28;
    }

    @Override
    public void execute() {
        if (Inventory.getCount() >= 28){
            for (Item i : Inventory.getItems()){
                boolean contains = false;
                for (long i2 : core.getStartingItems()){
                    if (i2 == i.getId()){
                        contains = true;
                    }
                }

                if (!contains){
                    final int inventoryCount = Inventory.getCount();
                    i.drop();
                    Time.sleep(new SleepCondition() {
                        @Override
                        public boolean isValid() {
                            return Inventory.getCount() != inventoryCount;
                        }
                    }, 500);
                }
            }
        }
    }
}
