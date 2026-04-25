package org.example.tfgbackend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.credentials.path}")
    private Resource firebaseCredentials;

    @PostConstruct
    public void initFirebase() throws IOException {
        System.out.println("=== INICIANDO FIREBASE ===");
        System.out.println("¿Existe?: " + firebaseCredentials.exists());

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(
                            firebaseCredentials.getInputStream()))
                    .build();
            FirebaseApp.initializeApp(options);
            System.out.println("=== FIREBASE OK ===");
        }
    }
}