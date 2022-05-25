package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
                           //Mettre en HBox
public class VueDuJeu extends HBox {


    private IJeu jeu;
    private VuePlateau plateau;

  //  private Label l1;  //carte Destinations
  //  private Label l2;
  //  private Label l3;
  //  private Label l4;

    @FXML
    private VBox gauche;
    @FXML
    private VBox milieu;
    @FXML
    private VBox droite;

    @FXML
    private Label information;

    private StringProperty infoProperty;


 //   private Label carteW1; //carte Wagons visibles
 //   private Label carteW2;
 //   private Label carteW3;
 //   private Label carteW4;
 //   private Label carteW5;

    private StringProperty w1SP; //StringPropety carteWagons visibles
    private StringProperty w2SP;
    private StringProperty w3SP;
    private StringProperty w4SP;
    private StringProperty w5SP;

    private StringProperty l1SP; //String property carte destinations initial
    private StringProperty l2SP;
    private StringProperty l3SP;
    private StringProperty l4SP;

    private VueJoueurCourant vueJoueurCourant;

    @FXML
    private Button bt;



    public VueDuJeu(IJeu jeu) {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/vuepricipale.fxml"));

        this.jeu = jeu;
        vueJoueurCourant = new VueJoueurCourant();
        plateau = new VuePlateau();

        this.setPrefHeight(600);
        this.setPrefWidth(600);
        setSpacing(10);
        //initialiserObjet();
        //buttonInit();
        //setListener();
    }

    public void initialize(){
       // initialiserObjet();
       // buttonInit();
       // setListener();
    }


    public void setListener(){
        //vueJoueurCourant.setListener(jeu);


        jeu.instructionProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                Platform.runLater(() -> infoProperty.setValue(t1));
            }
        });

        /*jeu.destinationsInitialesProperty().addListener(new ListChangeListener<Destination>() {
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
                        Platform.runLater(() -> getChildren().removeAll(change.getList()));
                    }
                }
            }
        });*/


       /* jeu.cartesWagonVisiblesProperty().addListener(new ListChangeListener<CouleurWagon>() {
            @Override
            public void onChanged(Change<? extends CouleurWagon> change) {
                while(change.next()){
                    if(change.wasAdded()){
                        Platform.runLater( () -> w1SP.set(change.getList().get(0).toString()));
                        Platform.runLater( () -> w2SP.set(change.getList().get(1).toString()));
                        Platform.runLater( () -> w3SP.set(change.getList().get(2).toString()));
                        Platform.runLater( () -> w4SP.set(change.getList().get(3).toString()));
                        Platform.runLater( () -> w5SP.set(change.getList().get(4).toString()));
                    }

                    if(change.wasRemoved()){
                        Platform.runLater(() -> getChildren().remove(change.getList().get(change.getFrom())));
                    }
                }
            }
        });*/
    }
    public IJeu getJeu() {
        return jeu;
    }

    public void creerBindings() {

        //vueJoueurCourant.creerBindings(jeu);
       // l1.textProperty().bind(l1SP);
       // l2.textProperty().bind(l2SP);
       // l3.textProperty().bind(l3SP);
       // l4.textProperty().bind(l4SP);
     /*   carteW1.textProperty().bind(w1SP);
        carteW2.textProperty().bind(w2SP);
        carteW3.textProperty().bind(w3SP);
        carteW4.textProperty().bind(w4SP);
        carteW5.textProperty().bind(w5SP);*/

    }



    @FXML
    public void buttonInit(){
        bt.onActionProperty().set(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                jeu.passerAEteChoisi();
            }
        });
    }

    public void initialiserObjet() {
/*
         gauche = new VBox(); //ajouter toutes les cartes Pioche (destinations, wagons) + bouton passer
         milieu = new VBox(); //ajouter dans l'ordre : Information,plateau,Inventaire
         droite = new VBox(); // Ajouter dans l'ordre les joueurs puis les logs
*/



        l1SP = new SimpleStringProperty();
        l2SP = new SimpleStringProperty();
        l3SP = new SimpleStringProperty();
        l4SP = new SimpleStringProperty();

        w1SP = new SimpleStringProperty();
        w2SP = new SimpleStringProperty();
        w3SP = new SimpleStringProperty();
        w4SP = new SimpleStringProperty();
        w5SP = new SimpleStringProperty();

        infoProperty = new SimpleStringProperty();

        //gauche.getChildren().add();
        droite.getChildren().add(vueJoueurCourant);
        milieu.getChildren().add(plateau);

    }


    //pas fini
    public Label trouveLabelDestination(IDestination d){
        return new Label(d.getNom());
    }
}