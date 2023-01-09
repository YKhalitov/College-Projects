"""
Name: movies_main.py
Author: Yaroslav Khalitov
Class: Computer Science for CS AP students and CS transfer students
       CSCI.140.01/242.01

Description:
    This program reads a file full of information of movies from the IMDB website.
    Includes 6 queries for accessing information.
    1. LOOKUP {tconst}: Look up a movie and rating by its unique identifier.
    2. CONTAINS {titleType} {words}: Find all movies of a certain type whose titles
        contain the sequence of words.
    3. YEAR AND GENRE {titleType} {year} {genre}: Find all movies of a certain
        type from a particular year that match a genre.
    4. RUNTIME {titleType} {min-minutes} {max-minutes}: Find all movies of a
        certain type that are within a range of runtimes.
    5. MOST VOTES {titleType} {num}: Find the given number of movies of a certain
        type with the most votes.
    6. TOP {titleType} {num} {start-year} {end-year}: Find the number of movies
        of a certain type by a range of years (inclusive) that are the highest rated and have at
        least 1000 votes.

    Queries should not take more than a few seconds to complete after all the IMDB data is loaded.

"""


import sys
import operator
from timeit import default_timer as timer
from dataclasses import dataclass

# dataclass to store movie information
@dataclass (frozen=True)
class Movie:
    tconst: str
    titleType: str
    primaryTitle: str
    isAdult: int
    startYear: int
    runtimeMinutes: int
    genres: str

# dataclass to store rating information
@dataclass (frozen=True)
class Rating:
    tconst: str
    averageRating: float
    numVotes: int

def lookup(identifier, movies, ratings):
    """
    Look up a specific movie in the IMDB dataset using an identifier
    and display all it's information
    :param identifier:
        The  identifier tied to the specific movie information.
    :param movies:
        The dictionary that has the identifier paired with the dataclass object Movie
        used to store data about a movie.
    :param ratings:
        The dictionary that has the identifier paired with the dataclass object Rating
        used to store rating data about a movie.
    :return None:
    """
    # initialize variables
    start = timer()

    # see if movie exists and display information
    if identifier in movies and identifier in ratings:
        result_movies = movies[identifier]
        result_ratings = ratings[identifier]
        print("\t" + "Identifier: " + result_movies.tconst + ", Title: " + result_movies.primaryTitle + ", Type: " + result_movies.titleType +
              ", Year:", result_movies.startYear + ", Runtime: " + str(result_movies.runtimeMinutes) + ", Genres: " + result_movies.genres, end="")
        print("\t" + "RATING: Identifier: " + result_ratings.tconst + ", Rating:", result_ratings.averageRating + ", Votes:", result_ratings.numVotes)
    else:
        print("\tMovie not found!")
        print("\tRating not found!")
    elapsed = timer() - start
    print("elapsed time (s):", elapsed)

def contains(mediaType, words, movies):
    """
    Look up and display the information of all occurrences
    where the parameter "words" appears in the title of the movie.
    :param mediaType:
        The specific type of media we are looking for.
    :param words:
        A string that's used to search through the data
        and identify all movies with this string in the title.
    :param movies:
        The dictionary that has the identifier paired with the dataclass object Movie
        used to store data about a movie.
    :return None:
    """
    # initialize variables
    start = timer()
    movieNotFound = True

    # parse movies into list and display information
    for movie in movies:
        movie = movies[movie]
        if mediaType == movie.titleType and words in movie.primaryTitle:
            movieNotFound = False
            print("\t" + "Identifier: " + movie.tconst + ", Title: " + movie.primaryTitle + ", Type: " + movie.titleType +
                ", Year: " + str(movie.startYear) + ", Runtime: "+
                str(movie.runtimeMinutes) + ", Genres: " + movie.genres, end="")
    if movieNotFound:
        print("\tNo Match Found!")
    elapsed = timer() - start
    print("elapsed time (s):", elapsed)

def year_and_genre(mediaType, year, genre, movies):
    """
    Look up and display the information of all occurrences
    where the parameters mediaType, year and genre all match the
    information from a movie.
    :param mediaType:
        The specific type of media we are looking for.
    :param year:
        The release year we are looking for.
    :param genre:
        The genre of movie we are looking for.
    :param movies:
        The dictionary that has the identifier paired with the dataclass object Movie
        used to store data about a movie.
    :return None:
    """
    # initialize variables
    start = timer()
    movieNotFound = True
    movieList = []

    # parse movies into list
    for movie in movies:
        movie = movies[movie]
        if mediaType == movie.titleType and year == movie.startYear and genre in movie.genres:
            movieNotFound = False
            movieList.append(movie)

    # sort movies alphabetically
    movieList.sort(key=operator.attrgetter("primaryTitle"))

    # display the information for each movie
    for movie in movieList:
        print("\t" + "Identifier: " + movie.tconst + ", Title: " + movie.primaryTitle + ", Type: " + movie.titleType +
            ", Year:", movie.startYear + ", Runtime: " +
            str(movie.runtimeMinutes) + ", Genres: " + movie.genres, end="")
    if movieNotFound:
        print("\tNo Match Found!")
    elapsed = timer() - start
    print("elapsed time (s):", elapsed)

