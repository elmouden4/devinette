package com.example.seance2;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etDeviner;
    private Button btnConfirmer;
    private Button btnRecommencer;
    private TextView tvResultat;
    private TextView tvTentatives;
    private TextView tvPlage;
    private ImageView ivPendu;

    private int nombreADiviner;
    private int tentatives = 0;
    private int maxTentatives = 5;
    private boolean jeuTermine = false;

    // ⚠️ IMPORTANT : Ces noms doivent correspondre EXACTEMENT à vos fichiers images
    // Vérifiez que vos images s'appellent : img.png, img_1.png, img_2.png, etc.
    // et qu'elles sont bien dans res/drawable/
    private int[] imagespendu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des vues
        etDeviner = findViewById(R.id.et_deviner);
        btnConfirmer = findViewById(R.id.btn_confirmer);
        btnRecommencer = findViewById(R.id.btn_recommencer);
        tvResultat = findViewById(R.id.tv_resultat);
        tvTentatives = findViewById(R.id.tv_tentatives);
        tvPlage = findViewById(R.id.tv_plage);
        ivPendu = findViewById(R.id.iv_pendu);

        // Initialiser le tableau des images du pendu
        initializeImages();

        // Initialiser le jeu
        initialiserJeu();

        // Écouteur pour le bouton confirmer
        btnConfirmer.setOnClickListener(v -> verifierDeviner());

        // Écouteur pour le bouton recommencer
        btnRecommencer.setOnClickListener(v -> initialiserJeu());
    }

    /**
     * Initialise le tableau des images du pendu
     * À MODIFIER : Remplacez les noms par les vrais noms de vos fichiers
     */
    private void initializeImages() {
        try {
            // Essayez d'abord avec les noms standard
            imagespendu = new int[]{
                    getDrawableId("img"),      // 0 erreurs
                    getDrawableId("img_1"),    // 1 erreur
                    getDrawableId("img_2"),    // 2 erreurs
                    getDrawableId("img_3"),    // 3 erreurs
                    getDrawableId("img_4"),    // 4 erreurs
                    getDrawableId("img_5"),    // 5 erreurs
                    getDrawableId("img_6")     // 6 erreurs
                    getDrawableId("img_7")     // 6 erreurs
            };
        } catch (Exception e) {
            // Si ça échoue, utilisez une image par défaut
            Toast.makeText(this, "Erreur : Images du pendu non trouvées",
                    Toast.LENGTH_LONG).show();

            // Images de secours (utilise une image par défaut du système)
            imagespendu = new int[]{
                    android.R.drawable.ic_dialog_info,
                    android.R.drawable.ic_dialog_info,
                    android.R.drawable.ic_dialog_info,
                    android.R.drawable.ic_dialog_info,
                    android.R.drawable.ic_dialog_info,
                    android.R.drawable.ic_dialog_info,
                    android.R.drawable.ic_dialog_info
            };
        }
    }

    /**
     * Récupère l'ID d'une ressource drawable par son nom
     */
    private int getDrawableId(String name) {
        return getResources().getIdentifier(name, "drawable", getPackageName());
    }

    private void initialiserJeu() {
        // Générer un nombre aléatoire entre 1 et 100
        nombreADiviner = (int) (Math.random() * 100) + 1;
        tvResultat.setTag(String.valueOf(nombreADiviner));

        // Réinitialiser les variables
        tentatives = 0;
        jeuTermine = false;

        // Réinitialiser l'interface
        etDeviner.setText("");
        etDeviner.setEnabled(true);
        btnConfirmer.setEnabled(true);
        tvResultat.setText("Entrez un nombre et cliquez sur OK");
        tvResultat.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark, getTheme()));
        tvTentatives.setText("0");
        tvTentatives.setTextColor(getResources().getColor(android.R.color.holo_red_dark, getTheme()));
        tvPlage.setText("Trouvez un nombre entre 1 et 100");
        tvPlage.setTextColor(getResources().getColor(android.R.color.black, getTheme()));

        // Afficher l'image initiale du pendu
        if (imagespendu != null && imagespendu.length > 0) {
            ivPendu.setImageResource(imagespendu[0]);
        }
    }

    private void verifierDeviner() {
        if (jeuTermine) {
            Toast.makeText(this, "Le jeu est terminé. Cliquez sur Recommencer", Toast.LENGTH_SHORT).show();
            return;
        }

        String input = etDeviner.getText().toString().trim();

        if (input.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer un nombre", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int nombreSaisi = Integer.parseInt(input);

            if (nombreSaisi == nombreADiviner) {
                // Correct !
                tvResultat.setText("🎉 Bravo ! Vous avez trouvé " + nombreADiviner +
                        " en " + tentatives + " tentative(s) !");
                tvResultat.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark, getTheme()));
                jeuTermine = true;
                etDeviner.setEnabled(false);
                btnConfirmer.setEnabled(false);

                Toast.makeText(this, "Vous avez gagné !", Toast.LENGTH_LONG).show();
            } else if (nombreSaisi < nombreADiviner) {
                // Trop petit
                tentatives++;
                tvTentatives.setText(String.valueOf(tentatives));
                tvResultat.setText("C'est trop petit ! Cherchez plus grand");
                tvResultat.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light, getTheme()));
                tvPlage.setText("Le nombre est entre " + (nombreSaisi + 1) + " et 100");
                afficherPendu();

                if (tentatives >= maxTentatives) {
                    perdu();
                }
            } else {
                // Trop grand
                tentatives++;
                tvTentatives.setText(String.valueOf(tentatives));
                tvResultat.setText("C'est trop grand ! Cherchez plus petit");
                tvResultat.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light, getTheme()));
                tvPlage.setText("Le nombre est entre 1 et " + (nombreSaisi - 1));
                afficherPendu();

                if (tentatives >= maxTentatives) {
                    perdu();
                }
            }

            // Nettoyer le champ de saisie
            etDeviner.setText("");
            etDeviner.requestFocus();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Veuillez entrer un nombre valide", Toast.LENGTH_SHORT).show();
        }
    }

    private void afficherPendu() {
        if (imagespendu != null && tentatives < imagespendu.length) {
            ivPendu.setImageResource(imagespendu[tentatives]);
        }
    }

    private void perdu() {
        tvResultat.setText("☠️ Perdu ! Le nombre était " + nombreADiviner +
                ". Vous avez utilisé " + tentatives + " tentative(s)");
        tvResultat.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark, getTheme()));
        jeuTermine = true;
        etDeviner.setEnabled(false);
        btnConfirmer.setEnabled(false);

        // Afficher l'image finale du pendu
        if (imagespendu != null && imagespendu.length > 0) {
            ivPendu.setImageResource(imagespendu[imagespendu.length - 1]);
        }

        Toast.makeText(this, "Vous avez perdu !", Toast.LENGTH_LONG).show();
    }
}