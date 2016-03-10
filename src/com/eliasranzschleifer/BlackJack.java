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

        Scanner input = new Scanner(System.in);
        String playChoice = null;
        Card dealtCard;
        boolean playerIsBust = playerHand.isBust(playerHand.score(true));
        boolean dealerIsBust = dealerHand.isBust(dealerHand.score(false));

        System.out.println(playerHand);
        do {
            System.out.print("Would you like to hit? (y/n): ");
            playChoice = input.nextLine();
            if(playChoice.equals("y")) {
                dealtCard = deck.get(0);
                System.out.println(dealtCard);
                deck.remove(dealtCard);
                playerHand.dealCard(dealtCard);
                System.out.println(playerHand);
                playerIsBust = playerHand.isBust(playerHand.score(true));
                if(playerIsBust) {
                    System.out.println("You're bust...");
                    break;
                }

            } else {
                System.out.println("You have opted to not hit, you're score is: " + playerHand.score(true));
            }
        } while (playChoice.equals("y"));


        do {
            dealtCard = deck.get(0);
            deck.remove(dealtCard);
            dealerHand.dealCard(dealtCard);
            System.out.println(dealerHand);
            System.out.println("Dealer score is: " + dealerHand.score(false));
            dealerIsBust = dealerHand.isBust(dealerHand.score(false));

            if(dealerIsBust) {
                System.out.println("Dealer is bust...");
                break;
            }
        } while(dealerHand.score(false) < 18);

        if((playerHand.score(true) > dealerHand.score(false) && !playerIsBust) || (dealerIsBust && !playerIsBust)) {
            System.out.println("You win!");
            System.out.println("Final score is...\nPlayer: " + playerHand.score(true) + "\nDealer: " + dealerHand.score(false));
        } else if((dealerHand.score(false) > playerHand.score(true) && !dealerIsBust) || (playerIsBust) && !dealerIsBust) {
            System.out.println("You lose!");
            System.out.println("Final score is...\nPlayer: " + playerHand.score(true) + "\nDealer: " + dealerHand.score(false));
        }
        else {
            System.out.println("You both lose...");
            System.out.println("Final score is...\nPlayer: " + playerHand.score(true) + "\nDealer: " + dealerHand.score(false));
        }



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

        public boolean isBust(int score) {
            if(score <= 21) {
                return false;
            } else {
                return true;
            }
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
