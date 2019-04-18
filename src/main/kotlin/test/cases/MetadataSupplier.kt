package test.cases

interface MetadataSupplier {
    fun getId(): Int
    fun getName(): String
    fun getPreParameters(): List<String>
    fun getPostParameters(): List<String>
}