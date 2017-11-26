package com.alexitc.coinalerts.commons

import com.alexitc.coinalerts.data.{AlertBlockingDataHandler, UserBlockingDataHandler}
import com.alexitc.coinalerts.models._

object DataHelper {

  def createVerifiedUser(
      email: UserEmail = RandomDataGenerator.email,
      password: UserPassword = RandomDataGenerator.password)(
      implicit userDataHandler: UserBlockingDataHandler): User = {

    val user = createUnverifiedUser(email, password)
    val token = userDataHandler.createVerificationToken(user.id).get
    userDataHandler.verifyEmail(token).get
  }

  def createUnverifiedUser(
      email: UserEmail = RandomDataGenerator.email,
      password: UserPassword = RandomDataGenerator.password)(
      implicit userDataHandler: UserBlockingDataHandler): User = {

    userDataHandler.create(email, UserHiddenPassword.fromPassword(password)).get
  }

  def createAlert(
      userId: UserId,
      createAlertModel: CreateAlertModel = RandomDataGenerator.createDefaultAlertModel())(
      implicit alertDataHandler: AlertBlockingDataHandler) = {

    alertDataHandler.create(createAlertModel, userId)
  }
}