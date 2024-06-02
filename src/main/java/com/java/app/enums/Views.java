package com.java.app.enums;

import com.java.app.interfaces.FileName;

import static com.java.app.Resources.string;

public enum Views implements FileName {
    SIGNON {
        private static final String fileName = "sign-on";

        @Override
        public String getFullFileName() {
            return viewsDir + fileName + pathEnd;
        }

        @Override
        public String toString() {
            return string("authorization");
        }
    }, SIGNUP {
        private static final String fileName = "sign-up";

        @Override
        public String getFullFileName() {
            return viewsDir + fileName + pathEnd;
        }

        @Override
        public String toString() {
            return string("signup");
        }
    }, MAIN {
        private static final String fileName = "main";

        @Override
        public String getFullFileName() {
            return viewsDir + fileName + pathEnd;
        }

        @Override
        public String toString() {
            return string("app_name");
        }
    };

    private static final String viewsDir = "/views/";
    private static final String pathEnd = "-view.fxml";
}
