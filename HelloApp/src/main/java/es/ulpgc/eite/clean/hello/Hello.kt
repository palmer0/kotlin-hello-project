package es.ulpgc.eite.clean.hello

import android.content.Context

import es.ulpgc.eite.clean.mvp.ContextView
import es.ulpgc.eite.clean.mvp.Model
import es.ulpgc.eite.clean.mvp.Presenter

/**
 * Created by Luis on 12/11/16.
 */

interface Hello {

    // State /////////////////////////////////////////////

    interface ByeToHello {
        fun onHelloScreenResumed()
        //fun setByeButtonClicked(clicked: Boolean)
        //fun setByeMsgText(text: String)
        //fun setByeGetTaskRunning(running: Boolean)
        var isByeButtonClicked: Boolean
        var isByeGetTaskRunning: Boolean
        var byeMessage: String?
    }

    interface HelloToBye {
        val isHelloButtonClicked: Boolean
        val isHelloGetTaskRunning: Boolean
        val helloMessage: String
        val managedContext: Context?
    }

    // Screen /////////////////////////////////////////////


    interface ViewToPresenter : Presenter<PresenterToView> {
        fun onGoToByeButtonClicked()
        fun onSayHelloButtonClicked()
    }

    interface PresenterToView : ContextView {
        fun hideHelloMessage()
        fun hideProgressIndicator()
        fun hideScreenToolbar()
        fun showHelloMessage()
        fun showProgressIndicator()
        fun setGoToByeButton(label: String)
        fun setHelloMessage(text: String)
        fun setSayHelloButton(label: String)
    }

    interface PresenterToModel : Model<ModelToPresenter> {
        val goToByeButtonLabel: String
        val helloMessage: String
        val sayHelloButtonLabel: String

        fun resetHelloGetMessageTask()
        fun startHelloGetMessageTask()
    }

    interface ModelToPresenter {
        fun onHelloGetMessageTaskFinished(text: String)
    }

}
