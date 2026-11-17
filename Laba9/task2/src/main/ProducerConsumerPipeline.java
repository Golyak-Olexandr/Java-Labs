package main;

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumerPipeline {

    private final int producersCount;
    private final int translatorsCount;
    private final int bufferCapacity;

    public ProducerConsumerPipeline(int producersCount, int translatorsCount, int bufferCapacity) {
        if (producersCount <= 0 || translatorsCount <= 0 || bufferCapacity <= 0) {
            throw new IllegalArgumentException("Counts and buffer capacity must be positive");
        }
        this.producersCount = producersCount;
        this.translatorsCount = translatorsCount;
        this.bufferCapacity = bufferCapacity;
    }

    public List<String> executeAndCollect(int messagesToConsume) throws InterruptedException {
        if (messagesToConsume < 0) {
            throw new IllegalArgumentException("Messages to consume must be non-negative");
        }

        CircularBuffer<String> firstBuffer = new CircularBuffer<>(bufferCapacity);
        CircularBuffer<String> secondBuffer = new CircularBuffer<>(bufferCapacity);

        List<Thread> daemonWorkers = createDaemonWorkers(firstBuffer, secondBuffer);
        daemonWorkers.forEach(Thread::start);

        List<String> consumedMessages = new ArrayList<>(messagesToConsume);
        for (int i = 0; i < messagesToConsume; i++) {
            consumedMessages.add(secondBuffer.take());
        }

        return consumedMessages;
    }

    List<Thread> createDaemonWorkers(
            CircularBuffer<String> firstBuffer,
            CircularBuffer<String> secondBuffer
    ) {
        List<Thread> workers = new ArrayList<>(producersCount + translatorsCount);

        for (int i = 1; i <= producersCount; i++) {
            Thread producerThread = new Thread(
                    new MessageProducerTask(i, firstBuffer),
                    "producer-daemon-" + i
            );
            producerThread.setDaemon(true);
            workers.add(producerThread);
        }

        for (int i = 1; i <= translatorsCount; i++) {
            Thread translatorThread = new Thread(
                    new MessageTranslatorTask(i, firstBuffer, secondBuffer),
                    "translator-daemon-" + i
            );
            translatorThread.setDaemon(true);
            workers.add(translatorThread);
        }

        return workers;
    }
}