package labyrinthe;


// Importations des bibliothèques


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


import labyrinthe.core.*;
import labyrinthe.core.objets.*;
import labyrinthe.core.cases.*;


// Classe publique Application


public class App extends Application {

    @Override
    // Point d'entrée de l'application JavaFX qui a pour paramètre la fenêtre principale de l'Application
    public void start(Stage primaryStage) throws IOException
    {
        // Affichage du Menu Principal
        new FirstStage();
    }

    // Programme principal
    public static void main(String[] args)
    {
        // Lancement de l'Application
        launch();
    }
}


// Classe Menu Principal


class FirstStage extends Stage
{
    // Déclaration des Noeuds (composants graphiques) du Menu Principal

    Button Jouer = new Button("Jouer");
    Button NouvellePartie = new Button("Nouvelle Partie");
    Button AjouterJoueur = new Button("Ajouter Joueur");
    Button CommencerPartie = new Button("Commencer");
    Button ChargerPacksDeTexture = new Button("Charger Packs de Texture");
    Button RevenirAuMenu = new Button("Retour");
    Button RevenirAJouer = new Button("Retour");
    Button Quitter = new Button("Quitter");

    // Déclaration du groupe du noeuds vertical

    VBox VBox1 = new VBox();

    // Déclaration des tableaux dynamiques des champs de joueurs et des noms de joueurs
    // en public static car l'utilisateur ajoutera des champs de texte en fonction du nombre de joueurs souhaité (de 2 à 4) qui sera initialisé dans le jeu

    public static ArrayList<String> listeNomsJoueurs = new ArrayList<>();
    public static ArrayList<TextField> listeChampsJoueurs = new ArrayList<>();
    public static int choixpack = 0 ;
    public static String chemin_dossier="";
    

    // Déclaration de FirstStage()

