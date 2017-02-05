package edu.knoldus

import java.util.Date

import twitter4j.Query

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


case class MyTweets(tweetText: String, userName: String, date: Date)

class TwitterOps(confFile: String) {

  val twitterFeeds = new TwitterConnection
  val configFile: String = confFile

  /*
  * this method gets a list of tweets(MyTweets) using a hash tag keywords
  */
  def getFeeds(hashTag: String): Future[List[MyTweets]] = {
    Future {
      try {
        twitterFeeds.getObject(configFile) match {
          case Some(x) =>
            val twitter = x
            val query = new Query(hashTag)
            query.setSince("2017-01-24")
            val list = twitter.search(query)
            val tweets = list.getTweets.asScala.toList
            val allTweets = tweets.map {
              tweet =>
                MyTweets(tweet.getText, tweet.getUser.getScreenName, tweet.getCreatedAt)
            }
            allTweets.sortBy(_.date)

          case _ => List[MyTweets]()
        }
      } catch {
        case ex: Exception => List[MyTweets]()
      }
    }
  }

  /*
  * this method counts number of feeds
  * on basis of a hash tag
  * */
  def countFeeds(hashTag: String): Future[Int] = {
    Future {
      try {
        twitterFeeds.getObject(configFile) match {
          case Some(x) =>
            val twitter = x
            val query = new Query(hashTag)
            query.setSince("2017-01-24")
            val list = twitter.search(query)
            val tweets = list.getTweets.asScala.toList
            tweets.size

          case _ => 0
        }
      } catch {
        case ex: Exception => 0
      }
    }
  }

  /*
  * this method returns a number with represents avergae tweets
  * related to a hash tag per day
  * */
  def avgTweetsPDay(hashTag: String): Future[Int] = {
    Future {
      try {
        twitterFeeds.getObject(configFile) match {
          case Some(x) =>
            val twitter = x
            val query = new Query(hashTag)
            query.setSince("2017-01-24")
            val list = twitter.search(query)
            val tweetsList = list.getTweets.asScala.toList
            val feedsMapByDate = tweetsList.groupBy(_.getCreatedAt)
            tweetsList.size / feedsMapByDate.size

          case _ => 0
        }
      }
      catch {
        case ex: Exception => 0
      }
    }
  }

  /*
  * this method returns a touple
  * first member tells average retweets per tweet
  * second member tell average favourites per tweet
  * */
  def avgRetweetsAndFavs(hashTag: String): Future[(Int, Int)] = {
    Future {
      try {
        twitterFeeds.getObject(configFile) match {
          case Some(x) =>
            val twitter = x
            val query = new Query(hashTag)
            query.setSince("2017-01-24")
            val list = twitter.search(query)
            val tweetsList = list.getTweets.asScala.toList
            val feedsListSize = tweetsList.size
            val retweetsList = tweetsList.map(_.getRetweetCount)
            val favList = tweetsList.map(_.getFavoriteCount)
            (retweetsList.sum / feedsListSize, favList.sum / feedsListSize)

          case _ => (0, 0)
        }
      }
      catch {
        case ex: Exception => (0, 0)
      }
    }
  }

}
