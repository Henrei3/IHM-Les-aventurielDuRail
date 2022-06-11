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
import javafx.scene.image.Image;
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

    @FXML
    private Label gc1;
    @FXML
    private Label gc2;
    @FXML
    private Label gc3;
    @FXML
    private Label gc4;


    private DoubleProperty d;
    private DoubleProperty inventaireWidth;

    private StringProperty sP1;
    private StringProperty sP2;
    private StringProperty sP3;
    private StringProperty sP4;

    private StringProperty gP1;
    private StringProperty gP2;
    private StringProperty gP3;
    private StringProperty gP4;

    @FXML
    private ImageView iP1;
    @FXML
    private ImageView iP2;
    @FXML
    private ImageView iP3;
    @FXML
    private ImageView iP4;


    public VueDuJeu(IJeu jeu) {

        this.jeu = jeu;
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
        setListener();
    }

    public void initJoueurs(){

        n1.setText(jeu.getJoueurs().get(0).getNom());
        n2.setText(jeu.getJoueurs().get(1).getNom());
        n3.setText(jeu.getJoueurs().get(2).getNom());
        n4.setText(jeu.getJoueurs().get(3).getNom());


        Image i1 = new Image("images/avatar-"+jeu.getJoueurs().get(0).getCouleur().name()+".png");
        iP1.setImage(i1);
        Image i2 = new Image("images/avatar-"+jeu.getJoueurs().get(1).getCouleur().name()+".png");
        iP2.setImage(i2);
        Image i3 = new Image("images/avatar-"+jeu.getJoueurs().get(2).getCouleur().name()+".png");
        iP3.setImage(i3);
        Image i4 = new Image("images/avatar-"+jeu.getJoueurs().get(3).getCouleur().name()+".png");
        iP4.setImage(i4);


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


        gP1 = new SimpleStringProperty("Wagons : "+ jeu.getJoueurs().get(0).getNbGares());
        gP2 = new SimpleStringProperty("Wagons : "+ jeu.getJoueurs().get(1).getNbGares());
        gP3 = new SimpleStringProperty("Wagons : "+ jeu.getJoueurs().get(2).getNbGares());
        gP4 = new SimpleStringProperty("Wagons : " + jeu.getJoueurs().get(3).getNbGares());

        gc1.textProperty().bind(gP1);
        gc2.textProperty().bind(gP2);
        gc3.textProperty().bind(gP3);
        gc4.textProperty().bind(gP4);

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
        setNbGare();
        setInventaireJoueur();


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

        jeu.getJoueurs().get(1).scoreProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    Platform.runLater(()->sP2.setValue("Score : " + t1.intValue()));
                }
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

    public void setNbGare(){
        jeu.getJoueurs().get(0).nbWagonsProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                System.out.println(t1);
                Platform.runLater(()->gP1.setValue("Wagons : " + t1));
            }
        });


            jeu.getJoueurs().get(1).nbWagonsProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    Platform.runLater(()->gP2.setValue("Wagons : " + t1.intValue()));
                }
            });


        jeu.getJoueurs().get(2).nbWagonsProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                Platform.runLater(()->gP3.setValue("Wagons : " + t1));
            }
        });



        jeu.getJoueurs().get(3).nbWagonsProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                Platform.runLater(()->gP4.setValue("Wagons : " + t1.intValue()));
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

    public void setInventaireJoueur(){
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
