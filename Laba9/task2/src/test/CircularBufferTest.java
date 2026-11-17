package test;

import main.CircularBuffer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CircularBufferTest {

    @Test
    @DisplayName("Порожній буфер має однакові головний і кінцевий індекси та коректно переходить по кільцю")
    void shouldWrapIndicesAroundRingAndPreserveFifoOrder() throws InterruptedException {
        CircularBuffer<String> buffer = new CircularBuffer<>(3);

        assertTrue(buffer.isEmpty());
        assertEquals(buffer.getHeadIndex(), buffer.getTailIndex());

        buffer.put("A");
        buffer.put("B");
        buffer.put("C");
        assertTrue(buffer.isFull());

        assertEquals("A", buffer.take());
        assertEquals("B", buffer.take());

        // Записуємо ще два елементи, щоб індекси перейшли через межу списку на початок кільця
        buffer.put("D");
        buffer.put("E");
        assertTrue(buffer.isFull());

        assertEquals("C", buffer.take());
        assertEquals("D", buffer.take());
        assertEquals("E", buffer.take());

        assertTrue(buffer.isEmpty());
        assertEquals(buffer.getHeadIndex(), buffer.getTailIndex());
    }

    @Test
    @Timeout(value = 3, unit = TimeUnit.SECONDS)
    @DisplayName("Блокує потік-споживач, поки буфер порожній")
    void shouldBlockConsumerWhenBufferIsEmpty() throws InterruptedException {
        CircularBuffer<Integer> buffer = new CircularBuffer<>(2);
        AtomicBoolean received = new AtomicBoolean(false);
        CountDownLatch consumerStarted = new CountDownLatch(1);

        Thread consumer = new Thread(() -> {
            try {
                consumerStarted.countDown();
                Integer value = buffer.take();
                if (value == 42) {
                    received.set(true);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        consumer.start();
        consumerStarted.await();
        Thread.sleep(50);

        assertFalse(received.get());
        buffer.put(42);
        consumer.join();
        assertTrue(received.get());
    }

    @Test
    @Timeout(value = 3, unit = TimeUnit.SECONDS)
    @DisplayName("Блокує потік-виробник, поки буфер повний")
    void shouldBlockProducerWhenBufferIsFull() throws InterruptedException {
        CircularBuffer<String> buffer = new CircularBuffer<>(1);
        buffer.put("first");

        AtomicBoolean secondInserted = new AtomicBoolean(false);
        CountDownLatch producerStarted = new CountDownLatch(1);

        Thread producer = new Thread(() -> {
            try {
                producerStarted.countDown();
                buffer.put("second");
                secondInserted.set(true);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        producerStarted.await();
        Thread.sleep(50);

        assertFalse(secondInserted.get());
        assertEquals("first", buffer.take());
        producer.join();
        assertTrue(secondInserted.get());
        assertEquals("second", buffer.take());
    }

    @Test
    @DisplayName("Валідує некоректну місткість буфера та null-елементи")
    void shouldRejectInvalidCapacityAndNullItems() {
        assertThrows(IllegalArgumentException.class, () -> new CircularBuffer<>(0));
        assertThrows(IllegalArgumentException.class, () -> new CircularBuffer<>(-5));

        CircularBuffer<String> buffer = new CircularBuffer<>(2);
        assertThrows(IllegalArgumentException.class, () -> buffer.put(null));
    }
}