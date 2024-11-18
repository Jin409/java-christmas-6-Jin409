package christmas;

import christmas.config.ChristmasConfig;

public class Application {
    public static void main(String[] args) {
        ChristmasConfig config = new ChristmasConfig();
        ChristmasController christmasController = config.christmasController();
        christmasController.run();
    }
}
