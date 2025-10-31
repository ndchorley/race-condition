import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("""
                Concurrent modification of a counter by two threads, 100000 times each
                
                1. Ordinary integer
                2. Atomic integer
                """);

        var option = Integer.parseInt(new Scanner(System.in).nextLine());

        if (option == 1) {
            runOrdinaryCounterScenario();
        } else {
            runAtomicCounterScenario();
        }
    }

    static int counter = 0;

    private static void incrementCounterOneHundredThousandTimes() {
        for (var i = 0; i < 100000; i++) {
            counter++;
        }
    }

    private static void runOrdinaryCounterScenario() throws InterruptedException {
        var thread1 =
            Thread
                .ofVirtual()
                .start(Main::incrementCounterOneHundredThousandTimes);

        var thread2 =
            Thread
                .ofVirtual()
                .start(Main::incrementCounterOneHundredThousandTimes);

        thread1.join();
        thread2.join();

        System.out.println("Final count: " + counter);
    }

    private static AtomicInteger atomicCounter = new AtomicInteger(0);

    private static void incrementAtomicCounterOneHundredThousandTimes() {
        for (var i = 0; i < 100000; i++) {
            atomicCounter.incrementAndGet();
        }
    }

    private static void runAtomicCounterScenario() throws InterruptedException {
        var thread1 =
            Thread
                .ofVirtual()
                .start(Main::incrementAtomicCounterOneHundredThousandTimes);

        var thread2 =
            Thread
                .ofVirtual()
                .start(Main::incrementAtomicCounterOneHundredThousandTimes);

        thread1.join();
        thread2.join();

        System.out.println("Final count: " + atomicCounter.intValue());
    }
}
