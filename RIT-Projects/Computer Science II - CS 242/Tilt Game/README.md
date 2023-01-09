# Tilt Game
A PTUI and GUI implementation for the sliding logic maze puzzle Tilt.

The game allows you to load puzzles, receive hints (from BFS and DFS search algorithms), reset, and of course play.


Rules:

The board gets populated with gray blockers that do not move and green and blue sliders.
You can only move the sliders by tilting the board up, down, left or right.
When you do that, all the sliders will move all the way across the board in that direction until they reach the end of the board or a blocker.
Moving them just half way is not allowed. The goal is to get all the green sliders to fall through the hole while
all the blue ones remain on the board


![Screenshot](Screenshots/TiltScreenshot.png)
