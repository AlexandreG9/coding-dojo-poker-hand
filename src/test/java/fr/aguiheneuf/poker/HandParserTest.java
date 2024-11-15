package fr.aguiheneuf.poker;

import fr.aguiheneuf.poker.domain.Card;
import fr.aguiheneuf.poker.enumeration.Color;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HandParserTest {
    static Stream<Map.Entry<String, Card>> cardProvider() {
        return Stream.of(
                Map.entry("2H", new Card(2, Color.HEARTH)),
                Map.entry("TS", new Card(10, Color.SPADE)),
                Map.entry("JH", new Card(11, Color.HEARTH)),
                Map.entry("QD", new Card(12, Color.DIAMOND)),
                Map.entry("KH", new Card(13, Color.HEARTH)),
                Map.entry("8C", new Card(8, Color.CLUB)),
                Map.entry("AH", new Card(14, Color.HEARTH))
        );
    }

    @ParameterizedTest
    @MethodSource("cardProvider")
    void input_parser_should_be_ok(Map.Entry<String, Card> entry) {
        String value = entry.getKey();
        Card expectedCard = entry.getValue();

        Card parsedCard = HandParser.cardParser(value);

        assertNotNull(parsedCard);
        assertEquals(expectedCard.color(), parsedCard.color());
        assertEquals(expectedCard.value(), parsedCard.value());
    }

    @Test
    void hand_parser_should_be_ok() {
        var input = "2H TS JH QD KH";
        List<Card> parsedHand = HandParser.handParser(input);

        assertEquals(5, parsedHand.size());
    }
}
