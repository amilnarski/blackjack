package com.eliasranzschleifer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class CardGame
{
    public static void main (String[] args) {
        BlackJack blackjack = new BlackJack();
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
            int score = 0;
            if(isPlayersHand) {
                score += secretCard.weight;
            }

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

    public static class BlackJack {
        Deck deck = new Deck();
        public BlackJack() {
            Hand playerHand = buildHand();
            Hand dealerHand = buildHand();

            Scanner input = new Scanner(System.in);
            String playChoice = null;
            Card dealtCard;
            boolean playerIsBust = playerHand.isBust(playerHand.score(true));
            boolean dealerIsBust = dealerHand.isBust(dealerHand.score(false));
            int dealerScore = dealerHand.score(true);

            System.out.println(playerHand);
            do {
                System.out.println("Your score is " + playerHand.score(true));
                System.out.println("Would you like to hit? (y/n): ");
                playChoice = input.nextLine();
                if(playChoice.equals("y")) {
                    dealtCard = deck.dealCard();
                    System.out.println(dealtCard);

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
            } while (playChoice.equals("y") && !playerIsBust);

            System.out.println();
            System.out.println();
            System.out.println(dealerHand);
            System.out.println();
            while(dealerScore < 16 && !dealerIsBust) {
                dealerIsBust = dealerHand.isBust(dealerHand.score(true));
                if(dealerIsBust) {
                    System.out.println("Dealer is bust...");
                    break;
                }

                dealtCard = deck.dealCard();
                dealerHand.dealCard(dealtCard);
                System.out.println(dealerHand);
                System.out.println("Dealer score is: " + dealerHand.score(true));

            }


            String result = finalScore(playerHand.score(true),dealerHand.score(true),playerIsBust,dealerIsBust);

            System.out.println(result);

        }
        public Hand buildHand() {
            Card secretCard = deck.dealCard();
            Card publicCard = deck.dealCard();
            Hand hand = new Hand(secretCard,publicCard);

            return hand;
        }
        public String finalScore(int playerScore,int dealerScore, boolean playerIsBust,boolean dealerIsBust) {

            System.out.println("Dealer Score is: " + dealerScore + " Bust? " + dealerIsBust);
            System.out.println("Player Score is: " + playerScore + " Bust? " + playerIsBust);

            if(playerScore > dealerScore && !playerIsBust && !dealerIsBust) {
                return "WIN and both players not bust";
            } else if(playerScore < dealerScore && !playerIsBust && !dealerIsBust) {
                return "LOSE and both players not bust";
            } else if(playerScore < dealerScore && !playerIsBust) {
                return "WIN and player is not bust";
            } else if(playerScore < dealerScore && !dealerIsBust) {
                return "LOSE and dealer is not bust";
            } else {
                return "LOSE";
            }
        }
    }

    public static class Deck {
        private List<Card> deck = new ArrayList<>();
        public Deck() {
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
            shuffleDeck(3);
        }

        public Card dealCard() {
            Card dealtCard = deck.get(0);
            deck.remove(dealtCard);
            return dealtCard;
        }

        private void shuffleDeck(int shuffleAmount) {
            for(int i=0;i<shuffleAmount;i++) {
                Collections.shuffle(deck);
            }
        }
    }

}
