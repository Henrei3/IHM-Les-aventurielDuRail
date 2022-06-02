package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Locale;

/**
 * Cette classe représente la vue d'une carte Destination.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueDestination extends ImageView {

    private IDestination destination;

    public VueDestination(IDestination destination, IJeu jeu, VBox pioche) {
        this.destination = destination;
        this.setFitWidth(160);
        this.setFitHeight(100);
//        this.setText(destination.toString());
       // System.out.println(destination.toImage().toLowerCase(Locale.ROOT));
        Image i = new Image("images/missions/eu-amsterdam-pamplona.png");
        this.setImage(i);

        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                jeu.uneDestinationAEteChoisie(destination.getNom());
                pioche.getChildren().remove(mouseEvent.getPickResult().getIntersectedNode());
            }
           });
        }

        public IDestination getDestination() {
            return destination;
        }
    }
