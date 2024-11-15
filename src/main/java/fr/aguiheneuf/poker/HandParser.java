package fr.aguiheneuf.poker;

import fr.aguiheneuf.poker.domain.Card;
import fr.aguiheneuf.poker.enumeration.Color;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandParser {

    private HandParser() {
        // Private constructor
    }

    public static List<Card> handParser(String hand) {
        var cardsString = Arrays.asList(hand.split(" "));
        return cardsString.stream().map(HandParser::cardParser).toList();
    }

    public static Card cardParser(String input) {
        String valueString = input.substring(0, 1);
        String colorString = input.substring(1, 2);


        return new Card(computeValue(valueString), computeColor(colorString));
    }

    private static Integer computeValue(String value) {
        final Pattern pattern = Pattern.compile("^\\d$");
        final Matcher matcher = pattern.matcher(value);

        if (matcher.find()) {
            return Integer.parseInt(value);
        }

        return switch (value) {
            case "A" -> 14;
            case "T" -> 10;
            case "J" -> 11;
            case "Q" -> 12;
            case "K" -> 13;
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }

    private static Color computeColor(String colorValue) {
        return switch (colorValue) {
            case "H" -> Color.HEARTH;
            case "S" -> Color.SPADE;
            case "D" -> Color.DIAMOND;
            case "C" -> Color.CLUB;
            default -> throw new IllegalStateException("Unexpected value: " + colorValue);
        };
    }

}
