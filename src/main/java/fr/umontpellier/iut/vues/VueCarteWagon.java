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
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Locale;

/**
 * Cette classe représente la vue d'une carte Wagon.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarteWagon extends ImageView {

    private ICouleurWagon couleurWagon;

    public VueCarteWagon(ICouleurWagon couleurWagon, IJeu jeu) {


        this.couleurWagon = couleurWagon;
        Image i = new Image("images/cartesWagons/carte-wagon-"+couleurWagon.toString().toUpperCase(Locale.ROOT)+".png");
        setImage(i);

        this.setFitWidth(160);
        this.setFitHeight(108);

        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                jeu.uneCarteWagonAEteChoisie(couleurWagon);
            }
        });
    }

    public ICouleurWagon getCouleurWagon() {
        return couleurWagon;
    }


}

