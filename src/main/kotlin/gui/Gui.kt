package gui

interface Gui {
    fun display(message: String)
    fun displayMultiple(messages: List<String>)
    fun subscribe(guiObserver: GuiObserver)
    fun unsubscribe(guiObserver: GuiObserver)
    fun askUserForInput(question: String)
}
