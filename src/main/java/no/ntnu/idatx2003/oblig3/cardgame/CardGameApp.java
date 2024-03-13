package no.ntnu.idatx2003.oblig3.cardgame;


import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;


public class CardGameApp extends Application {

  private DeckOfCards deck = new DeckOfCards();
    private List<PlayingCard> currentHand;
  private  TextArea cardDisplayArea = new TextArea();
  private Label sumLabel = new Label("Sum of the faces: ");
  private Label heartsLabel = new Label("Cards of hearts: ");
  private Label flushLabel = new Label("Flush: ");
  private Label  queenOfSpadesLabel = new Label("Queen of spades: ");
  private Text sumValue = new Text("0");
  private Text heartsValue = new Text("No hearts");
  private Text flushValue = new Text("No");
  private Text queenOfSpadesValue = new Text("No");

  /**
   * Represents the main windows of the application.
   *
   * @param primaryStage
   */
    @Override
    public void start(Stage primaryStage){
      VBox root = new VBox( 10 );
      HBox handCheckArea = new HBox( 10 );
      HBox statsArea = new HBox( 10 );

      Button dealHandButton = new Button("Deal hand");
      Button checkHandButton = new Button("Check hand");

      // Set up the text area where cards will be displayed
      cardDisplayArea.setEditable(false);

      // Set up the labels and text for displaying stats
      handCheckArea.getChildren().addAll(dealHandButton, checkHandButton);
      statsArea.getChildren().addAll(sumLabel, sumValue, heartsLabel, heartsValue, flushLabel, flushValue, queenOfSpadesLabel, queenOfSpadesValue);

      // Deal hand button action
      dealHandButton.setOnAction(e -> dealHand());

      // Check hand button action
      checkHandButton.setOnAction(e -> checkHand(currentHand));

      root.getChildren().addAll(cardDisplayArea, handCheckArea, statsArea);

      Scene scene = new Scene(root, 600, 400);
      primaryStage.setTitle("Card Game");
      primaryStage.setScene(scene);
      primaryStage.show();
    }

  /**
   * Deal a hand of cards and display them in the cardDisplayArea.
   * Also reset the stats.
   *
   */
  private void dealHand() {
    currentHand = deck.dealHand(5);
    cardDisplayArea.setText(currentHand.stream()
        .map(PlayingCard::getAsString)
        .collect(Collectors.joining(" ")));
    }
    /**
     * Check the hand for stats and update the GUI.
     *
     * @param hand the hand to check
     */

    private void checkHand(List<PlayingCard> hand) {
      if (hand == null || hand.isEmpty()) {
        // Oppdates GUI to check for empty hand
        sumValue.setText("N/A");
        heartsValue.setText("N/A");
        flushValue.setText("N/A");
        queenOfSpadesValue.setText("N/A");
        return;
      }

      // Calculate the sum of the faces of the cards on the hand
      int sumOfFaces = hand.stream()
          .mapToInt(PlayingCard::getFace)
          .sum();

      // Gets all the hearts in the hand
      String hearts = hand.stream()
          .filter(card -> card.getSuit() == 'H')
          .map(PlayingCard::getAsString)
          .collect(Collectors.joining(" "));
      hearts = hearts.isEmpty() ? "No Hearts" : hearts;

      // checks if the queen of spades is in the hand
      boolean queenOfSpades = hand.stream()
          .anyMatch(card -> card.getSuit() == 'S' && card.getFace() == 12);

      // checks if the hand is a flush
      boolean isFiveFlush = hand.stream()
          .collect(Collectors.groupingBy(PlayingCard::getSuit, Collectors.counting()))
          .values().stream()
          .anyMatch(count -> count == 5);

      // updates the GUI with the stats
      sumValue.setText(String.valueOf(sumOfFaces));
      heartsValue.setText(hearts);
      flushValue.setText(isFiveFlush ? "Yes" : "No");
      queenOfSpadesValue.setText(queenOfSpades ? "Yes" : "No");
    }
    public static void appMain(String[] args) {
      launch(args);
    }


}
