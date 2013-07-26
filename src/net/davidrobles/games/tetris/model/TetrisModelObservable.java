package net.davidrobles.games.tetris.model;

import net.davidrobles.games.tetris.view.TetrisModelObserver;

public interface TetrisModelObservable {

    void notifyObservers();

    void registerObserver(TetrisModelObserver observer);

}
