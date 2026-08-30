import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class InputHandler {

    private final BlockingQueue<String> commandQueue =
            new LinkedBlockingQueue<>();

    private volatile boolean running = true;


    // =================================
    // Start Input Listener
    // =================================

    public void start() {

        Thread inputThread =
                new Thread(() -> {

                    Scanner scanner =
                            new Scanner(System.in);

                    while (running && scanner.hasNextLine()) {

                        String command =
                                scanner.nextLine()
                                        .trim()
                                        .toUpperCase();

                        if (!command.isEmpty()) {

                            commandQueue.offer(command);
                        }
                    }

                    scanner.close();

                });

        inputThread.setDaemon(true);

        inputThread.start();
    }


    // =================================
    // Get Command
    // =================================

    public String getCommand() {

        return commandQueue.poll();
    }


    // =================================
    // Stop
    // =================================

    public void stop() {

        running = false;
    }
}