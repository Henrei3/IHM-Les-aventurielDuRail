package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.rails.CouleurWagon;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Cette classe représente la vue d'une carte Wagon.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarteWagon extends Pane {

    private ICouleurWagon couleurWagon;

    public VueCarteWagon(ICouleurWagon couleurWagon) {
        this.couleurWagon = couleurWagon;

        //Image i = new Image("/images/cartesWagons/carte-wagon-"+couleurWagon.toString()+".png");
       //getChildren().add(new ImageView(i));
    }

    public ICouleurWagon getCouleurWagon() {
        return couleurWagon;
    }

}