    FirstStage()
    {
        // Ajout de 2 champs de texte (car 2 joueurs minimum)
        listeChampsJoueurs.add(new TextField("Entrez Nom Joueur"));
        listeChampsJoueurs.add(new TextField("Entrez Nom Joueur"));

        // Ajout des composants graphiques dans le groupe
        VBox1.getChildren().add(Jouer);
        VBox1.getChildren().add(Quitter);

        // Espacement
        VBox1.setSpacing(25);
        // Alignement centré
        VBox1.setAlignment(Pos.CENTER);

        // Création de la scène
        Scene scene = new Scene(VBox1, 500, 500);

        // Création du fond d'écran
        BackgroundImage fond_ecran = new BackgroundImage(new Image("file:labyrinthe\\images\\fond\\fond.jpg", scene.getWidth(), scene.getHeight(), false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        VBox1.setBackground(new Background(fond_ecran));

        // Personnalisation des Noeuds

        Jouer.setStyle("-fx-background-color: #5192dc; ");
        NouvellePartie.setStyle("-fx-background-color: #5192dc; ");
        AjouterJoueur.setStyle("-fx-background-color: #5192dc; ");
        CommencerPartie.setStyle("-fx-background-color: #5192dc; ");
        ChargerPacksDeTexture.setStyle("-fx-background-color: #5192dc; ");
        RevenirAuMenu.setStyle("-fx-background-color: #5192dc; ");
        RevenirAJouer.setStyle("-fx-background-color: #5192dc; ");
        Quitter.setStyle("-fx-background-color: #5192dc; ");

        for (int i = 0 ; i < listeChampsJoueurs.size() ; i ++)
        {
            listeChampsJoueurs.get(i).setStyle("-fx-background-color: #5192dc; ");
        }

        // Initialisation de la scène contenant le groupe
        this.setScene(scene);
        // Affichage du menu principal
        this.show();


        // Actions sur les Boutons


        Jouer.setOnAction
        (
            e -> 
            {
                // Actualisation du Menu
                VBox1.getChildren().clear();
                VBox1.getChildren().add(NouvellePartie);
                VBox1.getChildren().add(ChargerPacksDeTexture);
                VBox1.getChildren().add(RevenirAuMenu);
            }
        );

        NouvellePartie.setOnAction
        (
            e -> 
            {
                // Actualisation du Menu
                VBox1.getChildren().clear();
                VBox1.getChildren().add(AjouterJoueur);
                VBox1.getChildren().addAll(listeChampsJoueurs);
                VBox1.getChildren().add(CommencerPartie);
                VBox1.getChildren().add(RevenirAJouer);
            }
        );

        AjouterJoueur.setOnAction
        (
            e -> 
            {
                // Si le nombre de joueurs entré par l'utilisateur est inférieur à 4
                if (listeChampsJoueurs.size() < 4)
                {
                    // Ajout du champ de texte et actualisation du Menu
                    listeChampsJoueurs.add(new TextField("Entrez Nom Joueur"));
                    VBox1.getChildren().clear();
                    VBox1.getChildren().add(AjouterJoueur);
                    VBox1.getChildren().addAll(listeChampsJoueurs);
                    VBox1.getChildren().add(CommencerPartie);
                    VBox1.getChildren().add(RevenirAJouer);

                    for (int i = 0 ; i < listeChampsJoueurs.size() ; i ++)
                    {
                        listeChampsJoueurs.get(i).setStyle("-fx-background-color: #5192dc; ");
                    }
                }
            }
        );
        
        CommencerPartie.setOnAction
        (
            e -> 
            {
                int compter_nb_noms = 0;

                // On ajoute les champs de texte de noms de joueurs et on compte le nombre de noms de joueurs
                for (int i = 0 ; i < listeChampsJoueurs.size() ; i ++)
                {
                    if (listeChampsJoueurs.get(i).getText() != "")
                    {
                        listeNomsJoueurs.add(listeChampsJoueurs.get(i).getText());
                        compter_nb_noms += 1;
                    }
                }

                // Si l'utilisateur a bien entré un nombre de noms égal au nombre de champs de texte
                if (compter_nb_noms == listeChampsJoueurs.size())
                {
                    // Fermeture du Menu Principal
                    this.close();
                    // Fenetre de jeu
                    new GameStage();
                }
                else
                {
                    // Sinon on réactualise la liste de noms jusqu'à ce le nombre de noms soit égal au nombre de champs de texte
                    listeNomsJoueurs.clear();
                }
            }
        );

        ChargerPacksDeTexture.setOnAction
        (
            e -> 
            {
                VBox1.getChildren().clear();
                FileChooser fileChooser = new FileChooser();
                // quand on clique sur le bouton l'explorateur de fichier s'ouvre 
                Button Bouton_fichier = new Button("Ouvrir fichier");
                Bouton_fichier.setOnAction(b -> {
                    // on recupere le l'adresse 
                    File selectedFile = fileChooser.showOpenDialog(this);
                    if (selectedFile.length() != 0){
                        // on recupere cette adresse dans une chaine de caractère 
                        String chaine = String.valueOf(selectedFile);
                        // position du mot textures pour pouvoir couper l'adresse récuperée 
                        int pos = chaine.indexOf("Textures");
                        chemin_dossier = chaine.substring(0, pos+9);
                        chemin_dossier = chemin_dossier.replace("/", "\\");
                        // si utilisateur a choisi un pack, la variable est affecté a 1
                        choixpack = 1;
                    }  
                });

                Bouton_fichier.setStyle("-fx-background-color: #5192dc; ");

                VBox1.getChildren().add(Bouton_fichier);
                VBox1.getChildren().add(RevenirAJouer);
            }
        );

        RevenirAuMenu.setOnAction
        (
            e -> 
            {
                // Actualisation du Menu
                VBox1.getChildren().clear();
                VBox1.getChildren().add(Jouer);
                VBox1.getChildren().add(Quitter);
            }
        );

        RevenirAJouer.setOnAction
        (
            e -> 
            {
                // Actualisation du Menu
                VBox1.getChildren().clear();
                VBox1.getChildren().add(NouvellePartie);
                VBox1.getChildren().add(ChargerPacksDeTexture);
                VBox1.getChildren().add(RevenirAuMenu);
            }
        );

        Quitter.setOnAction
        (
            e -> 
            {
                // Fermeture du Menu
                VBox1.getChildren().clear();
                this.close();
            }
        );
    }
}


// Classe Interface de Jeu


class GameStage extends Stage {

    // Variable indice du joueur actuel
    int indice_joueur = 0;

    // Variable passage case joueur
    int passage = 0;

    // Variable insertion case
    boolean insertion_case = false;

    // Variable rotation
    int rotation = 0;

    // Variable x case hors plateau

    int x_hors_plateau = 5;

    // Variable y case hors plateau

    int y_hors_plateau = -1;


    // Création d'un tableau dynamique de cases graphiques
    ArrayList<Rectangle> tableau_cases_graphiques = new ArrayList<Rectangle>();

    // Création d'un tableau dynamique de zones d'insertion
    ArrayList<Rectangle> tableau_insertion_cases_hors_plateau = new ArrayList<Rectangle>();

    // Création d'un tableau dynamique de cases hors plateau
    ArrayList<Rectangle> tableau_cases_hors_plateau = new ArrayList<Rectangle>();

    // Création d'un tableau dynamique d'objets
    ArrayList<Rectangle> tableau_objets = new ArrayList<Rectangle>();

    // Création d'un tableau dynamique de joueurs
    ArrayList<Rectangle> tableau_joueurs = new ArrayList<Rectangle>();

    // Variable taille plateau
    int taille_plateau_initial = 0;

    // Méthode pour dessiner la zone hors plateau
    private void dessiner_zone(Plateau plateau, Group root, ArrayList<Rectangle> tableau_insertion)
    {
        int hauteur = 1;
        int longueur = 1;

        // Création de la zone d'insertion verticale
        // en bas et en haut
        for (int i = 0 ; i < 2 ; i ++)
        {
            // Pour toute la longueur du plateau
            for (int j = 0 ; j < plateau.getLongueur() ; j ++)
            {
                if (i == 1)
                {
                    hauteur = plateau.getHauteur() + 2;
                }
                Rectangle case_insertion = new Rectangle();
                case_insertion.setX(50*j + 100);
                case_insertion.setY(hauteur*50-50);
                case_insertion.setWidth(50);
                case_insertion.setHeight(50);
                case_insertion.setFill(Color.GRAY);
                tableau_insertion.add(case_insertion);
            }
        }

        // Création de la zone d'insertion horizontale
        for (int i = 0 ; i < 2 ; i ++)
        {
            // Pour toute la hauteur du plateau
            for (int j = 0 ; j < plateau.getHauteur() ; j ++)
            {
                if (i == 1)
                {
                    longueur = plateau.getLongueur() + 2;
                }
                Rectangle case_insertion = new Rectangle();
                case_insertion.setX(longueur*50);
                case_insertion.setY(50*j+50);
                case_insertion.setWidth(50);
                case_insertion.setHeight(50);
                case_insertion.setFill(Color.GRAY);
                tableau_insertion.add(case_insertion);
            }
        }

        // Ajout de la zone d'insertion au groupe
        root.getChildren().addAll(tableau_insertion);
    }


    // Méthode pour dessiner le plateau
    private void dessiner_plateau(Plateau plateau, ArrayList<Rectangle> tableau_cases_graphiques, ArrayList<Rectangle> tableau_cases_hors_plateau, ArrayList<Rectangle> tableau_objets, ArrayList<Rectangle> tableau_joueurs, Group root)
    {
        // Création des cases du plateau sur la fenêtre
        for (int i = 0 ; i < plateau.getPlateau().size() ; i ++)
        {
            // On créé une forme pour chaque case graphique du plateau sur la fenêtre en fonction de la position des cases du plateau
            Rectangle rectangle = new Rectangle();
            rectangle.setX(50*(plateau.getPlateau().get(i).getPosition().getLongueur()) + 100);
            rectangle.setY(50*(plateau.getPlateau().get(i).getPosition().getHauteur()) + 50);
            rectangle.setWidth(50);
            rectangle.setHeight(50);

            // On créé un objet sur la fenêtre en fonction de la position de l'objet sur le plateau
            Rectangle objet = new Rectangle();
            objet.setX(50 * (plateau.getPlateau().get(i).getPosition().getLongueur()) + 100 + 10);
            objet.setY(50 * (plateau.getPlateau().get(i).getPosition().getHauteur()) + 10 + 50);
            objet.setWidth(25);
            objet.setHeight(25);

            // On créé un joueur sur la fenêtre en fonction de la position du joueur sur le plateau
            Rectangle joueur = new Rectangle();
            joueur.setX(50 * (plateau.getPlateau().get(i).getPosition().getLongueur()) + 100 + 10);
            joueur.setY(50 * (plateau.getPlateau().get(i).getPosition().getHauteur()) + 10 + 50);
            joueur.setWidth(25);
            joueur.setHeight(25);

            // Si la case du plateau ne contient pas d'objets, c'est une case ligne, coude ou T
                if(FirstStage.choixpack == 1){
                    if (plateau.getPlateau().get(i).getNom() == "ligne" || plateau.getPlateau().get(i).getNom() == "coude" || plateau.getPlateau().get(i).getNom() == "T")
                    {   
                        // on affecte la nouvelle adresse du pack lors de la conception du jeu
                        File file_case = new File(FirstStage.chemin_dossier + "\\cases\\" +plateau.getPlateau().get(i).getNom().toString() + Integer.toString(plateau.getPlateau().get(i).getRotation()) + ".jpg");
                        Image image_case = new Image(file_case.toURI().toString());
                        ImagePattern pattern_case = new ImagePattern(image_case);
                        rectangle.setFill(pattern_case);
                        tableau_cases_graphiques.add(rectangle);
                    }
                }
                else{
                    if (plateau.getPlateau().get(i).getNom() == "ligne" || plateau.getPlateau().get(i).getNom() == "coude" || plateau.getPlateau().get(i).getNom() == "T")
                    {
                        File file_case = new File("labyrinthe\\images\\cases\\" + plateau.getPlateau().get(i).getNom().toString() + Integer.toString(plateau.getPlateau().get(i).getRotation()) + ".jpg");
                        Image image_case = new Image(file_case.toURI().toString());
                        ImagePattern pattern_case = new ImagePattern(image_case);
                        rectangle.setFill(pattern_case);
                        tableau_cases_graphiques.add(rectangle);
                    }
                }
    

            // Si la case du plateau contient un objet, on place sur la case l'objet en le centrant
                if (plateau.getPlateau().get(i).getObjet() != null)
                {
                File file_objet = new File("labyrinthe\\images\\objets\\" + plateau.getPlateau().get(i).getObjet().getNom().toString() + ".png");
                Image image_objet = new Image(file_objet.toURI().toString());
                ImagePattern pattern_objet = new ImagePattern(image_objet);
                objet.setFill(pattern_objet);
                }

            // Pour chaque joueur de la liste des joueurs
            for (int j = 0 ; j < plateau.getListeJoueur().size() ; j ++)
            {
                // Si la position d'une case du plateau correspond à la position du joueur
                if (plateau.getPlateau().get(i).getPosition().equals(plateau.getListeJoueur().get(j).getPosition()))
                {
                    // On insère un joueur sur la case graphique en le centrant en fonction de l'index du joueur dans la liste des joueurs
                    if (plateau.getListeJoueur().get(j).getIndex() == j + 1)
                    {
                        File file_spawn = new File("labyrinthe\\images\\depart\\spawn" + Integer.toString(j + 1) + ".jpg");
                        Image image_spawn = new Image(file_spawn.toURI().toString());
                        ImagePattern pattern_spawn = new ImagePattern(image_spawn);
                        joueur.setFill(pattern_spawn);
                        tableau_joueurs.add(joueur);
                    }
                }
            }

            // On ajoute les objets au tableau d'objets
            tableau_objets.add(objet);

            // Ajout des cases graphiques et des objets au groupe
            root.getChildren().add(tableau_cases_graphiques.get(i));
            if (tableau_objets.get(i).getFill() != Color.BLACK)
            {
                root.getChildren().add(tableau_objets.get(i));
            }
        }

        // Création de la case hors du plateau sur la fenêtre
        Rectangle rectangle = new Rectangle();
        tableau_cases_hors_plateau.add(rectangle);

        // On ajoute la case hors plateau dans le groupe
        root.getChildren().addAll(tableau_cases_hors_plateau);

        // On ajoute les joueurs dans le groupe
        root.getChildren().addAll(tableau_joueurs);
    }

    // Déclaration de GameStage()

    GameStage()
    {
        // Création du bouton fermer
        Button Fermer = new Button("Quitter");
        Fermer.setLayoutX(250);
        Fermer.setLayoutY(550);

        // Création du label pour afficher le tour des joueurs
        Label label = new Label();
        label.setLayoutX(250);
        label.setLayoutY(500);
        label.setMaxWidth(400);

        // Personnalisation des Noeuds
        Fermer.setStyle("-fx-background-color: #5192dc; ");
        label.setStyle("-fx-font-weight: bold; ");

        // Création de la liste des objets
        ArrayList<Objet> listeObjet = new ArrayList<Objet>();
        Heaume heaume = new Heaume();
        listeObjet.add(heaume);
        Chandelier chandelier = new Chandelier();
        listeObjet.add(chandelier);
        Epee epee = new Epee();
        listeObjet.add(epee);
        Emeraude emeraude = new Emeraude();
        listeObjet.add(emeraude);
        
        // Création des joueurs
        ArrayList<Joueur> listeJoueurs = new ArrayList<>();
        for (int i = 0 ; i < FirstStage.listeNomsJoueurs.size() ; i ++)
        {
            Joueur j = new Joueur(FirstStage.listeNomsJoueurs.get(i), null);
            listeJoueurs.add(j);
        }
        
        // Ajout des objets à trouver pour chaque joueur
        ArrayList<Objet> listeToutObjet = new ArrayList<Objet>(listeObjet);
        for (int i = 0 ; i < listeJoueurs.size() ; i ++)
        {
            int randomObjet = ThreadLocalRandom.current().nextInt(0,listeToutObjet.size());
            listeJoueurs.get(i).addObjet(listeToutObjet.get(randomObjet));
            listeToutObjet.remove(randomObjet);
        }

        // creation cases 
        ArrayList<Case> listeCase = new ArrayList<>();
        ArrayList<Integer> nbCases = new ArrayList<>();
 
        listeCase.add(new CaseLigne());
        nbCases.add(12);

        listeCase.add(new CaseCoude());
        nbCases.add(20);

        listeCase.add(new CaseT());
        nbCases.add(18);

        ArrayList<Case> cases = new ArrayList<Case>();

        for (int i = 0; i < nbCases.size(); i++)
            for (int j = 0; j < nbCases.get(i); j++)
                cases.add(listeCase.get(i));

        // Création du plateau
        Plateau plateau = new Plateau(listeJoueurs, cases, listeObjet);
        // Plateau plateau = new Plateau(listeJoueurs, cases ,listeObjet);


        // Création du groupe
        Group root = new Group();

        // Création de la scène
        Scene scene = new Scene(root, 600, 600);

        // Dessiner plateau
        dessiner_plateau(plateau, tableau_cases_graphiques, tableau_cases_hors_plateau, tableau_objets, tableau_joueurs, root);

        // Dessiner zone d'insertion des cases hors plateau
        dessiner_zone(plateau, root, tableau_insertion_cases_hors_plateau);

        taille_plateau_initial = plateau.getPlateau().size();

        root.getChildren().add(Fermer);
        root.getChildren().add(label);

        label.setText("Au tour du joueur " + listeJoueurs.get(0).getNom());

        System.out.println(plateau);
        System.out.println("Entrez rotation, x, y");

        // Ajout d'un titre
        this.setTitle("Labyrinthe"); 

        // Ajout de la scène dans la fenêtre de jeu
        this.setScene(scene);
        
        // Affichage
        this.show();
        
        // Action Fermer
        Fermer.setOnAction
        (
            e -> 
            {
                this.close();
                new FirstStage();
            }
        );

        // Pour chaque case de la zone des cases hors plateau
        for (int i = 0 ; i < tableau_insertion_cases_hors_plateau.size() ; i ++)
        {
            // On récupère la case de la zone d'insertion sur laquelle on va cliquer
            Rectangle case_insertion = tableau_insertion_cases_hors_plateau.get(i);

            // Lorsqu'on clique sur la case de la zone d'insertion
            case_insertion.setOnMouseClicked
            (
                event ->
                {
                    // Si la case n'a pas été inséré
                    if (insertion_case == false)
                    {
                        // On récupère les coordonnées de la case hors plateau pour les convertir en coordonnées de plateau du terminal
                        x_hors_plateau = (((int) case_insertion.getX()) / 50 - 1) - 1 ;
                        y_hors_plateau = (( ((int) case_insertion.getY()) / 50*(plateau.getLongueur())) / plateau.getLongueur()) - 1;

                        // Si on clique droit
                        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1 && x_hors_plateau % 2 != 0 && y_hors_plateau % 2 != 0)
                        {
                            File file_test = new File("labyrinthe\\images\\cases\\" + plateau.getCaseDefaussee().getNom() + Integer.toString(plateau.getCaseDefaussee().getRotation()) + ".jpg");
                            Image image_test = new Image(file_test.toURI().toString());
                            ImagePattern pattern_test = new ImagePattern(image_test);
                            case_insertion.setFill(pattern_test);
                            insertion_case = true;
                        }
                    }
                    else if (insertion_case == true && case_insertion.getFill() != Color.GRAY)
                    {
                        // Si la case a été insérée et qu'on double clique gauche, la pièce tourne
                        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && (rotation == 0 || rotation < 270))
                        {
                            rotation += 90;
                            plateau.getCaseDefaussee().setRotation(rotation);
                            File file_test = new File("labyrinthe\\images\\cases\\" + plateau.getCaseDefaussee().getNom() + Integer.toString(rotation) + ".jpg");
                            Image image_test = new Image(file_test.toURI().toString());
                            ImagePattern pattern_test = new ImagePattern(image_test);
                            case_insertion.setFill(pattern_test);
                        } 
                        // Si la case a été insérée et qu'on double clique droit, la pièce tourne dans l'autre sens
                        else if (event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 2 && (rotation == 270 || rotation > 0))
                        {
                            rotation -= 90;
                            plateau.getCaseDefaussee().setRotation(rotation);
                            File file_test = new File("labyrinthe\\images\\cases\\" + plateau.getCaseDefaussee().getNom() + Integer.toString(rotation) + ".jpg");
                            Image image_test = new Image(file_test.toURI().toString());
                            ImagePattern pattern_test = new ImagePattern(image_test);
                            case_insertion.setFill(pattern_test);
                        }

                        System.out.println(rotation);
                        System.out.println(x_hors_plateau);
                        System.out.println(y_hors_plateau);
                    }
                }
            );
        }

        // Pour chaque case du plateau
        for (int i = 0 ; i < tableau_cases_graphiques.size() ; i ++)
        {
            Rectangle rect = tableau_cases_graphiques.get(i);
                    
            // Détection clic sur une case du plateau pour déplacer la case et le joueur
            rect.setOnMouseClicked
            (
                (EventHandler<? super MouseEvent>) new EventHandler<MouseEvent>()
                {
                    public void handle(MouseEvent e)
                    {
                        // Si la case a été insérée ou que la case hors du plateau a bien été cliquée
                        if (insertion_case == true || (x_hors_plateau != 0 && y_hors_plateau != 0))
                        {
                            // Pour la case
                        
                            if (passage % 2 == 0)
                            {
                                var caseDefaussee = plateau.getCaseDefaussee();

                                System.out.println(caseDefaussee);

                                System.out.println(rotation);
                                caseDefaussee.setRotation(rotation);
                                caseDefaussee.setPosition(new Position(x_hors_plateau, y_hors_plateau));
                                plateau.deplacerCase(caseDefaussee);

                                // Mise à jour des cases et des objets
                            
                                for (int i = 0 ; i < tableau_cases_graphiques.size() ; i ++)
                                {
                                    // Si la case ne contient pas d'objets, on met à jour la case ligne, coude ou T
                                    // Cases ligne
                                    {
                                        File file_case = new File("labyrinthe\\images\\cases\\" + plateau.getPlateau().get(i).getNom().toString() + Integer.toString(plateau.getPlateau().get(i).getRotation()) + ".jpg");
                                        Image image_case = new Image(file_case.toURI().toString());
                                        ImagePattern pattern_case = new ImagePattern(image_case);
                                        tableau_cases_graphiques.get(i).setX(50*(plateau.getPlateau().get(i).getPosition().getLongueur()) + 100);
                                        tableau_cases_graphiques.get(i).setY(50*(plateau.getPlateau().get(i).getPosition().getHauteur()) + 50);
                                        tableau_cases_graphiques.get(i).setFill(pattern_case);
                                        }


                                    // Si la case contient un objet, on déplace l'objet
                                    if (tableau_objets.get(i).getFill() != Color.BLACK)
                                    {
                                        File file_objet = new File("labyrinthe\\images\\objets\\" + plateau.getPlateau().get(i).getObjet().getNom().toString() + ".png");
                                        Image image_objet = new Image(file_objet.toURI().toString());
                                        ImagePattern pattern_objet = new ImagePattern(image_objet);
                                        tableau_objets.get(i).setX(50 * (plateau.getPlateau().get(i).getPosition().getLongueur()) + 100 + 10);
                                        tableau_objets.get(i).setY(50 * (plateau.getPlateau().get(i).getPosition().getHauteur()) + 10 + 50);
                                        tableau_objets.get(i).setFill(pattern_objet);
                                    }

                                    // Pour chaque joueur de la liste des joueurs
                                    for (int j = 0 ; j < plateau.getListeJoueur().size() ; j ++)
                                    {
                                        // Si la position d'une case du plateau correspond à la position du joueur
                                        if (plateau.getPlateau().get(i).getPosition().equals(plateau.getListeJoueur().get(j).getPosition()))
                                        {
                                            // On déplace le joueur
                                            if (plateau.getListeJoueur().get(j).getIndex() == j + 1)
                                            {
                                                File file_spawn = new File("labyrinthe\\images\\depart\\spawn" + Integer.toString(j + 1) + ".jpg");
                                                Image image_spawn = new Image(file_spawn.toURI().toString());
                                                ImagePattern pattern_spawn = new ImagePattern(image_spawn);
                                                tableau_joueurs.get(j).setX(50 * (plateau.getPlateau().get(i).getPosition().getLongueur()) + 100 + 10);
                                                tableau_joueurs.get(j).setY(50 * (plateau.getPlateau().get(i).getPosition().getHauteur()) + 10 + 50);
                                                tableau_joueurs.get(j).setFill(pattern_spawn);
                                            }
                                        }
                                    }
                                }

                                // On passage au déplacement de joueur
                                passage += 1;

                                System.out.println(plateau);
                                System.out.println("Entrez x, y");
                            }

                            // Pour le joueur

                            else if (passage % 2 != 0)
                            {
                                System.out.println(listeJoueurs.get(indice_joueur).getToutDeplacementPossible(plateau.getPlateau()));

                                // Conversion des coordonnées du plateau graphiques en coordonnées du plateau
                                int x = (((int) rect.getX()) / 50 - 1) - 1 ;
                                int y = (( ((int) rect.getY()) / 50*(plateau.getLongueur())) / plateau.getLongueur()) - 1;

                                System.out.println(x);
                                System.out.println(y);

                                listeJoueurs.get(indice_joueur).deplacer(plateau.getPlateau(), new Position(x,y));
                                plateau.joueurRecupereObjet(listeJoueurs.get(indice_joueur));

                                // Mise à jour du joueur

                                for (int i = 0 ; i < tableau_cases_graphiques.size() ; i ++)
                                {
                                    // Pour chaque joueur de la liste des joueurs
                                    for (int j = 0 ; j < plateau.getListeJoueur().size() ; j ++)
                                    {
                                        // Si la position d'une case du plateau correspond à la position du joueur
                                        if (plateau.getPlateau().get(i).getPosition().equals(plateau.getListeJoueur().get(j).getPosition()))
                                        {
                                            // On déplace le joueur
                                            if (plateau.getListeJoueur().get(j).getIndex() == j + 1)
                                            {
                                                File file_spawn = new File("labyrinthe\\images\\depart\\spawn" + Integer.toString(j + 1) + ".jpg");
                                                Image image_spawn = new Image(file_spawn.toURI().toString());
                                                ImagePattern pattern_spawn = new ImagePattern(image_spawn);
                                                tableau_joueurs.get(j).setX(50 * (plateau.getPlateau().get(i).getPosition().getLongueur()) + 100 + 10);
                                                tableau_joueurs.get(j).setY(50 * (plateau.getPlateau().get(i).getPosition().getHauteur()) + 10 + 50);
                                                tableau_joueurs.get(j).setFill(pattern_spawn);
                                            }
                                        }
                                    }
                                }

                                // La case hors du plateau devra être insérée de nouveau
                                insertion_case = false;
                                x_hors_plateau = 0;
                                y_hors_plateau = 0;

                                // Si un joueur a récupéré tous les objets, la fenêtre se ferme
                                if (listeJoueurs.get(indice_joueur).recupererToutObjet() == true)
                                {
                                    System.out.println(listeJoueurs.get(indice_joueur).getNom() + " a gagné !");
                                    System.exit(0);
                                }

                                // Passage au déplacement de joueur au tour suivant
                                passage += 1;
                                // On change de joueur
                                indice_joueur += 1;

                                // Si le joueur est le dernier de la liste, on recommence
                                if (indice_joueur > listeJoueurs.size() - 1)
                                {
                                    indice_joueur = 0;
                                }

                                System.out.println(plateau);
                                label.setText("Au tour du joueur " + listeJoueurs.get(indice_joueur).getNom());
                                System.out.println(plateau);
                                System.out.println("Entrez rotation, x, y");
                            }
                        }
                    }
                }
            );
        }
    }
}