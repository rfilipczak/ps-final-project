package edu.p.lodz.pl.common.utils.timer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TimerManager {
    private static final Logger logger = LogManager.getLogger(TimerManager.class);

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private final List<ScheduledFuture> timeouts = new ArrayList<>();

    public int requestNewTimeout(Runnable handler, long seconds) {
        logger.debug("Requesting new timeout");
        var timeout = executorService.schedule(handler, seconds, TimeUnit.SECONDS);
        timeouts.add(timeout);
        return timeouts.indexOf(timeout);
    }

    public void cancelAllTimeouts() {
        logger.debug("Canceling all timeouts");
        for (var timeout : timeouts) {
            timeout.cancel(true);
        }
        timeouts.clear();
    }

    public void cancelTimeout(int id) {
        timeouts.get(id).cancel(true);
    }

    public void close() {
        logger.debug("Shutting down scheduler");
        cancelAllTimeouts();
        executorService.shutdown();
    }
}
