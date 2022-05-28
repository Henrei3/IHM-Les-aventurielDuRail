package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.rails.CouleurWagon;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Cette classe représente la vue d'une carte Wagon.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarteWagon extends ImageView {

    private ICouleurWagon couleurWagon;

    public VueCarteWagon(ICouleurWagon couleurWagon, IJeu jeu, VBox pioche) {


        this.couleurWagon = couleurWagon;

        Image i = new Image("/images/cartesWagons/carte-wagon-"+couleurWagon.toString()+".png");
        setImage(i);
        this.setFitWidth(100);
        this.setFitHeight(100);

        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                 jeu.uneCarteWagonAEteChoisie(couleurWagon);
                 pioche.getChildren().remove(mouseEvent.getPickResult().getIntersectedNode());
            }
        });
    }

    public ICouleurWagon getCouleurWagon() {
        return couleurWagon;
    }


}

