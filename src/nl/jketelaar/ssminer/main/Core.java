package nl.jketelaar.ssminer.main;

import nl.jketelaar.ssminer.strategies.Dropper;
import nl.jketelaar.ssminer.strategies.Miner;
import nl.jketelaar.ssminer.ui.JKSSMiner;
import org.parabot.core.Context;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.soulsplit.accessors.Client;
import org.soulsplit.api.methods.Inventory;
import org.soulsplit.api.wrappers.Item;

import java.util.ArrayList;

/**
 * @author JKetelaar
 */
@ScriptManifest(author = "Paradox",
        category = Category.OTHER,
        description = "A powerminer for Soulsplit",
        name = "JKSSMiner",
        servers = { "Soulsplit" },
        version = 1.0)
public class Core extends Script implements Main {

    private ArrayList<Strategy> strategies = new ArrayList<>();
    private ArrayList<Long> startingItems;
    private int[] rockID;
    private int distance;

    @Override
    public boolean onExecute() {
        final JKSSMiner gui = new JKSSMiner(this);
        Client c = (Client) Context.getInstance().getClient();
        c.getClass();
        while (gui.getFrame().isVisible()){
            Time.sleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return !gui.getFrame().isVisible();
                }
            }, 2500);
        }

        if (rockID != null) {

            startingItems = new ArrayList<>();

            for (Item i : Inventory.getItems()) {
                if (i != null) {
                    startingItems.add(i.getId());
                }
            }

            setStartingItems(startingItems);

            this.strategies.add(new Dropper(this));
            this.strategies.add(new Miner(this));

            provide(strategies);
        }

        return true;
    }

    @Override
    public int[] getRockIDs() {
        return rockID;
    }

    @Override
    public ArrayList<Long> getStartingItems() {
        return startingItems;
    }

    @Override
    public void setStartingItems(ArrayList<Long> items) {
        this.startingItems = items;
    }

    @Override
    public int getDistance() {
        return distance;
    }

    @Override
    public void setDistance(int i) {
        this.distance = i;
    }

    @Override
    public void setRockIDs(int[] i) {
        this.rockID = i;
    }
}
