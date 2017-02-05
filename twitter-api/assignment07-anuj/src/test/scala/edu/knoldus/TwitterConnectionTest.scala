package edu.knoldus

import org.scalatest.FlatSpec


class TwitterConnectionTest extends FlatSpec{

  val twitterFeeds = new TwitterConnection

  it should "return noting if input is empty" in {
    assert(twitterFeeds.getObject("").isEmpty)
  }

  it should "return non empty list for correct input" in{
    assert(twitterFeeds.getObject("config").nonEmpty)
  }

}
