package rnk.l12.async;

import javax.servlet.AsyncEvent;
import javax.servlet.annotation.WebListener;
import java.io.IOException;

@WebListener
public class StatsAsyncListener implements javax.servlet.AsyncListener {
    @Override
    public void onComplete(AsyncEvent event) throws IOException {
        event.getAsyncContext().getRequest().removeAttribute("async_command");
    }

    @Override
    public void onTimeout(AsyncEvent event) throws IOException {
    }

    @Override
    public void onError(AsyncEvent event) throws IOException {
    }

    @Override
    public void onStartAsync(AsyncEvent event) throws IOException {

    }
}
