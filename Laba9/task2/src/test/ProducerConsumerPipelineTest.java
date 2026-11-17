package test;

import main.ProducerConsumerPipeline;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProducerConsumerPipelineTest {

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    @DisplayName("Вичитує рівно 100 перекладених повідомлень правильного формату з другого буфера")
    void shouldConsumeHundredTranslatedMessagesInExpectedFormat() throws InterruptedException {
        ProducerConsumerPipeline pipeline = new ProducerConsumerPipeline(5, 2, 10);

        List<String> messages = pipeline.executeAndCollect(100);

        assertEquals(100, messages.size());
        for (String message : messages) {
            assertTrue(
                    message.matches("Потік №[12] переклав повідомлення: \\[Потік №[1-5] згенерував повідомлення #\\d+\\]"),
                    "Неочікуваний формат повідомлення: " + message
            );
        }
    }
}