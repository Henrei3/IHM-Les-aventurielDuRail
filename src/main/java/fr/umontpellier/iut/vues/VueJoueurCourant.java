package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.CouleurWagon;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends VBox {
    private Label nomJoueur;
    private StringProperty stringProperty;
    private VBox carteWagonsJC;

    public VueJoueurCourant() {
        this.nomJoueur = new Label();
        carteWagonsJC = new VBox();
        carteWagonsJC.getChildren().add(new VueCarteWagon(CouleurWagon.GRIS));
        this.getChildren().addAll(this.nomJoueur, carteWagonsJC);

    }

    public void creerBindings(IJeu jeu) {
        stringProperty = new SimpleStringProperty();
        jeu.joueurCourantProperty().addListener(new ChangeListener<IJoueur>() {
            @Override
            public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur iJoueur, IJoueur t1) {
                Platform.runLater(() -> stringProperty.set("joueur courant :" + t1.getNom()));
            }
        });
        nomJoueur.textProperty().bind(stringProperty);
    }

    public void setListener(IJeu jeu) {

    }
}

