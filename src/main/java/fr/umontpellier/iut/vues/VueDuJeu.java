package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.rails.Destination;
import javafx.application.Platform;
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
    private StringProperty l1SP;
    private StringProperty l2SP;
    private StringProperty l3SP;
    private StringProperty l4SP;
    private VueJoueurCourant vueJoueurCourant;

    private Button bt;
    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;
        vueJoueurCourant = new VueJoueurCourant();
        plateau = new VuePlateau();
        this.setPrefHeight(100);
        this.setPrefWidth(100);
        getChildren().addAll(vueJoueurCourant);
        //getChildren().add(plateau)

        initialiserObjet();
        creerBindings();
        buttonInit();
        setListener();
    }


    public void setListener(){
        vueJoueurCourant.setListener(jeu);
        jeu.instructionProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                System.out.println(t1);
            }
        });

        jeu.destinationsInitialesProperty().addListener(new ListChangeListener<Destination>() {
            @Override
            public void onChanged(Change<? extends Destination> change) {
                while (change.next()) {
                    if (change.wasAdded()) {
                        Platform.runLater( () -> l1SP.set(change.getList().get(0).toString()));
                        Platform.runLater( () -> l2SP.set(change.getList().get(1).toString()));
                        Platform.runLater( () -> l3SP.set(change.getList().get(2).toString()));
                        Platform.runLater( () -> l4SP.set(change.getList().get(3).toString()));
                    }

                    if(change.wasRemoved()){
                        //Platform.runLater(() -> );
                    }
                }
            }
        });
    }
    public IJeu getJeu() {
        return jeu;
    }

    public void creerBindings() {
        vueJoueurCourant.creerBindings(jeu);
        l1.textProperty().bind(l1SP);
        l2.textProperty().bind(l2SP);
        l3.textProperty().bind(l3SP);
        l4.textProperty().bind(l4SP);


    }

    public void buttonInit(){
        bt.onActionProperty().set(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                jeu.passerAEteChoisi();
            }
        });
    }

    public void initialiserObjet() {
        VBox labelBox = new VBox();
        l1 = new Label();
        l2 = new Label();
        l3= new Label();
        l4 = new Label();
        l1SP = new SimpleStringProperty();
        l2SP = new SimpleStringProperty();
        l3SP = new SimpleStringProperty();
        l4SP = new SimpleStringProperty();

        labelBox.getChildren().addAll(l1,l2,l3,l4);
        bt = new Button("passer");
        getChildren().addAll(bt,labelBox);
    }

    public Label trouveLabelDestination(IDestination d){
        return new Label(d.getNom());
    }
}