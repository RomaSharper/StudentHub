package com.java.app.objects;

import com.java.app.enums.SoundTracks;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class Sounds {
    /**
     * Проигрывает звук
     * @param sound Звук
     **/
    public static void playSound(SoundTracks sound) {
        playSound(sound, 1);
    }

    /**
     * Проигрывает звук с заданной громкостью
     * @param sound Звук
     * @param volume Громкость от <code>0</code> до <code>1</code>
     **/
    public static void playSound(SoundTracks sound, double volume) {
        Media media = new Media(Objects.requireNonNull(Sounds.class.getResource(sound.getPath())).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(volume);
        mediaPlayer.play();
    }
}
