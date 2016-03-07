package com.eliasranzschleifer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BlackJack {

    public static void main (String[] args) {
        List<Card> deck = new ArrayList<>();

        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Value value : Card.Value.values()) {
                int weight;
                switch (value) {
                    case JACK:
                    case QUEEN:
                    case KING:
                        weight = 10;
                        break;
                    default:
                        weight = value.ordinal() + 1;

                }

                Card card = new Card(weight,suit,value);
                deck.add(card);
            }
        }

        for(int i=0;i <= 3;i++) {
            Collections.shuffle(deck);
        }

        Card playerSecretCard = deck.get(0);
        deck.remove(playerSecretCard);

        Card dealerSecretCard = deck.get(0);
        deck.remove(dealerSecretCard);

        Card playerPublicCard = deck.get(0);
        deck.remove(playerPublicCard);

        Card dealerPublicCard = deck.get(0);
        deck.remove(dealerPublicCard);

        Hand playerHand = new Hand(playerSecretCard,playerPublicCard);
        Hand dealerHand = new Hand(dealerSecretCard,dealerPublicCard);

        Scanner kb = new Scanner(System.in);



    }

    public static class Card {
        public enum Suit {
            HEARTS,SPADES,CLUBS,DIAMONDS
        }

        public enum Value {
            ACE,TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,NINE,TEN,JACK,QUEEN,KING
        }

        private final int weight;
        private final Suit suit;
        private final Value value;

        public Card(int weight, Suit suit, Value value) {
            this.weight = weight;
            this.suit = suit;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Card{" +
                    "weight=" + weight +
                    ", suit=" + suit +
                    ", value=" + value +
                    '}';
        }
    }

    public static class Hand {
        private final Card secretCard;
        private final List<Card> publicCards;

        public Hand(Card secretCard,Card publicCard) {
            this.secretCard = secretCard;
            this.publicCards = new ArrayList<>();
            dealCard(publicCard);
        }

        public void dealCard(Card card) {
            publicCards.add(card);
        }

        public int score(boolean isPlayersHand) {
            int score=secretCard.weight;
            for (Card publicCard : publicCards) {
                score += publicCard.weight;
            }

            return score;
        }

        @Override
        public String toString() {
            return "Hand{" +
                    "secretCard=" + secretCard +
                    ", publicCards=" + publicCards +
                    ", score=" + score(true) +
                    '}';
        }
    }
}
