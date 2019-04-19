package gui

class GuiLess(private val commandLineReader: ScannerAbstraction) : Gui {
    private val observers: ArrayList<GuiObserver> = ArrayList()

    override fun displayMultiple(messages: List<String>) {
        val messageIterator = messages.iterator()
        messageIterator.forEach { message -> println(message) }
    }

    override fun display(message: String) {
        println(message)
    }

    override fun subscribe(guiObserver: GuiObserver) {
        observers.add(guiObserver)
    }

    override fun unsubscribe(guiObserver: GuiObserver) {
        observers.remove(guiObserver)
    }

    override fun getUserInput(question: String) {
        print("$question: ")
        val userInput = commandLineReader.next()
        observers.iterator().forEach { guiObserver -> guiObserver.handleUserInput(userInput) }
    }
}