package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Example sourced from
 * <a href="https://github.com/apangin/java-profiling-presentation/blob/master/src/demo6/DatagramTest.java">Andrei Pangin's Java Profiling presentation</a>.
 */
@Path("/multicast")
public class MulticastResource
{
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String send() throws Exception {
        sendMulticasts();
        return "Multicast packets sent";
    }

    static void sendMulticasts() throws Exception {
        DatagramChannel ch = DatagramChannel.open();
        ch.bind(new InetSocketAddress(5555));
        ch.configureBlocking(false);

        ExecutorService pool =
            Executors.newCachedThreadPool(new ShortNameThreadFactory());
        for (int i = 0; i < 10; i++) {
            pool.submit(() -> {
                final ByteBuffer buf = ByteBuffer.allocateDirect(1000);
                final InetSocketAddress remoteAddr =
                    new InetSocketAddress("127.0.0.1", 5556);

                while (true) {
                    buf.clear();
                    ch.send(buf, remoteAddr);
                }
            });
        }

        System.out.println("Warming up...");
        Thread.sleep(3000);

        System.out.println("Benchmarking...");
        Thread.sleep(5000);
    }

    private static final class ShortNameThreadFactory implements ThreadFactory {

        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix = "thread-";

        public Thread newThread(Runnable r) {
            return new Thread(r, namePrefix + threadNumber.getAndIncrement());
        }
    }
}
