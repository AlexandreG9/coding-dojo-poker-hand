package fr.aguiheneuf.poker;

import fr.aguiheneuf.poker.domain.Card;
import fr.aguiheneuf.poker.enumeration.Hand;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class HandResolver {

    private HandResolver() {
        // Private constructor
    }

    public static Hand resolveHand(String handInput) {
        List<Card> cards = HandParser.handParser(handInput);

        return computeHand(cards);
    }

    public static Hand computeHand(List<Card> cards) {
        if (cards.isEmpty() || cards.size() > 5) {
            throw new IllegalArgumentException("Maximum 5 cards, actual quantity: " + cards.size());
        }

        if (isFull(cards)) {
            return Hand.FULL_HOUSE;
        }

        if (isFlush(cards)) {
            return Hand.FLUSH;
        }

        if (isBrelan(cards)) {
            return Hand.THREE_OF_KIND;
        }

        if (isDoublePair(cards)) {
            return Hand.DOUBLE_PAIR;
        }

        if (isPair(cards)) {
            return Hand.PAIR;
        }


        return Hand.HIGH_CARD;
    }

    private static boolean isFull(List<Card> cards) {
        var groupedCard = getGroupedCard(cards, () -> Card::value);

        List<Integer> values = groupedCard.keySet().stream().toList();

        return groupedCard.size() == 2 &&
                (groupedCard.get(values.getFirst()).size() == 3
                        || groupedCard.get(values.getFirst()).size() == 2);
    }


    private static boolean isPair(List<Card> cards) {
        var groupedCard = getGroupedCard(cards, () -> Card::value);

        return groupedCard.entrySet().stream().anyMatch(e -> e.getValue().size() == 2);
    }

    private static boolean isDoublePair(List<Card> cards) {
        var groupedCard = getGroupedCard(cards, () -> Card::value);

        return groupedCard.entrySet().stream()
                .filter(e -> e.getValue().size() == 2)
                .count() == 2;
    }

    private static boolean isBrelan(List<Card> cards) {
        var groupedCard = getGroupedCard(cards, () -> Card::value);

        return groupedCard.entrySet().stream().anyMatch(e -> e.getValue().size() == 3);
    }

    private static boolean isFlush(List<Card> cards) {
        var groupedCard = getGroupedCard(cards, () -> Card::color);

        return groupedCard.size() == 1;
    }

    private static <T> Map<T, List<Card>> getGroupedCard(List<Card> cards, Supplier<Function<Card, T>> supplier) {
        return cards.stream()
                .collect(Collectors.groupingBy(supplier.get()));
    }
}
