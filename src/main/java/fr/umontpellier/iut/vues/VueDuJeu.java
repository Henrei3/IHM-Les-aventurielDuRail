package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

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
public class VueDuJeu extends HBox {

    private IJeu jeu;
    private VuePlateau plateau;
    private VueJoueurCourant vueJoueurCourant;

    @FXML
    private Button bt;

    public VueDuJeu(IJeu jeu) {
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

    }

    public IJeu getJeu() {
        return jeu;
    }

    public void creerBindings() {
//        plateau.creerBindings();
    }

    @FXML
    public void btOnAction(){
        System.out.println("TEST FONCTIONNE");
    }

}
