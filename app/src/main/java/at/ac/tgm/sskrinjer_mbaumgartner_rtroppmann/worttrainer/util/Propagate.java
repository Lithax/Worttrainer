package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.util;

public final class Propagate {
    private Propagate() {}

    public static void propagate(Throwable t) {
        Propagate.<RuntimeException>throwAs(t);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Throwable> void throwAs(Throwable t) throws T {
        throw (T) t; // unchecked cast + throws T
    }

    @FunctionalInterface
    public interface ThrowingRunnable {
        void run() throws Exception;
    }

    public static void propagate(ThrowingRunnable runnable) {
        try {
            runnable.run();
        } catch (RuntimeException e) {
            throw e; // keep runtime exceptions as-is
        } catch (Exception e) {
            throw new RuntimeException(e); // wrap checked exceptions
        }
    }

    @FunctionalInterface
    public interface ThrowingSupplier<T> {
        T get() throws Exception;
    }

    public static <T> T propagate(ThrowingSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}