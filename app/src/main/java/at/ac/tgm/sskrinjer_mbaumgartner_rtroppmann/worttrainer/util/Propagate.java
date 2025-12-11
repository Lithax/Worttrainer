package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.util;

import java.util.function.Consumer;

public final class Propagate {
    public static volatile Consumer<Throwable> exceptHandler = t -> {
        t.printStackTrace();
    };

    private Propagate() {}

    public static void propagate(Throwable t) {
        Consumer<Throwable> handler = exceptHandler;
        if (handler != null) handler.accept(t);
    }

    @FunctionalInterface
    public interface ThrowingRunnable {
        void run() throws Exception;
    }

    public static void propagate(ThrowingRunnable runnable) {
        try {
            runnable.run();
        } catch (Throwable t) {
            Consumer<Throwable> handler = exceptHandler;
            if (handler != null) handler.accept(t);
        }
    }

    @FunctionalInterface
    public interface ThrowingSupplier<T> {
        T get() throws Exception;
    }

    public static <T> T propagate(ThrowingSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Throwable t) {
            Consumer<Throwable> handler = exceptHandler;
            if (handler != null) handler.accept(t);
            return null;
        }
    }
}
