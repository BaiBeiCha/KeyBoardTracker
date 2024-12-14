package org.baibei.keyboardtrackerdesktop.natives;

public class KeyBoardLayout {

    public native int getKeyBoardLayout();

    static {
        System.loadLibrary("KeyBoardLayout");
    }
}
