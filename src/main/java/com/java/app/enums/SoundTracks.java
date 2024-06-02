package com.java.app.enums;

public enum SoundTracks {
    STARTUP {
        @Override
        public String getPath() {
            return soundsPath + "win95.wav";
        }
    },
    ERROR {
        @Override
        public String getPath() {
            return soundsPath + "error-low.wav";
        }
    },
    LOADED {
        @Override
        public String getPath() {
            return soundsPath + "sound_buttons_button5.wav";
        }
    },
    ACCESS_DENIED {
        @Override
        public String getPath() {
            return soundsPath + "sound_buttons_button8.wav";
        }
    },
    ACCESS_GRANTED {
        @Override
        public String getPath() {
            return soundsPath + "sound_buttons_button9.wav";
        }
    },
    BELL {
        @Override
        public String getPath() {
            return soundsPath + "sound_buttons_bell1.wav";
        }
    },
    WELCOME {
        @Override
        public String getPath() {
            return soundsPath + "vista.wav";
        }
    },
    BYE {
        @Override
        public String getPath() {
            return soundsPath + "winxpshutdown.wav";
        }
    },
    CLICK {
        @Override
        public String getPath() {
            return soundsPath + "sound_buttons_button3.wav";
        }
    };

    private static final String soundsPath = "/sounds/";
    public abstract String getPath();
}
