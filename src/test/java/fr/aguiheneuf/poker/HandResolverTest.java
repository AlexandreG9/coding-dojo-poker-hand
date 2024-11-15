package fr.aguiheneuf.poker;

import fr.aguiheneuf.poker.enumeration.Hand;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class HandResolverTest {

    @Nested
    class FullHouseTest {
        @ParameterizedTest
        @ValueSource(strings = {
                "2H 2C 2D 5H 5C",
                "KH KC TD TH TC",
        })
        void should_be_full_house(String hand) {
            // When
            Hand result = HandResolver.resolveHand(hand);

            // Then
            assertEquals(Hand.FULL_HOUSE, result);
        }

        @Test
        void should_not_be_full_house() {
            var hand = "2H 5S 5D 5H 5C";

            Hand result = HandResolver.resolveHand(hand);

            assertNotEquals(Hand.FULL_HOUSE, result);
        }
    }

    @Nested
    class PairTest {
        @ParameterizedTest
        @ValueSource(strings = {
                "2H 2C 4C 5C 9H",
                "2H 4C 4C 5C 9H",
                "2H 3C 4C 4C 9H",
                "2H 3C 4C 9C 9H",
                "9D 3C 4C 5C 9H",
        })
        void should_be_pair(String hand) {
            Hand result = HandResolver.resolveHand(hand);
            assertEquals(Hand.PAIR, result);
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "2H 3C 4C 5C 9H"
        })
        void should_not_be_pair(String hand) {
            Hand result = HandResolver.resolveHand(hand);
            assertNotEquals(Hand.PAIR, result);
        }
    }

    @Nested
    class BrelanTest {
        @ParameterizedTest
        @ValueSource(strings = {
                "2H 2C 2C 5C 9H",
                "2H 4C 4C 4C 9H",
                "2H 3C 4C 4C 4H",
        })
        void should_be_brelan(String hand) {
            Hand result = HandResolver.resolveHand(hand);
            assertEquals(Hand.THREE_OF_KIND, result);
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "2H 2C 4C 5C 9H"
        })
        void should_not_be_brelan(String hand) {
            Hand result = HandResolver.resolveHand(hand);
            assertNotEquals(Hand.THREE_OF_KIND, result);
        }
    }
}
