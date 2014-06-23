package io.github.krris.qlearning.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by krris on 22.06.14.
 */
public class TickCounter {
    private final Logger LOG = LoggerFactory.getLogger(TickCounter.class);
    private int counter = 0;

    public void tick() {
        this.counter++;
    }

    public void endOfRound() {
        LOG.info(String.valueOf(this.counter));
    }
}
