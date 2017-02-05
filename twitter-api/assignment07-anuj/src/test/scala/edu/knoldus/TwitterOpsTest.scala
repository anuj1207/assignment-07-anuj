package edu.knoldus

import org.scalatest.FlatSpec

import scala.concurrent.Await
import scala.concurrent.duration._


class TwitterOpsTest extends FlatSpec{

  val twitterOps1 = new TwitterOps("config")
  val twitterOps2: TwitterOps = new TwitterOps("")

  it should "return empty list for getTweets for empty input" in{
    assert(Await.result(twitterOps1.getFeeds(""),10.second).isEmpty)
  }

  it should "return non empty list for getTweets for empty input" in{
    assert(Await.result(twitterOps1.getFeeds("#scala"),10.second).nonEmpty)
  }

  it should "return non empty list for getTweets for wrong config file" in{
    assert(Await.result(twitterOps2.getFeeds("#scala"),10.second).isEmpty)
  }

  it should "return zero for number of tweets for empty input" in{
    assert(Await.result(twitterOps1.countFeeds(""),10.second) == 0)
  }

  it should "return greater than zero for count of tweets" in{
    assert(Await.result(twitterOps1.countFeeds("#scala"),10.second) > 0)
  }

  it should "return zero for number of tweets for wrong config file" in{
    assert(Await.result(twitterOps2.countFeeds(""),10.second) == 0)
  }

  it should "return zero for average tweets" in{
    assert(Await.result(twitterOps1.avgTweetsPDay(""),10.second) == 0)
  }

  it should "return greater than zero for avg tweets" in{
    assert(Await.result(twitterOps1.avgTweetsPDay("#scala"),10.second) > 0)
  }

  it should "return zero for average tweets for wrong config file" in{
    assert(Await.result(twitterOps2.avgTweetsPDay(""),10.second) == 0)
  }

  it should "return zero for avg retweets and favourites" in{
    assert(Await.result(twitterOps1.avgRetweetsAndFavs(""),10.second) == (0,0))
  }

  it should "return non zero for avg retweets and favourites" in{
    assert(Await.result(twitterOps1.avgRetweetsAndFavs("#scala"),10.second) != (0,0))
  }

  it should "return zero for avg retweets and favourites for wrong config file" in{
    assert(Await.result(twitterOps2.avgRetweetsAndFavs(""),10.second) == (0,0))
  }

}
