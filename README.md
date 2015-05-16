# AndroidImageSearchApp
This is an Android Image Search app letting users to search/browse images through Google APIs.
Full list of completed user stories are given below.

Time spent: 20 hours

User stories and features:

* [x] Required: User can enter a search query that will display a grid of image results from the Google Image API

* [x] Required: User can click on "settings" which allows selection of advanced search options to filter results

* [x] Required: User can configure advanced search filters such as:

  Size (small, medium, large, extra-large)
  
  Color filter (black, blue, brown, gray, green, orange, pink, purple, red, teal, white, yellow)
  
  Type (faces, photo, clip art, line art)
  
  Site (i.e. espn.com)

* [x] Required: Subsequent searches will have any filters applied to the search results

* [x] Required: User can tap on any image in results to see the image full-screen

* [x] Required: User can scroll down “infinitely” to continue loading more image results (up to 8 pages)

* [x] Advanced: Robust error handling, check if internet is available, handle error cases, network failures. Show Toast messages in case of mentioned error cases.

* [x] Advanced: Use the ActionBar SearchView as the query box instead of an EditText

* [x] Advanced: User can share an image to their friends or email it to themselves

* [x] Advanced: Replace Filter Settings Activity with a lightweight modal overlay

* [x] Advanced: Improve the user interface and experiment with image assets and/or styling and coloring. Styling includes creating icons for each screen density size, hiding/customizing action bar etc.

* [x] Optional: Organizing the code through packaging and externalizing all string resources into strings.xml

* [x] Optional: Improving the performance by using Parcelable interface for Filter and ImageResult models and using ViewHolder pattern for (Staggered)GridView

* [x] Bonus: Use the StaggeredGridView to display improve the grid of image results

* [x] Bonus: (In-Progress)User can zoom or pan images displayed in full-screen detail view

Screenshots from the app:

![alt tag](https://github.com/esrako/AndroidInstagramClient/blob/master/photo.png)

Walkthrough:

![alt tag](https://github.com/esrako/AndroidInstagramClient/blob/master/project2.gif)

