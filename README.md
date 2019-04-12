Author: Steele Desmond - sdesmondemail@gmail.com
# Remi Tile Game

## Versions (Jar files)

Version 1.0 - Basic Rules Implementation

## How to use this program
Simply download and run the jar file. All source code is included in the jar
although you may find it easier to pull the project itself.

Upon first running the game, you can click the "Help?" button in the top
right-hand corner to learn the rules / controls of the game but I have also 
included them below.

## How to Play

#### Basic Rules:

Player 1 always goes first
##### On his/her turn the player:
- picks one tile from from the tile pool or from the top of the discard pile of the opponent

- adds the new tile to the board

- discards one tile from the board on top of his/her discard pile

##### if the tile pool is empty the discard tiles are returned to the pool

##### A first player wins the game by being the first to build a winning configuration

- 14 tiles organized in terms of sets or runs

-  Winner gains -50 points

- Loser gains the sum total of the values of the tiles on the board with the jocker counted as 25 points

- The game is played until 200 points is reached by any of the players

- The overall winner is the player with fewest number of points

#### Controls:

##### Clicking reset will reset the scores as well as the current round
##### Player one can move the tiles around in their hand
   - Clicking a tile in your hand will select the tile. If you have not discarded already, click the discard pile
      to discard the selected tile.

   - Clicking another tile in your hand after selecting a tile will then move the selected tile to the tile
      you clicked. If you click on the left of the tile, the selected tile will be placed to the left of that tile.
      Likewise, clicking on the right side will place the tile to the right.

- On your turn, you must first draw from (click on) the tile pool or player two's discard pile before discarding a card.

## Implemented Features

All of the features included in the basic rules should work if the game is 
played without trying to break it. The game has some small extra features aside 
from the required ones. The human player is able to sort the tiles within their hand
by selecting them (as explained in the controls).

## Testing and Debugging

There is a dummy player hand that is useful for testing the game rules in the 
GameRules file. The computer player's hand is hidden inside the newGame method
in the TileManager file and can be commented out. However, the tiles are set 
as hidden and unhidden inside the Player and ComputerPlayer files.

## Unfinished features

I have not implemented the advanced rules (as described in /docs). The design 
has been set up so that the melding process would be able to be implemented.
The display has a GameTable in the center of the gameboard and the placeTile / 
removeTile methods in the displayController work properly for moving tiles to
the gameBoard. There are also isSet and isRun methods in the GameRules file 
that can be used to detect the melding operations.

## Bugs

Sometimes when selecting the first tile in the hand after rearranging them, the 
initial click will be off. It seems to resort itself if you keep playing.

Sometimes when drawing a tile from the discard pile a "Duplicate StackPane found"
error is thrown but the game proceeds as normal.

## Project Design Concerns

There are some design implementations that are initially strange that were required
for me to set the design up properly. 

I am using an FXML loader and fxml files as
my displays for the application. Because I would run into issues with multiple 
references to the displayController that is linked to the FXML loader, I read online 
that the proper way to implement this is to provide a getDisplayController in the 
main application. So, for the objects that use the display they are instantiated 
in the GameCoordinator and it is passed through their constructor. The Coordinator 
itself does not use the display.

The Tile object was initially designed to be its own object, but as I designed the
display I decided to give some JavaFX-specific objects to the Tile object to make it
easier. The Tile object has a StackPane with objects on it, and the size/dimensions 
of the Pane are set in the DisplayController under setTileProperties().
