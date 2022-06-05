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
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends HBox {
    public VueJoueurCourant() {

    }

    public void creerBindings(IJeu jeu) {

    }

    public void setListener(IJeu jeu) {
        jeu.joueurCourantProperty().addListener(new ChangeListener<IJoueur>() {
            @Override
            public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur iJoueur, IJoueur t1) {
                Platform.runLater(()->{
                    getChildren().clear();
                    for(int i=0;i<t1.cartesWagonProperty().size();i++){
                        getChildren().add(new VueCarteWagon(t1.getCartesWagon().get(i),jeu));
                    }

                    if(getChildren().size()>4) {
                        for (Node r : getChildren()) {
                            ((VueCarteWagon) r).setFitWidth(160 /(getChildren().size()*0.20));
                            ((VueCarteWagon) r).setFitHeight(120);
                        }
                    }
                });
            }
        });
    }
}

