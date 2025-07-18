package loggingsystem;

import loggingsystem.pojo.Log;
import loggingsystem.service.Logger;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Logger logger = Logger.getInstance();

        logger.addLog(new Log("Startig frmo here"));
        logger.addLog(new Log("Startig two"));
        logger.addLog(new Log("Startig three"));
        logger.addLog(new Log("Startig final"));

        logger.appendLog();

    }
}
