# MobileDeviceExercise
MobileDeviceKeyboard Exercise

/*
 * Author: Daniel Park
 * Date: 9/18/2018
 * Asymmetrik Coding Exercise
 */

The MobileDevice Keyboard Coding Exercise calls for an efficient way of handling incomplete word prefixes to "predict"
the best word to autocomplete based on a confidence level that is generated through training a dictionary of words.

The implementation I utilized for this exercise was the use of the Trie data structure based on chars. This structure
allows the storage of words based on char types where each word is stored in data as a literal "link" of characters.
Additionally, a TrieNode (each node that is represented in a Trie structure) has a variable to determine whether the
current link of characters represents a complete word.

For my application, the Trie data structure is represented in the Java class "AutoCompleter.java". The AutoCompleter class
has two Objects that are doing the heavy lifting of the Autocomplete functionality. One is the "Trie" that is created as more words
are added to the dictionary of words being passed in. The other is a HashMap data structure, confTracker, that will map out
all words that are added to a (key, value) pair representing the confidence value of a word. The HashMap structure will allow
for O(1) complexity for lookup requests and thus work excellently with a dictionary (String,Integer) type of configuration.

When a user enters a prefix for a word, the program will search through the Trie data structure and append to a List
of all the words that begin with the prefix. The List will then be sorted (from highest to lowest) based on the
Confidence value of that particular word based on the dictionary training thus far. The sort utilizes the Collections.sort
method which has a worst-case complexity of O(n log n). Upon finishing the sort, the application will then return the
results of the potential AutoComplete predictions to the user from the most confident to lowest confident values.

Program Use:

Input-
Driver.java is configured to have an AutoCompleter object with an example passage for initial knowledge building.
Passage:
"The third thing that I need to tell you is that this thing does not think thoroughly."

Driver.java can then be run with the preset configurations or altered depending on the needs of the testing

Example Output:
"thi" -->  "thing" (2), "this" (1), "third" (1), "think" (1)
"nee" -->  "need" (1)
"th" -->  "that" (2), "thing" (2), "the" (1), "this" (1), "third" (1), "think" (1), "thoroughly." (1)
