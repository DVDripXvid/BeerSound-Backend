package com.beersound.beersoundbackend.messaging.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource

@Configuration
class FirebaseConfig {

    @Value("classpath:firebase/serviceAccountKey.json")
    private lateinit var serviceAccountResource: Resource

    @Bean
    fun initializeMessaging(): FirebaseMessaging {
                val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccountResource.inputStream))
                .build()

        FirebaseApp.initializeApp(options)
        return FirebaseMessaging.getInstance()
    }

}