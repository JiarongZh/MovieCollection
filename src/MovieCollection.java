import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class MovieCollection {
  private ArrayList<Movie> movies;
  private Scanner scanner;

  public MovieCollection(String fileName) {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies() {
    return movies;
  }

  public void menu() {
    String menuOption = "";

    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");

    while (!menuOption.equals("q")) {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();

      if (menuOption.equals("t")) {
        searchTitles();
      } else if (menuOption.equals("c")) {
        searchCast();
      } else if (menuOption.equals("k")) {
        searchKeywords();
      } else if (menuOption.equals("g")) {
        listGenres();
      } else if (menuOption.equals("r")) {
        listHighestRated();
      } else if (menuOption.equals("h")) {
        listHighestRevenue();
      } else if (menuOption.equals("q")) {
        System.out.println("Goodbye!");
      } else {
        System.out.println("Invalid choice!");
      }
    }
  }

  private void importMovieList(String fileName) {
    try {
      movies = new ArrayList<Movie>();
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();

      while ((line = bufferedReader.readLine()) != null) {
        // get data from the columns in the current row and split into an array
        String[] movieFromCSV = line.split(",");

        /* TASK 1: FINISH THE CODE BELOW */
        // using the movieFromCSV array,
        // obtain the title, cast, director, tagline,
        // keywords, overview, runtime (int), genres,
        // user rating (double), year (int), and revenue (int)
        String title = movieFromCSV[0];
        String cast = movieFromCSV[1];
        String director = movieFromCSV[2];
        String tagline = movieFromCSV[3];
        String keywords = movieFromCSV[4];
        String overview = movieFromCSV[5];
        int runtime = Integer.parseInt(movieFromCSV[6]);
        String genres = movieFromCSV[7];
        Double userRating = Double.parseDouble(movieFromCSV[8]);
        int year = Integer.parseInt(movieFromCSV[9]);
        int revenue = Integer.parseInt(movieFromCSV[10]);

        // create a Movie object with the row data:

        Movie movie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);

        // add the Movie to movies:
        movies.add(movie);

      }
      bufferedReader.close();
    } catch (IOException exception) {
      System.out.println("Unable to access " + exception.getMessage());
    }
  }

  private void searchTitles() {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++) {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1) {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    if (results.size() > 0) {
      // sort the results by title
      sortResults(results);

      // now, display them all to the user
      for (int i = 0; i < results.size(); i++) {
        String title = results.get(i).getTitle();

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + title);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = results.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }

  private void sortResults(ArrayList<Movie> listToSort) {
    for (int j = 1; j < listToSort.size(); j++) {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0) {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }


  private void displayMovieInfo(Movie movie) {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
    System.out.println("Box office revenue: " + movie.getRevenue());
  }

  private void searchKeywords() {
    System.out.println("Enter some keywords : ");
    String keyword = scanner.nextLine();
    keyword = keyword.toLowerCase();
    ArrayList<Movie> results = new ArrayList<Movie>();
    int count = 0;
    for (int i = 0; i < movies.size(); i++) {
      String moviekeyword = movies.get(i).getKeywords();
      moviekeyword = moviekeyword.toLowerCase();
      if (moviekeyword.contains(keyword)) {
        results.add(movies.get(i));
      }
    }
    sortResults(results);
    for (int j = 0; j < results.size(); j++) {
      System.out.println(j + 1 + ". " + results.get(j).getTitle());
    }
    if (results.size() > 0) {
      // sort the results by title
      sortResults(results);

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = results.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }

  }

  private void searchCast() {
    System.out.print("Enter a name");
    String name = scanner.nextLine();
    name = name.toLowerCase();
    ArrayList<String> results = new ArrayList<String>();
    int count = 0;
    for (int i = 0; i < movies.size(); i++) {
      String[] casts = movies.get(i).getCast().split("\\|");
      for (int k = 0; k < casts.length; k++) {
        if (!results.contains(casts[k]) && casts[k].toLowerCase().contains(name)) {
          results.add(casts[k]);
        }
      }
    }
    sortStrings(results);
    for (int j = 0; j < results.size(); j++) {
      System.out.println(j + 1 + ". " + results.get(j));
    }
    System.out.print("Who do you want to know more about? (Insert their number): ");
    int num = scanner.nextInt();
    String person = results.get(num - 1);
    ArrayList<Movie> contains = new ArrayList<Movie>();
    for (int k = 0; k < movies.size(); k++) {
      if (movies.get(k).getCast().contains(person)) {
        contains.add(movies.get(k));
      }
    }
    sortResults(contains);
    for (int l = 0; l < contains.size(); l++) {
      System.out.println(l + 1 + ". " + contains.get(l).getTitle());
    }
    scanner.nextLine();
    if (results.size() > 0) {
      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = contains.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }

  }

  private void sortStrings(ArrayList<String> listToSort) {
    for (int j = 1; j < listToSort.size(); j++) {
      String temp = listToSort.get(j);
      String tempTitle = temp;

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1)) < 0) {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }


  private void listGenres() {
    ArrayList<String> genres = new ArrayList<String>();
    for (int i = 0; i < movies.size(); i++) {
      String[] split = movies.get(i).getGenres().split("\\|");
      for (int k = 0; k < split.length; k++) {
        if (!genres.contains(split[k])) {
          genres.add(split[k]);
        }
      }
    }
    sortStrings(genres);
    for (int j = 0; j < genres.size(); j++) {
      System.out.println(j + 1 + ". " + genres.get(j));
    }
    if (genres.size() > 0) {
      System.out.println("Which would you like to see all movies for?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      String topic = genres.get(choice - 1);
      topic = topic.toLowerCase();
      ArrayList<Movie> results = new ArrayList<Movie>();
      for (int l = 0; l < movies.size(); l++) {
        if (movies.get(l).getGenres().toLowerCase().contains(topic)) {
          results.add(movies.get(l));
        }
      }
      sortResults(results);
      for (int m = 0; m < results.size(); m++) {
        System.out.println(m + 1 + ". " + results.get(m).getTitle());
      }
      System.out.print("What movie would you like to learn more about? \nEnter a number:");
      choice = scanner.nextInt();
      displayMovieInfo(results.get(choice - 1));
      scanner.nextLine();
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }

  private void listHighestRated() {
    ArrayList<Movie> greatest = new ArrayList<Movie>();
    double highest = 0.0;
    int temp = 0;
    for (int i = 0; i < 50; i++) {
      highest = 0.0;
      temp = 0;
      for (int k = 0; k < movies.size(); k++) {
        if (movies.get(k).getUserRating() >= highest && !greatest.contains(movies.get(k))) {
          highest = movies.get(k).getUserRating();
          temp = k;
        }
      }
      greatest.add(movies.get(temp));
    }
    for (int j = 0; j < 50; j++) {
      System.out.println(j + 1 + ". " + greatest.get(j).getTitle() + " " + greatest.get(j).getUserRating());
    }
    if (greatest.size() > 0) {
      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = greatest.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }

  }

  private void listHighestRevenue() {
    ArrayList<Movie> greatest = new ArrayList<Movie>();
    double highest = 0.0;
    int temp = 0;
    for (int i = 0; i < 50; i++) {
      highest = 0.0;
      temp = 0;
      for (int k = 0; k < movies.size(); k++) {
        if (movies.get(k).getRevenue() >= highest && !greatest.contains(movies.get(k))) {
          highest = movies.get(k).getRevenue();
          temp = k;
        }
      }
      greatest.add(movies.get(temp));
    }
    for (int j = 0; j < 50; j++) {
      System.out.println(j + 1 + ". " + greatest.get(j).getTitle() + " : " + greatest.get(j).getRevenue());
    }
    if (greatest.size() > 0) {
      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = greatest.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }
}