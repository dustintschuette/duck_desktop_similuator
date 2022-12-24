# Team Entei Group Project
### Members: Dustin Schuette, Julian Castellanos, Lorena Aguilera, Jefferson Pelera

## Running the Program
### Importing and running on IDE
<ol>
  <li> Import project from github to ide </li>
   <li> Run Gradle to refresh the project and grab dependencies </li>
   <li> Run MainApplication.java as a Java Application </li>
</ol>

### Running stand alone jar file
<ol>
  <li> Download the standalone jar file from the releases folder</li>
  <li> Run standalone jar on system </li>
</ol>

### Dependencies 
<ol>
  <li> Java (JavaSE-15 or higher) </li>
  <li> ACM Graphics Library </li>
  <li> JavaFX (version 14) </li>
  <li> Gradle </li>
  <li> Junit 4.12 </li>
  <li> Mockito </li>
</ol>
  

## Sprint 1
### Expected: (7 Stories for 28 points) || Completed: (7 Stories for 30 points)
### Features Added:
<ul>
  <li> Duck idle Animation : Duck has a custom idle animation </li>
  <li> Desktop Background : There is now a genreic windows XP desktop background </li>
  <li> Ability to drag duck around screen : Duck can be dragged by the mouse and reamins where he is released</li>
  <li> Right Click Menu: A stacked menu appears when the user right clicks, items in the list highlight when hovered over.</li>
  <li> Spawn ball: On the right click menu when the "spawn ball" option is selected a new interactive ball object is added to the screen and can be thrown and bounces off of the sides of the screen</li>
  <li> Spawn Bread: On the right click menu when the option "spawn bread" is selected an interactive bread object is added to the screen that can be dragged and fed to the duck. </li>
 </ul>
 
## Sprint 2
### Expected: (8 Stories for 30 points) || Completed: (11 Stories for 31 points)
### Features Added:
<ul>
  <li> More Mockito Tests : Bread, dragging, ball, and duck all have new unit tests </li>
  <li> Boss Key : There is now a key that when pressed hides onscreen elements </li>
  <li> The duck now walks : Duck can now walk to a destination, and can be told to walk to a destination using bread object</li>
  <li> The duck now Chases: The duck will chase the bread object when dragged by the mouse.</li>
  <li> New Animations: The duck now has an eating animation and walking left animation which happen when fed and walking left respectively</li>
  <li> Delays: The duck now waits after finishing a task before deciding to do something else. </li>
 </ul>
 
 ## Sprint 3
### Expected: (7 Stories for 28 points) || Completed: (10 Stories for 28 points)
### Features Added:
<ul>
  <li> Pet Sleeps : Pet gos to sleep after prolonged periods of inactivity</li>
  <li> Duck eats nearby Bread : The duck now looks for nearby bread to eat </li>
  <li> Menu option for hiding: Hide option now appears in the right click menu</li>
  <li> Toggle Duck walking: There is an option in the right click menu for toggling where the duck walks around the screen.</li>
  <li> Action Delay: The duck now waits after finishing an action to make it look more natural</li>
  <li> Objects return when hidden: objects now return when hidden using the boss key. </li>
  <li> Multiple breads: te user can now spawn multiple breads </li>
  <li> Ball now bounces off the duck: The duck now has collision for the ball. </li>
  <li> Hat Assets: There is now a spawnable hat </li>
  <li> Wearable hat: The hat can now stick to the duck </li>
 </ul>

 ## Sprint 4
### Expected: (9 Stories for 32 points) || Completed: (8 Stories for 28 points)
### Features Added:
<ul>
  <li> Despawning items: After right clicking on an item you can select to despawn it</li>
  <li> Sleep Animation: Added sleeping animation for the duck</li>
  <li> Eggs: Ducks can lay eggs that have a chance to hatch</li>
  <li> Settings option: Created settings option for selecting a key for the boss key</li>
 </ul>

## CustomerNotes
[CustomerNotes](CustomerNotes.md)

## TechDebt

[Techdebt](TechDebt.md)

