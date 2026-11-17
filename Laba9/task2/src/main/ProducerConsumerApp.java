package main;

import java.util.List;

public class ProducerConsumerApp {

    public static final int PRODUCERS_COUNT = 5;
    public static final int TRANSLATORS_COUNT = 2;
    public static final int BUFFER_CAPACITY = 10;
    public static final int MESSAGES_TO_READ = 100;

    public static void main(String[] args) throws InterruptedException {
        ProducerConsumerPipeline pipeline = new ProducerConsumerPipeline(
                PRODUCERS_COUNT,
                TRANSLATORS_COUNT,
                BUFFER_CAPACITY
        );

        List<String> messages = pipeline.executeAndCollect(MESSAGES_TO_READ);

        for (int i = 0; i < messages.size(); i++) {
            System.out.printf("%03d: %s%n", i + 1, messages.get(i));
        }
        System.out.printf("Успішно вичитано повідомлень: %d. Основний потік завершено.%n", messages.size());
    }
}