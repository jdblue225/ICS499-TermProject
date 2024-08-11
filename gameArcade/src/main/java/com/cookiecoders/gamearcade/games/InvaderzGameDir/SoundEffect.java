package com.cookiecoders.gamearcade.games.InvaderzGameDir;

import javafx.scene.media.AudioClip;

import java.net.URL;

/**
 * Created by tonysaavedra on 6/20/16.
 *
 */
public class SoundEffect {
    private AudioClip soundEffect;

    public SoundEffect(String filePath) {
        String fullPath = InvaderzConfig.getInstance().getGameResourcePath() + filePath;
        URL resourceUrl = getClass().getResource(fullPath);

        if (resourceUrl == null) {
            throw new IllegalArgumentException("Resource not found: " + fullPath);
        }
        soundEffect = new AudioClip(resourceUrl.toExternalForm());
    }

    public void playClip() {
        soundEffect.play();
    }
}
