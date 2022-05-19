package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.rails.Destination;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.LinkedList;

/**
 * Cette classe correspond à la fenêtre principale de l'application.
 *
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 *
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, les 5 cartes Wagons visibles, les destinations lors de l'étape d'initialisation de la partie, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class VueDuJeu extends VBox {

    private IJeu jeu;
    private VuePlateau plateau;
    private Label l1;
    private Label l2;
    private Label l3;
    private Label l4;

    private StringProperty l1Property;

    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        plateau = new VuePlateau();
        this.setPrefHeight(100);
        this.setPrefWidth(100);

        //getChildren().add(plateau)

        setListener();
        creerChoses();

    }


    public void setListener(){
        jeu.instructionProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                System.out.println(t1);
            }
        });

        jeu.destinationsInitialesProperty().addListener(new ListChangeListener<Destination>() {
            @Override
            public void onChanged(Change<? extends Destination> change) {
                change.next();
                if(change.wasAdded()){
                    //getChildren().add(new Label("destination"));
                }

                if(change.wasRemoved()){
                    System.out.println(change.toString());
                }
            }
        });
    }
    public IJeu getJeu() {
        return jeu;
    }

    public void creerBindings() { }
    public void creerChoses() {
        l1 = new Label();
        l2 = new Label();
        l3= new Label();
        l4 = new Label();
        l1Property = new SimpleStringProperty();
        l1.textProperty().bind(l1Property);

         Button bt = new Button("passer");
         getChildren().add(bt);
         bt.onActionProperty().set(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                jeu.passerAEteChoisi();
            }
        });
    }
}