package es.ulpgc.eite.clean.bye

import es.ulpgc.eite.clean.mvp.ContextView
import es.ulpgc.eite.clean.mvp.Model
import es.ulpgc.eite.clean.mvp.Presenter

/**
 * Created by Luis on 12/11/16.
 */

interface Bye {

    // State /////////////////////////////////////////////

    interface HelloToBye {
        //fun setHelloButtonClicked(clicked: Boolean)
        //fun setHelloGetTaskRunning(running: Boolean)
        //fun setHelloMsgText(text: String)
        var isHelloButtonClicked: Boolean
        var isHelloGetTaskRunning: Boolean
        var helloMessage: String?
        fun onByeScreenStarted()
    }

    interface ByeToHello {
        val isByeButtonClicked: Boolean
        val isByeGetTaskRunning: Boolean
        val byeMessage: String
        fun destroyView()
    }


    // Screen /////////////////////////////////////////////


    interface ViewToPresenter : Presenter<PresenterToView> {
        fun onSayByeButtonClicked()
        fun onBackToHelloButtonClicked()
    }

    interface PresenterToView : ContextView {
        fun hideScreenToolbar()
        fun finishScreen()
        fun setSayByeButton(label: String)
        fun setGoToHelloButton(label: String)
        fun setByeMessage(text: String)
        fun hideByeMessage()
        fun showByeMessage()
        fun hideProgressIndicator()
        fun showProgressIndicator()
    }

    interface PresenterToModel : Model<ModelToPresenter> {
        val sayByeButtonLabel: String
        val backToHelloButtonLabel: String
        val byeMessage: String
        fun startByeGetMessageTask()
        fun resetByeGetMessageTask()
    }

    interface ModelToPresenter {
        fun onByeGetMessageTaskFinished(text: String)
    }


}
