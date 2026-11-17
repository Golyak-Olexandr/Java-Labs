package main;

public class MessageTranslatorTask implements Runnable {

    private final int translatorId;
    private final CircularBuffer<String> sourceBuffer;
    private final CircularBuffer<String> destinationBuffer;

    public MessageTranslatorTask(
            int translatorId,
            CircularBuffer<String> sourceBuffer,
            CircularBuffer<String> destinationBuffer
    ) {
        if (sourceBuffer == null || destinationBuffer == null) {
            throw new IllegalArgumentException("Source and destination buffers must not be null");
        }
        this.translatorId = translatorId;
        this.sourceBuffer = sourceBuffer;
        this.destinationBuffer = destinationBuffer;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String originalMessage = sourceBuffer.take();
                String translatedMessage = String.format(
                        "Потік №%d переклав повідомлення: [%s]",
                        translatorId,
                        originalMessage
                );
                destinationBuffer.put(translatedMessage);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}