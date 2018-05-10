# tagventory
 
## The story behind the tag...
There once was a little boy, who liked the things in his room well organized and ordered. But one fateful day,
when he searched for an item in the big basement downstairs, he noticed that there was just too many stuff stacked and
shoved into every corner, thus making it impossible to find what he was seeking. Alas, his decision was made: Note down
every single object located within the basement. But he would not do this task with simple pen and paper. He was
cleverer. He made use of... *technology*. More precise: The Android Software Development Kit.

## How it works
The app's main feature is the ability to instant search through a list of user-defined items by typing the item name
and/or limiting the amount of search results via the tag-filter. Therefore, the main view consists of three elements:
The search bar, the filter and the item list.  
<img src="https://raw.githubusercontent.com/mec-kon/tagventory/master/demos/Basic_Example_1.png" alt="Main view with collapsed filter" width="180" height="308"> <img src="https://raw.githubusercontent.com/mec-kon/tagventory/master/demos/Basic_Example_2.png" alt="Main view with expanded filter" width="180" height="308">

Items can be added by tapping the "Add item"-button. This will make a dialogue appear, which allows the user to input
the item's name, add several user-generated tags (with their own names and colors) and an optional item counter.
Every item will then be shown in the item list. If the user now wants to search for one or multiple items, there are
multiple ways to do this.

1. **The search bar**  
The most obvious possibility. Typing in letters will continuously remove items from the item list until the desired
result is shown.  
<img src="https://raw.githubusercontent.com/mec-kon/tagventory/master/demos/Search_Bar_Example_1.png" alt="Typing a letter into the search bar" width="180" height="308"> <img src="https://raw.githubusercontent.com/mec-kon/tagventory/master/demos/Search_Bar_Example_2.png" alt="Typing more letters into the search bar" width="180" height="308"> <img src="https://raw.githubusercontent.com/mec-kon/tagventory/master/demos/Search_Bar_Example_3.png" alt="Typing even more letters into the search bar" width="180" height="308">

2. **The filter**  
Just like the search bar, elements that do not match the filter's configuration will be progressively removed from the
item list.
There are two ways to use the filter: Setting tags as "required" will make the item list show only those results that
include the corresponding tags.
Setting tags as "avoided" on the other hand will remove items that include the corresponding tags. Therefore, using
both filter options at once will eliminate most results from the item list beforehand without the need to use the
search bar.  
<img src="https://raw.githubusercontent.com/mec-kon/tagventory/master/demos/Filter_Example.png" alt="Setting two tags as required while avoiding another tag" width="180" height="308">

3. **Both search bar and filter**  
Basically just setting up the filters and then typing into the search bar, furthermore reducing the amount of results
shown.  
<img src="https://raw.githubusercontent.com/mec-kon/tagventory/master/demos/Filter_Search_Example_1.png" alt="Still having ELECTRICITY and TOOL as required tags while avoiding MULTIMEDIA, then typing in letters" width="180" height="308"> <img src="https://raw.githubusercontent.com/mec-kon/tagventory/master/demos/Filter_Search_Example_2.png" alt="Continuing to type in letters" width="180" height="308"> <img src="https://raw.githubusercontent.com/mec-kon/tagventory/master/demos/Filter_Search_Example_3.png" alt="Expanding the filter to change its settings" width="180" height="308"> <img src="https://raw.githubusercontent.com/mec-kon/tagventory/master/demos/Filter_Search_Example_4.png" alt="Clearing the list of avoided tags and deleting a letter" width="180" height="308"> <img src="https://raw.githubusercontent.com/mec-kon/tagventory/master/demos/Filter_Search_Example_5.png" alt="Collapsing the filter again" width="180" height="308">

## Less hardcoding, more possibilities
Of course, keeping track of your inventory isn't the only usage for our app. From the very beginning of the project, we
wanted to make sure that it can be used in any way imaginable.  
<img src="https://raw.githubusercontent.com/mec-kon/tagventory/master/demos/Alternate_Example_1.png" alt="Getting hungry?" width="180" height="308">

Think of this as an example: You always struggle to choose a dish to prepare for lunch. In order to get rid of that
problem, just use the app to make a list of dishes you like and add the respective ingredients as tags. Now, if you
want to cook something, take a look in your refrigerator and set those ingredients as required tags, which you need to
use up before they go bad. You will then receive a list of possible dishes that use those ingredients, preventing them
from going to waste.  
Or, you can set those ingredients to avoid, which you simply don't have left in stock. If you don't have any milk left,
there's no point in thinking about pancakes, simple as that.  
<img src="https://raw.githubusercontent.com/mec-kon/tagventory/master/demos/Alternate_Example_2.png" alt="Only showing dishes that do not use milk" width="180" height="308"> <img src="https://raw.githubusercontent.com/mec-kon/tagventory/master/demos/Alternate_Example_3.png" alt="No more milk left?" width="180" height="308">

You see, there's more to this app than just organizing inventory. You can make a To-Do-list and use tags as indicators
of respective importance, etc.
This is why we went for a tag-based approach. It is just insanely flexible in its possibilities and not just restricted
to a logistic use.

## A peek into the future
We plan on the app to go live on GooglePlay as soon as we reached our goal of full offline functionality. This will be
version 1.

After that, the next big step towards version 2 will be online synchronizing via user accounts, allowing multiple users
to share the same list and keeping any changes in these lists up to date. Obviously, this is important, if you plan to
organize any inventory that is accessed by more than one person. Wouldn't make any sense if everybody had his own list
with different information on the same thing, would it?

As soon as we're done with version 2, we reached both our main goals for the app. Any following updates will then be
based on user feedback and improve on the app's quality of life (possibly a few UI-adjustments or addition of requested
features).

## The people behind the code
We are mec-kon and nok-cem, two students that work on this project in their free time. Of course, we do this because
the app would be an actual help in organizing our stuff, but we'd also like to get more familiar with android app
development, since we're both interested in expanding our programming knowledge after several university-related and
also private projects in *Java*, *C*, *C++*, *Python*, even *BASIC C64* and several other languages. This is also why
we decided to use *Kotlin* for this project, a relatively new language. You live and learn!  
In addition to that, we'd like to build up our portfolios here on GitHub and we think this is a good opportunity to
expand upon them.

Note: Any screenshots in this document do NOT represent the current state of development but rather what to expect from
future versions. Expect various changes to the final look and features.
