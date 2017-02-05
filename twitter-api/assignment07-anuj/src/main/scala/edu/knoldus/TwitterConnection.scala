package edu.knoldus

import com.typesafe.config.{Config, ConfigFactory}
import twitter4j.conf.ConfigurationBuilder
import twitter4j.{Twitter, TwitterFactory}

class TwitterConnection {

  /*
  * this method returns an object of Twitter class
  * in Some if no exception occurred
  * else None
  * */
  def getObject(configFile: String): Option[Twitter] = {

    try {

      val conf: Config = ConfigFactory.load(configFile)
      val consumerKey = conf.getString("consumerKey")
      val consumerSecretKey = conf.getString("consumerSecretKey")
      val accessToken = conf.getString("accessToken")
      val accessTokenSecret = conf.getString("accessTokenSecret")
      val configurationBuilder = new ConfigurationBuilder()

      configurationBuilder.setDebugEnabled(true)
        .setOAuthConsumerKey(consumerKey)
        .setOAuthConsumerSecret(consumerSecretKey)
        .setOAuthAccessToken(accessToken)
        .setOAuthAccessTokenSecret(accessTokenSecret)
      val twitter = new TwitterFactory(configurationBuilder.build()).getInstance()
      Some(twitter)

    } catch {
      case ex: Exception => None 
    }
  }

}
