package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.CouleurWagon;
import fr.umontpellier.iut.rails.Destination;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.Flow;

/**
 * Cette classe correspond à la fenêtre principale de l'application.
 *
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 *
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, les 5 cartes Wagons visibles, les destinations lors de l'étape d'initialisation de la partie, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class VueDuJeu extends AnchorPane{

    private IJeu jeu;
    private VuePlateau plateau;
    private VueJoueurCourant vueJoueurCourant;


    @FXML
    private Button bt;
    @FXML
    private Label information;
    @FXML
    private Pane plateauPane;
    private StringProperty infoProperty;
    @FXML
    private Label n1;
    @FXML
    private Label n2;
    @FXML
    private Label n3;
    @FXML
    private Label n4;
    @FXML
    private Label sc1;
    @FXML
    private Label sc2;
    @FXML
    private Label sc3;
    @FXML
    private Label sc4;
    @FXML
    private Pane p1;
    @FXML
    private Pane p2;
    @FXML
    private Pane p3;
    @FXML
    private Pane p4;
    @FXML
    private VBox pioche;
    @FXML
    private HBox inventaire;

    public VueDuJeu(IJeu jeu) {

        this.jeu = jeu;
        vueJoueurCourant = new VueJoueurCourant();
        plateau = new VuePlateau(jeu);



        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/VueDuJeu.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        plateauPane.getChildren().add(plateau) ;



    }

    public IJeu getJeu() {
        return jeu;
    }

    public void initialize(){

    }

    public void initJoueurs(){

        n1.setText(jeu.getJoueurs().get(0).getNom());
        n2.setText(jeu.getJoueurs().get(1).getNom());
        n3.setText(jeu.getJoueurs().get(2).getNom());
        n4.setText(jeu.getJoueurs().get(3).getNom());
        sc1.setText("Score :" +jeu.getJoueurs().get(0).getScore());
        sc2.setText("Score :" +jeu.getJoueurs().get(1).getScore());
        sc3.setText("Score :" +jeu.getJoueurs().get(2).getScore());
        sc4.setText("Score :" +jeu.getJoueurs().get(3).getScore());
    }

    public void creerBindings() {
        initJoueurs();
        infoProperty = new SimpleStringProperty();
        information.textProperty().bind(infoProperty);
        Platform.runLater(()->plateau.creerBindings(this));
        setListener();
    }

    public void setListener() {
        jeu.instructionProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                infoProperty.set(t1);
            }
        });

        jeu.joueurCourantProperty().addListener(new ChangeListener<IJoueur>() {
            @Override
            public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur iJoueur, IJoueur t1) {
                if (t1.getNom() == jeu.getJoueurs().get(0).getNom()) {
                    p1.setBorder(new Border(new BorderStroke(Color.RED,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                    p2.setBorder(new Border(new BorderStroke(Color.BLACK,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                    p3.setBorder(new Border(new BorderStroke(Color.BLACK,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                    p4.setBorder(new Border(new BorderStroke(Color.BLACK,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                }
                if (t1.getNom() == jeu.getJoueurs().get(1).getNom()) {
                    p2.setBorder(new Border(new BorderStroke(Color.RED,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                    p1.setBorder(new Border(new BorderStroke(Color.BLACK,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                    p3.setBorder(new Border(new BorderStroke(Color.BLACK,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                    p4.setBorder(new Border(new BorderStroke(Color.BLACK,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                }
                if (t1.getNom() == jeu.getJoueurs().get(2).getNom()) {
                    p2.setBorder(new Border(new BorderStroke(Color.BLACK,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                    p4.setBorder(new Border(new BorderStroke(Color.BLACK,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                    p1.setBorder(new Border(new BorderStroke(Color.BLACK,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                    p3.setBorder(new Border(new BorderStroke(Color.RED,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                }
                if (t1.getNom() == jeu.getJoueurs().get(3).getNom()) {
                    p4.setBorder(new Border(new BorderStroke(Color.RED,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                    p3.setBorder(new Border(new BorderStroke(Color.BLACK,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                    p2.setBorder(new Border(new BorderStroke(Color.BLACK,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                    p1.setBorder(new Border(new BorderStroke(Color.BLACK,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                }
            }
        });

        jeu.getJoueurs().get(0).scoreProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                sc1.setText("Score : " + t1.intValue());
            }
        });
        jeu.getJoueurs().get(1).scoreProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                sc2.setText("Score : " + t1.intValue());
            }
        });
        jeu.getJoueurs().get(2).scoreProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                sc3.setText("Score : " + t1.intValue());
            }
        });
        jeu.getJoueurs().get(3).scoreProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                sc4.setText("Score : " + t1.intValue());
            }
        });

        jeu.cartesWagonVisiblesProperty().addListener(new ListChangeListener<CouleurWagon>() {
            @Override
            public void onChanged(Change<? extends CouleurWagon> change) {

                while (change.next()) {

                    if (change.wasAdded()) {
                        Platform.runLater(() -> pioche.getChildren().add(new VueCarteWagon(change.getList().get(change.getFrom()), jeu, pioche)));

                    }

                    if (change.wasRemoved()) {
                        //supprimer les cartes plûtot ici
                         //Platform.runLater(()-> carteSAMER());


                    }
                }
            }
        });



    /*    jeu.destinationsInitialesProperty().addListener(new ListChangeListener<Destination>() {
            @Override
            public void onChanged(Change<? extends Destination> change) {
                while(change.next()){
                    if(change.wasAdded()){
                        System.out.println(change.getList().size());
                        Platform.runLater(()->pioche.getChildren().add(new VueDestination(change.getList().get(change.getFrom()),jeu,pioche)));
                    }
                }
            }
        });*/

     /*   jeu.joueurCourantProperty().getValue().cartesWagonProperty().addListener(new ListChangeListener<CouleurWagon>() {
            @Override
            public void onChanged(Change<? extends CouleurWagon> change) {
                while(change.next()){

                }
            }
        });*/
    }


    public void carteSAMER() {
        for (int i = 0; i < pioche.getChildren().size(); i++) {
            pioche.getChildren().get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    pioche.getChildren().remove(mouseEvent.getPickResult().getIntersectedNode());
                }
            });
        }
    }


    @FXML
    public void btaction(){
        jeu.passerAEteChoisi();
    }

    public Pane getPlateauPane(){
        return plateauPane;
    }

}
