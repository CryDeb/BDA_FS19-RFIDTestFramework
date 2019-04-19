package gui

interface GuiObserver {
    fun handleUserInput(userMessage: String)
}