def runtime(mediaType, lower, upper, movies):
    """
    Look up and display the information of all occurrences
    where the parameter mediaType, and all values between
    parameters lower and upper match the runtime of the movie.
    :param mediaType:
        The specific type of media we are looking for.
    :param lower:
        The lower (included) bound value of runtime we are searching for.
    :param upper:
        The upper (included) bound value of runtime we are searching for.
    :param movies:
        The dictionary that has the identifier paired with the dataclass object Movie
        used to store data about a movie.
    :return None:
    """

    # initialize variables
    lower = int(lower)
    upper = int(upper)
    start = timer()
    movieNotFound = True
    movieList = []

    # parse movies into list
    for movie in movies:
        movie = movies[movie]
        if lower <= int(movie.runtimeMinutes) <= upper and mediaType == movie.titleType:
            movieNotFound = False
            movieList.append(movie)

    # sort movies by runtime than alphabetically
    movieList.sort(key=operator.attrgetter("primaryTitle"))
    movieList.sort(key=operator.attrgetter("runtimeMinutes"), reverse=True)

    # display the information for each movie
    for movie in movieList:
        print("\t" + "Identifier: " + movie.tconst + ", Title: " + movie.primaryTitle + ", Type: " + movie.titleType +
            ", Year: " + str(movie.startYear) + ", Runtime: " +
            str(movie.runtimeMinutes) + ", Genres: " + movie.genres, end="")
    if movieNotFound:
        print("\tNo Match Found!")
    elapsed = timer() - start
    print("elapsed time (s):", elapsed)

def most_votes(mediaType, topPosNum, movies, ratings):
    """
    Look up and display the information of a certain amount of movies
    in a certain media type based on it's number of votes.
    :param mediaType:
        The specific type of media we are looking for.
    :param topPosNum:
        The amount of movies to display
    :param movies:
        The dictionary that has the identifier paired with the dataclass object Movie
        used to store data about a movie.
    :param ratings:
        The dictionary that has the identifier paired with the dataclass object Rating
        used to store rating data about a movie.
    :return None:
    """
    # initialze variables
    topPosNum = int(topPosNum)
    movieNotFound = True
    start = timer()
    ratingList = []

    # parse movies into list
    for rating in ratings:
        rating = ratings[rating]
        if mediaType == (movies[rating.tconst]).titleType:
            movieNotFound = False
            ratingList.append(rating)
    # sort movies by number votes than alphabetically
    if not movieNotFound:
        ratingList.sort(key=lambda rating: movies[rating.tconst].primaryTitle)
        ratingList.sort(key=operator.attrgetter("numVotes"), reverse=True)
        # display the information for each movie
        for i in range(topPosNum):
            if (i < len(ratingList)):
                print("\t" + str(i+1) + ". VOTES: " + str(ratingList[i].numVotes) +
                      ", Identifier: " + movies[ratingList[i].tconst].tconst + ", Title: " + movies[
                          ratingList[i].tconst].primaryTitle + ", Type: " + movies[ratingList[i].tconst].titleType +
                      ", Year:", movies[ratingList[i].tconst].startYear + ", Runtime:",
                      movies[ratingList[i].tconst].runtimeMinutes, ", Genres: " + movies[ratingList[i].tconst].genres, end="")
    else:
        print("\tNo Match Found!")
    elapsed = timer() - start
    print("elapsed time (s):", elapsed)

