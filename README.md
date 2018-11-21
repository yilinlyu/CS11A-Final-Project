# CS11A-Final-Project
some kind of card game java program

Data structures:
  SLN: singly linked node
  SLL: singly linked list, composed of unlimited number of SLNs
  (A SLL forms a directional list - you can only traverse it in one direction (head to tail). It's different from arrays in that you don't
   have to specify the size of it at initialization, and this is the case in this game, since the number of cards players have is always 
   changing. However, a downside of it is that you can't access its element by indexing, i.e. you have to traverse every single element
   before to reach the desired element.)
   
Game structures: 
   Card: it has a value and a suit
   PlayerGF: player for playing go fish only; enables the player to make certain moves (receive a card, ask for a value, etc)
   GoFish: where must stuff like game flow happens; holds info about the game (who the players are, what are the cards in the pool, etc)
   
For printing only:
  CardSuitsValues: it only holds some numbers for printing the suit symbols and some strings for suit names. 
  
Console: very very simple driver class, think of it as the main method in a java class
