package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.CouleurWagon;
import fr.umontpellier.iut.rails.Destination;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

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
    @FXML
    private ImageView imgP1;
    @FXML
    private Button btP;

    private DoubleProperty d;
    private DoubleProperty inventaireWidth;

    private StringProperty sP1;
    private StringProperty sP2;
    private StringProperty sP3;
    private StringProperty sP4;



    public VueDuJeu(IJeu jeu) {

        this.jeu = jeu;
        //vueJoueurCourant = new VueJoueurCourant(jeu,this.prefHeightProperty(),prefHeightProperty());
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
        //inventaire.getChildren().add(vueJoueurCourant);
    }

    public IJeu getJeu() {
        return jeu;
    }

    public void initialize(){
        setListener();
    }

    public void initJoueurs(){

        n1.setText(jeu.getJoueurs().get(0).getNom());
        n2.setText(jeu.getJoueurs().get(1).getNom());
        n3.setText(jeu.getJoueurs().get(2).getNom());
        n4.setText(jeu.getJoueurs().get(3).getNom());

    }

    public void creerBindings() {
        initJoueurs();
        infoProperty = new SimpleStringProperty();
        information.textProperty().bind(infoProperty);

        sP1 = new SimpleStringProperty("Score : " +  jeu.getJoueurs().get(0).getScore());
        sP2 = new SimpleStringProperty("Score : " +jeu.getJoueurs().get(1).getScore());
        sP3 = new SimpleStringProperty("Score : " +jeu.getJoueurs().get(2).getScore());
        sP4 = new SimpleStringProperty("Score : " +jeu.getJoueurs().get(3).getScore());
        sc1.textProperty().bind(sP1);
        sc2.textProperty().bind(sP2);
        sc3.textProperty().bind(sP3);
        sc4.textProperty().bind(sP4);
        d = new SimpleDoubleProperty();
        inventaireWidth = new SimpleDoubleProperty();

        Platform.runLater(()->plateau.creerBindings(this));
    }





    public void setListener() {
        setInstruction();
        setCadreJoueurCourant();
        setCarteWagonVisible();
        setDestination();
        setScore();




        jeu.joueurCourantProperty().addListener(new ChangeListener<IJoueur>() {
            @Override
            public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur iJoueur, IJoueur t1) {
                Platform.runLater(()->{
                    inventaire.getChildren().clear();
                    for(int i=0;i<t1.cartesWagonProperty().size();i++){
                        inventaire.getChildren().add(new VueCarteWagon(t1.getCartesWagon().get(i),jeu));
                    }
                    for(Node n: inventaire.getChildren()){
                        VueCarteWagon c = (VueCarteWagon) n;
                        c.setFitHeight(100);
                    }



                });
            }
        });

        inventaire.getChildren().addListener(new ListChangeListener<Node>() { //bind pour l'ajout d'une carte
            @Override
            public void onChanged(Change<? extends Node> change) {
                while(change.next()){
                    if(change.wasAdded()){
                        VueCarteWagon h = (((VueCarteWagon)change.getList().get(change.getFrom())));
                        h.fitWidthProperty().bind(d);
                        if(inventaireWidth.getValue()!=0) {
                            d.setValue(inventaireWidth.getValue() / inventaire.getChildren().size());
                        }
                        else{
                            d.setValue(inventaire.getPrefWidth()/inventaire.getChildren().size() -2);
                        }
                    }
                }
            }
        });

        inventaire.widthProperty().addListener(new ChangeListener<Number>() { //bind pour le resize fenêtre
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if(!inventaire.getChildren().isEmpty()){
                    inventaireWidth.setValue(t1);
                    d.setValue(t1.doubleValue()/inventaire.getChildren().size()-1);
                }
            }
        });
    }
    public Node trouverWagon(ICouleurWagon w){
        for (Node r :pioche.getChildren()){
            if(w.equals(((VueCarteWagon)r).getCouleurWagon())) return r;
        }
        return null;
    }

    public Node trouverDestination(IDestination d){
        for (Node r :pioche.getChildren()){
            if(d.equals(((VueDestination)r).getDestination())) return r;
        }
        return null;
    }


    public void setScore(){
        jeu.getJoueurs().get(0).scoreProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                Platform.runLater(()->sP1.setValue("Score : " + t1));
            }
        });

        Platform.runLater(()->{
            jeu.getJoueurs().get(1).scoreProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    Platform.runLater(()->sP2.setValue("Score : " + t1.intValue()));
                }
            });
        });


        jeu.getJoueurs().get(2).scoreProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                Platform.runLater(()->sP3.setValue("Score : " + t1));
            }
        });



        jeu.getJoueurs().get(3).scoreProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                Platform.runLater(()->sP4.setValue("Score : " + t1.intValue()));
            }
        });
    }

    public void setInstruction(){
        jeu.instructionProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                infoProperty.set(t1);
            }
        });
    }

    public void setCadreJoueurCourant(){
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
    }

    public void setDestination(){
        jeu.destinationsInitialesProperty().addListener(new ListChangeListener<Destination>() {
            @Override
            public void onChanged(Change<? extends Destination> change) {
                Platform.runLater(() -> {
                    while (change.next()) {
                        if (change.wasAdded()) {
                            pioche.getChildren().add(new VueDestination(change.getList().get(change.getFrom()), jeu, pioche));
                        }
                        if (change.wasRemoved()) {
                            for (IDestination d : change.getRemoved()) {
                                pioche.getChildren().remove(trouverDestination(d));
                            }
                        }
                    }
                });
            }
        });
    }

    public void setCarteWagonVisible(){
        jeu.cartesWagonVisiblesProperty().addListener(new ListChangeListener<CouleurWagon>() {
            @Override
            public void onChanged(Change<? extends CouleurWagon> change) {
                Platform.runLater(() -> {
                    while (change.next()) {

                        if (change.wasAdded()) {
                            pioche.getChildren().add(new VueCarteWagon(change.getList().get(change.getFrom()), jeu));
                        }

                        if (change.wasRemoved()) {
                            for (ICouleurWagon w : change.getRemoved()) {
                                pioche.getChildren().remove(trouverWagon(w));
                            }
                        }
                    }
                });
            }
        });
    }


    @FXML
    public void btaction(){
        jeu.passerAEteChoisi();
    }

    public Pane getPlateauPane(){
        return plateauPane;
    }

    @FXML
    public void btPiocher(){
        jeu.uneCarteWagonAEtePiochee();
    }

}
