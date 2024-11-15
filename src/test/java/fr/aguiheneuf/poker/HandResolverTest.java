package fr.aguiheneuf.poker;

import fr.aguiheneuf.poker.enumeration.Hand;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static fr.aguiheneuf.poker.enumeration.Hand.*;
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
            assertHand(hand, FULL_HOUSE);
        }

        @Test
        void should_not_be_full_house() {
            var hand = "2H 5S 5D 5H 5C";

            Hand result = HandResolver.resolveHand(hand);

            assertNotEquals(FULL_HOUSE, result);
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
            assertHand(hand, PAIR);
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "2H 3C 4C 5C 9H"
        })
        void should_not_be_pair(String hand) {
            Hand result = HandResolver.resolveHand(hand);
            assertNotEquals(PAIR, result);
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
            assertHand(hand, THREE_OF_KIND);
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "2H 2C 4C 5C 9H"
        })
        void should_not_be_brelan(String hand) {
            Hand result = HandResolver.resolveHand(hand);
            assertNotEquals(THREE_OF_KIND, result);
        }
    }

    @Nested
    class DoublePairTest {
        @ParameterizedTest
        @ValueSource(strings = {
                "2H 2C 9C 5C 9H",
                "2H 4C 8C 4C 8H",
                "2H 3C 3C 4C 4H",
        })
        void should_be_double_pair(String hand) {
            assertHand(hand, DOUBLE_PAIR);
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "9H 4C 4C 4C 9H",
                "2H 1C 3C 5C 4H",
        })
        void should_not_be_double_pair(String hand) {
            Hand result = HandResolver.resolveHand(hand);
            assertNotEquals(DOUBLE_PAIR, result);
        }
    }

    @Nested
    class FlushTest {
        @ParameterizedTest
        @ValueSource(strings = {
                "2H 2H 9H 5H 9H",
                "2S 4S 8S 4S 8S",
                "2C 3C 3C 4C 4C",
                "2D 3D 3D 4D 4D",
        })
        void should_be_flush(String hand) {
            assertHand(hand, FLUSH);
        }

        @Test
        void shloud_not_be_flush() {
            assertNotHand("2D 3C 3H 4D 4D", FLUSH);
        }
    }

    private static void assertHand(String actualHand, Hand expectedHand) {
        Hand result = HandResolver.resolveHand(actualHand);
        assertEquals(expectedHand, result);
    }

    private static void assertNotHand(String actualHand, Hand expectedHand) {
        Hand result = HandResolver.resolveHand(actualHand);
        assertNotEquals(expectedHand, result);

    }


}
