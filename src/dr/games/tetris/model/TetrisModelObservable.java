package dr.games.tetris.model;

import dr.games.tetris.view.TetrisModelObserver;

public interface TetrisModelObservable {

    void notifyObservers();

    void registerObserver(TetrisModelObserver observer);

}
