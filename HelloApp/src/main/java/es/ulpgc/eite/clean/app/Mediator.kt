package es.ulpgc.eite.clean.app

import es.ulpgc.eite.clean.bye.Bye
import es.ulpgc.eite.clean.hello.Hello

/**
 * Created by imac on 23/1/18.
 */

interface Mediator {

    interface Lifecycle {

        fun resumingHelloScreen(presenter: Hello.ByeToHello)
        fun startingByeScreen(presenter: Bye.HelloToBye)
    }

    interface Navigation {

        fun backToHelloScreen(presenter: Bye.ByeToHello)
        fun goToByeScreen(presenter: Hello.HelloToBye)
    }

}