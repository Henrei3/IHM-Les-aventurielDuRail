package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IRoute;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.EventListener;
import java.util.List;
import java.util.ListResourceBundle;

/**
 * Cette classe présente les routes et les villes sur le plateau.
 *
 * On y définit le listener à exécuter lorsque qu'un élément du plateau a été choisi par l'utilisateur
 * ainsi que les bindings qui mettront ?à jour le plateau après la prise d'une route ou d'une ville par un joueur
 */
public class VuePlateau extends Pane {
    private IJeu jeu;

    public VuePlateau(IJeu jeu) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/plateau.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.jeu = jeu;
    }

    @FXML
    public void choixRouteOuVille() {
        System.out.println("elPP");
        //jeu.uneVilleOuUneRouteAEteChoisie();
    }

    @FXML
    ImageView image;
    @FXML
    private Group villes;
    @FXML
    private Group routes;

    public void creerBindings(VueDuJeu jeu) {
        bindRedimensionPlateau(jeu);
    }

    private void bindRedimensionPlateau(VueDuJeu jeu) {
        bindRoutes();
        bindVilles();

//        Les dimensions de l'image varient avec celle de la scène
        image.fitWidthProperty().bind(jeu.getPlateauPane().prefWidthProperty());
        image.fitHeightProperty().bind(jeu.getPlateauPane().prefHeightProperty());

        jeu.getPlateauPane().widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                image.fitWidthProperty().bind(new DoubleBinding() {
                    { super.bind(image.fitWidthProperty(), image.fitHeightProperty());}
                    @Override
                    protected double computeValue() {
                        return t1.doubleValue();
                    }
                });
            }
        });

        jeu.getPlateauPane().heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                image.fitHeightProperty().bind(new DoubleBinding() {
                    { super.bind(image.fitWidthProperty(), image.fitHeightProperty());}
                    @Override
                    protected double computeValue() {
                       return  t1.doubleValue();
                    }
                });
            }
        });
    }

    private void bindRectangle(Rectangle rect, double layoutX, double layoutY) {

//      Liste des propriétés à lier
        rect.widthProperty().bind(new DoubleBinding() { //bon
            { super.bind(image.fitWidthProperty(), image.fitHeightProperty());}
        @Override
         protected double computeValue() {
           return DonneesPlateau.largeurRectangle*image.getLayoutBounds().getWidth()/DonneesPlateau.largeurInitialePlateau;
          }
        });

        rect.heightProperty().bind(new DoubleBinding() { //bon
            { super.bind(image.fitWidthProperty(), image.fitHeightProperty());}
            @Override
            protected double computeValue() {
                return DonneesPlateau.hauteurRectangle*image.getLayoutBounds().getHeight()/DonneesPlateau.hauteurInitialePlateau;
            }
        });

         rect.layoutXProperty().bind(new DoubleBinding() { //bon
             { super.bind(image.fitWidthProperty(), image.fitHeightProperty());}

             @Override
             protected double computeValue() {
                 return layoutX * image.getLayoutBounds().getWidth()/ DonneesPlateau.largeurInitialePlateau;
             }
         });

        rect.xProperty().bind(new DoubleBinding() {
            { super.bind(image.fitWidthProperty(), image.fitHeightProperty());}

            @Override
            protected double computeValue() {
                return DonneesPlateau.xInitial * image.getLayoutBounds().getWidth() / DonneesPlateau.largeurInitialePlateau;
            }
        });

        rect.layoutYProperty().bind(new DoubleBinding() {
            { super.bind(image.fitWidthProperty(), image.fitHeightProperty());}

            @Override
            protected double computeValue() {
                return layoutY * image.getLayoutBounds().getHeight() / DonneesPlateau.hauteurInitialePlateau;
            }
        });

        rect.yProperty().bind(new DoubleBinding() {
            { super.bind(image.fitHeightProperty());}

            @Override
            protected double computeValue() {
                return  DonneesPlateau.yInitial * image.getLayoutBounds().getHeight() / DonneesPlateau.hauteurInitialePlateau;
            }
        });
    }

    private void bindRoutes() {
        for (Node nRoute : routes.getChildren()) {
            Group gRoute = (Group) nRoute;
            int numRect = 0;
            for (Node nRect : gRoute.getChildren()) {
                Rectangle rect = (Rectangle) nRect;
                bindRectangle(rect, DonneesPlateau.getRoute(nRoute.getId()).get(numRect).getLayoutX(), DonneesPlateau.getRoute(nRoute.getId()).get(numRect).getLayoutY());
                numRect++;
            }
        }
    }


    private void bindVilles() {
        for (Node nVille : villes.getChildren()) {
            Circle ville = (Circle) nVille;
            bindVille(ville, DonneesPlateau.getVille(ville.getId()).getLayoutX(), DonneesPlateau.getVille(ville.getId()).getLayoutY());
        }
    }

    private void bindVille(Circle ville, double layoutX, double layoutY) {
        ville.layoutXProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }
            @Override
            protected double computeValue() {
                return layoutX * image.getLayoutBounds().getWidth()/ DonneesPlateau.largeurInitialePlateau;
            }
        });
        ville.layoutYProperty().bind(new DoubleBinding() {
            {
                super.bind(image.fitWidthProperty(), image.fitHeightProperty());
            }
            @Override
            protected double computeValue() {
                return layoutY * image.getLayoutBounds().getHeight()/ DonneesPlateau.hauteurInitialePlateau;
            }
        });
        ville.radiusProperty().bind(new DoubleBinding() {
            { super.bind(image.fitWidthProperty(), image.fitHeightProperty());}
            @Override
            protected double computeValue() {
                return DonneesPlateau.rayonInitial * image.getLayoutBounds().getWidth() / DonneesPlateau.largeurInitialePlateau;
            }
        });
    }

}
