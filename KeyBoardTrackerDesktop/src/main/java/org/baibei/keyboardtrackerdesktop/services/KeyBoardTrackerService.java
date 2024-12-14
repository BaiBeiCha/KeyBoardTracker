package org.baibei.keyboardtrackerdesktop.services;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import org.baibei.keyboardtrackerdesktop.pojo.keys.Key;
import org.baibei.keyboardtrackerdesktop.pojo.keys.KeyConvertor;
import org.baibei.keyboardtrackerdesktop.pojo.words.Word;
import org.baibei.keyboardtrackerdesktop.repositories.StandardRepository;
import org.springframework.stereotype.Service;

@Service
public class KeyBoardTrackerService implements NativeKeyListener {

    private final StandardRepository standardRepository;
    private final StringBuilder word = new StringBuilder();

    private boolean shift = false;

    public KeyBoardTrackerService(StandardRepository standardRepository) {
        this.standardRepository = standardRepository;
        registerTracker();
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_SHIFT) {
            shift = true;
        } else {
            Key key = KeyConvertor.convertKey(e.getKeyCode(), shift);
            if (key.getCharacter() != ' ' && key.getCharacter() != '<') {
                standardRepository.update(key);
                word.append(key.getCharacter());
            } else if (key.getCharacter() == ' ' && !word.isEmpty()) {
                standardRepository.update(new Word(word.toString()));
                word.setLength(0);
            } else if (key.getCharacter() == '<') {
                if (!word.isEmpty()) {
                    word.setLength(word.length() - 1);
                }
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_SHIFT) {
            shift = false;
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {}

    private void registerTracker() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        GlobalScreen.addNativeKeyListener(this);
    }
}