def top(mediaType, topPosNum, lowerYear, upperYear, movies, ratings):
    """
    Look up and display the information of a certain amount of movies
    from each year based on their average rating, followed by number of votes.
    Must be the specified media type.
    :param mediaType:
        The specific type of media we are looking for.
    :param topPosNum:
        The amount of movies to display
    :param lowerYear:
         The lower (included) bound value of movie year we are searching for.
    :param upperYear:
        The upper (included) bound value of movie year we are searching for.
    :param movies:
        The dictionary that has the identifier paired with the dataclass object Movie
        used to store data about a movie.
    :param ratings:
        The dictionary that has the identifier paired with the dataclass object Rating
        used to store rating data about a movie.
    :return None:
    """
    # initialize variables
    topPosNum = int(topPosNum)
    lowerYear = int(lowerYear)
    upperYear = int(upperYear)
    movieNotFound = True
    start = timer()
    ratingList = []
    baseYear = upperYear - lowerYear
    yearCounter = lowerYear
    numberCounter = 0

    # create a nested list
    for i in range(baseYear + 1):
        ratingList.append([])

    # parse ratings into list
    for rating in ratings:
        rating = ratings[rating]
        positionInList = int(movies[rating.tconst].startYear) - lowerYear
        if (mediaType == movies[rating.tconst].titleType and rating.numVotes >= 1000 and 0 <= positionInList <= baseYear):
            movieNotFound = False
            ratingList[positionInList].append(rating)
    if not movieNotFound:
        # for each year sort the movies by average rating, number of votes then alphabetically
        for i in range(baseYear + 1):
            ratingList[i].sort(key=lambda rating: movies[rating.tconst].primaryTitle)
            ratingList[i].sort(key=operator.attrgetter("numVotes"), reverse=True)
            ratingList[i].sort(key=operator.attrgetter("averageRating"), reverse=True)

        # display the information for each movie
        for i in range(len(ratingList)):
            print("\tYEAR:", yearCounter)
            yearCounter += 1
            numberCounter = 1
            for j in range(topPosNum):
                if (j < len(ratingList[i])):
                    print("\t\t" + str(numberCounter) + ". RATING: " + str(ratingList[i][j].averageRating) + ", VOTES: " + str(ratingList[i][j].numVotes) +
                          ", Identifier: " + movies[ratingList[i][j].tconst].tconst + ", Title: " + movies[
                              ratingList[i][j].tconst].primaryTitle + ", Type: " + movies[ratingList[i][j].tconst].titleType +
                          ", Year:", movies[ratingList[i][j].tconst].startYear + ", Runtime:",
                          str(movies[ratingList[i][j].tconst].runtimeMinutes) + ", Genres: " + movies[ratingList[i][j].tconst].genres,
                          end="")
                    numberCounter += 1
    else:
        print("\tNo Match Found!")
    elapsed = timer() - start
    print("elapsed time (s):", elapsed)

def read_file():
    """
    Read the movie and rating information from the IMDB
    website into two dictionaries. Both dictionaries keys are the movies
    specific identifier. The values are two dataclasses used to store information.
    :return movies_dict:
        The dictionary storing each movie's general information.
    :return ratings_dict:
        The dictionary storing each movie's rating/voting information.
    """
    # read movies into dictionary
    print("reading data/title.basics.tsv into dict...")
    start = timer()
    with open("data/title.basics.tsv", encoding="utf-8") as f:
        movies_dict = {}
        next(f)
        for line in f:
            fields = line.split("\t")
            if fields[4] != "1":
                if fields[5] =="\\N":
                    fields[5] = 0
                if fields[7] =="\\N":
                    fields[7] = 0
                if fields[8] == "\\N":
                    fields[8] = "None"
                movies_dict[fields[0]] = Movie(
                    tconst=fields[0],
                    titleType=fields[1],
                    primaryTitle=fields[2],
                    isAdult=fields[4],
                    startYear=fields[5],
                    runtimeMinutes=fields[7],
                    genres=fields[8])
    elapsed = timer() - start
    print("elapsed time (s): ", elapsed)

    # read ratings into dictionary
    print("\nreading data/title.ratings.tsv into dict...")
    start = timer()
    with open("data/title.ratings.tsv", encoding="utf-8") as f:
        ratings_dict = {}
        next(f)
        for line in f:
            fields = line.split("\t")
            if fields[0] in movies_dict:
                ratings_dict[fields[0]] = Rating(
                    tconst=fields[0],
                    averageRating=fields[1],
                    numVotes=int(fields[2]))
    elapsed = timer() - start
    print("elapsed time (s):", elapsed)


    return(movies_dict, ratings_dict)


def main():
    """
    Main function reading movie and rating information into dictionaries, then
    performing operations on that information using queries and instructions
    from the system input.
    :return None:
    """
    # read IMDB into two dictionaries
    movies, ratings = read_file()

    # print amount of movies and ratings
    print("\nTotal movies:", len(movies))
    print("Total ratings:", len(ratings))

    # This loop automatically terminates when EOF is reached from the file,
    # or the user enters ^D to terminate standard input.
    # Determines which query with what parameters to run.
    for line in sys.stdin:
        line = line.strip()
        fields = line.split(" ")
        if fields[0] == "LOOKUP":
            print("\nprocessing:", fields[0], fields[1])
            lookup(fields[1], movies, ratings)
        elif fields[0] == "CONTAINS":
            for i in range(3, len(fields), 1):
                fields[2] = fields[2] + " " + fields[i]
            print("\nprocessing:", fields[0], fields[1], fields[2])
            contains(fields[1], fields[2], movies)
        elif fields[0] == "YEAR_AND_GENRE":
            print("\nprocessing:", fields[0], fields[1], fields[2], fields[3])
            year_and_genre(fields[1], fields[2], fields[3], movies)
        elif fields[0] == "RUNTIME":
            print("\nprocessing:", fields[0], fields[1], fields[2], fields[3])
            runtime(fields[1], fields[2], fields[3], movies)
        elif fields[0] == "MOST_VOTES":
            print("\nprocessing:", fields[0], fields[1], fields[2])
            most_votes(fields[1], fields[2], movies, ratings)
        elif fields[0] == "TOP":
            print("\nprocessing:", fields[0], fields[1], fields[2], fields[3], fields[4])
            top(fields[1], fields[2], fields[3], fields[4], movies, ratings)

if __name__ == '__main__':
    main()