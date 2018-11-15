package rnk.l12.async;

import org.apache.log4j.Logger;

import javax.servlet.AsyncEvent;
import javax.servlet.annotation.WebListener;
import java.io.IOException;

@WebListener
public class StatsAsyncListener implements javax.servlet.AsyncListener {
    private static final Logger logger = Logger.getLogger(StatsAsyncListener.class.getName());
    @Override
    public void onComplete(AsyncEvent event) throws IOException {
        logger.info("asynclistener oncomplete start");
        event.getAsyncContext().getRequest().removeAttribute("async_command");
        logger.info("asynclistener after proper complete");
    }

    @Override
    public void onTimeout(AsyncEvent event) throws IOException {
        logger.info("asynclistener ontimeout");
    }

    @Override
    public void onError(AsyncEvent event) throws IOException {
        logger.info("asynclistener onerror");
    }

    @Override
    public void onStartAsync(AsyncEvent event) throws IOException {
        logger.info("asynclistener onstartasync");
    }
}
