package main;

public class MessageProducerTask implements Runnable {

    private final int producerId;
    private final CircularBuffer<String> targetBuffer;

    public MessageProducerTask(int producerId, CircularBuffer<String> targetBuffer) {
        if (targetBuffer == null) {
            throw new IllegalArgumentException("Target buffer must not be null");
        }
        this.producerId = producerId;
        this.targetBuffer = targetBuffer;
    }

    @Override
    public void run() {
        long messageNumber = 1L;
        while (!Thread.currentThread().isInterrupted()) {
            String message = String.format(
                    "Потік №%d згенерував повідомлення #%d",
                    producerId,
                    messageNumber++
            );
            try {
                targetBuffer.put(message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}