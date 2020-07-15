# Game-Of-Life 1.2.0
Java implementation of John Conway's Game of Life. Improvements are ongoing.

### Features
- Tweakable game board size (50x50 - 100x100) controlled by slider
- Tweakable game speed using slider
- Standard controls ("Clear," "Start," and "Stop" buttons)
- Dropdown menu featuring notable, preexisting patterns (Glider, Gosper Glider Gun, Lightweight Spaceship, etc.) along with a pseudorandom selecton of active cells

### Future Improvements
- Adding more patterns to the presets
- Investigate the possbility of a pseudo-infinite board

### Nitpicky Details (New to this version)
There's nothing new as far as user features are concerned, but this version includes a massive
overhaul behind the scenes to the runtime complexity as far as rule implementation is concerned.
In 1.0.0, the runtime complexity was O(n<sup>2</sup>), which was fine for a 50x50 board size and
no controls over the speed of the simulation. After making the board size scalable to 100x100,
some sort of optimization was needed to improve the asymptotic complexity of the algorithms I
had written. In my first pass, I was able to get this down to O(n * m), where m is less than n
in all but the worst case. This ran much better, but still suffered some lag with a lot of active
"cells." In this version, I traded out a standard ArrayList in favor of a HashMap to take advantage
of its O(1) lookup method, allowing me to reduce the amount of code needed (always a good feeling)
and, even better, get the complexity down to O(n) when applying the rules of the game. It runs
buttery smooth now.