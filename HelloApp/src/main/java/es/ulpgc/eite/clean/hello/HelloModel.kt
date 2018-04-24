package es.ulpgc.eite.clean.hello

import android.os.Handler
import android.util.Log

import es.ulpgc.eite.clean.mvp.GenericModel


/**
 * Layer MODEL from Model View Presenter (MVP) pattern.
 * Responsible to deal with all business logic and data in general.
 */

class HelloModel : GenericModel<Hello.ModelToPresenter>(), Hello.PresenterToModel {

    private var AndroidServicesEnabled: Boolean = false


    /**
     * Method that recovers a reference to the PRESENTER
     * You must ALWAYS call [super.onCreate] here
     *
     * @param presenter Presenter interface
     */
    override fun onCreate(presenter: Hello.ModelToPresenter) {
        super.onCreate(presenter)
        Log.d(TAG, "calling onCreate()")

        AndroidServicesEnabled = true

        helloMessage = "Hello World!"
        sayHelloButtonLabel = "Say Hello"
        goToByeButtonLabel = "Go To Bye"

        resetHelloGetMessageTask()
    }

    /**
     * Called by layer PRESENTER when VIEW pass for a reconstruction/destruction.
     * Usefull for kill/stop activities that could be running on the background Threads
     *
     * @param isChangingConfiguration Informs that a change is occurring on the configuration
     */
    override fun onDestroy(isChangingConfiguration: Boolean) {

    }

    // Presenter To Model ////////////////////////////////////

    override lateinit var sayHelloButtonLabel: String
        private set

    override lateinit var goToByeButtonLabel: String
        private set

    override lateinit var helloMessage: String
        private set


    override fun resetHelloGetMessageTask() {
        isTaskFinished = false
    }

    override fun startHelloGetMessageTask() {
        if (isTaskFinished) {
            presenter.onHelloGetMessageTaskFinished(helloMessage)
        } else {
            startDelayedTask()
        }
    }


    /////////////////////////////////////////////////////////

    private fun delayedTask() {
        presenter.onHelloGetMessageTaskFinished(helloMessage)
        isTaskFinished = true
    }

    private fun startDelayedTask() {
        if (!AndroidServicesEnabled) {
            delayedTask()
        } else {
            Log.d(TAG, "calling startDelayedTask()")

            // Mock Hello: A handler to delay the answer
            Handler().postDelayed({ delayedTask() }, 5000)
        }
    }

    // These methods are only invoked from our JUnit tests


    var isTaskFinished: Boolean = false
        private set


    fun disableAndroidServices() {
        AndroidServicesEnabled = false
    }
}
