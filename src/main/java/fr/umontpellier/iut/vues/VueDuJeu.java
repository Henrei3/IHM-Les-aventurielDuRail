package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.rails.Destination;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.io.IOException;

/**
 * Cette classe correspond à la fenêtre principale de l'application.
 *
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 *
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, les 5 cartes Wagons visibles, les destinations lors de l'étape d'initialisation de la partie, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class VueDuJeu extends BorderPane {

    private IJeu jeu;
    private VuePlateau plateau;
    private VueJoueurCourant vueJoueurCourant;


    @FXML
    private Button bt;
    @FXML
    private Label information;
    @FXML
    private AnchorPane plateauPane;

    private StringProperty infoProperty;


    public VueDuJeu(IJeu jeu) {
      //  setPrefWidth(1600);
      //  setPrefHeight(1600);
        this.jeu = jeu;
        vueJoueurCourant = new VueJoueurCourant();
        plateau = new VuePlateau();


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/VueDuJeu.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        plateauPane.getChildren().add(plateau);

    }

    public IJeu getJeu() {
        return jeu;
    }

    public void creerBindings() {
        infoProperty = new SimpleStringProperty();
        setListener();
        information.textProperty().bind(infoProperty);
        Platform.runLater(()->plateau.creerBindings());
    }

    public void setListener(){
        jeu.instructionProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                infoProperty.set(t1);
            }
        });

        jeu.destinationsInitialesProperty().addListener(new ListChangeListener<Destination>() {
            @Override
            public void onChanged(Change<? extends Destination> change) {
                while(change.next()){
                    //System.out.println(change.getList().get(0).getNom());
                }

            }
        });

    }

    @FXML
    public void btaction(){
        jeu.passerAEteChoisi();
    }

}
