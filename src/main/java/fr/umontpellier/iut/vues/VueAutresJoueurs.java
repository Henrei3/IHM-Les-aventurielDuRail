package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import javafx.scene.layout.Pane;

/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueAutresJoueurs extends Pane {

    public VueAutresJoueurs(IJeu jeu){
        createBindings(jeu);
    }

    public void createBindings(IJeu jeu){


        setListener();
    }

    public void setListener(){

    }

}
